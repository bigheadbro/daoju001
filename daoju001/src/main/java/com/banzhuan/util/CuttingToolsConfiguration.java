package com.banzhuan.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.banzhuan.entity.CategoryEntity;
import com.banzhuan.entity.CuttingToolEntity;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class CuttingToolsConfiguration
{
	private static Map<String, String> level1Map = new HashMap<String, String>();
	private static Map<String, String> level2Map = new HashMap<String, String>();
	private static Map<String, String> level3Map = new HashMap<String, String>();
	private static Map<String, String> level4Map = new HashMap<String, String>();
	private static Map<String, String> codeMap = new HashMap<String, String>();
	private static Map<String, String> orderMap = new HashMap<String, String>();
	static
	{
		level1Map.put("01", "机夹铣刀");
		level1Map.put("02", "整体铣刀");
		level1Map.put("03", "车削刀具");
		level1Map.put("04", "孔加工刀具");
		level1Map.put("05", "刀柄");
		level1Map.put("06", "螺纹刀具");
		level1Map.put("99", "其他");

		//level2Map.put("0101", "面铣刀盘");
		//level2Map.put("0102", "铣刀杆");
		//level2Map.put("0103", "螺纹接头");
		//level2Map.put("0104", "合金接杆");
		level2Map.put("0105", "玉米铣刀");
		level2Map.put("0106", "铣槽刀盘");
		//level2Map.put("0106", "三面刃铣刀");
		//level2Map.put("0107", "T型槽刀");
		level2Map.put("0108", "铣刀片");
		level2Map.put("0101", "普通刀体");
		level2Map.put("0102", "组合刀体");
		
		level2Map.put("0201", "平头铣刀");
		level2Map.put("0202", "球头铣刀");
		level2Map.put("0203", "圆角铣刀");
		level2Map.put("0204", "锥形铣刀");
		level2Map.put("0205", "其他铣刀");
		level2Map.put("0301", "车刀杆");
		level2Map.put("0302", "车刀片");
		level2Map.put("0401", "整体钻头");
		level2Map.put("0402", "机夹式钻头");
		level2Map.put("0403", "铲钻");
		level2Map.put("0404", "U钻");
//		level2Map.put("0405", "铲钻");
//		level2Map.put("0406", "铲钻刀片");
//		level2Map.put("0407", "U钻");
//		level2Map.put("0408", "U钻刀片");
		level2Map.put("0409", "铰刀");
		level2Map.put("0410", "镗刀");
		
		level2Map.put("0501", "铣刀刀柄");
		//level2Map.put("0501", "热胀刀柄");
		//level2Map.put("0502", "液压刀柄");
		level2Map.put("0503", "面铣刀柄");
		level2Map.put("0504", "丝锥刀柄");
		//level2Map.put("0505", "ER刀柄");
		level2Map.put("0506", "筒夹");

		level2Map.put("0601", "螺纹铣刀");
		level2Map.put("0602", "螺纹车刀");
//		level2Map.put("0601", "螺纹铣刀");
//		level2Map.put("0602", "螺纹铣刀杆");
//		level2Map.put("0603", "螺纹铣刀片");
//		level2Map.put("0604", "螺纹车刀杆");
//		level2Map.put("0605", "螺纹车刀片");
		level2Map.put("0606", "丝锥");
		level2Map.put("0607", "板牙");
		level2Map.put("9901", "对刀仪");
		level2Map.put("9902", "寻边器");
		level2Map.put("9903", "滚压头");

		level3Map.put("010101", "面铣刀盘");
		level3Map.put("010102", "铣刀杆");
		level3Map.put("010201", "螺纹接头");
		level3Map.put("010202", "合金接杆");
		level3Map.put("010601", "三面刃铣刀");
		level3Map.put("010602", "T型槽刀");
		level3Map.put("020501", "V尖刀");
		level3Map.put("020502", "倒角刀");
		level3Map.put("020503", "T槽刀");
		level3Map.put("040101", "整体合金钻");
		level3Map.put("040102", "焊接合金钻");
		level3Map.put("040201", "机夹式钻头");
		level3Map.put("040202", "机夹式钻头刀片");
		level3Map.put("040301", "铲钻");
		level3Map.put("040302", "铲钻刀片");
		level3Map.put("040401", "U钻");
		level3Map.put("040402", "U钻刀片");
		level3Map.put("050101", "热胀刀柄");
		level3Map.put("050102", "液压刀柄");
		level3Map.put("050103", "冷压刀柄");
		level3Map.put("050104", "ER刀柄");
		level3Map.put("050105", "蜗杆刀柄");
		level3Map.put("060101", "整体螺纹铣刀");
		level3Map.put("060102", "螺纹铣刀杆");
		level3Map.put("060103", "螺纹铣刀片");
		level3Map.put("060201", "螺纹车刀杆");
		level3Map.put("060202", "螺纹车刀片");
		
		level3Map.put("020101", "标准平头铣刀");
		level3Map.put("020102", "波纹平头铣刀");
		level3Map.put("020103", "长颈平头铣刀");
		level3Map.put("020201", "标准球头铣刀");
		level3Map.put("020202", "长颈球头铣刀");
		level3Map.put("020301", "标准圆角铣刀");
		level3Map.put("020302", "长颈圆角铣刀");
		level3Map.put("020401", "锥形平头铣刀");
		level3Map.put("020402", "锥形圆角铣刀");
		level3Map.put("020403", "锥形球头铣刀");
		level3Map.put("030101", "外圆车刀杆");
		level3Map.put("030102", "内圆车刀杆");
		level3Map.put("030103", "切槽/切断刀杆");
		level3Map.put("030201", "普通车刀片");
		level3Map.put("030202", "精磨车刀片");
		level3Map.put("030203", "切槽/切断刀片");
		level3Map.put("060601", "手用丝锥");
		level3Map.put("060602", "挤压丝锥");
		level3Map.put("060603", "先端丝锥");
		level3Map.put("060604", "螺旋丝锥");

		codeMap.put("1", "010101");
		codeMap.put("2", "010102");
		codeMap.put("3", "010201");
		codeMap.put("4", "010202");
		codeMap.put("5", "010500");
		codeMap.put("6", "010601");
		codeMap.put("7", "010602");
		codeMap.put("8", "010800");
		codeMap.put("9", "020101");
		codeMap.put("10", "020102");
		codeMap.put("11", "020103");
		codeMap.put("12", "020201");
		codeMap.put("13", "020202");
		codeMap.put("14", "020301");
		codeMap.put("15", "020302");
		codeMap.put("16", "020401");
		codeMap.put("17", "020402");
		codeMap.put("18", "020403");
		codeMap.put("19", "020501");
		codeMap.put("20", "020502");
		codeMap.put("21", "020503");
		codeMap.put("22", "030101");
		codeMap.put("23", "030102");
		codeMap.put("24", "030103");
		
		codeMap.put("25", "030201");
		codeMap.put("26", "030202");
		codeMap.put("27", "030203");
		
		codeMap.put("28", "040101");
		codeMap.put("29", "040102");
		codeMap.put("30", "040201");
		codeMap.put("31", "040202");
		codeMap.put("32", "040301");
		codeMap.put("33", "040302");
		codeMap.put("34", "040401");
		codeMap.put("35", "040402");
		codeMap.put("36", "040900");
		codeMap.put("37", "041000");
		codeMap.put("38", "050101");
		codeMap.put("39", "050102");
		codeMap.put("40", "050103");
		codeMap.put("41", "050104");
		codeMap.put("42", "050105");
		codeMap.put("43", "050300");
		codeMap.put("44", "050400");
		codeMap.put("45", "050600");
		codeMap.put("46", "060101");
		codeMap.put("47", "060102");
		codeMap.put("48", "060103");
		codeMap.put("49", "060201");
		codeMap.put("50", "060202");
		codeMap.put("51", "060601");
		codeMap.put("52", "060602");
		codeMap.put("53", "060603");
		codeMap.put("54", "060604");
		codeMap.put("55", "060700");
		codeMap.put("56", "990100");
		codeMap.put("57", "990200");
		codeMap.put("58", "990300");

		orderMap.put("010101", "brand,diameter,cujing,usage,workingtool,angle,ctcount,pipesize");
		orderMap.put("010102", "brand,diameter,cujing,usage,workingtool,angle,ctcount,usefullength,totallength,shank,innercooling,shanktype,material");
		orderMap.put("010201", "brand,diameter,cujing,usage,workingtool,angle,ctcount,usefullength,interfacesize,innercooling");
		orderMap.put("010202", "brand,diameter,shank,interfacesize,usefullength,totallength,shanktype");
		orderMap.put("0105",   "brand,diameter,ctcount,workingtool,usefullength,totallength,interfacesize,shank,shanktype");
		orderMap.put("010601", "");
		orderMap.put("010602", "");
		orderMap.put("0108",   "brand,shape,backangle,workingtool,material");
		orderMap.put("020101", "brand,diameter,edgeno,workingtool,edgelength,totallength,shank,screwangle,innercooling,rangle,coatingtype,shanktype");
		orderMap.put("020102", "");
		orderMap.put("020103", "brand,diameter,edgeno,workingtool,edgelength,totallength,shank,screwangle,innercooling,necklength,coatingtype,shanktype");
		orderMap.put("020201", "brand,diameter,edgeno,workingtool,edgelength,totallength,shank");
		orderMap.put("020202", "brand,diameter,edgeno,workingtool,edgelength,totallength,shank,necklength");
		orderMap.put("020301", "brand,diameter,edgeno,workingtool,edgelength,totallength,shank,screwangle,innercooling,rangle,coatingtype,shanktype");
		orderMap.put("020302", "");
		orderMap.put("020401", "");
		orderMap.put("020402", "");
		orderMap.put("020403", "");
		orderMap.put("020501", "");
		orderMap.put("020502", "");
		orderMap.put("020503", "");
		orderMap.put("030101", "");
		orderMap.put("030102", "");
		orderMap.put("030103", "brand,direction,width,height,totallength");
		orderMap.put("030201", "brand,shape,backangle,workingtool,rangle,direction,material");
		orderMap.put("030202", "brand,shape,backangle,workingtool,rangle,direction,material");
		orderMap.put("030203", "brand,workingtool,thickness,maxslotdepth,material");
		orderMap.put("040101", "brand,diameter,diameterratio,workingtool,innercooling,screwangle,edgelengthe,totallength,shank,shanktype,coatingtype");
		orderMap.put("040102", "brand,diameter,diameterratio,workingtool,innercooling,screwangle,edgelengthe,totallength,shank,shanktype,coatingtype");
		orderMap.put("040201", "brand,drillrange,diameterratio,workingtool,innercooling,shank");
		orderMap.put("040202", "");
		orderMap.put("040301", "");
		orderMap.put("040302", "");
		orderMap.put("040401", "brand,diameter,diameterratio,workingtool,innercooling,screwangle,edgelengthe,totallength,shank,shanktype,direction");
		orderMap.put("040402", "");
		orderMap.put("0409",   "");
		orderMap.put("0410",   "");
		orderMap.put("050101", "");
		orderMap.put("050102", "");
		orderMap.put("050103", "brand,axistype,axisdetail,usefullength,handledsize,relativecollet,diameter");
		orderMap.put("050104", "brand,axistype,axisdetail,usefullength,handledsize,relativecollet,diameter");
		orderMap.put("050105", "");
		orderMap.put("0503",   "brand,axistype,axisdetail,pipesize,usefullength,diameter");
		orderMap.put("0504",   "brand,axistype,axisdetail,usefullength,handledsize");
		orderMap.put("0506",   "brand,handledsize,accuracy");
		orderMap.put("060101", "brand,screwtype,screwsize,screwdistance,screwdirection,edgeno,edgelength,totallength,shank,shanktype");
		orderMap.put("060102", "");
		orderMap.put("060103", "");
		orderMap.put("060201", "");
		orderMap.put("060202", "");
		orderMap.put("060601", "brand,screwtype,screwsize,screwdistance,workingtool,coatingtype,taptype");
		orderMap.put("060602", "brand,screwtype,screwsize,screwdistance,workingtool,coatingtype");
		orderMap.put("060603", "brand,screwtype,screwsize,screwdistance,workingtool,coatingtype");
		orderMap.put("060604", "brand,screwtype,screwsize,screwdistance,workingtool,coatingtype");
		orderMap.put("0607",   "");
		orderMap.put("9901",   "");
		orderMap.put("9902",   "");
		orderMap.put("9903",   "brand,minbore,maxbore,shanktype");

	}

	public static String orderParams(String param, String code)
	{
		//tmp为order的数组，paramArr为包含参数的li数组，paramSet为参数set
		String ret = "";
		String ret2 = "";
		String order = orderMap.get(code);
		Set<String> orderSet = new HashSet<String>();
		//order是空的，直接返回参数
		if(StringUtil.isEmpty(order))
		{
			return param;
		}
		
		String[] paramArr = param.split("</li>");

		String[] tmp = order.split(",");
		for(int i = 0;i<tmp.length;i++)
		{
			orderSet.add(tmp[i]);
		}
		
		for(int i = 0; i< tmp.length;i++)
		{
			if(StringUtil.isEmpty(tmp[i]))
			{
				break;
			}
			for(int j = 0;j< paramArr.length;j++)
			{
				int begin = paramArr[j].indexOf("\"") + 1;
				int end = paramArr[j].indexOf("\"",17);
				String p = paramArr[j].substring(begin, end);
				if(orderSet.contains(p))
				{
					if(!ret.contains(paramArr[j]) && StringUtil.isEqual(p, tmp[i]))
					{
						ret += paramArr[j]+"</li>";
					}
				}
				else
				{
					if(!ret2.contains(paramArr[j]))
					{
						ret2 += paramArr[j]+"</li>";
					}
				}
			}
		}
			
		return ret+ret2;
	}
	
	public static String getCodeValue(String order)
	{
		return codeMap.get(order);
	}

	public static void analyze()
	{
		try
		{
			Workbook book = Workbook.getWorkbook(new File("D:/data/type.xls"));
			// 获得第一个工作表对象
			Sheet sheet = book.getSheet(0);
			for (int j = 1; j < sheet.getRows(); j++)
			{
				if (StringUtil.isNotEmpty((sheet.getCell(2, j).getContents())))
				{
					System.out.println("level1Map.put(\""
							+ sheet.getCell(3, j).getContents() + "\",\""
							+ sheet.getCell(2, j).getContents() + "\");");
				}
				if (StringUtil.isNotEmpty((sheet.getCell(4, j).getContents())))
				{
					System.out.println("level2Map.put(\""
							+ sheet.getCell(3, j).getContents()
							+ sheet.getCell(5, j).getContents() + "\",\""
							+ sheet.getCell(4, j).getContents() + "\");");
				}
				if (StringUtil.isNotEmpty((sheet.getCell(6, j).getContents())))
				{
					System.out.println("level3Map.put(\""
							+ sheet.getCell(3, j).getContents()
							+ sheet.getCell(5, j).getContents()
							+ sheet.getCell(7, j).getContents() + "\",\""
							+ sheet.getCell(6, j).getContents() + "\");");
				}

			}
			book.close();
		} catch (Exception e)
		{
			System.out.println(e);
		}
	}

	public static boolean isLeaf(String code)
	{
		if (code.length() == 2)
		{
			return false;
		} else if (code.length() == 4)
		{
			if (StringUtil.isEmpty(level3Map.get(code + "01")))
				return true;
			else
				return false;
		} else
		{
			return true;
		}
	}

	// 补齐code
	public static String prehandleCode(String code)
	{
		if (code.length() == 4 && isLeaf(code))
		{
			return code + "00";
		} else
		{
			return code;
		}
	}

	public static String makeVersionTable(List<CuttingToolEntity> list)
	{
		String str = "<table class=\"version-table\"><thead><tr>";
		str += CuttingToolsConfiguration.getParamsHtml(list) + "</tr></thead><tbody>";
		String param = CuttingToolsConfiguration.getParamsHtml(list);
		for(int i = 0;i < list.size();i++)
		{
			str += "<tr>";
			str += "<td>"+list.get(i).getVersion() +"</td>";
		    if(StringUtil.isContains(param,">材质<"))
		        str += "<td>"+list.get(i).getMaterial() +"</td>";
		    
		    if(StringUtil.isContains(param,">主偏角<"))
		        str += "<td>"+list.get(i).getAngle() +"</td>";
		    
		    if(StringUtil.isContains(param,">刀片个数<"))
		        str += "<td>"+list.get(i).getCtcount() +"</td>";
		    
		    if(StringUtil.isContains(param,">直径<"))
		        str += "<td>"+list.get(i).getDiameter() +"</td>";
		    
		    if(StringUtil.isContains(param,">加工用途<"))
		        str += "<td>"+StringUtil.replaceSemicolon(list.get(i).getUsage())+"</td>";
		    
		    if(StringUtil.isContains(param,">光洁度<"))
		    {
		        if(list.get(i).getCujing() == 2)
				{
		        	str += "<td>精加工用</td>";
				}
				else if(list.get(i).getCujing() == 3)
				{
					str += "<td>粗加工用</td>";
				}
				else
				{
					str += "<td>一般加工用</td>";
				}
		    }
		    if(StringUtil.isContains(param,">有效长<"))
		        str += "<td>"+list.get(i).getUsefullength() +"</td>";
		    
		    if(StringUtil.isContains(param,">安装孔接口<"))
		        str += "<td>"+list.get(i).getPipesize() +"</td>";
		    
		    if(StringUtil.isContains(param,">柄径<"))
		        str += "<td>"+list.get(i).getShank() +"</td>";
		    
		    if(StringUtil.isContains(param,">柄部类型<"))
		        str += "<td>"+list.get(i).getShanktype() +"</td>";
		    
		    if(StringUtil.isContains(param,">形状<"))
		    {
		        if(StringUtil.isEqual(list.get(i).getShape() , "H"))
				{
					str += "<td>正六角形</td>";
				}
				else if(StringUtil.isEqual(list.get(i).getShape() , "O"))
				{
					str += "<td>八角形</td>";
				}
				else if(StringUtil.isEqual(list.get(i).getShape() , "P"))
				{
					str += "<td>五角形</td>";
				}
				else if(StringUtil.isEqual(list.get(i).getShape() , "S"))
				{
					str += "<td>正方形</td>";
				}
				else if(StringUtil.isEqual(list.get(i).getShape() , "T"))
				{
					str += "<td>三角形</td>";
				}
				else if(StringUtil.isEqual(list.get(i).getShape() , "C"))
				{
					str += "<td>菱形80°</td>";
				}
				else if(StringUtil.isEqual(list.get(i).getShape() , "D"))
				{
					str += "<td>菱形55°</td>";
				}
				else if(StringUtil.isEqual(list.get(i).getShape() , "E"))
				{
					str += "<td>菱形75°</td>";
				}
				else if(StringUtil.isEqual(list.get(i).getShape() , "F"))
				{
					str += "<td>菱形50°</td>";
				}
				else if(StringUtil.isEqual(list.get(i).getShape() , "M"))
				{
					str += "<td>菱形86°</td>";
				}
				else if(StringUtil.isEqual(list.get(i).getShape() , "V"))
				{
					str += "<td>菱形35°</td>";
				}
				else if(StringUtil.isEqual(list.get(i).getShape() , "W"))
				{
					str += "<td>不等角六角形</td>";
				}
				else if(StringUtil.isEqual(list.get(i).getShape() , "L"))
				{
					str += "<td>长方形90°</td>";
				}
				else if(StringUtil.isEqual(list.get(i).getShape() , "A"))
				{
					str += "<td>平行四边形顶角55°</td>";
				}
				else if(StringUtil.isEqual(list.get(i).getShape() , "R"))
				{
					str += "<td>圆形</td>";
				}
		    }
		    
		    if(StringUtil.isContains(param,">后角<"))
		        str += "<td>"+list.get(i).getBackangle() +"</td>";
		    
		    if(StringUtil.isContains(param,">适用工件<"))
		        str += "<td>"+StringUtil.replaceSemicolon(list.get(i).getWorkingtool())+"</td>";
		    
		    if(StringUtil.isContains(param,">刃数<"))
		        str += "<td>"+list.get(i).getEdgeno() +"</td>";
		    
		    if(StringUtil.isContains(param,">刃长<"))
		        str += "<td>"+list.get(i).getEdgelength() +"</td>";
		    
		    if(StringUtil.isContains(param,">总长<"))
		        str += "<td>"+list.get(i).getTotallength() +"</td>";
		    
		    if(StringUtil.isContains(param,">螺旋角<"))
		        str += "<td>"+list.get(i).getScrewangle() +"</td>";
		    
		    if(StringUtil.isContains(param,">涂层种类<"))
		        str += "<td>"+list.get(i).getCoatingtype() +"</td>";
		    
		    if(StringUtil.isContains(param,">R角<"))
		        str += "<td>"+list.get(i).getRangle() +"</td>";
		    
		    if(StringUtil.isContains(param,">方向<"))
		    {
		    	if(list.get(i).getDirection() == 2)
				{
					str += "<td>左手</td>";
				}
				else if(list.get(i).getDirection() == 3)
				{
					str += "<td>右手</td>";
				}
				else if(list.get(i).getDirection() == 1)
				{
					str += "<td>通用槽</td>";
				}
		    }

		    if(StringUtil.isContains(param,">最小加工直径<"))
		        str += "<td>"+list.get(i).getMinworkdiameter() +"</td>";
		    
		    if(StringUtil.isContains(param,">冷却方式<"))
		    {
		    	if(list.get(i).getInnercooling() == 1)
		    		str += "<td>一般</td>";
		    	else if(list.get(i).getInnercooling() == 2)
		    		str += "<td>内冷</td>";
		    	else if(list.get(i).getInnercooling() == 3)
		    		str += "<td>外冷</td>";
		    }
		    if(StringUtil.isContains(param,">倍径比<"))
		        str += "<td>"+list.get(i).getDiameterratio() +"</td>";
		    
		    if(StringUtil.isContains(param,">槽型状<"))
		        str += "<td>"+list.get(i).getSlotshape() +"</td>";
		    
		    if(StringUtil.isContains(param,">柄部规格<"))
		        str += "<td>"+list.get(i).getHandlenorm() +"</td>";
		    
		    if(StringUtil.isContains(param,">丝锥种类<"))
		        str += "<td>"+list.get(i).getTaptype() +"</td>";
		    
		    if(StringUtil.isContains(param,">螺纹类型<"))
		        str += "<td>"+list.get(i).getScrewtype() +"</td>";
		    
		    if(StringUtil.isContains(param,">主轴类型<"))
		        str += "<td>"+list.get(i).getAxistype() +"</td>";
		    
		    if(StringUtil.isContains(param,">主轴细分<"))
		        str += "<td>"+list.get(i).getAxisdetail() +"</td>";
		    
		    if(StringUtil.isContains(param,">厚度<"))
		        str += "<td>"+list.get(i).getThickness() +"</td>";
		    
		    if(StringUtil.isContains(param,">最大槽深<"))
		        str += "<td>"+list.get(i).getTaper() +"</td>";
		    
		    if(StringUtil.isContains(param,">锥度<"))
		        str += "<td>"+list.get(i).getMaxslotdepth() +"</td>";
		    
		    if(StringUtil.isContains(param,">槽宽<"))
		        str += "<td>"+list.get(i).getSlotwidth() +"</td>";
		    
		    if(StringUtil.isContains(param,">刀尖直径<"))
		        str += "<td>"+list.get(i).getPointdiameter() +"</td>";
		    
		    if(StringUtil.isContains(param,">可加持尺寸<"))
		        str += "<td>"+list.get(i).getHandledsize() +"</td>";
		    
		    if(StringUtil.isContains(param,">螺纹尺寸<"))
		        str += "<td>"+list.get(i).getScrewsize() +"</td>";
		    
		    if(StringUtil.isContains(param,">螺距<"))
		        str += "<td>"+list.get(i).getScrewdistance() +"</td>";
		    
		    if(StringUtil.isContains(param,">精度<"))
		        str += "<td>"+list.get(i).getAccuracy() +"</td>";
		    
		    if(StringUtil.isContains(param,">接口尺寸<"))
		        str += "<td>"+list.get(i).getInterfacesize() +"</td>";
		    
		    if(StringUtil.isContains(param,">颈长<"))
		        str += "<td>"+list.get(i).getNecklength() +"</td>";
		    
		    if(StringUtil.isContains(param,">对应筒夹"))
		        str += "<td>"+list.get(i).getRelativecollet() +"</td>";
		    
		    if(StringUtil.isContains(param,">宽度"))
		        str += "<td>"+list.get(i).getWidth() +"</td>";
		    
		    if(StringUtil.isContains(param,">高度"))
		        str += "<td>"+list.get(i).getHeight() +"</td>";
		    
		    if(StringUtil.isContains(param,">切槽范围"))
		        str += "<td>"+list.get(i).getGrooverange() +"</td>";
		    
		    if(StringUtil.isContains(param,">钻孔范围"))
		        str += "<td>"+list.get(i).getDrillrange() +"</td>";
		    
		    if(StringUtil.isContains(param,">螺纹方向"))
		        str += "<td>"+list.get(i).getScrewdirection() +"</td>";
		    
		    str += "<tr>";
		}
		str += "</tbody></table>";
		return str;
	}
	public static String getParamsHtml(List<CuttingToolEntity> cts)
	{
		String ret = "";
		for (int i = 0; i < cts.size(); i++)
		{
			if (StringUtil.isNotEmpty(cts.get(i).getVersion()) && !ret.contains("<th>型号</th>"))
			{
				ret += "<th>型号</th>";
			}
			if (StringUtil.isNotEmpty(cts.get(i).getMaterial())  && !ret.contains("<th>材质</th>"))
			{
				ret += "<th>材质</th>";
			}
			if (cts.get(i).getAngle() != 0 && !ret.contains("<th>主偏角</th>"))
			{
				ret += "<th>主偏角</th>";
			}
			if (cts.get(i).getCtcount() != 0 && !ret.contains("<th>刀片个数</th>"))
			{
				ret += "<th>刀片个数</th>";
			}
			if (StringUtil.isNotEmpty(cts.get(i).getDiameter()) && !ret.contains("<th>直径</th>"))
			{
				ret += "<th>直径</th>";
			}
			if (StringUtil.isNotEmpty(cts.get(i).getUsage())  && !ret.contains("<th>加工用途</th>"))
			{
				ret += "<th>加工用途</th>";
			}
			if (StringUtil.isNotEmpty(cts.get(i).getWorkingtype()) && !ret.contains("<th>加工类型</th>"))
			{
				ret += "<th>加工类型</th>";
			}
			if (cts.get(i).getCujing() != 0 && !ret.contains("<th>光洁度</th>"))
			{
				ret += "<th>光洁度</th>";
			}
			if (cts.get(i).getUsefullength() != 0 && !ret.contains("<th>有效长</th>"))
			{
				ret += "<th>有效长</th>";
			}
			if (cts.get(i).getPipesize() != 0 && !ret.contains("<th>安装孔接口</th>"))
			{
				ret += "<th>安装孔接口</th>";
			}
			if (StringUtil.isNotEmpty(cts.get(i).getShank())  && !ret.contains("<th>柄径</th>"))
			{
				ret += "<th>柄径</th>";
			}
			if (StringUtil.isNotEmpty(cts.get(i).getShanktype())  && !ret.contains("<th>柄部类型</th>"))
			{
				ret += "<th>柄部类型</th>";
			}
			if (StringUtil.isNotEmpty(cts.get(i).getShape())  && !ret.contains("<th>形状</th>"))
			{
				ret += "<th>形状</th>";
			}
			if (cts.get(i).getBackangle() != 1000 && !ret.contains("<th>后角</th>"))
			{
				ret += "<th>后角</th>";
			}
			if (StringUtil.isNotEmpty(cts.get(i).getWorkingtool())  && !ret.contains("<th>适用工件</th>"))
			{
				ret += "<th>适用工件</th>";
			}
			if (cts.get(i).getEdgeno() != 0 && !ret.contains("<th>刃数</th>"))
			{
				ret += "<th>刃数</th>";
			}
			if (StringUtil.isNotEmpty(cts.get(i).getEdgelength()) && !ret.contains("<th>刃长</th>"))
			{
				ret += "<th>刃长</th>";
			}
			if (StringUtil.isNotEmpty(cts.get(i).getTotallength()) && !ret.contains("<th>总长</th>"))
			{
				ret += "<th>总长</th>";
			}
			if (StringUtil.isNotEmpty(cts.get(i).getScrewangle()) && !ret.contains("<th>螺旋角</th>"))
			{
				ret += "<th>螺旋角</th>";
			}
			if (StringUtil.isNotEmpty(cts.get(i).getCoatingtype())  && !ret.contains("<th>涂层种类</th>"))
			{
				ret += "<th>涂层种类</th>";
			}
			if (StringUtil.isNotEmpty(cts.get(i).getRangle()) && !ret.contains("<th>R角</th>"))
			{
				ret += "<th>R角</th>";
			}
			if (cts.get(i).getDirection() != 0 && !ret.contains("<th>方向</th>"))
			{
				ret += "<th>方向</th>";
			}
			if (cts.get(i).getMinworkdiameter() != 0 && !ret.contains("<th>最小加工直径</th>"))
			{
				ret += "<th>最小加工直径</th>";
			}
			if (cts.get(i).getInnercooling() != 0 && !ret.contains("<th>冷却方式</th>"))
			{
				ret += "<th>冷却方式</th>";
			}
			if (StringUtil.isNotEmpty(cts.get(i).getDiameterratio())  && !ret.contains("<th>倍径比</th>"))
			{
				ret += "<th>倍径比</th>";
			}
			if (StringUtil.isNotEmpty(cts.get(i).getSlotshape())  && !ret.contains("<th>槽型状</th>"))
			{
				ret += "<th>槽型状</th>";
			}
			if (StringUtil.isNotEmpty(cts.get(i).getHandlenorm())  && !ret.contains("<th>柄部规格</th>"))
			{
				ret += "<th>柄部规格</th>";
			}
			if (StringUtil.isNotEmpty(cts.get(i).getTaptype())  && !ret.contains("<th>丝锥种类</th>"))
			{
				ret += "<th>丝锥种类</th>";
			}
			if (StringUtil.isNotEmpty(cts.get(i).getScrewtype())  && !ret.contains("<th>螺纹种类</th>"))
			{
				ret += "<th>螺纹种类</th>";
			}
			if (StringUtil.isNotEmpty(cts.get(i).getAxistype())  && !ret.contains("<th>主轴种类</th>"))
			{
				ret += "<th>主轴种类</th>";
			}
			if (StringUtil.isNotEmpty(cts.get(i).getAxisdetail())  && !ret.contains("<th>主轴细分</th>"))
			{
				ret += "<th>主轴细分</th>";
			}
			if (cts.get(i).getThickness() != 0 && !ret.contains("<th>厚度</th>"))
			{
				ret += "<th>厚度</th>";
			}
			if (cts.get(i).getMaxslotdepth() != 0 && !ret.contains("<th>最大槽深</th>"))
			{
				ret += "<th>最大槽深</th>";
			}
			if (cts.get(i).getTaper() != 0 && !ret.contains("<th>锥度</th>"))
			{
				ret += "<th>锥度</th>";
			}
			if (cts.get(i).getSlotwidth() != 0 && !ret.contains("<th>槽宽</th>"))
			{
				ret += "<th>槽宽</th>";
			}
			if (cts.get(i).getPointdiameter() != 0 && !ret.contains("<th>刀尖直径</th>"))
			{
				ret += "<th>刀尖直径</th>";
			}
			if (StringUtil.isNotEmpty(cts.get(i).getHandledsize())  && !ret.contains("<th>可加持尺寸</th>"))
			{
				ret += "<th>可加持尺寸</th>";
			}
			if (StringUtil.isNotEmpty(cts.get(i).getScrewsize())  && !ret.contains("<th>螺纹尺寸</th>"))
			{
				ret += "<th>螺纹尺寸</th>";
			}
			if (StringUtil.isNotEmpty(cts.get(i).getScrewdistance())  && !ret.contains("<th>螺距</th>"))
			{
				ret += "<th>螺距</th>";
			}
			if (StringUtil.isNotEmpty(cts.get(i).getAccuracy())  && !ret.contains("<th>精度</th>"))
			{
				ret += "<th>精度</th>";
			}
			if (StringUtil.isNotEmpty(cts.get(i).getInterfacesize())  && !ret.contains("<th>接口尺寸</th>"))
			{
				ret += "<th>接口尺寸</th>";
			}
			if (StringUtil.isNotEmpty(cts.get(i).getNecklength()) && !ret.contains("<th>颈长</th>"))
			{
				ret += "<th>颈长</th>";
			}
			if (StringUtil.isNotEmpty(cts.get(i).getRelativecollet()) && !ret.contains("<th>对应筒夹</th>"))
			{
				ret += "<th>对应筒夹</th>";
			}
			if (cts.get(i).getHeight() != 0 && !ret.contains("<th>高度</th>"))
			{
				ret += "<th>高度</th>";
			}
			if (cts.get(i).getWidth() != 0 && !ret.contains("<th>宽度</th>"))
			{
				ret += "<th>宽度</th>";
			}
			if (StringUtil.isNotEmpty(cts.get(i).getGrooverange()) && !ret.contains("<th>切槽范围</th>"))
			{
				ret += "<th>切槽范围</th>";
			}
			if (StringUtil.isNotEmpty(cts.get(i).getDrillrange()) && !ret.contains("<th>钻孔范围</th>"))
			{
				ret += "<th>钻孔范围</th>";
			}
			if (StringUtil.isNotEmpty(cts.get(i).getScrewdirection()) && !ret.contains("<th>螺纹方向</th>"))
			{
				ret += "<th>螺纹方向</th>";
			}
		}
		return ret;
	}

	public static String getSeriesParamSpan(CuttingToolEntity ct, String ret)
	{
		String tmp = "";
		if(StringUtil.isEmpty(ret))
		{
			return tmp;
		}
		if (ret.contains("brand"))
		{
			tmp += "<span>" + ct.getBrand() + "</span>";
		}
		if (ret.contains("material"))
		{
			tmp += "<span>" + ct.getMaterial() + "</span>";
		}
		if (ret.contains(",angle"))
		{
			tmp += "<span>主偏角"+ct.getAngle()+"°</span>";
		}
		if (ret.contains("usage"))
		{
			tmp += "<span>"+ct.getUsage().replace(";", "/")+"</span>";
		}
		if (ret.contains("workingtype"))
		{
			tmp += "<span>"+ct.getWorkingtype().replace("；", "/").replace(";", "/")+"</span>";
		}
		if (ret.contains("<span>光洁度</span>"))
		{
			if(ct.getCujing() == 2)
			{
				tmp += "<span>精加工用</span>";
			}
			else if(ct.getCujing() == 3)
			{
				tmp += "<span>粗加工用</span>";
			}
			else
			{
				tmp += "<span>一般加工用</span>";
			}
		}
		if (ret.contains("shanktype"))
		{
			tmp += "<span>"+ct.getShanktype()+"</span>";
		}
		if (ret.contains(",shape"))
		{
			String shape = ct.getShape();
			if(StringUtil.isEqual(shape, "H"))
			{
				tmp += "<span>正六角形</span>";
			}
			else if(StringUtil.isEqual(shape, "O"))
			{
				tmp += "<span>八角形</span>";
			}
			else if(StringUtil.isEqual(shape, "P"))
			{
				tmp += "<span>五角形</span>";
			}
			else if(StringUtil.isEqual(shape, "S"))
			{
				tmp += "<span>正方形</span>";
			}
			else if(StringUtil.isEqual(shape, "T"))
			{
				tmp += "<span>三角形</span>";
			}
			else if(StringUtil.isEqual(shape, "C"))
			{
				tmp += "<span>菱形80°</span>";
			}
			else if(StringUtil.isEqual(shape, "D"))
			{
				tmp += "<span>菱形55°</span>";
			}
			else if(StringUtil.isEqual(shape, "E"))
			{
				tmp += "<span>菱形75°</span>";
			}
			else if(StringUtil.isEqual(shape, "F"))
			{
				tmp += "<span>菱形50°</span>";
			}
			else if(StringUtil.isEqual(shape, "M"))
			{
				tmp += "<span>菱形86°</span>";
			}
			else if(StringUtil.isEqual(shape, "V"))
			{
				tmp += "<span>菱形35°</span>";
			}
			else if(StringUtil.isEqual(shape, "W"))
			{
				tmp += "<span>不等角六角形</span>";
			}
			else if(StringUtil.isEqual(shape, "L"))
			{
				tmp += "<span>长方形90°</span>";
			}
			else if(StringUtil.isEqual(shape, "A"))
			{
				tmp += "<span>平行四边形顶角55°</span>";
			}
			else if(StringUtil.isEqual(shape, "R"))
			{
				tmp += "<span>圆形</span>";
			}
			
		}
		if (ret.contains("<span>后角</span>"))
		{
			tmp += "<span>后角"+ct.getRangle()+"°</span>";
		}
		if (ret.contains("workingtool"))
		{
			tmp += "<span>"+ct.getWorkingtool().replace(";", "用/")+"</span>";
		}
		if (ret.contains("edgeno"))
		{
			tmp += "<span>"+ct.getEdgeno()+"刃</span>";
		}
		if (ret.contains("screwangle"))
		{
			tmp += "<span>螺旋角"+ct.getScrewangle()+"°</span>";
		}
		if (ret.contains("coatingtype"))
		{
			String coating = ct.getCoatingtype();
			if(StringUtil.isEqual(coating, "有"))
			{
				tmp += "<span>有涂层</span>";
			}
			else if(StringUtil.isEqual(coating, "无"))
			{
				tmp += "<span>无涂层</span>";
			}
			else
			{
				tmp += "<span>"+ coating +"</span>";
			}
		}
		if (ret.contains("rangle"))
		{
			tmp += "<span>"+ct.getRangle()+"</span>";
		}
		if (ret.contains("direction"))
		{
			if(ct.getDirection() == 2)
			{
				tmp += "<span>左手</span>";
			}
			else if(ct.getDirection() == 3)
			{
				tmp += "<span>右手</span>";
			}
			else if(ct.getDirection() == 1)
			{
				tmp += "<span>通用槽</span>";
			}
			
		}
		if (ret.contains("minworkdiamter"))
		{
			tmp += "<span>最小加工"+ct.getMinworkdiameter()+"mm</span>";
		}
		if (ret.contains("innercooling"))
		{
			if(ct.getInnercooling() == 1)
			{
				tmp += "<span>一般</span>";
			}
			else if(ct.getInnercooling() == 3)
			{
				tmp += "<span>外冷</span>";
			}
			else if(ct.getInnercooling() == 2)
			{
				tmp += "<span>内冷</span>";
			}
		}
		if (ret.contains(",diameterratio"))
		{
			tmp += "<span>"+ct.getDiameterratio()+"</span>";
		}
		if (ret.contains("slotshape"))
		{
			tmp += "<span>"+ct.getSlotshape()+"</span>";
		}
		if (ret.contains("handlenorm"))
		{
			tmp += "<span>"+ct.getHandlenorm()+"</span>";
		}
		if (ret.contains("taptype"))
		{
			tmp += "<span>"+ct.getTaptype()+"</span>";
		}
		if (ret.contains("screwtype"))
		{
			tmp += "<span>"+ct.getScrewtype()+"</span>";
		}
		if (ret.contains("axistype"))
		{
			tmp += "<span>"+ct.getAxistype()+"</span>";
		}
		if (ret.contains("thickness"))
		{
			tmp += "<span>厚度"+ct.getThickness()+"mm</span>";
		}
		if (ret.contains("maxslotdepth"))
		{
			tmp += "<span>槽深"+ct.getMaxslotdepth()+"mm</span>";
		}
		if (ret.contains(",taper"))
		{
			tmp += "<span>锥度"+ct.getTaper()+"°</span>";
		}
		if (ret.contains("slotwidth"))
		{
			tmp += "<span>槽宽"+ct.getSlotwidth()+"mm</span>";
		}
		if (ret.contains("pointdiameter"))
		{
			tmp += "<span>刀尖直径"+ct.getPointdiameter()+"mm</span>";
		}
		if (ret.contains("handledsize"))
		{
			tmp += "<span>"+ct.getHandledsize()+"</span>";
		}
		if (ret.contains("screwsize"))
		{
			tmp += "<span>"+ct.getScrewsize()+"</span>";
		}
		if (ret.contains("screwdistance"))
		{
			tmp += "<span>"+ct.getScrewdistance()+"</span>";
		}
		if (ret.contains("accuracy"))
		{
			tmp += "<span>精度"+ct.getAccuracy()+"</span>";
		}
		if (ret.contains("interfacesize"))
		{
			tmp += "<span>"+ct.getInterfacesize()+"</span>";
		}
		if (ret.contains("relativecollet"))
		{
			tmp += "<span>"+ct.getNecklength()+"</span>";
		}
		if (ret.contains("<span>height</span>"))
		{
			tmp += "<span>"+ct.getHeight()+"</span>";
		}
		if (ret.contains("<span>宽度</span>"))
		{
			tmp += "<span>"+ct.getWidth()+"</span>";
		}
		if (ret.contains("<span>切槽范围</span>"))
		{
			tmp += "<span>"+ct.getGrooverange()+"</span>";
		}
		if (ret.contains("<span>钻孔范围</span>"))
		{
			tmp += "<span>"+ct.getDrillrange()+"</span>";
		}
		if (ret.contains("<span>螺纹方向</span>"))
		{
			tmp += "<span>"+ct.getScrewdirection()+"</span>";
		}
		return tmp;
	}

	public static List<String> convertSetToList(HashSet<String> param)
	{
		Iterator<String> iter = param.iterator();
		List<String> list = new ArrayList<String>();
		while(iter.hasNext())
		{
			String str = iter.next();
			if(StringUtil.isNotEmpty(str))
				list.add(str);
		}
		return list;
	}
	
	public static List<String> convertShapeToList(HashSet<String> param)
	{
		Iterator<String> iter = param.iterator();
		List<String> list = new ArrayList<String>();
		while(iter.hasNext())
		{
			String str = iter.next();
			if(StringUtil.isNotEmpty(str))
			{
				if(StringUtil.isEqual(str, "H"))
				{
					list.add("H(正六角形)");
				}
				else if(StringUtil.isEqual(str, "O"))
				{
					list.add("O(八角形)");
				}
				else if(StringUtil.isEqual(str, "P"))
				{
					list.add("P(五角型)");
				}
				else if(StringUtil.isEqual(str, "S"))
				{
					list.add("S(正方形)");
				}
				else if(StringUtil.isEqual(str, "T"))
				{
					list.add("T(三角形)");
				}
				else if(StringUtil.isEqual(str, "C"))
				{
					list.add("C(菱形80°)");
				}
				else if(StringUtil.isEqual(str, "D"))
				{
					list.add("D(菱形55°)");
				}
				else if(StringUtil.isEqual(str, "E"))
				{
					list.add("E(菱形75°)");
				}
				else if(StringUtil.isEqual(str, "F"))
				{
					list.add("F(菱形50°)");
				}
				else if(StringUtil.isEqual(str, "M"))
				{
					list.add("M(菱形86°)");
				}
				else if(StringUtil.isEqual(str, "V"))
				{
					list.add("V(菱形35°)");
				}
				else if(StringUtil.isEqual(str, "W"))
				{
					list.add("W(不等角六角形)");
				}
				else if(StringUtil.isEqual(str, "L"))
				{
					list.add("L(长方形90°)");
				}
				else if(StringUtil.isEqual(str, "A"))
				{
					list.add("A(四边形顶角85°)");
				}
				else if(StringUtil.isEqual(str, "B"))
				{
					list.add("B(四边形顶角82°)");
				}
				else if(StringUtil.isEqual(str, "K"))
				{
					list.add("K(四边形顶角55°)");
				}
				else if(StringUtil.isEqual(str, "R"))
				{
					list.add("R(圆形)");
				}
			}
		}
		return list;
	}
	
	public static List<String> mergeList(HashSet<String> param)
	{
		List<List<String>> list = CuttingToolsConfiguration.sortSize(param);
		List<String> list1 = list.get(0);
		List<String> list2 = list.get(1);
		list1.addAll(list2);
		return list1;
	}
	
	public static String aggregateSpan(List<String> list)
	{
		String ret="";
		if(list.size()<10000)
		{
			for(int i = 0; i<list.size();i++)
	        {
				ret += "<span>"+list.get(i)+"</span>";
	        }
			return ret;
		}
		else
		{
			for(int i = 0; i<list.size();i++)
	        {
				if(i%15==0)
				{
					ret += "<div class=\"aggr\"><h3>"+list.get(i)+"~</h3><div class=\"list clearfix\">";
				}
				ret += "<span>"+list.get(i)+"</span>";
				if(i%15==14)
				{
					ret += "</div></div>";
					int begin = ret.lastIndexOf("~");
					ret = ret.substring(0, begin+1)+list.get(i)+ret.substring(begin+1, ret.length());
				}
	        }
			if(list.size()%15 != 14)
			{
				ret += "</div></div>";
				ret = ret.substring(0, ret.lastIndexOf("~")+1)+list.get(list.size()-1)+ret.substring(ret.lastIndexOf("~")+1, ret.length());
			}
			return ret;
		}
	}
	//公制英寸分离，返回两种结果
	public static List<List<String>> sortSize(HashSet<String> list)
	{
		List<String> list1 = new ArrayList<String>();  
		List<String> list2 = new ArrayList<String>();  
		List<String> list3 = new ArrayList<String>();  
		Map<String, Double> map1 = new HashMap<String, Double>();
		Map<String, Double> map2 = new HashMap<String, Double>();
        for(final String value : list){  
        	if(StringUtil.isEmpty(value))
        	{
        		continue;
        	}
        	if(StringUtil.isContansLetter(value))
        	{
        		list3.add(value);
        		continue;
        	}
            if(!value.contains("\""))//不是英制
            	map1.put(value, Double.valueOf(value));
            else//英制
            {
            	double big = 0;
            	double small = 0;
            	String size = value.replace("\"", "");
            	if(size.contains("."))
            	{
            		map2.put(value,Double.valueOf(size));
            		continue;
            	}
            	if(size.contains("-"))
            	{
            		big = Double.valueOf(size.split("-")[0]);
            		double fenmu = Double.valueOf(size.split("-")[1].split("/")[0]);
            		double fenzi = Double.valueOf(size.split("-")[1].split("/")[1]);
            		small = fenmu/fenzi;
            		map2.put(value, big+small);
            		continue;
            	}
            	if(size.contains("/"))
            	{
            		double fenmu = Double.valueOf(size.split("/")[0]);
            		double fenzi = Double.valueOf(size.split("/")[1]);
            		map2.put(value, fenmu/fenzi);
            	}
            }
        }  
        List<Map.Entry<String, Double>> infoIds1 =
        	    new ArrayList<Map.Entry<String, Double>>(map1.entrySet());
        List<Map.Entry<String, Double>> infoIds2 =
        	    new ArrayList<Map.Entry<String, Double>>(map2.entrySet());
        Collections.sort(infoIds1, new Comparator<Map.Entry<String, Double>>() {   
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {      
                return (o1.getValue()-o2.getValue() > 0)?1:-1;
            }
        }); 
        Collections.sort(infoIds2, new Comparator<Map.Entry<String, Double>>() {   
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {      
                return (o1.getValue()-o2.getValue() > 0)?1:-1;
            }
        }); 
        List<List<String>> ret = new ArrayList<List<String>>();
        for (int i = 0; i < infoIds1.size(); i++) 
       	{
        	list1.add(infoIds1.get(i).getKey());
       	}
        for (int i = 0; i < infoIds2.size(); i++) 
       	{
        	list2.add(infoIds2.get(i).getKey());
       	}
        ret.add(list1);
        ret.add(list2);
        ret.add(list3);
        return ret;
	}
	
	public static List<String> splitWorkingtoolOrUsage(HashSet<String> set)
	{
		HashSet<String> list = new HashSet<String>();
		List<String> ret = new ArrayList<String>();
		for(final String value : set){  
			if(StringUtil.isEmpty(value))
				continue;
			String[] tmp = value.contains("/")?value.split("/"):value.split(";");
			for(int i = 0;i<tmp.length;i++)
				list.add(tmp[i]);
			
		}
		for(final String value : list){  
			ret.add(value);
		}
		return ret;
	}
	
	public static List<String> sortIntegerList(HashSet<Integer> param)
	{
		List<Integer> list = new ArrayList<Integer>();
		for(final Integer value : param){  
        	if(value != 0)
        		list.add(value);  
        }  
        Collections.sort(list);  
        List<String> ret = new ArrayList<String>();  
        for(final Integer value : list){  
            ret.add(String.valueOf(value));  
        }  
        return ret;
	}
	
	public static List<String> sortBaList(HashSet<Integer> param)
	{
		List<Integer> list = new ArrayList<Integer>();
		for(final Integer value : param){  
        	if(value != 1000)
        		list.add(value);  
        }  
        Collections.sort(list);  
        List<String> ret = new ArrayList<String>();  
        for(final Integer value : list){  
        	switch(value)
    		{
    		case 3:
    			ret.add("A(3°)");
    			break;
    		case 5:
    			ret.add("B(5°)");
    			break;
    		case 7:
    			ret.add("C(7°)");
    			break;
    		case 15:
    			ret.add("D(15°)");
    			break;
    		case 20:
    			ret.add("E(20°)");
    			break;
    		case 25:
    			ret.add("F(25°)");
    			break;
    		case 30:
    			ret.add("G(30°)");
    			break;
    		case 0:
    			ret.add("N(0°)");
    			break;
    		case 11:
    			ret.add("P(11°)");
    			break;
    		}
        }  
        return ret;
	}
	
	public static List<String> sortDoubleList(HashSet<Double> param)
	{
		List<Double> list = new ArrayList<Double>();
		for(final Double value : param){  
        	if(value != 0)
        		list.add(value);  
        }  
        Collections.sort(list);  
        List<String> ret = new ArrayList<String>();  
        for(final Double value : list){  
            ret.add(String.valueOf(value));  
        }  
        return ret;
	}
	
	public static String formatCujing(String direction)
	{
		
		if(StringUtil.isEqual(direction, "粗加工"))
		{
			return "3";
		}
		else if(StringUtil.isEqual(direction, "粗加工"))
		{
			return "2";
		}
		else if(StringUtil.isEqual(direction, "一般加工"))
		{
			return "1";
		}
		else
		{
			return "0";
		}
	}
	
	public static String formatDirection(String direction)
	{
		
		if(StringUtil.isEqual(direction, "左手"))
		{
			return "2";
		}
		else if(StringUtil.isEqual(direction, "右手"))
		{
			return "3";
		}
		else if(StringUtil.isEqual(direction, "通用槽"))
		{
			return "1";
		}
		else
		{
			return "0";
		}
	}
	
	public static String convertBackangle(int ba)
	{
		if(ba != 1000)
    	{
    		switch(ba)
    		{
    		case 3:
    			return "A(3°)";
    		case 5:
    			return "B(5°)";
    			
    		case 7:
    			return "C(7°)";
    			
    		case 15:
    			return "D(15°)";
    			
    		case 20:
    			return "E(20°)";
    			
    		case 25:
    			return "F(25°)";
    			
    		case 30:
    			return "G(30°)";
    			
    		case 0:
    			return "N(0°)";
    			
    		case 11:
    			return "P(11°)";
    			
    		}
    	}
		return "";
	}
	
	public static String formatBackangle(String ba)
	{
		
		if(StringUtil.isEqual(ba, "A(3°)"))
		{
			return "3";
		}
		else if(StringUtil.isEqual(ba, "B(5°)"))
		{
			return "5";
		}
		else if(StringUtil.isEqual(ba, "C(7°)"))
		{
			return "7";
		}
		else if(StringUtil.isEqual(ba, "D(15°)"))
		{
			return "15";
		}
		else if(StringUtil.isEqual(ba, "E(20°)"))
		{
			return "20";
		}
		else if(StringUtil.isEqual(ba, "F(25°)"))
		{
			return "25";
		}
		else if(StringUtil.isEqual(ba, "G(30°)"))
		{
			return "30";
		}
		else if(StringUtil.isEqual(ba, "N(0°)"))
		{
			return "0";
		}
		else if(StringUtil.isEqual(ba, "P(11°)"))
		{
			return "11";
		}
		else
		{
			return "1000";
		}
	}
	
	public static String convertShape(String shape)
	{
		if(StringUtil.isNotEmpty(shape))
		{
			if(StringUtil.isEqual(shape, "H"))
			{
				return "H(正六角形)";
			}
			else if(StringUtil.isEqual(shape, "O"))
			{
				return "O(八角形)";
			}
			else if(StringUtil.isEqual(shape, "P"))
			{
				return "P(五角型)";
			}
			else if(StringUtil.isEqual(shape, "S"))
			{
				return "S(正方形)";
			}
			else if(StringUtil.isEqual(shape, "T"))
			{
				return "T(三角形)";
			}
			else if(StringUtil.isEqual(shape, "C"))
			{
				return "C(菱形80°)";
			}
			else if(StringUtil.isEqual(shape, "D"))
			{
				return "D(菱形55°)";
			}
			else if(StringUtil.isEqual(shape, "E"))
			{
				return "E(菱形75°)";
			}
			else if(StringUtil.isEqual(shape, "F"))
			{
				return "F(菱形50°)";
			}
			else if(StringUtil.isEqual(shape, "M"))
			{
				return "M(菱形86°)";
			}
			else if(StringUtil.isEqual(shape, "V"))
			{
				return "V(菱形35°)";
			}
			else if(StringUtil.isEqual(shape, "W"))
			{
				return "W(不等角六角形)";
			}
			else if(StringUtil.isEqual(shape, "L"))
			{
				return "L(长方形90°)";
			}
			else if(StringUtil.isEqual(shape, "A"))
			{
				return "A(四边形顶角85°)";
			}
			else if(StringUtil.isEqual(shape, "B"))
			{
				return "B(四边形顶角82°)";
			}
			else if(StringUtil.isEqual(shape, "K"))
			{
				return "K(四边形顶角55°)";
			}
			else if(StringUtil.isEqual(shape, "R"))
			{
				return "R(圆形)";
			}
		}
		return "";
		
	}
	public static String formatShape(String shape)
	{
		String ret="";
		if(StringUtil.isEqual(shape, "H(正六角形)"))
		{
			ret = "H";
		}
		else if(StringUtil.isEqual(shape, "O(八角形)"))
		{
			ret ="O";
		}
		else if(StringUtil.isEqual(shape, "P(五角型)"))
		{
			ret = "P";
		}
		else if(StringUtil.isEqual(shape, "S(正方形)"))
		{
			ret = "S";
		}
		else if(StringUtil.isEqual(shape, "T(三角形)"))
		{
			ret = "T";
		}
		else if(StringUtil.isEqual(shape, "C(菱形80°)"))
		{
			ret = "C";
		}
		else if(StringUtil.isEqual(shape, "D(菱形55°)"))
		{
			ret = "D";
		}
		else if(StringUtil.isEqual(shape, "E(菱形75°)"))
		{
			ret = "E";
		}
		else if(StringUtil.isEqual(shape, "F(菱形50°)"))
		{
			ret = "F";
		}
		else if(StringUtil.isEqual(shape, "M(菱形86°)"))
		{
			ret = "M";
		}
		else if(StringUtil.isEqual(shape, "V(菱形35°)"))
		{
			ret = "V";
		}
		else if(StringUtil.isEqual(shape, "W(不等角六角形)"))
		{
			ret = "W";
		}
		else if(StringUtil.isEqual(shape, "L(长方形90°)"))
		{
			ret = "L";
		}
		else if(StringUtil.isEqual(shape, "A(四边形顶角85°)"))
		{
			ret ="A";
		}
		else if(StringUtil.isEqual(shape, "B(四边形顶角82°)"))
		{
			ret ="B";
		}
		else if(StringUtil.isEqual(shape, "K(四边形顶角55°)"))
		{
			ret = "K";
		}
		else if(StringUtil.isEqual(shape, "R(圆形)"))
		{
			ret ="R";
		}
		return ret;
	}
	
	public static String formatCooling(String cooling)
	{
		
		if(StringUtil.isEqual(cooling, "内冷"))
		{
			return "2";
		}
		else if(StringUtil.isEqual(cooling, "外冷"))
		{
			return "3";
		}
		else if(StringUtil.isEqual(cooling, "一般"))
		{
			return "1";
		}
		else
		{
			return "0";
		}
	}
	
	public static String getCategoryPic(String pic)
	{
		if (StringUtil.isEmpty(pic))
		{
			return null;
		}
		if (pic.contains("|"))
		{
			String ret = "";
			String[] pics = pic.split("[|]");
			for (int i = 0; i < pics.length; i++)
			{
				ret += "<li><a target=\"__blank\" href=\"/img/sample/"+ pics[i] + "\"><img src=\"/img/sample/" + pics[i] + "\" /></a></li>";
			}
			return ret;
		} else
		{
			return "<li><a target=\"__blank\" href=\"/img/sample/"+ pic + "\"><img src=\"/img/sample/" + pic + "\" /></a></li>";
		}
	}

	public static String getCategoryPicForWx(String pic)
	{
		if (StringUtil.isEmpty(pic))
		{
			return null;
		}
		if (pic.contains("|"))
		{
			String ret = "";
			String[] pics = pic.split("[|]");
			for (int i = 0; i < pics.length; i++)
			{
				ret += "<li><a target=\"__blank\" href=\"/img/sample/"+ pics[i] + "\">样本"+String.valueOf(i+1)+"</a></li>";
			}
			return ret;
		} else
		{
			return "<li><a target=\"__blank\" href=\"/img/sample/"+ pic + "\">样本</a></li>";
		}
	}
	
	public static String getCategoryHtml(String code, boolean lastislink)
	{
		String ret = "";
		if (code.length() == 6)
		{
			if (Integer.valueOf(code.substring(0, 2)) != 0)
			{
				ret += "<a class=\"top\" href=\"/category/" + code.substring(0, 2) + "\">"
						+ level1Map.get(code.substring(0, 2)) + "</a>";
			}
			if (Integer.valueOf(code.substring(2, 4)) != 0)
			{
				ret += " > <a href=\"/category/" + code.substring(0, 4) + "\">"
						+ level2Map.get(code.substring(0, 4)) + "</a>";
			}
			if (Integer.valueOf(code.substring(4, 6)) != 0)
			{
				if (lastislink)
					ret += " > <a href=\"/category/" + code.substring(0, 6)
							+ "\">" + level3Map.get(code.substring(0, 6))
							+ "</a>";
				else
					ret += " > " + level3Map.get(code.substring(0, 6));
			}
			return ret;
		} 
		else if (code.length() == 4)
		{
			if (Integer.valueOf(code.substring(0, 2)) != 0)
			{
				ret += "<a class=\"top\" href=\"/category/" + code.substring(0, 2) + "\">"
						+ level1Map.get(code.substring(0, 2)) + "</a>";
			}
			if (Integer.valueOf(code.substring(2, 4)) != 0)
			{
				if (lastislink)
					ret += "-><a href=\"/category/" + code.substring(0, 4)
							+ "\">" + level2Map.get(code.substring(0, 4))
							+ "</a>";
				else
					ret += " > " + level2Map.get(code.substring(0, 4));
			}
			return ret;
		} 
		else
		{
			if (Integer.valueOf(code.substring(0, 2)) != 0)
			{
				if (lastislink)
					ret += "<a class=\"top\" href=\"/category/" + code.substring(0, 2)
							+ "\">" + level1Map.get(code.substring(0, 2))
							+ "</a>";
				else
					ret += "<span class=\"top\">"+level1Map.get(code.substring(0, 2))+"</span>";
			}
			return ret;
		}
	}

	public static List<CategoryEntity> getNextCategories(String code)
	{
		List<CategoryEntity> categories = new ArrayList<CategoryEntity>();
		if (code.length() == 4)// 查找3级的类型
		{
			Iterator<Entry<String, String>> iter = level3Map.entrySet()
					.iterator();

			while (iter.hasNext())
			{
				Map.Entry<String, String> entry = (Map.Entry<String, String>) iter
						.next();
				if (entry.getKey().toString().substring(0, 4).equals(code))
				{
					CategoryEntity cat = new CategoryEntity();
					cat.setCode(entry.getKey().toString());
					cat.setName(entry.getValue().toString());
					cat.setPic(entry.getValue().toString().contains("/") ? entry
							.getValue().toString().replace("/", "")
							: entry.getValue().toString());
					categories.add(cat);
				}
			}
		} 
		else
		{
			Iterator<Entry<String, String>> iter = level2Map.entrySet()
					.iterator();

			while (iter.hasNext())
			{
				Map.Entry<String, String> entry = (Map.Entry<String, String>) iter
						.next();
				if (entry.getKey().toString().substring(0, 2).equals(code))
				{
					CategoryEntity cat = new CategoryEntity();
					cat.setCode(entry.getKey().toString());
					cat.setName(entry.getValue().toString());
					cat.setPic(entry.getValue().toString().contains("/") ? entry
							.getValue().toString().replace("/", "")
							: entry.getValue().toString());
					categories.add(cat);
				}
			}
		}
		Collections.sort(categories);
		return categories;
	}

	public static String getType(String code)
	{
		if(code == null)
		{
			return "";
		}
		if(StringUtil.isEqual(code.substring(2, 6), "0000"))
		{
			return level1Map.get(code.substring(0, 2));
		}
		else if(StringUtil.isEqual(code.substring(4, 6), "00"))
		{
			return level2Map.get(code.substring(0, 4));
		}
		else
		{
			return level3Map.get(code);
		}
	}

	public static List<CuttingToolEntity> getCtsByType(List<CuttingToolEntity> cts, String code)
	{
		List<CuttingToolEntity> ret = new ArrayList<CuttingToolEntity>();
		for(int i = 0;i<cts.size();i++)
		{
			if(StringUtil.isEqual(cts.get(i).getCode().substring(0, 2),code))
			{
				ret.add(cts.get(i));
			}
		}
		return ret;
	}
	
	public static void sortProductCode()
	{
		try
		{
			Workbook book = Workbook.getWorkbook(new File("D:/data/type.xls"));
			// 获得第一个工作表对象
			Sheet sheet = book.getSheet(0);
			for (int j = 1; j < sheet.getRows(); j++)
			{
				Cell cell1 = sheet.getCell(1, j);
				Cell cell2 = sheet.getCell(0, j);
				System.out.println("codeMap.put(\"" + cell1.getContents()
						+ "\",\"" + cell2.getContents() + "\");");
			}
			book.close();
		} catch (Exception e)
		{
			System.out.println(e);
		}
	}
}
