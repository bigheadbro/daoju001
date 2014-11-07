package com.banzhuan.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class ReadCuttingToolsType {
	private static Map<String, String> level1Map = new HashMap<String, String>();
	private static Map<String, String> level2Map = new HashMap<String, String>();
	private static Map<String, String> level3Map = new HashMap<String, String>();
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
	
	
	public static void main(String[] args) {
		analyze();
	}
}
