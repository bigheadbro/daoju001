package com.banzhuan.util;

import java.awt.Rectangle;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.sanselan.ImageReadException;

public class Util {

	public static void cropImage(String lastdir, File file, String aa,
			String subpath) throws IOException {
		FileInputStream is = null;
		ImageInputStream iis = null;

		try {
			/*
			 * 图片裁剪区域。Rectangle 指定了坐标空间中的一个区域，通过 Rectangle 对象
			 * 的左上顶点的坐标（x，y）、宽度和高度可以定义这个区域。
			 */
			BufferedImage sourceImg = JpegReader.readImage(file);
			ImageIO.write(sourceImg, lastdir, file);

			int x, y, width, height;

			if (StringUtil.isEmpty(aa)) {
				x = sourceImg.getWidth() / 4;
				y = sourceImg.getHeight() / 4;
				width = sourceImg.getWidth() / 2;
				height = sourceImg.getHeight() / 2;
			} else {
				x = Integer.parseInt(aa.split(",")[0]);
				y = Integer.parseInt(aa.split(",")[1]);
				width = Integer.parseInt(aa.split(",")[2]);
				height = Integer.parseInt(aa.split(",")[3]);
			}
			Rectangle rect;
			if (StringUtil.isEmpty(aa)) {
				rect = new Rectangle(x, y, width, height);
			} else {
				if (sourceImg.getHeight() / 200 < sourceImg.getWidth() / 310) {
					if (sourceImg.getWidth() / 310 >= 1) {
						rect = new Rectangle(x * sourceImg.getWidth() / 310, y
								* sourceImg.getWidth() / 310, width
								* sourceImg.getWidth() / 310, height
								* sourceImg.getWidth() / 310);
					} else {
						rect = new Rectangle(x * 310 / sourceImg.getWidth(), y
								* 310 / sourceImg.getWidth(), width * 310
								/ sourceImg.getWidth(), height * 310
								/ sourceImg.getWidth());
					}
				} else {
					if (sourceImg.getHeight() / 200 >= 1) {
						rect = new Rectangle(x * sourceImg.getHeight() / 200, y
								* sourceImg.getHeight() / 200, width
								* sourceImg.getHeight() / 200, height
								* sourceImg.getHeight() / 200);
					} else {
						rect = new Rectangle(x * 200 / sourceImg.getHeight(), y
								* 200 / sourceImg.getHeight(), width * 200
								/ sourceImg.getHeight(), height * 200
								/ sourceImg.getHeight());
					}
				}
			}

			// 读取图片文件
			is = new FileInputStream(file.getPath());
			/*
			 * 返回包含所有当前已注册 ImageReader 的 Iterator，这些 ImageReader 声称能够解码指定格式。
			 * 参数：formatName - 包含非正式格式名称 .（例如 "jpeg" 或 "tiff"）等 。
			 */
			Iterator<ImageReader> it = ImageIO
					.getImageReadersByFormatName(lastdir);
			ImageReader reader = it.next();
			// 获取图片流
			iis = ImageIO.createImageInputStream(is);
			/*
			 * <p>iis:读取源.true:只向前搜索 </p>.将它标记为 ‘只向前搜索’。
			 * 此设置意味着包含在输入源中的图像将只按顺序读取，可能允许 reader 避免缓存包含与以前已经读取的图像关联的数据的那些输入部分。
			 */
			reader.setInput(iis, true);

			/*
			 * <p>描述如何对流进行解码的类<p>.用于指定如何在输入时从 Java Image I/O
			 * 框架的上下文中的流转换一幅图像或一组图像。用于特定图像格式的插件 将从其 ImageReader 实现的
			 * getDefaultReadParam 方法中返回 ImageReadParam 的实例。
			 */
			ImageReadParam param = reader.getDefaultReadParam();
			// 提供一个 BufferedImage，将其用作解码像素数据的目标。
			param.setSourceRegion(rect);
			/*
			 * 使用所提供的 ImageReadParam 读取通过索引 imageIndex 指定的对象，并将 它作为一个完整的
			 * BufferedImage 返回。
			 */
			BufferedImage bi = reader.read(0, param);

			// 保存新图片
			ImageIO.write(bi, lastdir, new File(subpath));
		} catch (ImageReadException e) {
			e.printStackTrace();
		} finally {
			if (is != null)
				is.close();
			if (iis != null)
				iis.close();
		}
	}

