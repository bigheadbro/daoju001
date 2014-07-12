package com.banzhuan.util;

import java.io.BufferedReader;  

import java.io.IOException;  
  
import java.io.InputStream;  
  
import java.io.InputStreamReader;  
  
import java.net.Authenticator;  
  
import java.net.HttpURLConnection;  
  
import java.net.PasswordAuthentication;  
  
import java.net.URL;  
  
import java.net.URLConnection;  
  
import java.util.Properties;  
  
  
  
public class MailGetter {  
  
    // 一个public方法，返回字符串，错误则返回"error open url"  
  
    public static String getContent(String strUrl) {  
  
        try {  
  
            URL url = new URL(strUrl);  
  
            BufferedReader br = new BufferedReader(new InputStreamReader(url  
  
                    .openStream()));  
  
            String s = "";  
  
            StringBuffer sb = new StringBuffer("");  
  
            while ((s = br.readLine()) != null) {  
  
                sb.append(s + "/r/n");  
  
            }  
  
            br.close();  
  
            return sb.toString();  
  
        } catch (Exception e) {  
  
            return "error open url:" + strUrl;  
  
        }  
  
    }  
  
  
  
    public static void initProxy(String host, int port, final String username,  
  
            final String password) {  
  
        Authenticator.setDefault(new Authenticator() {  
  
            protected PasswordAuthentication getPasswordAuthentication() {  
  
                return new PasswordAuthentication(username,  
  
                        new String(password).toCharArray());  
  
            }  
  
        });  
  
        System.setProperty("http.proxyType", "4");  
  
        System.setProperty("http.proxyPort", Integer.toString(port));  
  
        System.setProperty("http.proxyHost", host);  
  
        System.setProperty("http.proxySet", "true");  
  
    }  
  
  
    public static void main(String[] args) throws IOException, InterruptedException {  
  
         String url;
         
         String curLine = "";  
         String curLine2 ="";
         
         String qq = "";  
         String name = "";  
         int rowCnt = 4;
         int count = 0;
         for(int index = 0;index <= 3373;index++)
         {
        	 Thread.sleep(2000);
        	 url = "http://www.waixie.net/task/index.php?pageNum_treat=" + String.valueOf(index);  
	         URL server = new URL(url);  
	  
	         HttpURLConnection connection = (HttpURLConnection) server.openConnection();  
	  
	         connection.connect();  
	  
	         InputStream is = connection.getInputStream();  
	  
	         BufferedReader reader = new BufferedReader(new InputStreamReader(is));  
	  
	         while ((curLine = reader.readLine()) != null) 
	         {  
	        	 if(StringUtil.isContains(curLine, "class=\"STYLE22 STYLE9\">"))
	        	 {
	        		 int begin =curLine.lastIndexOf("href=\"") + "href=\"".length();
	        		 int end = curLine.indexOf("\" target=\"_blank\"", begin);
	        		 name = "http://www.waixie.net/task/" + curLine.substring(begin,end);

	    	         URL server2 = new URL(name);  
	    	  
	    	         HttpURLConnection connection2 = (HttpURLConnection) server2.openConnection();  
	    	  
	    	         connection2.connect();  
	    	         InputStream is2 = connection2.getInputStream();  
	    	  
	    	         BufferedReader reader2 = new BufferedReader(new InputStreamReader(is2));  
	    	         boolean nextlineismail = false;
	    	         while ((curLine2 = reader2.readLine()) != null) 
	    	         {  
	    	        	 if(StringUtil.isContains(curLine2, "class=\"STYLE37\">"))
	    	        	 {
	    	        		 int begin2 =curLine2.lastIndexOf("class=\"STYLE37\">") + "class=\"STYLE37\">".length();
	    	        		 int end2 = curLine2.indexOf("</span></u></td>", begin);
	    	        		 String company = curLine2.substring(begin2,end2);
	    	        		 System.out.print(company+":");  
	    	        	 } 
	    	        	 if(nextlineismail)
	    	        	 {
	    	        		 nextlineismail = false;
	    	        		 int begin2 =curLine2.lastIndexOf("<span class=\"STYLE19 STYLE35 STYLE36\">") + "<span class=\"STYLE19 STYLE35 STYLE36\">".length();
	    	        		 int end2 = curLine2.indexOf("</span></td>", begin2);
	    	        		 String mail = curLine2.substring(begin2,end2);
	    	        		 System.out.println(mail);
	    	        		 
	    	        	 }
	    	        	 if(StringUtil.isContains(curLine2, "电子信箱"))
	    	        	 {
	    	        		 nextlineismail = true;
	    	        	 } 
	    	  
	    	         }  
	    	         is2.close();
	        	 } 
	  
	         }  
	         is.close();  
  
         }
         
  
    }  
  
}  