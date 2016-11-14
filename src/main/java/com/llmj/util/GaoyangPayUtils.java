package com.llmj.util;

import java.text.Collator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.llmj.service.GaoyangService;

public class GaoyangPayUtils {

	public static void mainff(String[] args) {
		System.out.println(bankList());
	}

	public static String txBankList() {
		List<String> bankMapAll = new ArrayList<String>();
		bankMapAll.add("中国工商银行,102");
		bankMapAll.add("中国农业银行,103");
		bankMapAll.add("中国银行,104");
		bankMapAll.add("中国建设银行,105");
		bankMapAll.add("交通银行,301");
		bankMapAll.add("中国邮政储蓄银行,100");
		bankMapAll.add("兴业银行,309");
		bankMapAll.add("平安银行,307");
		bankMapAll.add("上海浦东发展银行,310");
		bankMapAll.add("中信银行,302");
		bankMapAll.add("中国光大银行,303");
		bankMapAll.add("中国民生银行,305");
		bankMapAll.add("广发银行,306");
		bankMapAll.add("招商银行,308");

		Map<String, String> bankMap = new TreeMap<String, String>(
		        Collator.getInstance(java.util.Locale.CHINA));
		bankMap.put("吉林省农村信用社", "901");
		bankMap.put("安徽涡阳农村商业银行", "9003");
		bankMap.put("南充市商业银行", "9004");
		bankMap.put("江苏新沂农村商业银行", "9009");
		bankMap.put("邯郸银行", "9014");
		bankMap.put("长沙银行", "9012");
		bankMap.put("北京农村商业银行", "9013");
		bankMap.put("北京银行", "403");
		bankMap.put("上海银行", "401");
		bankMap.put("华夏银行", "304");
		bankMap.put("恒丰银行", "311");
		bankMap.put("浙商银行", "316");
		bankMap.put("渤海银行", "317");
		bankMap.put("厦门银行", "402");
		bankMap.put("烟台银行", "404");
		bankMap.put("福建海峡银行", "405");
		bankMap.put("宁波银行", "408");
		bankMap.put("齐鲁银行", "409");
		bankMap.put("焦作市商业银行", "411");
		bankMap.put("温州银行", "412");
		bankMap.put("广州银行", "413");
		bankMap.put("汉口银行", "414");
		bankMap.put("盛京银行", "417");
		bankMap.put("洛阳银行", "418");
		bankMap.put("辽阳银行", "419");
		bankMap.put("大连银行", "420");
		bankMap.put("苏州银行", "421");
		bankMap.put("河北银行", "422");
		bankMap.put("杭州银行", "423");
		bankMap.put("南京银行", "424");
		bankMap.put("东莞银行", "425");
		bankMap.put("金华银行", "426");
		bankMap.put("乌鲁木齐市商业银行", "427");
		bankMap.put("绍兴银行", "428");
		bankMap.put("成都银行", "429");
		bankMap.put("抚顺银行", "430");
		bankMap.put("临商银行", "431");
		bankMap.put("湖北银行", "432");
		bankMap.put("葫芦岛银行", "433");
		bankMap.put("天津银行", "434");
		bankMap.put("郑州银行", "435");
		bankMap.put("宁夏银行", "436");
		bankMap.put("珠海华润银行", "437");
		bankMap.put("齐商银行", "438");
		bankMap.put("锦州银行", "439");
		bankMap.put("徽商银行", "440");
		bankMap.put("哈尔滨银行", "442");
		bankMap.put("浙江民泰商业银行", "9005");
		bankMap.put("廊坊银行", "9006");
		bankMap.put("沧州银行", "9007");
		bankMap.put("湖州银行", "9017");
		bankMap.put("浙江泰隆商业银行股份有限公司", "9008");
		bankMap.put("江苏东台农村商业银行", "9011");
		bankMap.put("中国农业发展银行", "9015");
		bankMap.put("江苏江南农村商业银行", "9016");
		bankMap.put("江苏银行", "9002");
		bankMap.put("无锡农村商业银行", "9001");
		bankMap.put("宁波慈溪农村合作银行", "9010");
		bankMap.put("青岛银行", "450");
		bankMap.put("辽宁东港农村商业银行股份有限公司", "lndg");
		bankMap.put("河南省农村信用社联合社", "hnnc");
		bankMap.put("日照银行", "455");
		bankMap.put("浙江温岭农村合作银行", "222");
		bankMap.put("吉林银行", "451");
		bankMap.put("济宁银行", "1510");
		bankMap.put("河北省农村信用社联合社", "1530");
		bankMap.put("吉林九台农村商业银行股份有限公司", "1111");
		bankMap.put("大连农村商业银行", "111");
		bankMap.put("花旗银行", "3010");
		bankMap.put("江苏沭阳农村商业银行", "3143");
		bankMap.put("广州农村商业银行股份有限公司", "gznc");

		for (Map.Entry<String, String> entry : bankMap.entrySet()) {
			String key = entry.getKey();
			String val = entry.getValue();
			bankMapAll.add(key + "," + val);
		}

		JSONArray newJsonArray = new JSONArray();
		for (String k : bankMapAll) {
			String kArr[] = k.split(",");
			String key = kArr[0];
			String val = kArr[1];
			JSONObject item = new JSONObject();
			item.put("picPath", "http://qa-pic.lenglianmajia.com/bank/");
			item.put("bigPicName", val + ".gif");
			item.put("smallPicName", "s_" + val + ".gif");
			item.put("zfNo", val);
			item.put("bankName", key);
			item.put("bankType", "");
			newJsonArray.add(item);
		}
		String resultTxt = newJsonArray.toString();
		return resultTxt;
	}

	public static String bankList() {
		GaoyangService gaoyangService = GaoYangServiceFactory.getInstance()
		        .instanceService();
		String resultTxt = "";
		try {
			Map<String, Object> bankListResult = gaoyangService.queryBankList();
			if (bankListResult != null) {
				if ("SUCCESS".equalsIgnoreCase(
				        (String) bankListResult.get("status"))) {
					String bankList = bankListResult.get("bankList").toString();
					JSONArray arr = JSON.parseArray(bankList);
					JSONArray newJsonArray = new JSONArray();
					Set<String> filter = new HashSet<String>();
					for (int i = 0; i < arr.size(); i++) {
						JSONObject item = (JSONObject) arr.getJSONObject(i);
						String pcId = item.getString("pcId");
						if (filter.contains(pcId)) {
							continue;
						}
						item.put("bigPicName", pcId + ".gif");
						item.put("smallPicName", "s_" + pcId + ".gif");
						item.put("zfNo", pcId);
						filter.add(pcId);
						newJsonArray.add(item);
					}
					resultTxt = newJsonArray.toString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return resultTxt;
		}
		return resultTxt;
	}
}
