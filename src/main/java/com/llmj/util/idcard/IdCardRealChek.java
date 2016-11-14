package com.llmj.util.idcard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commons.utils.Cipher3DES;
import com.commons.utils.HttpRequestClient;
import com.commons.utils.RsaSignCoder;

import net.sf.json.JSONObject;

/**
 * 
 * @author liyong
 *
 */
public class IdCardRealChek {
	public static Logger logger = LoggerFactory.getLogger(IdCardRealChek.class);

	/**
	 * 真实发送身份证二要素验证，传入参数姓名，身份证号 返回值map，取值key：code，如果为00，则是通过的，其余均失败，原因取值key：desc
	 * 
	 * @param realName
	 * @param idCard
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> sendCheckMsg(String realName,
	        String idCard) {
		Map<String, String> returnMap = new HashMap<String, String>();
		try {
			Map<String, String> checkMap = IdCardCons.csMap;

			// 1、组装请求报文
			JSONObject headerJson = new JSONObject();
			JSONObject conditionJson = new JSONObject();

			String batchNo = String.valueOf(System.currentTimeMillis());

			headerJson.put("qryBatchNo", batchNo); // 验证批次号 用户生成的唯一编号
			headerJson.put("userCode", checkMap.get("userCode")); // 商户编号，即用户编号
			headerJson.put("sysCode", checkMap.get("sysCode")); // 应用编号
			headerJson.put("qryReason", "认证授权"); // 原因

			String[] dates = IdCardCons.getDates();
			headerJson.put("qryDate", dates[0]);// 格式：yyyyMMdd
			headerJson.put("qryTime", dates[1]);// 格式：hhmmss

			conditionJson.put("realName", realName);
			conditionJson.put("idCard", idCard);
			JSONObject allJson = new JSONObject();
			allJson.put("header", headerJson);
			allJson.put("condition", conditionJson);
			String data = allJson.toString();
			logger.info("请求报文：{}", data);

			String privateKey = checkMap.get("privateKey");// 平台提供的加密秘钥

			String vector = IdCardCons.getRandom(); // 用户自己生成的 随机8位数字或者字母
			// 2、加密请求报文
			String encrData = Cipher3DES.encrypt(data, privateKey, vector);

			// 3、对请求报文进行签名
			String signature = RsaSignCoder.sign(encrData,
			        checkMap.get("signPrivateKey"));

			/**
			 * 组装请求参数 调用实名认证接口
			 */
			// 4、请求API对应的接口
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			parameters.add(new BasicNameValuePair("condition", encrData));
			parameters.add(new BasicNameValuePair("userCode",
			        checkMap.get("userCode")));
			parameters.add(new BasicNameValuePair("signature", signature));
			parameters.add(new BasicNameValuePair("vector", vector));// 随机生成的8位数偏移量

			String reslult = HttpRequestClient
			        .invoke_http(checkMap.get("sendUrl"), parameters, "UTF-8");
			logger.info("返回报文：{}", reslult);

			/**
			 * 解析平台返回报文
			 */
			// 5、获取接口返回报文
			Map<String, String> resultMap = new HashMap<String, String>();
			// 将json字符串转换成jsonObject
			JSONObject requestJson = JSONObject.fromObject(reslult);
			Iterator<String> it = requestJson.keys();
			// 遍历jsonObject数据，添加到Map对象
			while (it.hasNext()) {
				String key = String.valueOf(it.next());
				String value = String.valueOf(requestJson.get(key));
				resultMap.put(key, value);
			}
			String sign = resultMap.get("signature");
			String datas = resultMap.get("contents");

			/**
			 * 验签及解密报文
			 */
			// 6、验证加密内容报文的签名
			boolean isTrue = RsaSignCoder.verify(datas,
			        checkMap.get("signPublicKey"), sign);
			if (isTrue) {
				// 确认解签是否通过 返回true则表示验证通过
				// 下一步进行解密报文体加密数据（签名主要用于验证报文数据是否被篡改）
				// 7、验签通过则进行解密返回的加密报文
				String str = Cipher3DES.decrypt(datas, privateKey, vector);
				com.alibaba.fastjson.JSONObject jsonObj = com.alibaba.fastjson.JSONObject
				        .parseObject(str);
				if (str.contains("record")) {
					// 返回正确结果了
					com.alibaba.fastjson.JSONArray arrayObj = jsonObj
					        .getJSONArray("data");
					com.alibaba.fastjson.JSONObject recordArr = arrayObj
					        .getJSONObject(0);
					com.alibaba.fastjson.JSONArray recordArray = recordArr
					        .getJSONArray("record");
					com.alibaba.fastjson.JSONObject recordObj = recordArray
					        .getJSONObject(0);
					String code = recordObj.getString("resCode");
					String desc = recordObj.getString("resDesc");

					returnMap.put("code", code);
					returnMap.put("desc", desc);
				} else {
					// 返回错误结果了
					String code = jsonObj.getJSONObject("msg")
					        .getString("code");
					String desc = jsonObj.getJSONObject("msg")
					        .getString("codeDesc");
					returnMap.put("code", code);
					returnMap.put("desc", desc);
				}
			} else {
				returnMap.put("code", "111");
				returnMap.put("desc", "请联系平台客服！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("调用iDcard异常,异常信息:{}", e);
			returnMap.put("code", "111");
			returnMap.put("desc", "请联系平台客服！");
			return returnMap;
		}
		return returnMap;
	}
}
