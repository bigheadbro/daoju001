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
		ArrayList<String> set = readEdmFileByLines("EDM/notqq.txt");

		for (int i = 11773; i < 15768; i++) {//for (int i = 2960; i < 3960; i++) {
			try
			{
			/*while (true) {
				Calendar time = Calendar.getInstance();
				if (time.get(Calendar.HOUR_OF_DAY) == 9 ||time.get(Calendar.HOUR_OF_DAY) == 14 ||time.get(Calendar.HOUR_OF_DAY) == 16 ||time.get(Calendar.HOUR_OF_DAY) == 21 ) {
					if (time.get(Calendar.MINUTE) <= 30) {
						break;
					}
				}
			}*/
			Message message = new Message("client@sends.daoshifu.com", "刀师傅");
			// 正文， 使用html形式，或者纯文本形式
			message.setBody("<p><a href=\"http://www.daoshifu.com\"><img src=\"http://daoshifu.com/img/dan.jpg\" style=\"width:600px\" /></a></p><p style=\"text-align: left;font-size:12px;font-family:'微软雅黑';\">如果您不愿意继续接受此类邮件,可点此<a href=\"https://sendcloud.sohu.com/webapi/unsubscribes.get.xml?api_user=yourusername\">退订此类邮件</a></p>");
			// 添加to, cc, bcc replyto
			message.setSubject("刀师傅全品牌选刀系统即将上线");

			message.addRecipient(set.get(i));
			//message.addRecipient("346938819@qq.com");
			// 组装消息发送邮件
			// 不同于登录SendCloud站点的帐号，您需要登录后台创建发信域名，获得对应发信域名下的帐号和密码才可以进行邮件的发送。
			SendCloud sendCloud = new SendCloud(
					"postmaster@daoshifu.sendcloud.org", "fFNUbnBnMcr6");
			sendCloud.setMessage(message);
			// sendCloud.setDebug(true); //设置调试, 可以看到java mail的调试信息
			sendCloud.send();

			// 获取emailId列表
			System.out.println(sendCloud.getEmailIdList());
			}
			catch(Exception ex){
				System.out.println(ex.toString());
			}
		}
	}
}
