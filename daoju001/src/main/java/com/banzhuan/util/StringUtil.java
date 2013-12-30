package com.banzhuan.util;

import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 一个字符串工具类
 * @author yaoyinjie.michael
 * 2012-12-17
 */
public class StringUtil {
	/**
	 * 是否空字符串 null OR "" 都认为是空字符串 返回true. 其他返回 false
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str)
	{
		if(str == null || "".equals(str.trim()))
			return true;
		return false;
	}
	
	/**
	 * 是否空字符串 null OR "" 都认为是空字符串 返回false. 其他返回 true
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str)
	{
		return !isEmpty(str);
	}
	
	/**
	 * 判断一个字符串数组里面的字符串是否都为空 是:返回true, 否: 返回 false
	 * @param strs
	 * @return
	 */
	public static boolean isEmpty(String... strs)
	{
		if(strs == null)
			return true;
		for(String str:strs)
		{
			if(isNotEmpty(str))
				return false;
		}
		return true;
	}
	
	/**
	 * 判断一个字符串数组里面的字符串是否都为空 是:返回false, 否: 返回 true
	 * @param args
	 * @return
	 */
	public static boolean isNotEmpty(String... args)
	{
		return !isEmpty(args);
	}
	
	/**
	 * 判断字符串str1 和 str2 是否相同
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean isEqual(String str1, String str2)
	{
		if(str1 == null && str2 == null)
			return true;
		if(str1 == null)
			return false;
		if(str2 == null)
			return false;
		return str1.equals(str2);
	}
	
	/**
	 * 判断字符串str1 和 str2 是否相同
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean isNotEqual(String str1, String str2)
	{
		return !isEqual(str1, str2);
	}
	
	/**
	 * base64 编码
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptBASE64(String key) throws Exception {
	    return (new BASE64Decoder()).decodeBuffer(key);
	}
	
	/**
	 * base64 编码
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String str){
		MessageDigest md = null;
	    String ret = null;
	    try
	    {
	        md = MessageDigest.getInstance("MD5");
	        byte[] digest = md.digest(str.getBytes());
	        ret = byte2hex(digest);
	    }
	    catch (Exception e)
	    {
	        e.printStackTrace();
	    }
	    return ret;
	}
	
	/**
	 * 字符串1是否包含字符串2
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean isContains(String str1, String str2)
	{
		if(isEmpty(str1) && isEmpty(str2))
			return true;
		if(isNotEmpty(str1) && isEmpty(str2))
			return false;
		if(isEmpty(str1) && isNotEmpty(str2))
			return false;
		return str1.contains(str2);
	}
	
	/**
	 * 判断字符串str1是否包含字符串数组strs中的每一个字符串
	 * @param str1
	 * @param strs
	 * @return
	 */
	public static boolean isContains(String str1, String ...strs)
	{
		if(strs == null)
			return false;
		for(String str:strs)
		{
			if(!isContains(str1, str))
				return false;
		}
		return true;
	}
	
	/**
	 * 判断字符串str1是否包含给定字符串数组的任意一个字符串
	 * @param str1
	 * @param strs
	 * @return
	 */
	public static boolean isContainsAnyOne(String str1, String ...strs)
	{
		if(strs == null)
			return false;
		for(String str:strs)
		{
			if(isContains(str1, str))
				return true;
		}
		return false;
	}
	
	
	private static String byte2hex(byte[] b)
	{
	    String hs = "";
	    String stmp = "";
	    for (int n = 0; n < b.length; n++)
	    {
	        stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
	        if (stmp.length() == 1)
	        {
	            hs = hs + "0" + stmp;
	        }
	        else
	        {
	            hs = hs + stmp;
	        }
	    }
	    return hs.toUpperCase();
	}
	
	
	/**
	 * base64解码
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptBase64(byte[] key) throws Exception {
	    return (new BASE64Encoder()).encodeBuffer(key);
	}
	
	/**
	 * 邮箱正则表达式
	 */
	private static final String EMAIL_REGEX="^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

	/**
	 * IP正则表达式
	 */
	private static final String IP_REGEX="\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b";

