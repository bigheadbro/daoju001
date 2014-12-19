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
        response.setContentType("text/json;charset=gbk");  
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
        response.setContentType("text/json;charset=gbk");  
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
        response.setContentType("text/json;charset=gbk");  
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
        response.setContentType("text/json;charset=gbk");  

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
        response.setContentType("text/json;charset=gbk");  

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
        response.setContentType("text/json;charset=gbk");  
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
        response.setContentType("text/json;charset=gbk");  
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
        response.setContentType("text/json;charset=gbk");  
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
        response.setContentType("text/json;charset=gbk");  
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
        response.setContentType("text/json;charset=gbk");  
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
        response.setContentType("text/json;charset=gbk");  
	
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
        response.setContentType("text/json;charset=gbk");  
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
        response.setContentType("text/json;charset=gbk");  
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
        response.setContentType("text/json;charset=gbk");  
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
        response.setContentType("text/json;charset=gbk");  
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
        response.setContentType("text/json;charset=gbk");  
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
	
	public static void sendVersionsList(HttpServletResponse response, List<CuttingToolEntity> list, Map<String,List<String>> map)
	{
		JSONObject object = new JSONObject();  
		String str = "<table class=\"version-table\"><thead><tr>";
		str += CuttingToolsConfiguration.getParamsHtml(list) + "</tr></thead><tbody>";
		String param = CuttingToolsConfiguration.getParamsHtml(list);
		for(int i = 0;i < list.size();i++)
		{
			str += "<tr>";
			str += "<td>"+list.get(i).getVersion() +"</td>";
		    if(StringUtil.isContains(param,">材质<"))
		        str += "<td>"+list.get(i).getMaterial() +"</td>";
		    
		    if(StringUtil.isContains(param,">主偏角<"))
		        str += "<td>"+list.get(i).getAngle() +"</td>";
		    
		    if(StringUtil.isContains(param,">刀片个数<"))
		        str += "<td>"+list.get(i).getCtcount() +"</td>";
		    
		    if(StringUtil.isContains(param,">直径<"))
		        str += "<td>"+list.get(i).getDiameter() +"</td>";
		    
		    if(StringUtil.isContains(param,">加工用途<"))
		        str += "<td>"+StringUtil.replaceSemicolon(list.get(i).getUsage())+"</td>";
		    
		    if(StringUtil.isContains(param,">光洁度<"))
		    {
		        if(list.get(i).getCujing() == 2)
				{
		        	str += "<td>精加工用</td>";
				}
				else if(list.get(i).getCujing() == 3)
				{
					str += "<td>粗加工用</td>";
				}
				else
				{
					str += "<td>一般加工用</td>";
				}
		    }
		    if(StringUtil.isContains(param,">有效长<"))
		        str += "<td>"+list.get(i).getUsefullength() +"</td>";
		    
		    if(StringUtil.isContains(param,">安装孔接口<"))
		        str += "<td>"+list.get(i).getPipesize() +"</td>";
		    
		    if(StringUtil.isContains(param,">柄径<"))
		        str += "<td>"+list.get(i).getShank() +"</td>";
		    
		    if(StringUtil.isContains(param,">柄部类型<"))
		        str += "<td>"+list.get(i).getShanktype() +"</td>";
		    
		    if(StringUtil.isContains(param,">形状<"))
		    {
		        if(StringUtil.isEqual(list.get(i).getShape() , "H"))
				{
					str += "<td>正六角形</td>";
				}
				else if(StringUtil.isEqual(list.get(i).getShape() , "O"))
				{
					str += "<td>八角形</td>";
				}
				else if(StringUtil.isEqual(list.get(i).getShape() , "P"))
				{
					str += "<td>五角形</td>";
				}
				else if(StringUtil.isEqual(list.get(i).getShape() , "S"))
				{
					str += "<td>正方形</td>";
				}
				else if(StringUtil.isEqual(list.get(i).getShape() , "T"))
				{
					str += "<td>三角形</td>";
				}
				else if(StringUtil.isEqual(list.get(i).getShape() , "C"))
				{
					str += "<td>菱形80°</td>";
				}
				else if(StringUtil.isEqual(list.get(i).getShape() , "D"))
				{
					str += "<td>菱形55°</td>";
				}
				else if(StringUtil.isEqual(list.get(i).getShape() , "E"))
				{
					str += "<td>菱形75°</td>";
				}
				else if(StringUtil.isEqual(list.get(i).getShape() , "F"))
				{
					str += "<td>菱形50°</td>";
				}
				else if(StringUtil.isEqual(list.get(i).getShape() , "M"))
				{
					str += "<td>菱形86°</td>";
				}
				else if(StringUtil.isEqual(list.get(i).getShape() , "V"))
				{
					str += "<td>菱形35°</td>";
				}
				else if(StringUtil.isEqual(list.get(i).getShape() , "W"))
				{
					str += "<td>不等角六角形</td>";
				}
				else if(StringUtil.isEqual(list.get(i).getShape() , "L"))
				{
					str += "<td>长方形90°</td>";
				}
				else if(StringUtil.isEqual(list.get(i).getShape() , "A"))
				{
					str += "<td>平行四边形顶角55°</td>";
				}
				else if(StringUtil.isEqual(list.get(i).getShape() , "R"))
				{
					str += "<td>圆形</td>";
				}
		    }
		    
		    if(StringUtil.isContains(param,">后角<"))
		        str += "<td>"+list.get(i).getBackangle() +"</td>";
		    
		    if(StringUtil.isContains(param,">适用工件<"))
		        str += "<td>"+StringUtil.replaceSemicolon(list.get(i).getWorkingtool())+"</td>";
		    
		    if(StringUtil.isContains(param,">刃数<"))
		        str += "<td>"+list.get(i).getEdgeno() +"</td>";
		    
		    if(StringUtil.isContains(param,">刃长<"))
		        str += "<td>"+list.get(i).getEdgelength() +"</td>";
		    
		    if(StringUtil.isContains(param,">总长<"))
		        str += "<td>"+list.get(i).getTotallength() +"</td>";
		    
		    if(StringUtil.isContains(param,">螺旋角<"))
		        str += "<td>"+list.get(i).getScrewangle() +"</td>";
		    
		    if(StringUtil.isContains(param,">涂层种类<"))
		        str += "<td>"+list.get(i).getCoatingtype() +"</td>";
		    
		    if(StringUtil.isContains(param,">R角<"))
		        str += "<td>"+list.get(i).getRangle() +"</td>";
		    
		    if(StringUtil.isContains(param,">方向<"))
		    {
		    	if(list.get(i).getDirection() == 2)
				{
					str += "<td>左手</td>";
				}
				else if(list.get(i).getDirection() == 3)
				{
					str += "<td>右手</td>";
				}
				else if(list.get(i).getDirection() == 1)
				{
					str += "<td>通用槽</td>";
				}
		    }

		    if(StringUtil.isContains(param,">最小加工直径<"))
		        str += "<td>"+list.get(i).getMinworkdiameter() +"</td>";
		    
		    if(StringUtil.isContains(param,">冷却方式<"))
		    {
		    	if(list.get(i).getInnercooling() == 1)
		    		str += "<td>一般</td>";
		    	else if(list.get(i).getInnercooling() == 2)
		    		str += "<td>内冷</td>";
		    	else if(list.get(i).getInnercooling() == 3)
		    		str += "<td>外冷</td>";
		    }
		    if(StringUtil.isContains(param,">倍径比<"))
		        str += "<td>"+list.get(i).getDiameterratio() +"</td>";
		    
		    if(StringUtil.isContains(param,">槽型状<"))
		        str += "<td>"+list.get(i).getSlotshape() +"</td>";
		    
		    if(StringUtil.isContains(param,">柄部规格<"))
		        str += "<td>"+list.get(i).getHandlenorm() +"</td>";
		    
		    if(StringUtil.isContains(param,">丝锥种类<"))
		        str += "<td>"+list.get(i).getTaptype() +"</td>";
		    
		    if(StringUtil.isContains(param,">螺纹类型<"))
		        str += "<td>"+list.get(i).getScrewtype() +"</td>";
		    
		    if(StringUtil.isContains(param,">主轴类型<"))
		        str += "<td>"+list.get(i).getAxistype() +"</td>";
		    
		    if(StringUtil.isContains(param,">主轴细分<"))
		        str += "<td>"+list.get(i).getAxisdetail() +"</td>";
		    
		    if(StringUtil.isContains(param,">厚度<"))
		        str += "<td>"+list.get(i).getThickness() +"</td>";
		    
		    if(StringUtil.isContains(param,">最大槽深<"))
		        str += "<td>"+list.get(i).getTaper() +"</td>";
		    
		    if(StringUtil.isContains(param,">锥度<"))
		        str += "<td>"+list.get(i).getMaxslotdepth() +"</td>";
		    
		    if(StringUtil.isContains(param,">槽宽<"))
		        str += "<td>"+list.get(i).getSlotwidth() +"</td>";
		    
		    if(StringUtil.isContains(param,">刀尖直径<"))
		        str += "<td>"+list.get(i).getPointdiameter() +"</td>";
		    
		    if(StringUtil.isContains(param,">可加持尺寸<"))
		        str += "<td>"+list.get(i).getHandledsize() +"</td>";
		    
		    if(StringUtil.isContains(param,">螺纹尺寸<"))
		        str += "<td>"+list.get(i).getScrewsize() +"</td>";
		    
		    if(StringUtil.isContains(param,">螺距<"))
		        str += "<td>"+list.get(i).getScrewdistance() +"</td>";
		    
		    if(StringUtil.isContains(param,">精度<"))
		        str += "<td>"+list.get(i).getAccuracy() +"</td>";
		    
		    if(StringUtil.isContains(param,">接口尺寸<"))
		        str += "<td>"+list.get(i).getInterfacesize() +"</td>";
		    
		    if(StringUtil.isContains(param,">镗孔上限<"))
		        str += "<td>"+list.get(i).getMaxbore() +"</td>";
		    
		    if(StringUtil.isContains(param,">镗孔下限<"))
		        str += "<td>"+list.get(i).getMinbore() +"</td>";
		    
		    if(StringUtil.isContains(param,">颈长<"))
		        str += "<td>"+list.get(i).getNecklength() +"</td>";
		    
		    if(StringUtil.isContains(param,">对应筒夹"))
		        str += "<td>"+list.get(i).getRelativecollet() +"</td>";
		    
		    if(StringUtil.isContains(param,">宽度"))
		        str += "<td>"+list.get(i).getWidth() +"</td>";
		    
		    if(StringUtil.isContains(param,">高度"))
		        str += "<td>"+list.get(i).getHeight() +"</td>";
		    
		    if(StringUtil.isContains(param,">切槽范围"))
		        str += "<td>"+list.get(i).getGrooverange() +"</td>";
		    
		    if(StringUtil.isContains(param,">钻孔范围"))
		        str += "<td>"+list.get(i).getDrillrange() +"</td>";
		    
		    if(StringUtil.isContains(param,">螺纹方向"))
		        str += "<td>"+list.get(i).getScrewdirection() +"</td>";
		    
		    str += "<tr>";
		}
		str += "</tbody></table>";
        response.setContentType("text/json;charset=gbk");  
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
