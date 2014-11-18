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
	private static Map<String, String> codeMap = new HashMap<String, String>();
	static 
	{
		level1Map.put("铣削刀具", "01");
		level1Map.put("车削刀具", "02");
		level1Map.put("孔加工刀具", "03");
		level1Map.put("刀柄", "04");
		level1Map.put("螺纹刀具", "05");
		level1Map.put("其它", "99");
		
		level2Map.put("铣槽刀盘", "0106");
		level2Map.put("整体铣刀", "0107");
		level2Map.put("车刀杆", "0201");
		level2Map.put("超大加持力刀柄", "0406");
		level2Map.put("丝锥", "0506");

		level3Map.put("平头铣刀", "010701");
		level3Map.put("球头铣刀", "010702");
		level3Map.put("圆角铣刀", "010703");
		level3Map.put("锥形铣刀", "010704");

		codeMap.put("1","01010000");
		codeMap.put("2","01020000");
		codeMap.put("3","01030000");
		codeMap.put("4","01040000");
		codeMap.put("5","01050000");
		codeMap.put("6","01060100");
		codeMap.put("7","01060200");
		codeMap.put("8","01070101");
		codeMap.put("9","01070102");
		codeMap.put("10","01070103");
		codeMap.put("11","01070201");
		codeMap.put("12","01070202");
		codeMap.put("13","01070301");
		codeMap.put("14","01070302");
		codeMap.put("15","01070401");
		codeMap.put("16","01070402");
		codeMap.put("17","01070403");
		codeMap.put("18","01070500");
		codeMap.put("19","01070600");
		codeMap.put("20","01070700");
		codeMap.put("21","01080000");
		codeMap.put("22","02010100");
		codeMap.put("23","02010200");
		codeMap.put("24","02010300");
		codeMap.put("25","02010400");
		codeMap.put("26","02010500");
		codeMap.put("27","03010000");
		codeMap.put("28","03020000");
		codeMap.put("29","03030000");
		codeMap.put("30","03040000");
		codeMap.put("31","03050000");
		codeMap.put("32","03060000");
		codeMap.put("33","03070000");
		codeMap.put("34","03080000");
		codeMap.put("35","03090000");
		codeMap.put("36","04010000");
		codeMap.put("37","04020000");
		codeMap.put("38","04030000");
		codeMap.put("39","04040000");
		codeMap.put("40","04050000");
		codeMap.put("41","04060100");
		codeMap.put("42","04060200");
		codeMap.put("43","05010000");
		codeMap.put("44","05020000");
		codeMap.put("45","05030000");
		codeMap.put("46","05040000");
		codeMap.put("47","05050000");
		codeMap.put("48","05060100");
		codeMap.put("49","05060200");
		codeMap.put("50","05060300");
		codeMap.put("51","05060400");
		codeMap.put("52","05070000");
		codeMap.put("53","99010000");
		codeMap.put("54","99020000");
	}
	public static String getCodeValue(String order)
	{
		return codeMap.get(order);
	}
	public static void analyze()
	{
		String name = "";
		String code = "";
		try {
            Workbook book = Workbook.getWorkbook(new File("D:/Data/type.xls"));
            // 获得第一个工作表对象
            Sheet sheet= book.getSheet(0);
            for(int j = 1; j < sheet.getRows(); j++)
            {
            	Cell cell = sheet.getCell(7, j);
            	name = cell.getContents();
            	if(StringUtil.isEmpty(name))
            	{
            		code = "00";
            		cell = sheet.getCell(5, j);
            		name = cell.getContents();
            		if(StringUtil.isEmpty(name))
                	{
            			cell = sheet.getCell(3, j);
                		name = cell.getContents();
                		code = sheet.getCell(2,j).getContents() +  sheet.getCell(4,j).getContents() +"0000";
                	}
            		else
            		{
            			code = "00";
            			code = sheet.getCell(2,j).getContents() +  sheet.getCell(4,j).getContents() + sheet.getCell(6,j).getContents() + "00";
            		}
            	}
            	else
            	{
            		code = sheet.getCell(2,j).getContents() +  sheet.getCell(4,j).getContents() + sheet.getCell(6,j).getContents() + sheet.getCell(8,j).getContents();
            		System.out.println("level2Map.put(\"" + sheet.getCell(5, j).getContents() + "\", \"" + sheet.getCell(2,j).getContents() +  sheet.getCell(4,j).getContents()+ sheet.getCell(6,j).getContents() + "\");");
        		}
            	if(StringUtil.isNotEmpty(name))
            	{
            		//System.out.println(name + "," + code);
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
		sortProductCode();
	}
}
