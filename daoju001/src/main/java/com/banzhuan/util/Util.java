package com.banzhuan.util;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.mail.Authenticator;
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

public class Util {

	private static int size = 300;
	public static void cropImage(String lastdir, String srcpath, int x, int y, int width,
            int height, String subpath) throws IOException 
	{
		FileInputStream is = null;
        ImageInputStream iis = null;

        try {
            // 读取图片文件
            is = new FileInputStream(srcpath);
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
            /*
             * 图片裁剪区域。Rectangle 指定了坐标空间中的一个区域，通过 Rectangle 对象
             * 的左上顶点的坐标（x，y）、宽度和高度可以定义这个区域。
             */
            BufferedImage sourceImg =ImageIO.read(new FileInputStream(srcpath)); 
            
            Rectangle rect;
            if(sourceImg.getWidth() > sourceImg.getHeight())
            {
            	rect = new Rectangle(x * sourceImg.getWidth() / size, y * sourceImg.getWidth() / size, 
            			width * sourceImg.getWidth() / size, height * sourceImg.getWidth() / size);
            }
            else
            {
            	rect = new Rectangle(x * sourceImg.getHeight() / size, y * sourceImg.getHeight() / size, 
            			width * sourceImg.getHeight() / size, height * sourceImg.getHeight() / size);
            }
            // 提供一个 BufferedImage，将其用作解码像素数据的目标。
            param.setSourceRegion(rect);
            /*
             * 使用所提供的 ImageReadParam 读取通过索引 imageIndex 指定的对象，并将 它作为一个完整的
             * BufferedImage 返回。
             */
            BufferedImage bi = reader.read(0, param);
            
            // 保存新图片
            ImageIO.write(bi, lastdir, new File(subpath));
        } 
        finally 
        {
            if (is != null)
                is.close();
            if (iis != null)
                iis.close();
        }
	}

	public static String genRandomName(String prefix)
	{
		UUID uuid = UUID.randomUUID();  
		return uuid.toString() + prefix;
	}
	
	public static void sendEmail(final String sender,final String password,String[] receivers, String title, String mailContent, File[] attachements, String mimetype, String charset) {
	    Properties props = new Properties();
	    //设置smtp服务器地址
	    //这里使用QQ邮箱，记得关闭独立密码保护功能和在邮箱中设置POP3/IMAP/SMTP服务
	    props.put("mail.smtp.host", "smtp.exmail.qq.com");
	    //需要验证
	    props.put("mail.smtp.auth", "true");
	    //创建验证器
	    Authenticator authenticator = new Authenticator() {
	        protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication(sender, password);
	        }
	    };
	    //使用Properties创建Session
	    Session session = Session.getDefaultInstance(props, authenticator);
	    //Set the debug setting for this Session
	    session.setDebug(true);
	    try {
	    	//使用session创建MIME类型的消息
	        MimeMessage mimeMessage = new MimeMessage(session);
	        //设置发件人邮件
	        mimeMessage.setFrom(new InternetAddress(sender));
	        //获取所有收件人邮箱地址
	        InternetAddress[] receiver = new InternetAddress[receivers.length];
	        for (int i=0; i<receivers.length; i++) {
	        	receiver[i] = new InternetAddress(receivers[i]);
	        }
	        //设置收件人邮件
	        mimeMessage.setRecipients(Message.RecipientType.TO, receiver);
	        //设置标题
	        mimeMessage.setSubject(title, charset);
	        //设置邮件发送时间
	        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	        //mimeMessage.setSentDate(format.parse("2011-12-1"));
	        mimeMessage.setSentDate(new Date());
	        //创建附件
	        Multipart multipart = new MimeMultipart();
	        //创建邮件内容
	        MimeBodyPart body = new MimeBodyPart();
	        //设置邮件内容
	        body.setContent(mailContent, (mimetype!=null && !"".equals(mimetype) ? mimetype : "text/plain")+ ";charset="+ charset);
	        multipart.addBodyPart(body);//发件内容
	        //设置附件
	        if(attachements!=null){
	            for (File attachement : attachements) {
	                MimeBodyPart attache = new MimeBodyPart();
	                attache.setDataHandler(new DataHandler(new FileDataSource(attachement)));
	                String fileName = getLastName(attachement.getName());
	                attache.setFileName(MimeUtility.encodeText(fileName, charset, null));
	                multipart.addBodyPart(attache);
	            }
	        }
	        //设置邮件内容（使用Multipart方式）
	        mimeMessage.setContent(multipart);
	        //发送邮件
	        Transport.send(mimeMessage);
	    } catch (Exception e) {
	    	e.printStackTrace();
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
	
	public static void main(String[] args) {  
		String rec[] = {"346938819@qq.com", "123576884@qq.com", "410526674@qq.com"};
    	sendEmail("noreply@daoshifu.com","cisco123",rec,
    			"找回密码", "买了一个企业邮箱，测试一下自动发邮件功能，木哈哈", null, "", "UTF-8");
    }
}
