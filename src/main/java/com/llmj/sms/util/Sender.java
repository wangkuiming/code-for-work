package com.llmj.sms.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Sender {
	public static Logger logger = LoggerFactory.getLogger(Sender.class);

	public Sender() {
		this("default", "default");
	}

	public Sender(String name, String pwd) {
		comName = name;
		comPwd = pwd;
		Server = "http://www.china-sms.com";
	}

	public Sender(String name, String pwd, int serverNum) {
		comName = name;
		comPwd = pwd;
		if (serverNum == 2) {
			Server = "http://www6.china-sms.com";
		} else {
			Server = "http://www.china-sms.com";
		}
	}

	public String massSend(String dst, String msg, String time, String subNo) {
		String sUrl = null;
		try {
			sUrl = Server + "/send/gsend.asp?name=" + comName + "&pwd=" + comPwd
			        + "&dst=" + dst + "&msg=" + URLEncoder.encode(msg, "GB2312")
			        + "&time=" + time + "&sender=" + subNo;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("短信发送异常，异常信息", e);
		}
		return getUrl(sUrl);
	}

	public String readSms() {
		String sUrl = null;
		sUrl = Server + "/send/readsms.asp?name=" + comName + "&pwd=" + comPwd;
		try {
			URLEncoder.encode(sUrl, "GB2312");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("短信发送异常，异常信息", e);
		}
		return getUrl(sUrl);
	}

	public String getFee() {
		String sUrl = null;
		sUrl = Server + "/send/getfee.asp?name=" + comName + "&pwd=" + comPwd;
		return getUrl(sUrl);
	}

	public String changePwd(String newPwd) {
		String sUrl = null;
		sUrl = Server + "/send/cpwd.asp?name=" + comName + "&pwd=" + comPwd
		        + "&newpwd=" + newPwd;
		try {
			URLEncoder.encode(sUrl, "GB2312");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("短信发送异常，异常信息", e);
		}
		return getUrl(sUrl);
	}

	public String checkContent(String content) {
		String sUrl = null;
		sUrl = Server + "/send/checkcontent.asp?name=" + comName + "&pwd="
		        + comPwd + "&content=" + content;
		try {
			URLEncoder.encode(sUrl, "GB2312");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("短信发送异常，异常信息", e);
		}
		return getUrl(sUrl);
	}

	public String getUrl(String urlString) {
		StringBuffer sb = new StringBuffer();
		try {
			URL url = new URL(urlString);
			URLConnection conn = url.openConnection();
			BufferedReader reader = new BufferedReader(
			        new InputStreamReader(conn.getInputStream()));
			for (String line = null; (line = reader.readLine()) != null;)
				sb.append(line + "\n");
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("短信发送异常，异常信息", e);
		}
		return sb.toString();
	}

	private String comName;
	private String comPwd;
	private String Server;
}
