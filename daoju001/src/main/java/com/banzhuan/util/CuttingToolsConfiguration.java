package com.banzhuan.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class CuttingToolsConfiguration {
	private static Map<String, String> level1Map = new HashMap<String, String>();
	private static Map<String, String> level2Map = new HashMap<String, String>();
	private static Map<String, String> level3Map = new HashMap<String, String>();
	private static Map<String, String> level4Map = new HashMap<String, String>();
	private static Map<String, String> codeMap = new HashMap<String, String>();
	static 
	{
		level1Map.put("01","机夹铣刀");
		level1Map.put("02","整体铣刀");
		level1Map.put("03","车削刀具");
		level1Map.put("04","孔加工刀具");
		level1Map.put("05","刀柄");
		level1Map.put("06","螺纹刀具");
		level1Map.put("99","其他");
		
		level2Map.put("0101","面铣刀盘");
		level2Map.put("0102","铣刀杆");
		level2Map.put("0103","螺纹接头");
		level2Map.put("0104","合金接杆");
		level2Map.put("0105","玉米铣刀");
		level2Map.put("0106","三面刃铣刀");
		level2Map.put("0107","T型槽刀");
		level2Map.put("0108","铣刀片");
		level2Map.put("0201","平头铣刀");
		level2Map.put("0202","球头铣刀");
		level2Map.put("0203","圆角铣刀");
		level2Map.put("0204","锥形铣刀");
		level2Map.put("0205","V尖刀");
		level2Map.put("0206","倒角刀");
		level2Map.put("0207","T槽刀");
		level2Map.put("0301","车刀杆");
		level2Map.put("0401","整体合金钻");
		level2Map.put("0402","焊接合金钻");
		level2Map.put("0403","机夹式钻头");
		level2Map.put("0404","机夹式钻头刀片");
		level2Map.put("0405","铲钻");
		level2Map.put("0406","铲钻刀片");
		level2Map.put("0407","U钻");
		level2Map.put("0408","U钻刀片");
		level2Map.put("0409","铰刀");
		level2Map.put("0410","镗刀");
		level2Map.put("0501","热胀刀柄");
		level2Map.put("0502","液压刀柄");
		level2Map.put("0503","面铣刀柄");
		level2Map.put("0504","丝锥刀柄");
		level2Map.put("0505","ER刀柄");
		level2Map.put("0506","筒夹");
		level2Map.put("0507","超大加持力刀柄");
		level2Map.put("0601","螺纹铣刀");
		level2Map.put("0602","螺纹铣刀杆");
		level2Map.put("0603","螺纹铣刀片");
		level2Map.put("0604","螺纹车刀杆");
		level2Map.put("0605","螺纹车刀片");
		level2Map.put("0606","丝锥");
		level2Map.put("0607","板牙");
		level2Map.put("9901","对刀仪");
		level2Map.put("9902","寻边器");
		
		level3Map.put("020101","标准平头铣刀");
		level3Map.put("020102","波纹平头铣刀");
		level3Map.put("020103","长颈平头铣刀");
		level3Map.put("020201","标准球头铣刀");
		level3Map.put("020202","长颈球头铣刀");
		level3Map.put("020301","标准圆角铣刀");
		level3Map.put("020302","长颈圆角铣刀");
		level3Map.put("020401","锥形平头铣刀");
		level3Map.put("020402","锥形圆角铣刀");
		level3Map.put("020403","锥形球头铣刀");
		level3Map.put("030101","外圆车刀杆");
		level3Map.put("030102","内圆车刀杆");
		level3Map.put("030103","切断/切槽刀杆");
		level3Map.put("030104","普通车刀片");
		level3Map.put("030105","精磨车刀片");
		level3Map.put("030106","切槽/切断");
		level3Map.put("050701","冷压刀柄");
		level3Map.put("050702","蜗杆刀柄");
		level3Map.put("060601","手用丝锥");
		level3Map.put("060602","挤压丝锥");
		level3Map.put("060603","先端丝锥");
		level3Map.put("060604","螺旋丝锥");
		
		codeMap.put("1","010100");
		codeMap.put("2","010200");
		codeMap.put("3","010300");
		codeMap.put("4","010400");
		codeMap.put("5","010500");
		codeMap.put("6","010600");
		codeMap.put("7","010700");
		codeMap.put("8","010800");
		codeMap.put("9","020101");
		codeMap.put("10","020102");
		codeMap.put("11","020103");
		codeMap.put("12","020201");
		codeMap.put("13","020202");
		codeMap.put("14","020301");
		codeMap.put("15","020302");
		codeMap.put("16","020401");
		codeMap.put("17","020402");
		codeMap.put("18","020403");
		codeMap.put("19","020500");
		codeMap.put("20","020600");
		codeMap.put("21","020700");
		codeMap.put("22","030101");
		codeMap.put("23","030102");
		codeMap.put("24","030103");
		codeMap.put("25","030104");
		codeMap.put("26","030105");
		codeMap.put("27","030106");
		codeMap.put("28","040100");
		codeMap.put("29","040200");
		codeMap.put("30","040300");
		codeMap.put("31","040400");
		codeMap.put("32","040500");
		codeMap.put("33","040600");
		codeMap.put("34","040700");
		codeMap.put("35","040800");
		codeMap.put("36","040900");
		codeMap.put("37","041000");
		codeMap.put("38","050100");
		codeMap.put("39","050200");
		codeMap.put("40","050300");
		codeMap.put("41","050400");
		codeMap.put("42","050500");
		codeMap.put("43","050600");
		codeMap.put("44","050701");
		codeMap.put("45","050702");
		codeMap.put("46","060100");
		codeMap.put("47","060200");
		codeMap.put("48","060300");
		codeMap.put("49","060400");
		codeMap.put("50","060500");
		codeMap.put("51","060601");
		codeMap.put("52","060602");
		codeMap.put("53","060603");
		codeMap.put("54","060604");
		codeMap.put("56","060700");
		codeMap.put("57","990100");
		codeMap.put("58","990200");

	}
	public static String getCodeValue(String order)
	{
		return codeMap.get(order);
	}
	public static void analyze()
	{
		try {
            Workbook book = Workbook.getWorkbook(new File("D:/data/type.xls"));
            // 获得第一个工作表对象
            Sheet sheet= book.getSheet(0);
            for(int j = 1; j < sheet.getRows(); j++)
            {
            	if(StringUtil.isNotEmpty((sheet.getCell(2, j).getContents())))
    			{
            		System.out.println("level1Map.put(\""+sheet.getCell(3, j).getContents()+"\",\""+ sheet.getCell(2, j).getContents() + "\");");
    			}
            	if(StringUtil.isNotEmpty((sheet.getCell(4, j).getContents())))
    			{
            		System.out.println("level2Map.put(\""+sheet.getCell(3, j).getContents()+sheet.getCell(5, j).getContents()+"\",\""+ sheet.getCell(4, j).getContents() + "\");");
    			}
            	if(StringUtil.isNotEmpty((sheet.getCell(6, j).getContents())))
    			{
            		System.out.println("level3Map.put(\""+sheet.getCell(3, j).getContents()+sheet.getCell(5, j).getContents()+sheet.getCell(7, j).getContents()+"\",\""+ sheet.getCell(6, j).getContents() + "\");");
    			}
            	
            }
            book.close();
        } catch (Exception e) {
            System.out.println(e);
        }
	}
	
	public static void readHotCt()
	{
		try {
            Workbook book = Workbook.getWorkbook(new File("D:/Data/type.xls"));
            // 获得第一个工作表对象
            Sheet sheet= book.getSheet(1);
            for(int j = 2; j < sheet.getRows(); j++)
            {
            	Cell cell = sheet.getCell(7, j);
            	
            }
            book.close();
        } catch (Exception e) {
            System.out.println(e);
        }
	}
	
	public static String getType(String code)
	{
		String ret = level1Map.get(code.substring(0, 2));
		if(StringUtil.isNotEmpty(level2Map.get(code.substring(0, 4))))
		{
			ret += " -> " +level2Map.get(code.substring(0, 4));
			if(StringUtil.isNotEmpty(level3Map.get(code.substring(0, 6))))
			{
				ret += " -> " +level3Map.get(code.substring(0, 6));
				if(StringUtil.isNotEmpty(level4Map.get(code.substring(0, 8))))
				{
					ret += " -> " +level4Map.get(code.substring(0, 8));
					
				}
			}
		}
		return ret;
	}
	
	public static void sortProductCode()
	{
		try {
            Workbook book = Workbook.getWorkbook(new File("D:/data/type.xls"));
            // 获得第一个工作表对象
            Sheet sheet= book.getSheet(0);
            for(int j = 1; j < sheet.getRows(); j++)
            {
            	Cell cell1 = sheet.getCell(1, j);
            	Cell cell2 = sheet.getCell(0, j);
            	System.out.println("codeMap.put(\"" + cell1.getContents() + "\",\"" + cell2.getContents() + "\");");
            }
            book.close();
        } catch (Exception e) {
            System.out.println(e);
        }
	}
	public static void main(String[] args) {
		analyze();
	}
}
