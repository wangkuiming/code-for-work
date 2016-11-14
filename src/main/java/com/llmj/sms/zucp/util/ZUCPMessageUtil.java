package com.llmj.sms.zucp.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * <p>Title: ZUCPMessageUtil.java</p>
 * 
 * <p>Description: 
 * 		创世漫道，新短信平台的发送接口统计
 * </p>
 * 
 * <p>Date: Mar 17, 2015</p>
 * 
 * <p>Time: 11:04:36 AM</p>
 * 
 * <p>Copyright: Copyright (c) 2015</p>
 * 
 * <p>Company: xebest.com</p>
 * 
 * @author liyong
 * @version 1.0
 * 
 * <p>============================================</p>
 * <p>Modification History
 * <p>Mender: </p>
 * <p>Date: </p>
 * <p>Reason: </p>
 * <p>============================================</p>
 */
public class ZUCPMessageUtil {
	
	private static Logger logger = LoggerFactory.getLogger(ZUCPMessageUtil.class);
	
	/**
	 * 短信平台第一次使用，注册
	 * @param model
	 * @return
	 */
	public static String register(String url,ZUCPRegisterModel model){
		List<NameValuePair> params = model.getRegisterNameValuePair(model);
//		String url = SendMessageUtil.proInfo.getProperty("REGISTER");
		String result = execute(url, params.toArray(new NameValuePair[params.size()]));
		return result;
	}
	
