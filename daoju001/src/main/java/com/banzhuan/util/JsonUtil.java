package com.banzhuan.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.banzhuan.entity.CuttingToolEntity;

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
	
	public static void showAlert(HttpServletResponse response, String title, String content, String btn, String info, String link){
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
	
	public static void sendAgentInfo(HttpServletResponse response, String name, String logo, String brandname, String brandlink, boolean isverify, int cntAnswer, int cntSample, String phone, String qq){
		JSONObject object = new JSONObject();  
        response.setContentType("text/Xml;charset=gbk");  
        object.element("name", name);
        object.element("logo", logo);
        object.element("brandname", brandname);
        object.element("brandlink", brandlink);
        object.element("isverify", isverify);
        object.element("cntAnswer", cntAnswer);
        object.element("cntSample", cntSample);
        object.element("phone", phone);
        object.element("qq", qq);
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
	
	public static void sendAnswernt(HttpServletResponse response,int state, String content, String price, boolean freeuse)
	{
		JSONObject object = new JSONObject();  
        response.setContentType("text/Xml;charset=gbk");  

        object.element("state", state);
        object.element("content", content);
        object.element("price", price);
        object.element("freeuse", freeuse);
        
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
	
	public static void sendAddress(HttpServletResponse response,String pca, String detail, String name, String zip, String phone, int id, boolean isdefault, boolean isedit)
	{
		JSONObject object = new JSONObject();  
        response.setContentType("text/Xml;charset=gbk");  

        object.element("pca", pca);
        object.element("detail", detail);
        object.element("name", name);
        object.element("zip", zip);
        object.element("phone", phone);
        object.element("id", id);
        object.element("isdefault", isdefault);
        object.element("isedit", isedit);
        
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
	
	public static void sendComment(HttpServletResponse response, String comment, String userName, String logo, String brandName, String verifiedLink, String time,int commentid)
	{
		JSONObject object = new JSONObject();  
        response.setContentType("text/Xml;charset=gbk");  
        object.element("comment", comment);
        object.element("userName", userName);
        object.element("logo", logo);
        object.element("brandName", brandName);
        object.element("verifiedLink", verifiedLink);
        object.element("time", time);
        object.element("commentid", commentid);
        
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
	
	public static void sendFileLink(HttpServletResponse response, String addr)
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
	
	public static void checkAnswerStatus(HttpServletResponse response, int status)
	{
		JSONObject object = new JSONObject();  
		String strStatus = "";
        response.setContentType("text/Xml;charset=gbk");  
       	switch(status)
       	{
       	case 1:
       		strStatus = "登录后，才可以回答问题";
       		break;
       	case 2:
       		strStatus = "您的账号没有提供解决方案的权限，请升级账户或者咨询客服查询详情。";
       		break;
       	case 3:
       		strStatus = "未认证代理商不能提供专业解决方案，需要认证请联系我们！";
       		break;
       	case 4:
       		strStatus = "";
       	}
	
        object.element("status", status);
        object.element("code", strStatus);
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
	
	public static void checkAuthStatus(HttpServletResponse response, int status)
	{
		JSONObject object = new JSONObject();  
		String strStatus = "";
		int mid = 0;
        response.setContentType("text/Xml;charset=gbk");  
       	switch(status)
       	{
       	case 1://未登录
       		strStatus = "登录之后，才可以升级账户。";
       		break;
       	case 2:
       		strStatus = "你已经拥有该权限，无需升级账户。";
       		break;
       	case 3:
       		mid = 1;
       		break;
       	case 4:
       		mid = 2;
       	}
	
        object.element("status", status);
        object.element("code", strStatus);
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
	
	public static void checkAskStatus(HttpServletResponse response, int status)
	{
		JSONObject object = new JSONObject();  
		String strStatus = "";
        response.setContentType("text/Xml;charset=gbk");  
       	switch(status)
       	{
       	case 1:
       		strStatus = "";
       		break;
       	case 2:
       		strStatus = "普通用户不能提供专业解决方案，请使用回复进行交流！";
       		break;
       	case 3:
       		strStatus = "代理商不能提问！";
       		break;
       	case 4:
       		strStatus = "";
       	}
	
        object.element("status", status);
        object.element("code", strStatus);
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
	
	public static void sendMsgCount(HttpServletResponse response, int msgCount)
	{
		JSONObject object = new JSONObject();  
        response.setContentType("text/Xml;charset=gbk");  
	
        object.element("msgCount", msgCount);
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
	
	public static void showErrorMsg(HttpServletResponse response)
	{
		PrintWriter out = null;  
        try {  
            out = response.getWriter();  
            out.println("<script type=\"text/javascript\">");  
    		out.println("$(\".error-msg\").attr(\"display\",\"block\");");  
    		out.println("</script>");
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
	
	public static void sendSingleString(HttpServletResponse response, String str)
	{
		JSONObject object = new JSONObject();  
        response.setContentType("text/Xml;charset=gbk");  
        object.element("str", str);
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
	
	public static void sendTwoString(HttpServletResponse response, String str1, String str2)
	{
		JSONObject object = new JSONObject();  
        response.setContentType("text/Xml;charset=gbk");  
        object.element("str1", str1);
        object.element("str2", str2);
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
	
	public static void sendCts(HttpServletResponse response, List<CuttingToolEntity> list)
	{
		JSONArray jsonArray = JSONArray.fromObject(list);
		JSONObject object = new JSONObject();  
        response.setContentType("text/Xml;charset=gbk");  
        object.element("cts", jsonArray);
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
	
	public static void sendSeriesList(HttpServletResponse response, List<CuttingToolEntity> list, Map<String,List<String>> map)
	{
		JSONObject object = new JSONObject();  
		String str = "";
		for(int i = 0;i < list.size();i++)
		{
			str += "<a class=\"series-item clearfix\" href=\"/detail/"+list.get(i).getId()+"\">"
					+"<div class=\"series-pic\">"
					+"<img src=\"/img/series/"+list.get(i).getCover()+"\" />"
					+"</div>"
					+"<div class=\"series-params\">"
					+"<h3>"+list.get(i).getSeriesname()+"</h3>"
					+"<h4>"+CuttingToolsConfiguration.getSeriesParamSpan(list.get(i), list.get(i).getSamecolume())+"</h4>"
					+"<p>"+list.get(i).getOutline()+"</p>"
					+"</div>"
					+"</a>";
		}
        response.setContentType("text/Xml;charset=gbk");  
        object.element("cts", str);
        object.element("map",map);
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
