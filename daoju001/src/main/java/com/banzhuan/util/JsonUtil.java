package com.banzhuan.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtil {
	private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);
	private static final String XINLANG_IP_URL = "http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=";
	private static final String TAOBAO_IP_URL = "http://ip.taobao.com/service/getIpInfo.php?ip=";

	public static void main(String[] args) throws IOException {
		System.out.println(getProvinceAndCity("180.153.101.64"));
	}

	public static Map<String, String> getProvinceAndCity(String ip) {
		String str;
		Map<String, String> map = null;
		try {
			str = getJsonStr(XINLANG_IP_URL + ip);
			map = parserProvinceAndCityFromXinLang(str);
			if (map.size() >= 2)
				return map;
			str = getJsonStr(TAOBAO_IP_URL + ip);
			map = parserProvinceAndCityFromTaoBao(str);
		} catch (IOException e) {
			logger.error("get ip local json is error:" + e.getMessage());
		}
		return map;
	}

	private static Map<String, String> parserProvinceAndCityFromXinLang(
			String str) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			JSONObject jsonObject = JSONObject.fromObject(str);
			map.put("province", jsonObject.getString("province"));
			map.put("city", jsonObject.getString("city"));
		} catch (Exception e) {
			logger.error("get ip local from xinlang is error:" + e.getMessage());
		}
		return map;
	}

	private static Map<String, String> parserProvinceAndCityFromTaoBao(
			String str) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			JSONObject jsonObject = JSONObject.fromObject(str);
			jsonObject = jsonObject.getJSONObject("data");
			if (jsonObject == null)
				return map;
			map.put("province", jsonObject.getString("region"));
			map.put("city", jsonObject.getString("city"));
		} catch (Exception e) {
			logger.error("get ip local from taobao is error:" + e.getMessage());
		}
		return map;
	}

	private static String getJsonStr(String urlStr) throws IOException {
		StringBuffer sb = new StringBuffer();
		java.net.URL url = new java.net.URL(urlStr);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				url.openStream()));
		String line;
		while ((line = in.readLine()) != null) {
			sb.append(line);
		}
		in.close();
		return sb.toString();
	}

	/**
	 * 将java对象转换成json字符串
	 * 
	 * @param javaObj
	 * @return
	 */
	public static String getJsonStringFromObject(Object javaObj) {
		JSONObject json;
		json = JSONObject.fromObject(javaObj);
		return json.toString();
	}
	
	public static void showAlert(HttpServletResponse response, String title, String content, String btn, String link, String info){
		JSONObject object = new JSONObject();  
        response.setContentType("text/Xml;charset=gbk");  
        object.element("title", title);
        object.element("content", content);
        object.element("btn", btn);
        object.element("link", link);
        object.element("info", info);
        PrintWriter out = null;  
        try {  
            out = response.getWriter();  
            out.println(object.toString());  
        }  
        catch (IOException ex1) {  
            ex1.printStackTrace();  
        }  
        finally {  
            out.close();  
        }  
	}
	
	public static void sendImg(HttpServletResponse response, String addr)
	{
		JSONObject object = new JSONObject();  
        response.setContentType("text/Xml;charset=gbk");  
        object.element("addr", addr);
        PrintWriter out = null;  
        try {  
            out = response.getWriter();  
            out.println(object.toString());  
        }  
        catch (IOException ex1) {  
            ex1.printStackTrace();  
        }  
        finally {  
            out.close();  
        }  
	}
	
	public static void sendLoginError(HttpServletResponse response, String mail, String pwd)
	{
		JSONObject object = new JSONObject();  
        response.setContentType("text/Xml;charset=gbk");  
        object.element("mail", mail);
        object.element("pwd", pwd);
        PrintWriter out = null;  
        try {  
            out = response.getWriter();  
            out.println(object.toString());  
        }  
        catch (IOException ex1) {  
            ex1.printStackTrace();  
        }  
        finally {  
            out.close();  
        }  
	}
}
