package com.llmj.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.llmj.common.redis.routing.RoutingXeRedis;
import com.llmj.common.utils.MD5Util;
import com.llmj.util.common.RedisHelper;
import com.llmj.util.constant.Config;
import com.llmj.util.constant.Contents;
import com.llmj.util.constant.Contents.llmjMsg;

public class LlmjAppInterceptor implements HandlerInterceptor {

	public static Logger logger = LoggerFactory
	        .getLogger(LlmjAppInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request,
	        HttpServletResponse response, Object handler) throws Exception {
		String path = request.getServletPath();
		// 排除放开的URL列表
		Set<String> excludePaths = new HashSet<String>();
		String excludePath = Config.getStringValue("excludePath").trim();
		if (StringUtils.isNotBlank(excludePath)) {
			excludePaths.addAll(Arrays.asList(excludePath.split(";")));
		}
		if (excludePaths.contains(path)) {
			return true;
		}

		String uuidParam = request.getParameter("uuid");
		String versionParam = request.getParameter("version");
		String clientTypeParam = request.getParameter("client_type");
		String dataParam = request.getParameter("data");
		String signParam = request.getParameter("sign");
		String userId = request.getParameter("userId");

		// 校验是否是manager请求
		String manageSys = request.getParameter("manageSys");
		if (StringUtils.isNotBlank(manageSys)) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("uuid", uuidParam);
			params.put("version", versionParam);
			params.put("client_type", clientTypeParam);
			params.put("manageSys", manageSys);
			params.put("data", dataParam);
			if (PaySignUtil.checkManageSysSign(params, signParam)) {
				return true;
			}
		}

		// 客户端的uuid
		if (StringUtils.isBlank(uuidParam)) {
			sendAjaxResponseError(response, llmjMsg.L_0002.getCode(),
			        "参数错误,uuid缺失");
			return false;
		}

		// version
		if (StringUtils.isBlank(versionParam)) {
			sendAjaxResponseError(response, llmjMsg.L_0002.getCode(),
			        "参数错误,version缺失");
			return false;
		}

		// 客户端类型1：PC 2:IOS 3:Android 4 :微信
		if (StringUtils.isBlank(clientTypeParam)) {
			sendAjaxResponseError(response, llmjMsg.L_0002.getCode(),
			        "参数错误,client_type缺失");
			return false;
		}

		if (StringUtils.isBlank(userId)) {
			sendAjaxResponseError(response, llmjMsg.L_0002.getCode(),
			        "参数错误,userId缺失");
			return false;
		} else {
			// 判断用户是否登录
			RoutingXeRedis redis = new RoutingXeRedis("xeRedis");
			if (StringUtils
			        .isBlank(redis.get(Contents.USER_LOGIN_REDIS + userId))) {
				sendAjaxResponseError(response, llmjMsg.L_0006.getCode(),
				        llmjMsg.L_0006.getMsg());
				return false;
			}

			Set<String> concurrencyControlPaths = new HashSet<String>();
			String concurrencyControlPath = Config
			        .getStringValue("concurrencyControlPath").trim();
			if (StringUtils.isNotBlank(concurrencyControlPath)) {
				concurrencyControlPaths.addAll(
				        Arrays.asList(concurrencyControlPath.split(";")));
			}
			String tempPath = path.substring(0, path.indexOf("."));
			if ("1".equals(RedisHelper.xeRedis
			        .get(userId + "_" + clientTypeParam + "_" + tempPath))) {
				sendAjaxResponseError(response, llmjMsg.L_0001.getCode(),
				        llmjMsg.L_0001.getMsg() + ",操作过于频繁请稍后再试！");
				return false;
			}
			if (concurrencyControlPaths.contains(tempPath)) {
				RedisHelper.xeRedis.setex(
				        userId + "_" + clientTypeParam + "_" + tempPath, 60,
				        "1");
			}
		}

		if (StringUtils.isBlank(signParam)) {
			sendAjaxResponseError(response, llmjMsg.L_0002.getCode(),
			        "参数错误,sign缺失");
			return false;
		} else {
			// 验证签名
			String localSign = MD5Util
			        .md5(uuidParam + Config.getStringValue("encryption")
			                + dataParam + clientTypeParam);
			if (!localSign.equals(signParam)) {
				sendAjaxResponseError(response, llmjMsg.L_0001.getCode(),
				        llmjMsg.L_0001.getMsg() + ",sign签名错误");
				return false;
			}
		}
		return true;
	}

	public void sendAjaxResponseError(HttpServletResponse res, String code,
	        String msg) {
		JSONObject object = new JSONObject();
		object.put(Contents.CODE, code);
		object.put(Contents.MSG, msg);
		PrintWriter out = null;
		try {
			if (res != null) {
				res.setCharacterEncoding("UTF-8");
				res.setContentType("application/json;charset=utf-8");
				out = res.getWriter();
				out.print(object);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}

	public void postHandle(HttpServletRequest req, HttpServletResponse res,
	        Object arg2, ModelAndView arg3) throws Exception {
		logger.info("==============执行顺序: 2、postHandle================");
	}

	/**
	 * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等
	 * 
	 * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
	 */
	public void afterCompletion(HttpServletRequest req, HttpServletResponse res,
	        Object arg2, Exception arg3) throws Exception {
		String path = req.getServletPath();
		String tempPath = path.substring(0, path.indexOf("."));
		String clientTypeParam = req.getParameter("client_type");
		String userId = req.getParameter("userId");
		RedisHelper.xeRedis
		        .del(userId + "_" + clientTypeParam + "_" + tempPath);
	}
}
