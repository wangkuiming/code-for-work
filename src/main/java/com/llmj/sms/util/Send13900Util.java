package com.llmj.sms.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.llmj.sms.zucp.util.ZUCPMessageUtil;
import com.llmj.sms.zucp.util.ZUCPSendModel;
import com.llmj.util.constant.Config;

/**
 * 
 * @author liyong 13900短信通道
 */
public class Send13900Util {
	public static Logger logger = LoggerFactory.getLogger(Send13900Util.class);

	public static void main(String[] args) {
		System.out.println(sendShortMsg("13718920174", "冷链马甲开通啦，请你及时维护自己的账号"));
	}

	/**
	 * 短信通道调用，只用判断返回值flag 0000为成功，其余均为失败
	 * 
	 * @param mobile
	 * @param content
	 * @param smsNumber
	 * @param smsTypeName
	 * @return
	 */
	public static Map<String, Object> sendShortMsg(String mobile,
	        String content) {
		logger.info("发送短信,mobile:{},content:{}", mobile, content);
		Map<String, Object> result = new HashMap<String, Object>();

		boolean sendSms = Boolean.valueOf(Config.getStringValue("send_sms"))
		        .booleanValue();
		if (sendSms) {
			/**
			 * 切换为漫道的通道
			 */
			ZUCPSendModel model = new ZUCPSendModel();
			model.setContent(content);
			model.setMobile(mobile);
			model.setRrid(String.valueOf(System.currentTimeMillis()));
			result = ZUCPMessageUtil.sendSuccess(ZUCPMessageUtil.send(model),
			        model);
			String flag = result.get("flag").toString();
			String message = result.get("message").toString();

			result.clear();
			result.put("flag", flag.equals("true") ? "0000" : "1111");
			result.put("message", message);
		} else {
			// 开关关闭时永远成功
			result.put("message", content);
			result.put("flag", "0000");
		}

		logger.info("发送短信结果,mobile:{},content:{},result:{}", mobile, content,
		        result);
		return result;
	}
}