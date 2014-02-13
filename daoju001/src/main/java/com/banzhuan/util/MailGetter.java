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
  
/*         String proxy = "http://192.168.22.81";  
  
         int port = 80;  
  
         String username = "username";  
  
         String password = "password";  
  
         initProxy(proxy, port, username, password);  */
         
         String curLine = "";  
         
         String qq = "";  
         String name = "";  
         int rowCnt = 4;
         int count = 0;
         for(int index = 1;index <= 474;index++)
         {
        	 Thread.sleep(5000);
        	 url = "http://company.cut35.com/pv-s-v-p"+ String.valueOf(index) + ".html";  
	         URL server = new URL(url);  
	  
	         HttpURLConnection connection = (HttpURLConnection) server.openConnection();  
	  
	         connection.connect();  
	  
	         InputStream is = connection.getInputStream();  
	  
	         BufferedReader reader = new BufferedReader(new  
	  
	         InputStreamReader(is));  
	  
	         while ((curLine = reader.readLine()) != null) 
	         {  
	        	 if(StringUtil.isContains(curLine, "<a href=\"http://wpa.qq.com/msgrd?V=1&amp;Uin="))
	        	 {
	        		 int begin =curLine.lastIndexOf("&amp;Site=") + "&amp;Site=".length();
	        		 int end = curLine.indexOf("&", begin);
	        		 name = curLine.substring(begin,end);
	        		 System.out.print(name+":");
	        		 begin = curLine.lastIndexOf("<a href=\"http://wpa.qq.com/msgrd?V=1&amp;Uin=") + "<a href=\"http://wpa.qq.com/msgrd?V=1&amp;Uin=".length();
	        		 end = curLine.indexOf("&", begin);
	        		 qq = curLine.substring(begin,end);
	        		 System.out.print(qq+"@qq.com;");  
	        		 count++;
	        		 if(count%rowCnt == 0)
	        		 {
	        			 System.out.print("\n");
	        		 }
	        	 } 
	  
	         }  
	         is.close();  
  
         }
         
  
    }  
  
}  