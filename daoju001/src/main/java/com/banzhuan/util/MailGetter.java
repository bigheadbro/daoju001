package com.banzhuan.util;

import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import java.net.Authenticator;
import java.net.MalformedURLException;

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

	public static void waixie() throws IOException {
		String curLine2 = "";
		for (int index = 140326; index <= 200000; index++) {
			URL server2 = new URL("http://waixie.net/task/info.php?id="
					+ String.valueOf(index));

			HttpURLConnection connection2 = (HttpURLConnection) server2
					.openConnection();

			connection2.connect();
			InputStream is2 = connection2.getInputStream();

			BufferedReader reader2 = new BufferedReader(new InputStreamReader(
					is2));
			boolean nextlineismail = false;
			boolean isempty = false;
			while ((curLine2 = reader2.readLine()) != null) {
				if (StringUtil.isContains(curLine2, "class=\"STYLE37\">")) {
					int begin2 = curLine2.lastIndexOf("class=\"STYLE37\">")
							+ "class=\"STYLE37\">".length();
					int end2 = curLine2.indexOf("</span></u></td>", begin2);
					String company = curLine2.substring(begin2, end2);
					if (StringUtil.isEmpty(company)) {
						isempty = true;
					} else {
						System.out.print(String.valueOf(index) + ":" + company
								+ ":");
						isempty = false;
					}
				}
				if (nextlineismail) {
					nextlineismail = false;
					int begin2 = curLine2
							.lastIndexOf("class=\"STYLE19 STYLE35 STYLE36\">")
							+ "class=\"STYLE19 STYLE35 STYLE36\">".length();
					int end2 = curLine2.indexOf("</", begin2);
					String mail = curLine2.substring(begin2, end2);
					System.out.println(mail);

				}
				if (StringUtil.isContains(curLine2, "STYLE36\">电子信箱")
						&& !isempty) {
					nextlineismail = true;
				}

			}
			is2.close();
		}
	}

	public static void cutinfo() throws IOException 
	{

		for (int index = 1; index <= 487; index++) 
		{
			String curLine2 = "";
			String curLine = "";
			URL server2 = new URL("http://www.cutinfo.cn/gongsi/index.php?keyword=&sclei=&jinyin=&province=&city=&page="
					+ String.valueOf(index));

			HttpURLConnection connection2 = (HttpURLConnection) server2
					.openConnection();

			connection2.connect();
			InputStream is2 = connection2.getInputStream();

			BufferedReader reader2 = new BufferedReader(new InputStreamReader(
					is2));

			while ((curLine2 = reader2.readLine()) != null) 
			{
				if (StringUtil.isContains(curLine2, "../factory/aboutus.php")) 
				{
					String link = curLine2.split("aboutus.php")[1].split("\"")[0];
					URL server = new URL("http://www.cutinfo.cn/factory/aboutus.php" + link);

					HttpURLConnection connection = (HttpURLConnection) server.openConnection();

					connection.connect();
					InputStream is = connection.getInputStream();

					BufferedReader reader = new BufferedReader(new InputStreamReader(is));
					boolean nextlineismail = false;
					boolean nextlineismailup = false;
					while ((curLine = reader.readLine()) != null) 
					{
						if (nextlineismail) {
							nextlineismail = false;
							System.out.println(curLine);
							break;
						}
						if (nextlineismailup) {
							nextlineismailup = false;
							nextlineismail = true;
						}
						if (StringUtil.isContains(curLine, "E-Mail</FONT>")) 
						{
							nextlineismailup = true;
						}
					}
					is.close();
				}
			}
			is2.close();
		}
		
		
	}

	public static void daojuz() throws IOException 
	{

		for (int index = 16; index <= 16; index++) 
		{
			for(int j = 1;j<= 13;j++)
			{
				String curLine2 = "";
				String curLine = "";
				URL server2 = new URL("http://www.daojuz.com/company/search.php?areaid="+ String.valueOf(index) +"&page="+ String.valueOf(j));
	
				HttpURLConnection connection2 = (HttpURLConnection) server2
						.openConnection();
	
				connection2.connect();
				InputStream is2 = connection2.getInputStream();
	
				BufferedReader reader2 = new BufferedReader(new InputStreamReader(
						is2));
	
				while ((curLine2 = reader2.readLine()) != null) 
				{
					if (StringUtil.isContains(curLine2, "<strong class=\"px14\">")) 
					{
						int begin2 = curLine2.lastIndexOf("<a href=\"") + "<a href=\"".length();
						int end2 = curLine2.indexOf("\" target=\"_blank\">", begin2);
						String link = curLine2.substring(begin2, end2);
						URL server = new URL(link + "contact");
	
						try
						{
						HttpURLConnection connection = (HttpURLConnection) server.openConnection();
	
						connection.connect();
						InputStream is = connection.getInputStream();
	
						BufferedReader reader = new BufferedReader(new InputStreamReader(is));
						boolean nextlineismail = false;
						while ((curLine = reader.readLine()) != null) 
						{
							if (nextlineismail) {
								nextlineismail = false;
								System.out.println(curLine);
								break;
							}
							if (StringUtil.isContains(curLine, "电子邮件")) 
							{
								nextlineismail = true;
							}
						}
						is.close();
						}catch(Exception ex){
							
						}
					}
				}
				is2.close();
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		daojuz();
	}

}