	/**
	 * 手机正则表达式
	 */
	private static final String MOBILE_REGEX="^((13[0-9])|(15[^4,\\D])|(18[0,0-9]))\\d{8}$";
	
	/**
	 * 身份证正则表达式
	 */
	private static final String CARD_REGEX="((11|12|13|14|15|21|22|23|31|32|33|34|35|36|37|41|42|43|44|45|46|50|51|52|53|54|61|62|63|64|65|71|81|82|91)\\d{4})((((19|20)(([02468][048])|([13579][26]))0229))|((20[0-9][0-9])|(19[0-9][0-9]))((((0[1-9])|(1[0-2]))((0[1-9])|(1\\d)|(2[0-8])))|((((0[1,3-9])|(1[0-2]))(29|30))|(((0[13578])|(1[02]))31))))((\\d{3}(x|X))|(\\d{4}))";
	
	/**
	 * qq号正则表达式
	 */
	private static final String QQ_REGEX="^[1-9]*[1-9][0-9]*$";
	
	/**
	 * 数字正则表达式
	 */
	private static final String DIGIT_REGEX="[0-9]*";
	/**
	 * 判断是否是数字
	 * @param str
	 * @return
	 */
	public static boolean isDigit(String str)
	{
		if(isEmpty(str))
			return false;
		 Pattern p = Pattern.compile(DIGIT_REGEX); 
		 Matcher m = p.matcher(str);
		 return m.matches();
	}
	
	/**
	 * 判断是否是QQ
	 * @param str
	 * @return 是:true  否: false
	 */
	public static boolean isQQ(String str)
	{
		if(isEmpty(str))
			return false;
		 Pattern p = Pattern.compile(QQ_REGEX); 
		 Matcher m = p.matcher(str);
		 return m.matches();
	}
	
	/**
	 * 判断是否是身份证
	 * @param str
	 * @return 是:true  否: false
	 */
	public static boolean isCard(String str)
	{
		if(isEmpty(str))
			return false;
		 Pattern p = Pattern.compile(CARD_REGEX); 
		 Matcher m = p.matcher(str);
		 return m.matches();
	}
	
	/**
	 * 判断是否是邮箱地址
	 * @param str
	 * @return 是:true  否: false
	 */
	public static boolean isEmail(String str)
	{
		if(isEmpty(str))
			return false;
		 Pattern p = Pattern.compile(EMAIL_REGEX); 
		 Matcher m = p.matcher(str);
		 return m.matches();
	}
	

	
	/**
	 * 判断是否是IP地址
	 * @param str
	 * @return 是:true  否: false
	 */
	public static boolean isIp(String str)
	{
		if(isEmpty(str))
			return false;
		 Pattern p = Pattern.compile(IP_REGEX); 
		 Matcher m = p.matcher(str);
		 return m.matches();
	}
	
	/**
	 * 判断是否是手机号码
	 * @param str
	 * @return 是:true  否: false
	 */
	public static boolean isMobile(String str)
	{
		if(isEmpty(str))
			return false;
		 Pattern p = Pattern.compile(MOBILE_REGEX); 
		 Matcher m = p.matcher(str);
		 return m.matches();
	}
	
	/**
	 * 获取字符串长度, 为空返回-1
	 * @return 
	 */
	public static int getLength(String str)
	{
		if(isEmpty(str))
			return -1;
		return str.length();
	}
	
	/**
	 * 判断一个字符串是否包含另一个字符串
	 * @param ori: 原始串
	 * @param dest: 被包含串
	 * @return
	 */
	public boolean isContain(String ori, String dest)
	{
		if(isEmpty(ori))
			return false;
		if(isEmpty(dest))
			return false;
		if(ori.indexOf(dest)>-1)
			return true;
		return false;
	}
	/**
	 * 判断一个字符串是否包含另一个字符串
	 * @param ori: 原始串
	 * @param dest: 被包含串
	 * @return
	 */
	public boolean isContain(String ori, String dest, String split)
	{
		if(isEmpty(ori))
			return false;
		if(isEmpty(dest))
			return false;
		String[] ss = ori.split(split);
		for(String s:ss)
		{
			if(isEqual(s,dest))
				return true;
		}
		return false;
	}
	
