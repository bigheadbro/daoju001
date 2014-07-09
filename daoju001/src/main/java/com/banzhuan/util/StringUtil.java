package com.banzhuan.util;

import java.security.MessageDigest;
import java.text.DateFormat;
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
	
	public static boolean isEqualex(int str1, int str2)
	{
		return str1 == str2;
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
	
	public static boolean isIlegal(String str)
	{
		return isContains(str,"刀师傅") || isContains(str.toLowerCase(),"daoshifu");
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
			SimpleDateFormat todayFormat = new SimpleDateFormat("HH:mm");
			if(isToday(date))
			{
				return todayFormat.format(date);
			}
			else
			{
				return dateFormat.format(date);
			}
		} catch (ParseException e) {
		}
		return "";
	}
	
	public static boolean isToday(Date date)
	{
        Date now = new Date();

        boolean result = true;

        result &= date.getYear()==now.getYear();

        result &= date.getMonth()==now.getMonth();

        result &= date.getDate()==now.getDate();

        return result;

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
		System.out.print(isIlegal("daoshifu"));
	}
	
	public static boolean isProperImageFile(String type)
	{
		if(isEqual(type,".jpg") || isEqual(type , ".jpeg") || isEqual(type , ".gif") || isEqual(type , ".png"))
		{
			return true;
		}

		return false;
	}
	
	public static String getTodayString()
	{
		Date currentTime = new Date();
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
	    String dateString = formatter.format(currentTime);
		return dateString;
	}
	
	public static String getCurrentDate()
	{
		Date currentTime = new Date();
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	    String dateString = formatter.format(currentTime);
		return dateString;
	}
	
	public static String getCurrentTime()
	{
		Date currentTime = new Date();
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	    String dateString = formatter.format(currentTime);
		return dateString;
	}
	
	public static String convertDate(String str)
	{
		if(str == null)
		{
			return null;
		}
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return df.format(date);
	}
	public static String convertToChar(int i)
	{
		String str = "";
		char c = (char)(i + 96);
		str = String.valueOf(c).toUpperCase();
		return str;
	}
	
	public static String fileType(String type)
	{
		return type.substring(1);
	}
	
	public static String fileSize(double size)
	{
		if(String.valueOf(size).length()>=5)
			return String.valueOf(size).substring(0,4);
		return String.valueOf(size);
	}
	private static Map<Integer, String> industryMap = new HashMap<Integer, String>();
	private static Map<Integer, String> processMethodMap = new HashMap<Integer, String>();
	private static Map<Integer, String> wpMaterialMap = new HashMap<Integer, String>();
	private static Map<Integer, String> wpHardnessMap = new HashMap<Integer, String>();
	private static Map<Integer, String> msgTypeMap = new HashMap<Integer, String>();
	private static Map<Integer, String> brandMap = new HashMap<Integer, String>();
	
	static
	{
		brandMap.put(1, "EMUGE,EMUGE.jpg,德国");
		brandMap.put(2, "日立,HITACHI.jpg,日本");
		brandMap.put(3, "INGERSOLL,INGERSOLL.jpg,德国");
		brandMap.put(4, "克洛伊,KORLOY.jpg,韩国");
		brandMap.put(5, "蓝帜,蓝帜.jpg,德国");
		brandMap.put(6, "京瓷,京瓷.jpg,日本");
		brandMap.put(7, "MANYO,MANYO.jpg,日本");
		brandMap.put(8, "玛帕,MAPAL.jpg,德国");
		brandMap.put(9, "铣星,MILLSTAR.jpg,美国");
		brandMap.put(10, "MST,MST.jpg,日本");
		brandMap.put(11, "NACHI,NACHI.jpg,日本");
		brandMap.put(12, "日研,NIKKEN.jpg,日本");
		brandMap.put(13, "NTK,NTK.jpg,日本");
		brandMap.put(14, "REGO-FIX,REGO-FIX.jpg,瑞士");
		brandMap.put(15, "山特维克,SANDIVK.jpg,瑞典");
		brandMap.put(16, "VARGUS,VARGUS.jpg,以色列");
		brandMap.put(17, "YAMAWA,YAMAWA.jpg,日本");
		brandMap.put(18, "YG,YG.jpg,韩国");
		brandMap.put(19, "安威,安威.jpg,台湾");
		brandMap.put(20, "大昭和,大昭和.jpg,日本");
		brandMap.put(21, "WIDIA,WIDIA.jpg,德国");
		brandMap.put(22, "WALTER,瓦尔特.jpg,德国");
		brandMap.put(23, "SAFETY,SAFETY.jpg,法国");
		brandMap.put(24, "弗雷萨,弗雷萨.jpg,瑞士");
		brandMap.put(25, "钴领,钴领.jpg,德国");
		brandMap.put(26, "肯纳,肯纳.jpg,美国");
		brandMap.put(27, "绿叶,绿叶.jpg,美国");
		brandMap.put(28, "名古屋,名古屋.jpg,中国");
		brandMap.put(29, "黛杰,黛杰.jpg,日本");
		brandMap.put(30, "三菱,三菱.jpeg,日本");
		brandMap.put(31, "斯特拉姆,斯特拉姆.jpg,瑞士");
		brandMap.put(32, "泰珂洛,泰珂洛.jpg,日本");
		brandMap.put(33, "特固克,特固克.jpg,韩国");
		brandMap.put(34, "雄克,雄克.jpg,德国");
		brandMap.put(35, "伊斯卡,伊斯卡.jpg,以色列");
		brandMap.put(36, "优能,优能.jpg,日本");
		brandMap.put(37, "正河源,正河源.jpg,台湾");
		brandMap.put(38, "株洲钻石,株洲钻石.jpg,中国");
		brandMap.put(39, "住友,住友.jpeg,日本");
		brandMap.put(40, "GARRTOOL,GARRTOOL.jpg,美国");
		brandMap.put(41, "DINE,DINE.jpg,韩国");
		brandMap.put(42, "DORMER,DORMER.jpg,英国");
		brandMap.put(43, "VERGNANO,VERGNANO.jpg,意大利");
		brandMap.put(44, "ARNO,ARNO.jpg,德国");
		brandMap.put(45, "華菱超硬,HUALING.jpg,中国");
		brandMap.put(46, "ZEUS,ZEUS.jpg,德国");
		brandMap.put(47, "OSG,osg.jpg,日本");
		brandMap.put(48, "瑞德卡特,瑞德卡特.jpg,瑞士");
		brandMap.put(49, "圣和,圣和.jpg,日本");
		brandMap.put(50, "拿海纳,拿海纳.jpg,德国");
		brandMap.put(51, "阿诺,ahno.jpg,中国");
		brandMap.put(52, "TIZ,TIZ.jpg,波兰");
		brandMap.put(53, "世邦,世邦.jpg,台湾");
		brandMap.put(54, "田野井,田野井.png,日本");
		
		brandMap.put(10000, "刀师傅,刀师傅.jpg,中国");
		
		
		msgTypeMap.put(1, "提供了专业解决方案");
		msgTypeMap.put(2, "回复了你");
		
		industryMap.put(0, "不限");
		industryMap.put(1, "模具");
		industryMap.put(2, "船舶");
		industryMap.put(3, "石油设备");
		industryMap.put(4, "散热器/空调");
		industryMap.put(5, "发电设备");
		industryMap.put(6, "塑料机械");
		industryMap.put(7, "内燃机");
		industryMap.put(8, "大型机械设备");
		industryMap.put(9, "汽车零件");
		industryMap.put(10, "电子行业");
		industryMap.put(11, "通用零件(阀、泵、螺栓等)");
		industryMap.put(12, "其他");
		
		processMethodMap.put(0, "不限");
		processMethodMap.put(1, "平面铣削");
		processMethodMap.put(2, "侧壁铣削");
		processMethodMap.put(3, "曲面铣削");
		processMethodMap.put(4, "铣槽");
		processMethodMap.put(5, "钻孔");
		processMethodMap.put(6, "镗孔");
		processMethodMap.put(7, "铰孔");
		processMethodMap.put(8, "螺纹铣削");
		processMethodMap.put(9, "车削(粗加工）");
		processMethodMap.put(10, "车削(槽加工）");
		processMethodMap.put(11, "车削(螺纹加工）");
		processMethodMap.put(12, "去毛刺");
		
		wpMaterialMap.put(0, "不限");
		wpMaterialMap.put(1, "钢");
		wpMaterialMap.put(2, "铸铁");
		wpMaterialMap.put(3, "不锈钢");
		wpMaterialMap.put(4, "高温合金");
		wpMaterialMap.put(5, "铝");
		wpMaterialMap.put(6, "有色金属");
		wpMaterialMap.put(7, "石墨");
		wpMaterialMap.put(8, "树脂");

		wpHardnessMap.put(0, "不限");
		wpHardnessMap.put(1, "HRC20以下");
		wpHardnessMap.put(2, "HRC20-30");
		wpHardnessMap.put(3, "HRC30-40");
		wpHardnessMap.put(4, "HRC40-50");
		wpHardnessMap.put(5, "HRC50-58");
		wpHardnessMap.put(6, "HRC58-62");
		wpHardnessMap.put(7, "HRC62-65");
		wpHardnessMap.put(8, "HRC65-68");
		wpHardnessMap.put(9, "HRC68以上");
		
	}
	
	public static String getBrand(int key)
	{
		if(key == 0)
			return "";
		return brandMap.get(key).split(",")[0];
	}
	
	public static String getBrandLogo(int key)
	{
		if(key == 0)
			return "";
		return brandMap.get(key).split(",")[1];
	}
	
	public static String getBrandCountry(int key)
	{
		if(key == 0)
			return "";
		return brandMap.get(key).split(",")[2];
	}
	
	public static String getIndustry(int key)
	{
		return industryMap.get(key);
	}
	
	public static String getMaterial(int key)
	{
		return wpMaterialMap.get(key);
	}
	
	public static String getHardness(int key)
	{
		return wpHardnessMap.get(key);
	}
	
	public static String getMethod(int key)
	{
		return processMethodMap.get(key);
	}
	public static Map<String, String> genMap()
	{
		return new HashMap<String, String>(10);
	}
	
	public static Map<String, String> genSortMap()
	{
		return new TreeMap<String, String>();
	}
	
	public static void test(boolean b)
	{
		boolean a = b;
		String.valueOf(a);
	}
	
}
