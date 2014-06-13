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

		for (int i = 11240; i < set.size(); i++) {
			try
			{
			while (true) {
				Calendar time = Calendar.getInstance();
				if (time.get(Calendar.HOUR_OF_DAY) == 13
						|| time.get(Calendar.HOUR_OF_DAY) == 9
						|| time.get(Calendar.HOUR_OF_DAY) == 17
						|| time.get(Calendar.HOUR_OF_DAY) == 20) {
					if (time.get(Calendar.MINUTE) <= 30) {
						break;
					}
				}
			}
			Message message = new Message("client@daoshifu.com", "刀师傅");
			// 正文， 使用html形式，或者纯文本形式
			message.setBody("<div style=\"text-align: center;width: 700px;background-color:#e6e6e6;padding: 20px;\">"
					+ "<div style=\"position:relative;text-align:center;background-color:white;border-radius: 10px;\">"
					+ "	<div style=\"margin-top:30px;text-align:center;\">"
					+ "<h2 style=\"font-family:'微软雅黑';display: inline-block;text-align:center;background-color:white;border-radius: 5px;color:#3f5e81;border:1px solid #3f5e81;font-size:16px;padding:8px\">第二季</h2>"
					+ "	</div>	"
					+ "<h1 style=\"display: inline-block;color:#3f5e81;font-size:72px;font-family:'微软雅黑';font-weight: 100;margin-top:0px;margin-bottom:10px;border-bottom:1px solid #3f5e81;padding-bottom:15px;\">疯狂放送</h1>"
					+ "<p style=\"color:#3f5e81;font-size:16px;font-family:'微软雅黑';font-weight: 700;line-height: 25px;\">刀师傅邀您免费试用 瓦格斯™<br/>旗下新品 犀飞利 去毛刺刀</p>"
					+ "<p style=\"font-size:14px;font-family:'微软雅黑';font-weight: 100;line-height: 25px;color:#8e8e8e\">只需1分钟简单填写收件信息，即可在6月底获得试用品。<br/>"
					+ "由本站特约代理商-苏州鼎锐，以快递包邮形式发给各位，<br/>"
					+ "另可按各位要求，发送样本等资料。<br/></p>"
					+ "<a style=\"font-size:16px;font-family:'微软雅黑';display: inline-block;text-decoration: none;margin-top: 30px;color:white;background-color:#3f5e81;padding:10px 30px;border-radius:5px\" href=\"http://www.daoshifu.com/event\">立即预约</a>"
					+ "<p style=\"font-size:14px;font-family:'微软雅黑';font-weight: 100;line-height: 25px;color:#8e8e8e\">本产品由犀飞利全国总代理  苏州鼎锐提供</p>"
					+ "<h1 style=\"color:#3d5e81;font-size:30px;padding-top: 30px;font-family:'微软雅黑';font-weight: 100;border-top: 1px solid #eee;margin: 20px\">犀飞利</h1>"
					+ "<p style=\"font-size:12px;font-family:'微软雅黑';font-weight: 100;line-height: 25px;color:#3d5e81;margin: 0 107px;\">"
					+ "			成立于1960年的瓦格斯公司,首次将旗下手动工具产品线,犀飞利手动去毛刺刀引入中国。<br/>"
					+ "是模具行业、汽车制造业、航空业、塑料电子产品以及管接头行业的首选工具。<br/></p>"
					+ "<img style=\"margin-top:40px;margin-bottom:80px;padding-bottom:25px;border-bottom:1px solid #e9e9e9;\" src=\"http://www.daoshifu.com/uploadfile/edm-item.png\" />"
					+"<p style=\"position:absolute;left: 65px;bottom: 10px;font-size:12px;font-family:'微软雅黑';color:#8e8e8e\">如果您不愿意继续接受此类邮件,可点此<a href=\"https://sendcloud.sohu.com/webapi/unsubscribes.get.xml?api_user=yourusername\">退订此类邮件</a></p>"
					+ "<img style=\"position:absolute;right: 65px;bottom: 10px;\" src=\"http://www.daoshifu.com/uploadfile/edmdsf.png\" />"
					+ "</div>");
			// 添加to, cc, bcc replyto
			message.setSubject("犀飞利免费试用——刀师傅6月活动");

			message.addRecipient(set.get(i));

			// message.addRecipient("346938819@qq.com").addRecipient("123576884@qq.com");
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
			catch(Exception ex){
				System.out.println(ex.toString());
			}
		}
	}
}
