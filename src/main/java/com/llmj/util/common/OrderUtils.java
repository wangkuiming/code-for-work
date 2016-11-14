package com.llmj.util.common;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.llmj.mq.MessageSender;
import com.llmj.util.constant.Config;
import com.llmj.util.constant.Contents;

public class OrderUtils {
	public static Logger logger = LoggerFactory.getLogger(OrderUtils.class);

	/**
	 * 根据订单类型，生成订单号
	 * 
	 * @author: GaoMJ
	 * @date: 2015年9月11日
	 * @param orderType
	 * @return
	 */
	public static String getOrderNo(String orderType) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		String orderNo = sdf.format(new Date());
		return orderType + orderNo;
	}

	/**
	 * false 不包含 true 包含
	 * 
	 * @author: GaoMJ
	 * @date: 2016年3月18日
	 * @param mobile
	 * @return
	 */
	public static boolean sendMessageFilterMobile(String mobile) {
		if (StringUtils.isBlank(mobile)) {
			return false;
		}
		String lists = Config.getStringValue("order_send_message_filter");
		String listArray[] = lists.split(";");
		List<String> list = Arrays.asList(listArray);
		if (list.contains(mobile)) {
			return false;
		}
		return true;
	}

	public static boolean orderTypeIsGC(String orderType) {
		return Contents.GOODS_CAR_ORDER_NO_PREFIX.equalsIgnoreCase(orderType);
	}

	public static boolean orderTypeIsCG(String orderType) {
		return Contents.CAR_GOODS_ORDER_NO_PREFIX.equalsIgnoreCase(orderType);
	}

	public static boolean orderTypeIsGW(String orderType) {
		return Contents.GOODS_WAREHOUSE_ORDER_NO_PREFIX
		        .equalsIgnoreCase(orderType);
	}

	public static boolean orderTypeIsWG(String orderType) {
		return Contents.WAREHOUSE_GOODS_ORDER_NO_PREFIX
		        .equalsIgnoreCase(orderType);
	}

	/**
	 * 根据货源/车源/仓库源主键通知搜索
	 * 
	 * @author: GaoMJ
	 * @date: 2015年9月24日
	 * @param order
	 */
	public static void notifySearchUpdResource(MessageSender messageSender,
	        String lineId, String carGoodsResource,
	        String warehouseGoodsResource, String warehouseResource) {
		logger.info(
		        "通知MQ->lineId：{},carGoodsResource:{},warehouseGoodsResource:{},warehouseResource:{}",
		        lineId, carGoodsResource, warehouseGoodsResource,
		        warehouseResource);
		JSONObject message = new JSONObject();
		if (StringUtils.isNotBlank(carGoodsResource)) {
			message.put("id", carGoodsResource);
			message.put("type", Contents.llmjCMStype.LLMJCMSTYPE_1.getKey());
			messageSender.sendMessage(message.toJSONString());
		}
		if (StringUtils.isNotBlank(warehouseGoodsResource)) {
			message.put("id", warehouseGoodsResource);
			message.put("type", Contents.llmjCMStype.LLMJCMSTYPE_2.getKey());
			messageSender.sendMessage(message.toJSONString());
		}
		if (StringUtils.isNotBlank(warehouseResource)) {
			message.put("id", warehouseResource);
			message.put("type", Contents.llmjCMStype.LLMJCMSTYPE_4.getKey());
			messageSender.sendMessage(message.toJSONString());
		}
		if (StringUtils.isNotBlank(lineId)) {
			message.put("id", lineId);
			message.put("type", Contents.llmjCMStype.LLMJCMSTYPE_5.getKey());
			messageSender.sendMessage(message.toJSONString());
		}
	}
}
