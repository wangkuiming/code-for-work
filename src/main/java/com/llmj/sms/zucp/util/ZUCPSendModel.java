package com.llmj.sms.zucp.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.NameValuePair;

/**
 * 
 * <p>Title: ZUCPSendModel.java</p>
 * 
 * <p>Description: 
 * 		漫道发送短信数据结构
 * </p>
 * 
 * <p>Date: Mar 17, 2015</p>
 * 
 * <p>Time: 1:05:32 PM</p>
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
public class ZUCPSendModel extends ZUCPBaseModel {

	/**
	 * 发送的手机号，最大支持10000个，建议5000一下，以英文够好隔开
	 */
	private String mobile;

	/**
	 * 发送的短信内容，支持长短信
	 * 如果一次提交小于等于70字符 系统会默认为一条短信发出 扣费一条
	 * 如果大于70字符 系统会默认为长短信处理 此时根据长短信前边会加(1/2) 或者(2/2)的标志 此时扣费按67字符扣一条 
	 *(内容不超过500字符) 纯单字节是160一条,超过160个字符后，按普通长短信收费，即67个字符每条收费。
	 */
	private String content;

	/**
	 * 扩展码
	 */
	private String ext;

	/**
	 * 定时发送的时间
	 */
	private String stime;

	/**
	 * 本次发送标示，若置空，则返回漫道系统生成的流水号
	 */
	private String rrid;

	/**
	 * 规定解析的编码，默认置空
	 */
	private String msgfmt;
	
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getStime() {
		return stime;
	}

	public void setStime(String stime) {
		this.stime = stime;
	}

	public String getRrid() {
		return rrid;
	}

	public void setRrid(String rrid) {
		this.rrid = rrid;
	}
	
	public String getMsgfmt() {
		return msgfmt;
	}

	public void setMsgfmt(String msgfmt) {
		this.msgfmt = msgfmt;
	}

	/**
	 * 获取短信发送的参数list
	 * @param model
	 * @return
	 */
	public List<NameValuePair> getSendNameValuePair(ZUCPSendModel model) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new NameValuePair("Sn", model.getSn()));
		params.add(new NameValuePair("Pwd", new MD5().getMD5String((model.getSn() + model.getPwd())).toUpperCase()));
		params.add(new NameValuePair("Mobile", model.getMobile()));
		try {
			/**
			 * 通道的原因，发送短信签名还是放在后边，短信内容［签名］提交
			 *
			 * 有些通道签名要求在前头的，漫道系统就会自动放在前头
			 */
//			System.out.println("本次发送内容："+model.getContent()+"【鲜易网】");
			params.add(new NameValuePair("Content", URLEncoder.encode(model.getContent()+"【冷链马甲】","utf-8")));
		} catch (UnsupportedEncodingException e) {
//			System.out.println("转换短信内容字节码为utf8出错");
		}
		params.add(new NameValuePair("Ext", model.getExt()));
		params.add(new NameValuePair("stime", model.getStime()));
		params.add(new NameValuePair("Rrid", model.getRrid()));
		params.add(new NameValuePair("msgfmt", model.getMsgfmt()));
		return params;
	}
}
