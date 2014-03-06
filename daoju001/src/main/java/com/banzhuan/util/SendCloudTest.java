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
		ArrayList<String> set = readEdmFileByLines("EDM/part2.txt");
		
		for(int i=681;i<4100;i++)
		{
			while(true)
			{
				Calendar time = Calendar.getInstance();
				if(time.get(Calendar.HOUR_OF_DAY) == 13 || time.get(Calendar.HOUR_OF_DAY) == 9)
				{
					if(time.get(Calendar.MINUTE) <= 40)
					{
						break;
					}
				}
			}
			Message message = new Message("client@daoshifu.com", "刀师傅");
			// 正文， 使用html形式，或者纯文本形式
			message.setBody("<div style=\"text-align: center;width: 700px;background-color:#e6e6e6;padding: 20px;\">"
					+ "<div style=\"text-align:center;border-radius: 10px;background-color:white;\">"
					+ "<h1 style=\"color:#0099cb;font-size:30px;padding-top: 30px;font-family:'微软雅黑';font-weight: 100;\">还在到处找刀具？</h1>"
					+ "<p style=\"font-size:14px;font-family:'hei';font-weight: 100;line-height: 25px;letter-spacing: 2px;\">30多个知名刀具品牌一级代理商入驻,百分百安全认证，安全可靠;<br/>"
					+ "有问必答，当天获得各品牌代理商提供方案、报价以及免费试用;<br/>"
					+ "代理商联系方式公开透明，线上交流，线下交易，防止恶性竞争;<br/>"
					+ "提供各品牌样本下载，具体细致了解产品性能与特点。</p>"
					+ "	<a style=\"font-size:16px;font-family:'微软雅黑';display: inline-block;text-decoration: none;margin-top: 30px;color:white;background-color:#0099cb;padding:10px 30px;border-radius:5px\" href=\"http://www.daoshifu.com\">马上加入</a>"
					+ "<div style=\"text-align:center;margin-top: 80px;margin-bottom: 15px;\"><img style=\"\" src=\"http://daoshifu.com/uploadfile/edm228.png\"></img></div>"
					+ "刀师傅全体员工 敬上" + "</div>" + "</div>");
			// 添加to, cc, bcc replyto
			message.setSubject("还在到处找刀具？快来刀师傅吧");
	
			
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
			
			Thread.sleep(6000);
		}
	}
}
