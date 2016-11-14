package com.llmj.util.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Common {

	/**
	 * 判断变量是否为空
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s) {
		if (null == s || "".equals(s) || "".equals(s.trim())
		        || "null".equalsIgnoreCase(s)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 返回当前时间 格式：yyyy-MM-dd hh:mm:ss
	 * 
	 * @return String
	 */
	public static String fromDateH() {
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return format1.format(new Date());
	}

	/**
	 * 返回当前时间 格式：yyyy-MM-dd
	 * 
	 * @return String
	 */
	public static String fromDateY() {
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		return format1.format(new Date());
	}
	
	/**
	 * 字符串格式日期转Date类型
	 *<p>getDate()</p>
	 * @author Hanzhonghua
	 * @data 2016年7月12日上午10:18:37
	 * @param date
	 * @return
	 * @throws ParseException Date
	 */
	public static Date getDate(String date) {
		if (date != null && !"".equals(date)) {
			try {
				Date d = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(date);
				return d;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 验证手机号码是否正确 Common.isPhone()<BR>
	 * <P>
	 * Author : pandaijun
	 * </P>
	 * <P>
	 * Date : 2015-9-24
	 * </P>
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isPhone(String s) {
		Pattern p = Pattern.compile(
		        "^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\\d{8})$");
		Matcher m = p.matcher(s);
		return m.matches();
	}

	/**
	 * 验证用户密码是否正确 Common.isPassword()<BR>
	 * <P>
	 * Author : pandaijun
	 * </P>
	 * <P>
	 * Date : 2015-9-24
	 * </P>
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isPassword(String s) {
		return s.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6}$");
	}

	/**
	 * 验证用户支付密码是否填写正确 Common.isPassword()<BR>
	 * <P>
	 * Author : pandaijun
	 * </P>
	 * <P>
	 * Date : 2015-9-24
	 * </P>
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isPayPassword(String s) {
		return s.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$");
	}

	/**
	 * 判断是否是数字 Common.isInteger()<BR>
	 * <P>
	 * Author : zhuzhou
	 * </P>
	 * <P>
	 * Date : 2015-9-28
	 * </P>
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isInteger(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean checkAmount(String amount) {
		java.util.regex.Pattern pattern = java.util.regex.Pattern
		        .compile("^((\\d{0,7})|([0]{1}))(\\.(\\d){0,2})?$"); // 最大为9999999.99
		                                                             // 最小为0
		Matcher matcher = pattern.matcher(amount);
		return matcher.matches();
	}

	/**
	 * 当前时间距离当天23:59:59的秒差
	 * @Title: getGapTime
	 * @Description: 
	 * @author Hanzhonghua
	 * @date 2016年10月31日 下午3:17:39 
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static int getGapTime(){
		Calendar c = new GregorianCalendar();
	    c.set(Calendar.HOUR_OF_DAY, 23);
	    c.set(Calendar.MINUTE, 59);
	    c.set(Calendar.SECOND, 59);
	    long time = new Date().getTime();
	    int l = (int) ((c.getTimeInMillis()-time)/1000);
	    //System.out.println("秒时间差："+l);
	    return l;
	}
	
	//格式化id
	public static String getId(String idType) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		String id = sdf.format(new Date());
		return idType + id;
	}
	
	public static void main(String[] args) throws ParseException {
		Date date = getDate("2011-11-11 20:20:20");
		System.out.println(date);
	}

}