	/**
	 * 统计字符串字符个数， 未区别中文和英文
	 * @param str
	 * @return
	 */
	public static int calStrNum(String str)
	{
		if(isEmpty(str))
			return 0;
		return str.length();
	}
	
	/**
	 * 生成一个唯一的UUID
	 * @return
	 */
	public static String genUUID()
	{
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		if(isNotEmpty(str))
			str=str.replace("-", "");
		return str;
	}
	
	/**
	 * 获取格式化后的日期字符串
	 * @param timeStr 日期字符串
	 * @param srcFormat  原始串格式化表达式
	 * @param desFormat  目标串格式化表达式
	 * @return
	 */
	public static String formatDate(String timeStr, String srcFormat, String desFormat)
	{
		if(isEmpty(timeStr))
		{
			return "";
		}
		try {
			SimpleDateFormat df = new SimpleDateFormat(srcFormat);
			Date date = df.parse(timeStr);
			SimpleDateFormat dateFormat = new SimpleDateFormat(desFormat);
			return dateFormat.format(date);
		} catch (ParseException e) {
		}
		return "";
	}
	
	/**
	 * 获取格式化后的日期字符串(默认原始串格式化表达式为yyyy-MM-dd HH:mm:ss 默认目标串格式化表达式为yyyy-MM-dd)
	 * @param dateStr 日期字符串
	 * @return
	 */
	public static String formatDate(String dateStr)
	{
		return formatDate(dateStr, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd");
	}
	
	/**
	 * 计算指定时间与当前时间的时间差
	 * @param dateStr
	 * @return
	 */
	public static int diffTimeYear(String dateStr, String srcFormat)
	{
		if(isEmpty(dateStr))
		{
			return 0;
		}
		try {
			SimpleDateFormat df = new SimpleDateFormat(srcFormat);
			Date date = df.parse(dateStr);
			Calendar calendar = Calendar.getInstance();
			int now = calendar.get(Calendar.YEAR);
			calendar.setTime(date);
			int before = calendar.get(Calendar.YEAR);
			return now-before;
		} catch (ParseException e) {
		}
		return 0;
	}
	
	/**
	 * 计算指定时间与当前时间的时间差, 默认时间格式串为yyyy-MM-dd HH:mm:ss
	 * @param dateStr
	 * @return
	 */
	public static int diffTimeYear(String dateStr)
	{
		return diffTimeYear(dateStr, "yyyy-MM-dd HH:mm:ss");
	}
	
	public static void main(String[] args)
	{
		System.out.println(diffTimeYear("1985-10-25 00:00:00"));
	}
	
	
	
	private static Map<String, String> industryMap = new HashMap<String, String>();
	private static Map<String, String> hopePositionMap = new HashMap<String, String>();
	
	static
	{
		industryMap.put("3", "男装/女装");
		industryMap.put("4", "箱包/鞋帽");
		industryMap.put("5", "首饰/珠宝");
		industryMap.put("6", "彩妆/护肤");
		industryMap.put("1", "电脑/配件");
		
		industryMap.put("13", "收藏品");
		industryMap.put("2", "手机/数码");
		industryMap.put("14", "家电/办公");
		industryMap.put("9", "母婴/儿童");
		industryMap.put("10", "食品/保健");
		
		industryMap.put("11", "运动/户外");
		industryMap.put("8", "文化/娱乐");
		industryMap.put("16", "玩具");
		industryMap.put("12", "其他");
		
		
		hopePositionMap.put("0", "售前客服");
		hopePositionMap.put("1", "售后客服");
		hopePositionMap.put("2", "客服主管");
		
		
	}
	public static String getIndustry(String key)
	{
		return industryMap.get(key);
	}
	public static String getHopePosition(String key)
	{
		return hopePositionMap.get(key);
	}
	
	public static Map<String, String> genMap()
	{
		return new HashMap<String, String>(10);
	}
	
	public static Map<String, String> genSortMap()
	{
		return new TreeMap<String, String>();
	}
}