	/**
	 * 查询短信平台余额
	 * @param model
	 * @return
	 */
	public static String balance(){
		String url = Constants.getAttribute("BALANCE");
		ZUCPBalanceAndReceiveModel model = new ZUCPBalanceAndReceiveModel();
		List<NameValuePair> params = model.getBalanceNameValuePair(model);
//		String url = SendMessageUtil.proInfo.getProperty("BALANCE");
		String result = execute(url, params.toArray(new NameValuePair[params.size()]));
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(result);
		} catch (DocumentException e) {
			logger.error("不得了，格式化漫道短信返回数据失败",e);
			return null;
		}
		 Element root = doc.getRootElement();
	     String newString = root.getText();
		return newString;
	}
	
	/**
	 * 查询短信平台用户回复
	 * @param model
	 * @return
	 */
	public static String receive(String url,ZUCPBalanceAndReceiveModel model){
		List<NameValuePair> params = model.getBalanceNameValuePair(model);
//		String url = SendMessageUtil.proInfo.getProperty("RECEIVE");
		String result = execute(url, params.toArray(new NameValuePair[params.size()]));
		return result;
	}
	
	/**
	 * 进行短信平台的充值
	 * @param model
	 * @return
	 */
	public static String pay(String url,ZUCPPayModel model){
		List<NameValuePair> params = model.getPayNameValuePair(model);
//		String url = SendMessageUtil.proInfo.getProperty("PAY");
		String result = execute(url, params.toArray(new NameValuePair[params.size()]));
		return result;
	}
	
	/**
	 * 进行短信发送
	 * @param model
	 * @return
	 */
	public static String send(ZUCPSendModel model){
		String url = Constants.getAttribute("SENDURL");
		List<NameValuePair> params = model.getSendNameValuePair(model);
//		String url = SendMessageUtil.proInfo.getProperty("SENDURL");
		String result = execute(url, params.toArray(new NameValuePair[params.size()]));
		return result;
	}
	
	/**
	 * 更新sn对应的用户的密码
	 * @param model
	 * @return
	 */
	public static String modifyPwd(String url,ZUCPModifyPwdModel model){
		List<NameValuePair> params = model.getRegisterNameValuePair(model);
//		String url = SendMessageUtil.proInfo.getProperty("MODIFYPWD");
		String result = execute(url, params.toArray(new NameValuePair[params.size()]));
		return result;
	}
	
	/**
	 * 执行的发送方法
	 * @param url
	 * @param params
	 * @return
	 */
	public static String execute(String url, NameValuePair[] params) {
		
		String result = null;
		//测试用
		if(url.equals(Constants.getAttribute("SENDURL"))){
			
			HttpClient client = new HttpClient();
			
			PostMethod postMethod = new PostMethod(url);
			
			postMethod.setRequestBody(params);
			
			int statusCode = 0;
			try {
				statusCode = client.executeMethod(postMethod);
			} catch (HttpException e) {
			} catch (IOException e) {
			}
			
			try {
				if (statusCode == HttpStatus.SC_OK) {
					result = postMethod.getResponseBodyAsString();
					return result;
				} else {
				}
			} catch (IOException e) {
			}
			postMethod.releaseConnection();
			
		}

		return result;
	}
	
	/**
	 * 判定漫道本次发送短信是否成功
	 * @param result
	 * @param model
	 * @return
	 */
	public static Map<String,Object> sendSuccess(String result,ZUCPSendModel model){
		Map<String,Object> map = new HashMap<String, Object>();
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(result);
		} catch (DocumentException e) {
			logger.error("不得了，格式化漫道短信返回数据失败",e);
			return null;
		}
	     Element root = doc.getRootElement();
	     String newString = root.getText();
	     String oldString = model.getRrid();
	     if(newString.equals(oldString)){
		
//	     if(true){
	    	 map.put("flag", "true");
	    	 map.put("message", "发送成功");
	    	 return map;
	     }else{
	    	 logger.info("漫道返回错误码"+newString);
	    	 map.put("flag", "false");
	    	 map.put("message", Constants.getErrorCodeMap().get(newString));
	    	 return map;
	     }
	}
	
	/**
	 * 短信报警，整体方法，判定小于一定的次数后，进行余量报警
	 * ZUCPMessageUtil.warningMessageCount()<BR>
	 * <P>Author :  liyong </P>  
	 * <P>Date : 2015-4-4 </P>
	 */
	public static void warningMessageCount(){
		String count = balance();
		logger.info("本次查询到的短信平台剩余条数为："+count);
		
		String warningCount = Constants.getAttribute("WARNING_COUNT");
		if(Long.valueOf(count)<=Long.valueOf(warningCount)){
			/**
			 * 短信预警开关开启
			 */
			if(Constants.WARNING_MESSAGE_FLAG == 0){
				String warningMan = Constants.getAttribute("WARNING_MAN");
				String warningContent = Constants.getAttribute("WARNING_CONTENT");
				logger.info("开始发送报警短信，发送给："+warningMan);
				ZUCPSendModel model = new ZUCPSendModel();
				model.setMobile(warningMan);
				warningContent = warningContent.replace("{count}",count);
				model.setContent(warningContent);
				model.setRrid(String.valueOf(System.currentTimeMillis()));
				
				Map<String,Object> result = sendSuccess(send(model), model);
				logger.info(result.get("flag").toString());
				Constants.WARNING_MESSAGE_FLAG++;
			}else{
				/**
				 * 判定是否到临界值，然后继续打开开关，发送
				 */
				Constants.WARNING_MESSAGE_FLAG++;
				String warningFlag = Constants.getAttribute("WARNING_FlAG");
				if(Constants.WARNING_MESSAGE_FLAG == Integer.valueOf(warningFlag)){
					Constants.WARNING_MESSAGE_FLAG = 0;
					logger.info("发送次数已经达到临界值,重新开启发送开关成功");
				}
			}
		}else{
			logger.info("短信超过预警余量");
			if(Constants.WARNING_MESSAGE_FLAG!=0){
				Constants.WARNING_MESSAGE_FLAG = 0;
				logger.info("短信已经充值,重新开启发送开关成功");
			}
		}
	}
	
	public static void main(String[] args) {
		
//		ZUCPBalanceAndReceiveModel model = new ZUCPBalanceAndReceiveModel();
//		
//		String url = "http://sdk.entinfo.cn:8060/webservice.asmx/balance";
//			
//		String result = balance(url, model);
//		System.out.println(result);
		
		ZUCPSendModel model = new ZUCPSendModel();
		model.setContent("您开通银行卡快捷支付短信验证码为957862,本验证码5分钟后自动失效。祝您购物愉快！");
		model.setMobile("18768835005");
		model.setRrid(String.valueOf(System.currentTimeMillis()));
		String result = send(model);
		System.out.println(result);
		System.out.println(sendSuccess(result, model));
		System.out.println(model.getContent());
		System.out.println(model.getPwd());
//		ZUCPBalanceAndReceiveModel model =new ZUCPBalanceAndReceiveModel();
//		String balance = balance();
//		System.out.println(balance);
	
	}
}
