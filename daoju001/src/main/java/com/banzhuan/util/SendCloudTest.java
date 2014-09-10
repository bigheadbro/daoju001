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
		ArrayList<String> set = readEdmFileByLines("EDM/client.txt");

		for (int i = 16482; i < set.size(); i++) {
			try
			{
			while (true) {
				Calendar time = Calendar.getInstance();
				if (time.get(Calendar.HOUR_OF_DAY) == 9 ||time.get(Calendar.HOUR_OF_DAY) == 13 ||time.get(Calendar.HOUR_OF_DAY) == 16 ||time.get(Calendar.HOUR_OF_DAY) == 21 ) {
					if (time.get(Calendar.MINUTE) <= 30) {
						break;
					}
				}
			}
			Message message = new Message("client@daoshifu.com", "刀师傅");
			// 正文， 使用html形式，或者纯文本形式
			message.setBody("<div style=\"font-family:'楷体';text-align: center;\">"
					+"<div style=\"width: 832px;background-color:#7d7d7d;display:inline-block;\">"
					+"<div style=\"text-align: center;width: 832px;background: url(http://www.daoshifu.com/img/edm4.png) repeat-x 0 0;\">"
						+"	<h1 style=\"color:white;padding-top:900px;font-size:100px;font-weight:100;margin-bottom:20px;margin-top:0\">全新改版</h1>"
							+"<p style=\"color:white;font-size:28px;line-height:40px;\">三大功能重装上阵！<br/>2014年7月,我们期待您的光临！</p>"
							+"<div style=\"margin-top: 60px;\">"
								+"<div style=\"margin-left:10px;width:230px;border-right: 1px solid white;display:inline-block;\">"
									+"<h2 style=\"font-weight:100;display:inline-block;color:white;border:2px solid white;padding:10px 30px;border-radius:10px;margin-top: 0;\"><a style=\"color:white\" href=\"http://www.daoshifu.com/items\">刀具商城</a></h2>"
									+"<div style=\"width:160px;display:inline-block;\">"
										+"<p style=\"text-align:left;color:white;font-size: 14px;\">?刀师傅网精选认证卖家，支付宝担保交易，网上买刀具再无后顾之忧。"
										+"<br/><br/>?双向免费，价格优惠，全部顺丰包邮，货源不断更新。"
										+"</p>"
									+"</div>"
								+"</div>"
								+"<div style=\"width:230px;border-right: 1px solid white;display:inline-block;\">"
									+"<h2 style=\"font-weight:100;display:inline-block;color:white;border:2px solid white;padding:10px 30px;border-radius:10px;margin-top: 0;\"><a style=\"color:white\" href=\"http://www.daoshifu.com/products\">线上展会</a></h2>"
									+"<div style=\"width:160px;display:inline-block;\">"
										+"<p style=\"text-align:left;color:white;font-size: 14px;\">?刀师傅网精选特色刀具，足不出户看24小时在线刀具展会。"
										+"<br/><br/>?免费发布，有效展示，直接沟通，打造刀具界O2O平台。"
										+"</p>"
									+"</div>"
								+"</div>"
								+"<div style=\"width:230px;display:inline-block;\">"
									+"<h2 style=\"font-weight:100;display:inline-block;color:white;border:2px solid white;padding:10px 30px;border-radius:10px;margin-top: 0;\"><a style=\"color:white\" href=\"http://www.daoshifu.com/questions\">刀具咨询</a></h2>"
									+"<div style=\"width:160px;display:inline-block;\">"
										+"<p style=\"text-align:left;color:white;font-size: 14px;\">?找不到合适的刀具？来刀师傅提问！资深刀具专家为你解答！"
										+"<br/><br/>?免费提问！高质量解答！打造最专业刀具咨询平台！"
										+"</p>"
									+"</div>"
								+"</div>"
							+"</div>"
							+"<div style=\"margin-top: 70px;padding-bottom: 100px;\">"
							+"<img src=\"http://www.daoshifu.com/img/edm41.png\" />"
							+"</div>"
							+"<p style=\"text-align: center;font-size:12px;font-family:'微软雅黑';color:white\">如果您不愿意继续接受此类邮件,可点此<a href=\"https://sendcloud.sohu.com/webapi/unsubscribes.get.xml?api_user=yourusername\">退订此类邮件</a></p>"
						+"</div>"
					+"</div>"
				+"</div>");
			// 添加to, cc, bcc replyto
			message.setSubject("刀师傅 全新改版 重装上阵");

			message.addRecipient(set.get(i));

			// message.addRecipient("346938819@qq.com").addRecipient("123576884@qq.com");
			// 组装消息发送邮件
			// 不同于登录SendCloud站点的帐号，您需要登录后台创建发信域名，获得对应发信域名下的帐号和密码才可以进行邮件的发送。
			SendCloud sendCloud = new SendCloud(
					"postmaster@daoshifu.sendcloud.org", "fFNUbnBnMcr6");
			sendCloud.setMessage(message);
			// sendCloud.setDebug(true); //设置调试, 可以看到java mail的调试信息
			sendCloud.send();

			// 获取emailId列表
			System.out.println(sendCloud.getEmailIdList());

			Thread.sleep(5500);
			}
			catch(Exception ex){
				System.out.println(ex.toString());
			}
		}
	}
}
