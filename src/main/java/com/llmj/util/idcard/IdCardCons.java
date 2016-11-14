package com.llmj.util.idcard;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.llmj.util.constant.Config;

public class IdCardCons {
	/**
	 * 测试数据匹配
	 */
	public static Map<String, String> csMap = new HashMap<String, String>();

	/**
	 * 正式数据匹配
	 */
	public static Map<String, String> proMap = new HashMap<String, String>();

	static {
		/**
		 * 放置测试环境数据
		 */
		csMap.put("userCode", Config.getStringValue("idcard.userCode"));// 放置测试的商户编码
		csMap.put("sysCode", Config.getStringValue("idcard.sysCode"));// 放置测试的应用编号
		csMap.put("privateKey", Config.getStringValue("idcard.privateKey"));// 放置测试appkey
		// 测试商户签名私钥
		csMap.put("signPrivateKey",
		        Config.getStringValue("idcard.signPrivateKey"));
		// 测试固定的平台签名公钥
		csMap.put("signPublicKey",
		        Config.getStringValue("idcard.signPublicKey"));
		csMap.put("sendUrl", Config.getStringValue("idcard.sendUrl"));

		/**
		 * 放置正式环境数据
		 */
		proMap.put("userCode", "HFJK20161019000005");// 放置正式的商户编码
		proMap.put("sysCode", "HFJKAPP20161024000002");// 放置正式的应用编号
		proMap.put("privateKey", "p7DlZCrc1blGCAQyv4MaVA3g");// 放置正式appkey
		// 商户签名私钥
		proMap.put("signPrivateKey",
		        "MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEAwsehFHLOcCDB5u7uph4quE6B19Wzd7vAnq0+Oz/dpR/5Hj0vqHviS4MVZdbL9qDc7sS1x8GH0j/9nFXLsTmbCQIDAQABAkA6eaofNHRLb1onFVYqIuZaXJ9Yh7yQVpp2SLZlHUBSYOcGzknVjCoJOAUU/d2JsFU4pexavPvRzSS3Dl2u+sqxAiEA+YDfNp4j5xT0NXXjQsjoJiypNs6cCrDd80BG/nPmms0CIQDH2fu3ExZeu4wZcpzixKSSyCflVq5mn5gpTFbFd5v5LQIhAIiEdh35a2WeNn0l8uJw8W0p1LCzXfgPjHHzwqSpoA0lAiAmtzvkQ3ZZ88s4QtPRunaHWO8hpqEykEhR98/3gSxeIQIhAMKlG6cDB7LDkKiMKmYnKnMuZRXDl2zy3ADGpk00tzSg");
		// 固定的平台签名公钥
		proMap.put("signPublicKey",
		        "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJJY/kusrKic6XAhHMakIX06nEnMnvceRV5m8dLIKQcz0RNkDXiEQ/EIV0hZQNQlTIyB5f6OzQeDjJlDUGgkyD8CAwEAAQ==");
		proMap.put("sendUrl",
		        "http://api.hfdatas.com/superapi/super/auth/idcard");
	}

	/**
	 * 获取年月日时分秒字符串数组
	 * 
	 * @return
	 */
	public static String[] getDates() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyMMdd HHmmss");
		String str = sdf.format(new Date());
		return str.split(" ");
	}

	/**
	 * 获取8位随机数
	 * 
	 * @return
	 */
	public static String getRandom() {
		return String.valueOf((long) (89999999 * Math.random() + 10000000));
	}
}
