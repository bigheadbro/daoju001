package com.banzhuan.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.sohu.sendcloud.Message;
import com.sohu.sendcloud.SendCloud;
import com.sohu.sendcloud.SmtpApiHeader;
import com.sohu.sendcloud.constant.AppFilter;

public class SendCloudTest {

	public static ArrayList<String> readEdmFileByLines(String fileName) {
		ArrayList<String> array = new ArrayList<String>();
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				array.add(tempString);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return array;
	}
	
	public static void main(String[] args) throws Exception {
		ArrayList<String> set = readEdmFileByLines("EDM/edm.txt");

		for(int i=14289;i<set.size();i++)
		{
			while(true)
			{
				Calendar time = Calendar.getInstance();
				if(time.get(Calendar.HOUR_OF_DAY) == 13 || time.get(Calendar.HOUR_OF_DAY) == 9|| 
						time.get(Calendar.HOUR_OF_DAY) == 17 || time.get(Calendar.HOUR_OF_DAY) == 20)
				{
					if(time.get(Calendar.MINUTE) <= 30)
					{
						break;
					}
				}
			}
			Message message = new Message("client@daoshifu.com", "刀师傅");
			// 正文， 使用html形式，或者纯文本形式
			message.setBody("<div style=\"text-align: center;width: 700px;background-color:#e6e6e6;padding: 20px;\">"
				+"<div style=\"position:relative;text-align:center;background-color:white;border-radius: 10px;\">"
				+"<h1 style=\"color:#0099cb;font-size:72px;padding-top: 30px;font-family:'微软雅黑';font-weight: 100;\">疯狂放送</h1>"
				+"<p style=\"font-size:14px;font-family:'微软雅黑';font-weight: 100;line-height: 25px;\">刀师傅感恩回馈,邀您免费试用 黛杰最新 <span style=\"color:#bc9b55\">RPMT1204</span></p>"
				+"<p style=\"font-size:14px;font-family:'微软雅黑';font-weight: 100;line-height: 25px;color:#8e8e8e\">只需1分钟简单填写收件信息，即可在四月底获得试用品。<br/>"
				+"		由本站特约代理商-上海时达，以顺丰包邮形式发给各位，<br/>"
				+"		另可按各位要求，发送样本等资料。<br/></p>"
				+"<a style=\"font-size:16px;font-family:'微软雅黑';display: inline-block;text-decoration: none;margin-top: 30px;color:white;background-color:#0099cb;padding:10px 30px;border-radius:5px\" href=\"http://www.daoshifu.com/event\">立即预约</a>"
				+"<h1 style=\"color:#bc9b55;font-size:30px;padding-top: 30px;font-family:'微软雅黑';font-weight: 100;border-top: 1px solid #eee;margin: 45px\">RPMT1204</h1>"
				+"<p style=\"font-size:14px;font-family:'微软雅黑';font-weight: 100;line-height: 25px;color:#bc9b55;margin: 0 107px;\">日本黛杰公司推出的全新RPMT1204，<br/>"
				+"		采用复合涂层技术，大幅度优化耐磨耐崩性能。<br/>"
				+"			特别适合于叶片粗加工、模具粗加工等情况。将刀片的韧性与硬度完美结合，以最严谨的态度来制作最通用的产品。<br/></p>"
				+"<img style=\"margin-top:40px;margin-bottom: 45px;\" src=\"http://www.daoshifu.com/uploadfile/edm47.jpg\" />"
				+"<img style=\"position:absolute;right: 10px;bottom: 10px;\" src=\"http://www.daoshifu.com/uploadfile/edm472.jpg\" />"
				+"</div></div>");
			// 添加to, cc, bcc replyto
			message.setSubject("黛杰刀具免费试用——刀师傅4月活动");
	
			
			message.addRecipient(set.get(i));
			
	
			//message.addRecipient("346938819@qq.com").addRecipient("123576884@qq.com");
			// 组装消息发送邮件
			// 不同于登录SendCloud站点的帐号，您需要登录后台创建发信域名，获得对应发信域名下的帐号和密码才可以进行邮件的发送。
			SendCloud sendCloud = new SendCloud(
					"postmaster@daoshifu.sendcloud.org", "2nxNPv0q");
			sendCloud.setMessage(message);
			// sendCloud.setDebug(true); //设置调试, 可以看到java mail的调试信息
			sendCloud.send();
	
			// 获取emailId列表
			System.out.println(sendCloud.getEmailIdList());
			
			Thread.sleep(5500);
		}
	}
}
