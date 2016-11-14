package com.llmj.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.llmj.common.utils.HttpRequestUtil;
import com.llmj.common.utils.MD5Util;
import com.llmj.common.utils.UUIDUtil;
import com.llmj.util.constant.Config;

public class PaySignUtil {
	public static Logger LOGGER = LoggerFactory.getLogger(PaySignUtil.class);

	/**
	 * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 * 
	 * @param params
	 *            需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	private static String getSignString(Map<String, String> params) {
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		StringBuffer pre = new StringBuffer();
		String key = null;
		String value = null;
		for (int i = 0; i < keys.size(); i++) {
			key = keys.get(i);
			value = params.get(key);
			if (value == null || "".equals(value.trim()))
				continue;
			pre = pre.append(key).append("=").append(value).append("&");
		}
		pre.append("merKey=" + Config.getStringValue("merKey"));
		String preStr = pre.toString();
		return MD5Util.md5(preStr);
	}

	public static Map<String, String> getSign(Map<String, String> params) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("merId", Config.getStringValue("merId"));
		map.put("nonceStr", UUIDUtil.getUUID32Str());
		map.putAll(params);
		map.put("sign", getSignString(map));
		return map;
	}

	/**
	 * 定时任务验签
	 * 
	 * @author: GaoMJ
	 * @date: 2015年10月21日
	 * @param params
	 * @param sign
	 * @return
	 */
	public static boolean checkManageSysSign(Map<String, String> params,
	        String sign) {
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		StringBuffer pre = new StringBuffer();
		String key = null;
		String value = null;
		for (int i = 0; i < keys.size(); i++) {
			key = keys.get(i);
			value = params.get(key);
			if (value == null || "".equals(value.trim()))
				continue;
			pre = pre.append(key).append("=").append(value).append("&");
		}
		pre.append(
		        "manageSysMerKey=" + Config.getStringValue("manageSysMerKey"));
		String preStr = pre.toString();
		return MD5Util.md5(preStr).equals(sign);
	}

	public static boolean checkSign(Map<String, String> params, String sign) {
		String memId = Config.getStringValue("merId");// 商户号固定
		if (!memId.equalsIgnoreCase(params.get("merId"))) {
			return false;
		}
		if (StringUtils.isBlank(sign)) {
			return false;
		}
		if (sign.equalsIgnoreCase(getSignString(params))) {
			return true;
		}
		return false;
	}
	
	public static boolean checkSignNoMemId(Map<String, String> params, String sign) {
		if (StringUtils.isBlank(sign)) {
			return false;
		}
		if (sign.equalsIgnoreCase(getSignString(params))) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("merOrderId", "41201509212008532344");
		Map<String, String> map = getSign(param);
		String responseContent = HttpRequestUtil.sendHttpPostRequest(
		        "http://qa-merchant.api.xebest.com/recharge/queryOrder.shtml",
		        map);
		System.out.println(responseContent);
	}
}
