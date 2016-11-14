package com.llmj.sms.zucp.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Constants {

	//短信模板启用状态
	public static final String USE_STATUS = "1";//启用
	public static final String USE_STATUST = "2";//暂停
	//短信模板发送类型
	public static final String SEND_TYPE_PT = "1";//普通
	public static final String SEND_TYPE_LS = "2";//临时
	//短信发送状态
	public static final String SEND_STATUS_SUCCESS = "1";//成功
	public static final String SEND_STATUS_FAIL = "2";//失败
	public static final String SEND_STATUS_TO_BE_SEND = "3";//待发送
	
	public static final String SEND_SMS_TYPE_CF = "1";//触发
	public static final String SEND_SMS_TYPE_DY = "2";//订阅
	public static final String SEND_SMS_TYPE_JS = "3";//及时发送
	public static final String SEND_SMS_TYPE_DS = "4";//定时发送
	
	public static final int SMS_SPLIT_NUM = 300;//当短信批量发送总条数大于此数时拆分
	

	public static Map<String,String> getSmsSendStatusMap(){
		Map<String,String> map = new HashMap<String, String>();
		map.put(SEND_STATUS_SUCCESS, "发送成功");
		map.put(SEND_STATUS_FAIL, "发送失败");
		map.put(SEND_STATUS_TO_BE_SEND, "待发送");
		return map;
	}
	public static Map<String,String> getSmsTypeMap(){
		Map<String,String> map = new HashMap<String, String>();
		map.put(SEND_SMS_TYPE_CF, "触发类");
		map.put(SEND_SMS_TYPE_DY, "订阅类");
		map.put(SEND_SMS_TYPE_JS, "即时发");
		map.put(SEND_SMS_TYPE_DS, "定时发");
		return map;
	}

	public static int WARNING_MESSAGE_FLAG=0;
	
	/**
	 * 发送短信失败时返回的错误描述
	 * @return
	 */
	public static Map<String,Object> getErrorCodeMap(){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("-2", "帐号/密码不正确");
		map.put("-4", "余额不足支持本次发送");
		map.put("-5", "数据格式错误");
		map.put("-6", "参数有误");
		map.put("-7", "权限受限");
		map.put("-8", "流量控制错误");
		map.put("-9", "扩展码权限错误");
		map.put("-10", "内容长度长");
		map.put("-11", "内部数据库错误");
		map.put("-12", "序列号状态错误");
		map.put("-14", "服务器写文件失败");
		map.put("-17", "没有权限");
		map.put("-19", "禁止同时使用多个接口地址");
		map.put("-20", "相同手机号，相同内容重复提交");
		map.put("-22", "Ip鉴权失败");
		map.put("-23", "缓存无此序列号信息");
		map.put("-601", "序列号为空，参数错误");
		map.put("-602", "序列号格式错误，参数错误");
		map.put("-603", "密码为空，参数错误");
		map.put("-604", "手机号码为空，参数错误");
		map.put("-605", "内容为空，参数错误");
		map.put("-606", "ext长度大于9，参数错误");
		map.put("-607", "参数错误 扩展码非数字");
		map.put("-608", "参数错误 定时时间非日期格式");
		map.put("-609", "rrid长度大于18,参数错误");
		map.put("-610", "参数错误 rrid非数字");
		map.put("-611", "参数错误 内容编码不符合规范");
		map.put("-623", "手机个数与内容个数不匹配");
		map.put("-624", "扩展个数与手机个数数");
		return map;
	}
	
	
	private static Map<String, String> map;

	static {
		ParseConstant();
	}

	public static String getAttribute(String key) {
		return map.get(key) != null ? map.get(key) : "";
	}
	
	@SuppressWarnings("unchecked")
	private static void ParseConstant() {
		String key = "";
		String value = "";
		map = new HashMap<String, String>();
		SAXReader saxReader = new SAXReader();
		try {
			Document document = saxReader.read(Constants.class.getClassLoader()
					.getResourceAsStream(
							"com/llmj/sms/zucp/util/constant.xml"));
			Element root = document.getRootElement();
			// 遍历根结点root的所有子节点element
			for (Iterator iter = root.elementIterator(); iter.hasNext();) {
				Element element = (Element) iter.next();
				// 遍历结点element的子节点（key，value），并进行处�?
				for (Iterator iterInner = element.elementIterator(); iterInner
						.hasNext();) {
					Element elementInner = (Element) iterInner.next();
					if (elementInner.getName().equals("key")) {
						key = elementInner.getText();
					}
					if (elementInner.getName().equals("value")) {
						value = elementInner.getText();
					}
					if ((!"".equals(key)) && (!"".equals(value)) && (key != null) && (value != null)) {
						map.put(key, value);
						key = null;
						value = null;
					}
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static HashMap<String, String> getDataHashMap(String path) {
		HashMap<String, String> returnValue = new HashMap<String, String>();
		SAXReader saxReader = new SAXReader();
		String key = "";
		String value = "";
		try {
			Document document = saxReader.read(Constants.class.getClassLoader().getResourceAsStream(path));
			Element root = document.getRootElement();
			// 遍历根结点root的所有子节点element
			for (Iterator iter = root.elementIterator(); iter.hasNext();) {
				Element element = (Element) iter.next();
				// 遍历结点element的子节点（key，value），并进行处�?
				for (Iterator iterInner = element.elementIterator(); iterInner.hasNext();) {
					Element elementInner = (Element) iterInner.next();
					if (elementInner.getName().equals("key")) {
						key = elementInner.getText();
					}
					if (elementInner.getName().equals("value")) {
						value = elementInner.getText();
					}
					if ((!"".equals(key)) && (!"".equals(value)) && (key != null) && (value != null)) {
//						System.out.println("key" + key+":"+value);
						returnValue.put(key, value);
						key = null;
						value = null;
					}
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return returnValue;

	}
}
