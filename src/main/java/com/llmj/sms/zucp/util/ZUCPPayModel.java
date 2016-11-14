package com.llmj.sms.zucp.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang.StringUtils;

/**
 * 
 * <p>Title: ZUCPPayModel.java</p>
 * 
 * <p>Description: 
 * 		欠费发起充值
 * </p>
 * 
 * <p>Date: Mar 17, 2015</p>
 * 
 * <p>Time: 12:49:56 PM</p>
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
public class ZUCPPayModel extends ZUCPBaseModel{
	/**
	 * 欠费使用，充值卡号
	 */
	private String cardno;
	
	/**
	 * 欠费使用，充值密码
	 */
	private String cardpwd;
	
	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public String getCardpwd() {
		return cardpwd;
	}

	public void setCardpwd(String cardpwd) {
		this.cardpwd = cardpwd;
	}
	
	public List<NameValuePair> getPayNameValuePair(ZUCPPayModel model) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		if (!StringUtils.isBlank(model.getSn()))
			params.add(new NameValuePair("Sn", model.getSn()));
		if (!StringUtils.isBlank(model.getPwd()))
//			params.add(new NameValuePair("Pwd", new MD5().getMD5String((model.getSn()+model.getPwd())).toUpperCase()));
		if(!StringUtils.isBlank(model.getCardno()))
			params.add(new NameValuePair("cardno", model.getCardno()));
		if(!StringUtils.isBlank(model.getCardpwd()))
			params.add(new NameValuePair("cardno", model.getCardpwd()));
		return params;
	}
}
