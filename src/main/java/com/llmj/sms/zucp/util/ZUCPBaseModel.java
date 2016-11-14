package com.llmj.sms.zucp.util;

/**
 * 
 * <p>Title: ZUCPModel.java</p>
 * 
 * <p>Description: 
 * 		封装漫道短信Model
 * </p>
 * 
 * <p>Date: Mar 17, 2015</p>
 * 
 * <p>Time: 11:30:56 AM</p>
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
public abstract class ZUCPBaseModel {

	/**
	 * 漫道提供的sn号
	 */
	private String sn = "SDK-BBX-010-23770";

	/**
	 * 我方注册使用的密码，每次发送短信时间需要将密码 sn后都发送过去
	 */
	private String pwd = "a-369(-4";

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

}
