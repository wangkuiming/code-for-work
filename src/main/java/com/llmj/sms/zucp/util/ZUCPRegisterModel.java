package com.llmj.sms.zucp.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.NameValuePair;

/**
 * 
 * <p>Title: ZUCPRegisterModel.java</p>
 * 
 * <p>Description: 
 * 		漫道短信注册model
 * </p>
 * 
 * <p>Date: Mar 17, 2015</p>
 * 
 * <p>Time: 12:16:06 PM</p>
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
public class ZUCPRegisterModel extends ZUCPBaseModel {
	/**
	 * 注册使用，省
	 */
	private String province;
	/**
	 * 注册使用，市
	 */
	private String city;

	/**
	 * 注册使用， 行业
	 */
	private String trade;

	/**
	 * 注册使用，公司名称
	 */
	private String entName;

	/**
	 * 注册使用，联系人
	 */
	private String linkName;

	/**
	 * 注册使用，电话
	 */
	private String phone;

	/**
	 * 注册使用，移动电话
	 */
	private String mobile;

	/**
	 * 注册使用，邮件地址
	 */
	private String email;
	/**
	 * 注册使用， 传真
	 */
	private String fax;

	/**
	 * 注册使用，地址
	 */
	private String address;

	/**
	 * 注册使用，邮政编码
	 */
	private String postCode;

	/**
	 * 注册使用，企业签名
	 */
	private String sign;

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTrade() {
		return trade;
	}

	public void setTrade(String trade) {
		this.trade = trade;
	}

	public String getEntName() {
		return entName;
	}

	public void setEntName(String entName) {
		this.entName = entName;
	}

	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public List<NameValuePair> getRegisterNameValuePair(ZUCPRegisterModel model) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		/**
		 * 依次赋值不为空的值到键值对存储结构中
		 */
		params.add(new NameValuePair("Sn", model.getSn()));
		params.add(new NameValuePair("Pwd", model.getPwd()));
		params.add(new NameValuePair("Province", model.getProvince()));
		params.add(new NameValuePair("City", model.getCity()));
		params.add(new NameValuePair("Trade", model.getTrade()));
		params.add(new NameValuePair("Entname", model.getEntName()));
		params.add(new NameValuePair("Linkman", model.getLinkName()));
		params.add(new NameValuePair("Phone", model.getPhone()));
		params.add(new NameValuePair("Mobile", model.getMobile()));
		params.add(new NameValuePair("Email", model.getEmail()));
		params.add(new NameValuePair("Fax", model.getFax()));
		params.add(new NameValuePair("Address", model.getAddress()));
		params.add(new NameValuePair("Postcode", model.getPostCode()));
		params.add(new NameValuePair("Sign", model.getSign()));
		
		return params;
	}
}
