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
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
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

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.biff.LabelRecord;

import org.apache.sanselan.ImageReadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.banzhuan.common.Constant;
import com.banzhuan.controller.CommonController;
import com.banzhuan.entity.BrandEntity;
import com.banzhuan.entity.UserEntity;

public class Util {

	private static Logger logger = LoggerFactory.getLogger(Util.class);
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

	public static String genOrderNO() {
		String orderNumber = "DSF" + (new Date()).toString();
		return orderNumber;
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

	public static Map<Integer,String> getIndexInfo() {
		Map<Integer,String> array = new LinkedHashMap<Integer,String>();
		File file = new File("C:/index/index.txt");
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				int number = Integer.valueOf(tempString.split(",")[0]);
				String sr = tempString.split(",")[1];
				array.put(number, sr);
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

	public static void addmailToEDM() {
		HashSet<String> array = new HashSet<String>();
		// 先读取edm邮箱
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

		file = new File("EDM/client.txt");
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
			FileWriter writer = new FileWriter("EDM/notqq.txt");
			Iterator it = array.iterator();
			while (it.hasNext()) {
				String tmp = it.next().toString();
				if(!tmp.contains("qq"))
				{
					writer.write(tmp + "\n");
				}
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void removeMailFromEDM() {
		HashSet<String> array = new HashSet<String>();
		// 先读取edm邮箱
		File file = new File("EDM/notqq.txt");
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

		// 去除重复邮箱
		file = new File("EDM/failedm.txt");
		reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				if(array.contains(tempString))
				{
					array.remove(tempString);
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
		try {
			FileWriter writer = new FileWriter("EDM/edm1.txt");
			Iterator it = array.iterator();
			while (it.hasNext()) {
				writer.write(it.next() + "\n");
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
		props.put("mail.smtp.port", "25");
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

	public static void ReadAllFile(List<File> list, String filePath) {
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

	public static String GenAvatar()
	{
		String avatar;
		int max=Constant.MAX_AVATAR;
        int min=1;
        Random random = new Random();

        int s = random.nextInt(max)%(max-min+1) + min;
        avatar = "../img/avatar/" + String.valueOf(s)+".jpg";
		return avatar;
	}
	
	public static void removeSameMail(String fileName) {
		HashSet<String> array = new HashSet<String>();
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				if(StringUtil.isNotEmpty(tempString))
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
		Iterator<String> iterator=array.iterator();
		while(iterator.hasNext()){
			System.out.println(iterator.next());
		}
	}
	public static void removeSameMailFromWaixie() {
		HashSet<String> array = new HashSet<String>();
		File file = new File("EDM/waixie.txt");
		BufferedReader reader = null;
		String tempString = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				if(!StringUtil.isEqual(tempString.substring(tempString.length()-1, tempString.length()), ":"))
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
		Iterator<String> iterator=array.iterator();
		while(iterator.hasNext()){
			System.out.println(iterator.next().split(":")[2]);
		}
	}
	
	public static String queryMaterial(String param)
	{
		String result = "该材质不在查询范围";
		String brand = "";
    	String material = "";
		List<String> range = new ArrayList<String>();
		if(StringUtil.isEmpty(param.substring(2)))
		{
			return result;
		}
		try {
            Workbook book = Workbook.getWorkbook(new File("D:/Data/materilsort.xls"));
            // 获得第一个工作表对象
            Sheet sheet;
            for(int i = 0; i < book.getSheets().length;i++)
            {
	            sheet = book.getSheet(i);
	            for(int j = 1; j < sheet.getRows(); j++)
	            {
	            	for(int k = 1; k < sheet.getColumns(); k++)
	            	{
			            // 得到第一列第一行的单元格
			            Cell cell1 = sheet.getCell(k, j);
			            String[] content = cell1.getContents().toLowerCase().split(" ");
			            int size = cell1.getContents().toLowerCase().split(" ").length;
			            
			            while(size-->0)
			            {
			            	if(StringUtil.isEqual(content[size], param.substring(2).toLowerCase()))
			            	{
				            	brand = sheet.getCell(k,0).getContents();
				            	material = sheet.getName() + "材质, ";
				            	
				            	String tmp = sheet.getCell(0,j).getContents();
				            	range.add(tmp);
			            	}
			            }
	            	}
	            }
	            if(range.size() != 0)
	            {
	            	result = brand + material + "加工范围";
	            	for(int a = 0; a< range.size(); a++)
	            	{
	            		result += range.get(a)+ " ";
	            	}
	            	return result;
	            }
            }
            book.close();
        } catch (Exception e) {
        	logger.error(e.toString());
        }
		return result;
	}
	
	public static String querySteel(String param)
	{
		String result = "该材质不在查询范围";
    	String material = "";
    	if(StringUtil.isEmpty(param.substring(2)))
		{
			return result;
		}
		try {
            Workbook book = Workbook.getWorkbook(new File("D:/Data/steel.xls"));
            // 获得第一个工作表对象
            Sheet sheet= book.getSheet(0);
            for(int j = 1; j < sheet.getRows(); j++)
            {
            	for(int k = 0; k < sheet.getColumns(); k++)
            	{
		            // 得到第一列第一行的单元格
		            Cell cell1 = sheet.getCell(k, j);
		            if(StringUtil.isEqual(cell1.getContents().toLowerCase().split("[|]")[0], param.substring(2).toLowerCase()))
		            {
		            	material = cell1.getContents().toLowerCase().split("[|]")[1] + "\n";
		            	result = material;
		            	for(int tmp=0;tmp<5;tmp++)
		            	{
		            		if(tmp != k)
		            		{
		            			String content = sheet.getCell(tmp,j).getContents().split("[|]")[0];
		            			if(StringUtil.isEmpty(content))
		            			{
		            				content = "-";
		            			}
		            			result += sheet.getCell(tmp,0).getContents() + " " + content +"\n";
		            		}
		            	}
		            	return result;
		            }
            	}
            }
            book.close();
        } catch (Exception e) {
            System.out.println(e);
        }
		return result;
	}
	
	public List<UserEntity> sortProviders(List<UserEntity> providers)
	{
		Collections.sort(providers,new Comparator<UserEntity>() {   
		    public int compare(UserEntity o1, UserEntity o2) {   
				return o2.getAuthority() - o1.getAuthority();
		    }
		}); 
		return providers;
	}
	
	public static void main(String[] args) {
		removeMailFromEDM();
	}
}
