package com.llmj.sms.zucp.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.NameValuePair;

/**
 * 
 * <p>Title: ZUCPBalanceModel.java</p>
 * 
 * <p>Description: 
 * 		查询漫道的短信平台金钱余额,查询用户短信回复，均使用此model
 * </p>
 * 
 * <p>Date: Mar 17, 2015</p>
 * 
 * <p>Time: 1:00:59 PM</p>
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
public class ZUCPBalanceAndReceiveModel extends ZUCPBaseModel{

	public List<NameValuePair> getBalanceNameValuePair(ZUCPBalanceAndReceiveModel model){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new NameValuePair("Sn", model.getSn()));
		params.add(new NameValuePair("Pwd", new MD5().getMD5String((model.getSn()+model.getPwd())).toUpperCase()));
		
		/**
		 * 当该类用作接受短信回复时，
		 * 单个回复内容为：12323666,47512,15201692834,%b2%e2%ca%d41,2010-12-30 9:15:56
		 * 如有多条回车换行隔开 \n,一次最多返回999条短信
		 * 其中内容需要用gb2312进行解码才能正常使用
		 * 一旦本次回复被该sn号进行了提取，再次提取将不会获得重复的短信回复
		 */
		
		return params;
	}
	
}
