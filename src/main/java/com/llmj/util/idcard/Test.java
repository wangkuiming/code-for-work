package com.llmj.util.idcard;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class Test {

	public static void main(String[] args) {
		Map<String,String> result = IdCardRealChek.sendCheckMsg("张三", "410823159505620321");
		System.out.println(JSONObject.toJSONString(result));
	}

}
