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

		for (int i = 13989; i < 15997; i++) {
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
			message.setBody("<div  style=\"margin: 10px auto;width: 700px;\">"
							+"<div class=\"\">"
							+"<img style=\"width:700px;display:block;\" src=\"http://www.daoshifu.com/img/sys.png\" />"
							+"<h1 style=\"text-align:center;font-size:24px;color:#8d8d8d;font-weight:normal;margin-top:70px;\">花一分钟，与我们共建全品牌选刀系统</h1>"
							+"<a id=\"query\" target=\"__blank\" href=\"http://www.wenjuan.com/s/3QfaQns/\" style=\"display:block;text-decoration: none;text-align: center;margin: 30px auto;background-color: #44bde5;color: white;font-size: 30px;border-radius: 7px;padding: 10px;width: 160px;\">填写问卷</a>"
							+"</div>"
							+"<ul style=\"border-top:1px solid #eaeaea;margin-top:50px;padding-top: 40px;\">"
							+"<li style=\"list-style: none;text-align: left;display: inline-block;\">"
							+"<h1 style=\"font-size:22px;color:#8d8d8d;margin-bottom: 15px;\">更直观</h1>"
							+"<p style=\"font-size:14px;color:#cecece;line-height:28px;\">数千款热销品<br/>产品特性一目了然</p>"
							+"</li>"
							+"<li style=\"list-style: none;text-align: left;display: inline-block;margin-left:63px\">"
							+"<h1 style=\"font-size:22px;color:#8d8d8d;margin-bottom: 15px;\">更准确</h1>"
							+"<p style=\"font-size:14px;color:#cecece;line-height:28px;\">70余个参数<br/>准确匹配方案</p>"
							+"</li>"
							+"<li style=\"list-style: none;text-align: left;display: inline-block;margin-left:63px\">"
							+"<h1 style=\"font-size:22px;color:#8d8d8d;margin-bottom: 15px;\">更安全</h1>"
							+"<p style=\"font-size:14px;color:#cecece;line-height:28px;\">供应商经过刀师傅认证<br/>货源严格保证</p>"
							+"</li>"
							+"<li style=\"list-style: none;text-align: left;display: inline-block;margin-left:63px\">"
							+"<h1 style=\"font-size:22px;color:#8d8d8d;margin-bottom: 15px;\">更全面</h1>"
							+"<p style=\"font-size:14px;color:#cecece;line-height:28px;\">目标覆盖市场<br/>90%以上的品牌</p>"
							+"</li>"
							+"</ul>"
							+"</div>"
							+"<p style=\"text-align: center;font-size:12px;font-family:'微软雅黑';\">如果您不愿意继续接受此类邮件,可点此<a href=\"https://sendcloud.sohu.com/webapi/unsubscribes.get.xml?api_user=yourusername\">退订此类邮件</a></p>");
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