	public static String genRandomName(String prefix) {
		UUID uuid = UUID.randomUUID();
		return uuid.toString() + prefix;
	}

	public static HashSet<String> readFileByLines(String fileName) {
		HashSet<String> array = new HashSet<String>();
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				int length = tempString.split(";").length;
				for (int i = 0; i < length; i++) {
					array.add(tempString.split(";")[i].split(":")[1]);
					System.out.println(tempString.split(";")[i].split(":")[1]);
				}
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

	public static void addmailToEDM(String fileName) {
		HashSet<String> array = new HashSet<String>();
		//先读取edm邮箱
		File file = new File("EDM/edm.txt");
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
		
		//添加新邮箱
		file = new File(fileName);
		reader = null;
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
		try {
			FileWriter writer = new FileWriter("EDM/edm.txt");
			Iterator it = array.iterator();
			while(it.hasNext())
			{
				writer.write(it.next()+"\n");
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void sendEmail(final String sender, final String password,
			String[] receivers, String title, String mailContent,
			File[] attachements, String mimetype, String charset) {
		Properties props = new Properties();
		// 设置smtp服务器地址
		// 这里使用QQ邮箱，记得关闭独立密码保护功能和在邮箱中设置POP3/IMAP/SMTP服务
		props.put("mail.smtp.host", "smtp.exmail.qq.com");
		// 需要验证
		props.put("mail.smtp.auth", "true");
		// 创建验证器
		Authenticator authenticator = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(sender, password);
			}
		};
		// 使用Properties创建Session
		Session session = Session.getDefaultInstance(props, authenticator);
		// Set the debug setting for this Session
		session.setDebug(true);
		try {
			// 使用session创建MIME类型的消息
			MimeMessage mimeMessage = new MimeMessage(session);
			// 设置发件人邮件
			mimeMessage.setFrom(new InternetAddress(sender));
			// 获取所有收件人邮箱地址
			InternetAddress[] receiver = new InternetAddress[receivers.length];
			for (int i = 0; i < receivers.length; i++) {
				receiver[i] = new InternetAddress(receivers[i]);
			}
			// 设置收件人邮件
			mimeMessage.setRecipients(Message.RecipientType.TO, receiver);
			// 设置标题
			mimeMessage.setSubject(title, charset);
			// 设置邮件发送时间
			// SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			// mimeMessage.setSentDate(format.parse("2011-12-1"));
			mimeMessage.setSentDate(new Date());
			// 创建附件
			Multipart multipart = new MimeMultipart();
			// 创建邮件内容
			MimeBodyPart body = new MimeBodyPart();
			// 设置邮件内容
			body.setContent(mailContent,
					(mimetype != null && !"".equals(mimetype) ? mimetype
							: "text/plain") + ";charset=" + charset);
			multipart.addBodyPart(body);// 发件内容
			// 设置附件
			if (attachements != null) {
				for (File attachement : attachements) {
					MimeBodyPart attache = new MimeBodyPart();
					attache.setDataHandler(new DataHandler(new FileDataSource(
							attachement)));
					String fileName = getLastName(attachement.getName());
					attache.setFileName(MimeUtility.encodeText(fileName,
							charset, null));
					multipart.addBodyPart(attache);
				}
			}
			// 设置邮件内容（使用Multipart方式）
			mimeMessage.setContent(multipart);
			// 发送邮件
			Transport.send(mimeMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void EDM(final String sender, final String password,
			String[] receivers, String title, String mailContent,
			File[] attachements, String mimetype, String charset) {
		Properties props = new Properties();
		// 设置smtp服务器地址
		// 这里使用QQ邮箱，记得关闭独立密码保护功能和在邮箱中设置POP3/IMAP/SMTP服务
		props.put("mail.smtp.host", "smtp.exmail.qq.com");
		// 需要验证
		props.put("mail.smtp.auth", "true");
		// 创建验证器
		Authenticator authenticator = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(sender, password);
			}
		};
		// 使用Properties创建Session
		Session session = Session.getDefaultInstance(props, authenticator);
		// Set the debug setting for this Session
		session.setDebug(true);
		try {
			// 使用session创建MIME类型的消息
			MimeMessage mimeMessage = new MimeMessage(session);
			// 设置发件人邮件
			mimeMessage.setFrom(new InternetAddress(sender));
			// 获取所有收件人邮箱地址
			InternetAddress[] receiver = new InternetAddress[receivers.length];
			for (int i = 0; i < receivers.length; i++) {
				receiver[i] = new InternetAddress(receivers[i]);
			}
			// 设置收件人邮件
			mimeMessage.setRecipients(Message.RecipientType.TO, receiver);
			// 设置标题
			mimeMessage.setSubject(title, charset);

			//
			// This HTML mail have to 2 part, the BODY and the embedded image
			//
			MimeMultipart multipart = new MimeMultipart("related");

			// first part (the html)
			BodyPart messageBodyPart = new MimeBodyPart();

			String htmlText = "<h1 style=\"font-size:24px;font-family:'微软雅黑';color:#0099cb;border-bottom:1px solid #0099cb;padding-bottom:10px;\">刀师傅</h1>"
					+ "<img style=\"width:1024px\" src=\"cid:image\">"
					+ "<p style=\"font-size:14px;font-family:'微软雅黑';margin-left:65px\">抢先关注刀师傅，2014颠覆刀具旧世界！更多内容尽在<a href=\"http://www.daoshifu.com?fromlink\">www.daoshifu.com</a></p>"
					+ "<p style=\"border-top:1px solid #e0e0e0;font-family:'微软雅黑';color:#aaa;text-align:center;padding-top:10px;font-size:13px;\">© 2013 刀师傅 | 沪ICP备13047239号-1</p>";
			messageBodyPart.setContent(htmlText, "text/html; charset=utf-8");

			// add it
			multipart.addBodyPart(messageBodyPart);

			// second part (the image)
			messageBodyPart = new MimeBodyPart();
			DataSource fds = new FileDataSource("EDM/comic1.jpg");
			messageBodyPart.setDataHandler(new DataHandler(fds));
			messageBodyPart.setHeader("Content-ID", "<image>");

			// add it
			multipart.addBodyPart(messageBodyPart);

			// put everything together
			mimeMessage.setContent(multipart);

			Transport.send(mimeMessage);
		} catch (Exception e) {

		}
	}

	private static String getLastName(String fileName) {
		int pos = fileName.lastIndexOf("\\");
		if (pos > -1) {
			fileName = fileName.substring(pos + 1);
		}
		pos = fileName.lastIndexOf("/");
		if (pos > -1) {
			fileName = fileName.substring(pos + 1);
		}
		return fileName;
	}

	public static void ReadAllFile(List<File> list,String filePath) {
		File f = null;
		f = new File(filePath);
		File[] files = f.listFiles(); // 得到f文件夹下面的所有文件。
		
		for (File file : files) {
			if (file.isDirectory()) {
				// 如何当前路劲是文件夹，则循环读取这个文件夹下的所有文件
				ReadAllFile(list, file.getAbsolutePath());
			} else {
				list.add(file);
			}
		}
	}

	public static void main(String[] args) {
		//HashSet<String> set = readFileByLines("EDM/cut35-mail.txt");
		// String rec[] =
		// {"346938819@qq.com","123576884@qq.com","410526674@qq.com"};
		// EDM("noreply@daoshifu.com","cisco123","346938819@qq.com","刀师傅-第一家刀具在线交流平台",
		// "", null, "", "UTF-8");
		/*
		 * String rec[] = new String[set.size()]; set.toArray(rec); String tmp[]
		 * = new String[10]; for(int i = 1;i<rec.length;i++) { tmp[i%10] =
		 * rec[i]; if(i%10 == 0) { for(int j=0;j<10;j++) {
		 * //EDM("noreply@daoshifu.com","cisco123",tmp,"刀师傅-第一家刀具在线交流平台", "",
		 * null, "", "UTF-8"); } } }
		 */
		//addmailToEDM("EDM/cut35mail.txt");
		//readFileByLines("EDM/cut35-mail.txt");
	}
}
