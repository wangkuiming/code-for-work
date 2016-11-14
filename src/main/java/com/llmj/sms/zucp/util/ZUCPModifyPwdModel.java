package com.llmj.sms.zucp.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang.StringUtils;

/**
 * 
 * <p>Title: ZUCPModifyPwdModel.java</p>
 * 
 * <p>Description: 
 * 		漫道修改sn对应的账号的密码，一天不超过10次
 * </p>
 * 
 * <p>Date: Mar 17, 2015</p>
 * 
 * <p>Time: 1:18:32 PM</p>
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
public class ZUCPModifyPwdModel extends ZUCPBaseModel{
	
	/**
	 * 新密码
	 */
	private String newpwd;
	
	public String getNewpwd() {
		return newpwd;
	}

	public void setNewpwd(String newpwd) {
		this.newpwd = newpwd;
	}

	public List<NameValuePair> getRegisterNameValuePair(ZUCPModifyPwdModel model) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		if (!StringUtils.isBlank(model.getSn()))
			params.add(new NameValuePair("Sn", model.getSn()));
		if (!StringUtils.isBlank(model.getPwd()))
			params.add(new NameValuePair("Pwd", model.getPwd()));
		if(!StringUtils.isBlank(model.getNewpwd()))
			params.add(new NameValuePair("newpwd", model.getNewpwd()));
		return params;
	}
}
