package com.banzhuan.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
	static
	{
		level1Map.put("01", "机夹铣刀");
		level1Map.put("02", "整体铣刀");
		level1Map.put("03", "车削刀具");
		level1Map.put("04", "孔加工刀具");
		level1Map.put("05", "刀柄");
		level1Map.put("06", "螺纹刀具");
		level1Map.put("99", "其他");

		level2Map.put("0101", "面铣刀盘");
		level2Map.put("0102", "铣刀杆");
		level2Map.put("0103", "螺纹接头");
		level2Map.put("0104", "合金接杆");
		level2Map.put("0105", "玉米铣刀");
		level2Map.put("0106", "三面刃铣刀");
		level2Map.put("0107", "T型槽刀");
		level2Map.put("0108", "铣刀片");
		level2Map.put("0201", "平头铣刀");
		level2Map.put("0202", "球头铣刀");
		level2Map.put("0203", "圆角铣刀");
		level2Map.put("0204", "锥形铣刀");
		level2Map.put("0205", "V尖刀");
		level2Map.put("0206", "倒角刀");
		level2Map.put("0207", "T槽刀");
		level2Map.put("0301", "车刀杆");
		level2Map.put("0401", "整体合金钻");
		level2Map.put("0402", "焊接合金钻");
		level2Map.put("0403", "机夹式钻头");
		level2Map.put("0404", "机夹式钻头刀片");
		level2Map.put("0405", "铲钻");
		level2Map.put("0406", "铲钻刀片");
		level2Map.put("0407", "U钻");
		level2Map.put("0408", "U钻刀片");
		level2Map.put("0409", "铰刀");
		level2Map.put("0410", "镗刀");
		level2Map.put("0501", "热胀刀柄");
		level2Map.put("0502", "液压刀柄");
		level2Map.put("0503", "面铣刀柄");
		level2Map.put("0504", "丝锥刀柄");
		level2Map.put("0505", "ER刀柄");
		level2Map.put("0506", "筒夹");
		level2Map.put("0507", "超大加持力刀柄");
		level2Map.put("0601", "螺纹铣刀");
		level2Map.put("0602", "螺纹铣刀杆");
		level2Map.put("0603", "螺纹铣刀片");
		level2Map.put("0604", "螺纹车刀杆");
		level2Map.put("0605", "螺纹车刀片");
		level2Map.put("0606", "丝锥");
		level2Map.put("0607", "板牙");
		level2Map.put("9901", "对刀仪");
		level2Map.put("9902", "寻边器");
		level2Map.put("9903", "滚压头");

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
		level3Map.put("030104", "普通车刀片");
		level3Map.put("030105", "精磨车刀片");
		level3Map.put("030106", "切槽/切断刀片");
		level3Map.put("050701", "冷压刀柄");
		level3Map.put("050702", "蜗杆刀柄");
		level3Map.put("060601", "手用丝锥");
		level3Map.put("060602", "挤压丝锥");
		level3Map.put("060603", "先端丝锥");
		level3Map.put("060604", "螺旋丝锥");

		codeMap.put("1", "010100");
		codeMap.put("2", "010200");
		codeMap.put("3", "010300");
		codeMap.put("4", "010400");
		codeMap.put("5", "010500");
		codeMap.put("6", "010600");
		codeMap.put("7", "010700");
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
		codeMap.put("19", "020500");
		codeMap.put("20", "020600");
		codeMap.put("21", "020700");
		codeMap.put("22", "030101");
		codeMap.put("23", "030102");
		codeMap.put("24", "030103");
		codeMap.put("25", "030104");
		codeMap.put("26", "030105");
		codeMap.put("27", "030106");
		codeMap.put("28", "040100");
		codeMap.put("29", "040200");
		codeMap.put("30", "040300");
		codeMap.put("31", "040400");
		codeMap.put("32", "040500");
		codeMap.put("33", "040600");
		codeMap.put("34", "040700");
		codeMap.put("35", "040800");
		codeMap.put("36", "040900");
		codeMap.put("37", "041000");
		codeMap.put("38", "050100");
		codeMap.put("39", "050200");
		codeMap.put("40", "050300");
		codeMap.put("41", "050400");
		codeMap.put("42", "050500");
		codeMap.put("43", "050600");
		codeMap.put("44", "050701");
		codeMap.put("45", "050702");
		codeMap.put("46", "060100");
		codeMap.put("47", "060200");
		codeMap.put("48", "060300");
		codeMap.put("49", "060400");
		codeMap.put("50", "060500");
		codeMap.put("51", "060601");
		codeMap.put("52", "060602");
		codeMap.put("53", "060603");
		codeMap.put("54", "060604");
		codeMap.put("56", "060700");
		codeMap.put("57", "990100");
		codeMap.put("58", "990200");
		codeMap.put("59", "990300");

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

	public static String getParamsHtml(List<CuttingToolEntity> cts)
	{
		String ret = "";
		for (int i = 0; i < cts.size(); i++)
		{
			if (StringUtil.isNotEmpty(cts.get(i).getVersion()) && !ret.contains("<span>型号</span>"))
			{
				ret += "<span>型号</span>";
			}
			if (StringUtil.isNotEmpty(cts.get(i).getMaterial())  && !ret.contains("<span>材质</span>"))
			{
				ret += "<span>材质</span>";
			}
			if (cts.get(i).getAngle() != 0 && !ret.contains("<span>主偏角</span>"))
			{
				ret += "<span>主偏角</span>";
			}
			if (cts.get(i).getCtcount() != 0 && !ret.contains("<span>刀片个数</span>"))
			{
				ret += "<span>刀片个数</span>";
			}
			if (cts.get(i).getDiameter() != 0 && !ret.contains("<span>直径</span>"))
			{
				ret += "<span>直径</span>";
			}
			if (StringUtil.isNotEmpty(cts.get(i).getUsage())  && !ret.contains("<span>加工用途</span>"))
			{
				ret += "<span>加工用途</span>";
			}
			if (cts.get(i).getCujing() != 0 && !ret.contains("<span>光洁度</span>"))
			{
				ret += "<span>光洁度</span>";
			}
			if (cts.get(i).getUsefullength() != 0 && !ret.contains("<span>有效长</span>"))
			{
				ret += "<span>有效长</span>";
			}
			if (cts.get(i).getPipesize() != 0 && !ret.contains("<span>安装孔接口</span>"))
			{
				ret += "<span>安装孔接口</span>";
			}
			if (StringUtil.isNotEmpty(cts.get(i).getShank())  && !ret.contains("<span>柄径</span>"))
			{
				ret += "<span>柄径</span>";
			}
			if (StringUtil.isNotEmpty(cts.get(i).getShanktype())  && !ret.contains("<span>柄部类型</span>"))
			{
				ret += "<span>柄部类型</span>";
			}
			if (StringUtil.isNotEmpty(cts.get(i).getShape())  && !ret.contains("<span>形状</span>"))
			{
				ret += "<span>形状</span>";
			}
			if (cts.get(i).getBackangle() != 0 && !ret.contains("<span>后角</span>"))
			{
				ret += "<span>后角</span>";
			}
			if (StringUtil.isNotEmpty(cts.get(i).getWorkingtool())  && !ret.contains("<span>适用工件</span>"))
			{
				ret += "<span>适用工件</span>";
			}
			if (cts.get(i).getEdgeno() != 0 && !ret.contains("<span>刃数</span>"))
			{
				ret += "<span>刃数</span>";
			}
			if (cts.get(i).getEdgelength() != 0 && !ret.contains("<span>刃长</span>"))
			{
				ret += "<span>刃长</span>";
			}
			if (cts.get(i).getTotallength() != 0 && !ret.contains("<span>总长</span>"))
			{
				ret += "<span>总长</span>";
			}
			if (cts.get(i).getScrewangle() != 0 && !ret.contains("<span>螺旋角</span>"))
			{
				ret += "<span>螺旋角</span>";
			}
			if (StringUtil.isNotEmpty(cts.get(i).getCoatingtype())  && !ret.contains("<span>涂层种类</span>"))
			{
				ret += "<span>涂层种类</span>";
			}
			if (cts.get(i).getRangle() != 0 && !ret.contains("<span>R角</span>"))
			{
				ret += "<span>R角</span>";
			}
			if (cts.get(i).getDirection() != 0 && !ret.contains("<span>方向</span>"))
			{
				ret += "<span>方向</span>";
			}
			if (cts.get(i).getMinworkdiameter() != 0 && !ret.contains("<span>最小加工直径</span>"))
			{
				ret += "<span>最小加工直径</span>";
			}
			if (cts.get(i).getInnercooling() != 0 && !ret.contains("<span>冷却方式</span>"))
			{
				ret += "<span>冷却方式</span>";
			}
			if (StringUtil.isNotEmpty(cts.get(i).getDiameterratio())  && !ret.contains("<span>倍径比</span>"))
			{
				ret += "<span>倍径比</span>";
			}
			if (StringUtil.isNotEmpty(cts.get(i).getSlotshape())  && !ret.contains("<span>槽型状</span>"))
			{
				ret += "<span>槽型状</span>";
			}
			if (StringUtil.isNotEmpty(cts.get(i).getHandlenorm())  && !ret.contains("<span>柄部规格</span>"))
			{
				ret += "<span>柄部规格</span>";
			}
			if (StringUtil.isNotEmpty(cts.get(i).getTaptype())  && !ret.contains("<span>丝锥种类</span>"))
			{
				ret += "<span>丝锥种类</span>";
			}
			if (StringUtil.isNotEmpty(cts.get(i).getScrewtype())  && !ret.contains("<span>螺纹种类</span>"))
			{
				ret += "<span>螺纹种类</span>";
			}
			if (StringUtil.isNotEmpty(cts.get(i).getAxistype())  && !ret.contains("<span>主轴种类</span>"))
			{
				ret += "<span>主轴种类</span>";
			}
			if (StringUtil.isNotEmpty(cts.get(i).getAxisdetail())  && !ret.contains("<span>主轴细分</span>"))
			{
				ret += "<span>主轴细分</span>";
			}
			if (cts.get(i).getThickness() != 0 && !ret.contains("<span>厚度</span>"))
			{
				ret += "<span>厚度</span>";
			}
			if (cts.get(i).getMaxslotdepth() != 0 && !ret.contains("<span>最大槽深</span>"))
			{
				ret += "<span>最大槽深</span>";
			}
			if (cts.get(i).getTaper() != 0 && !ret.contains("<span>锥度</span>"))
			{
				ret += "<span>锥度</span>";
			}
			if (cts.get(i).getSlotwidth() != 0 && !ret.contains("<span>槽宽</span>"))
			{
				ret += "<span>槽宽</span>";
			}
			if (cts.get(i).getPointdiameter() != 0 && !ret.contains("<span>刀尖直径</span>"))
			{
				ret += "<span>刀尖直径</span>";
			}
			if (StringUtil.isNotEmpty(cts.get(i).getHandledsize())  && !ret.contains("<span>可加持尺寸</span>"))
			{
				ret += "<span>可加持尺寸</span>";
			}
			if (StringUtil.isNotEmpty(cts.get(i).getScrewsize())  && !ret.contains("<span>螺纹尺寸</span>"))
			{
				ret += "<span>螺纹尺寸</span>";
			}
			if (StringUtil.isNotEmpty(cts.get(i).getScrewdistance())  && !ret.contains("<span>螺距</span>"))
			{
				ret += "<span>螺距</span>";
			}
			if (StringUtil.isNotEmpty(cts.get(i).getAccuracy())  && !ret.contains("<span>精度</span>"))
			{
				ret += "<span>精度</span>";
			}
			if (StringUtil.isNotEmpty(cts.get(i).getInterfacesize())  && !ret.contains("<span>接口尺寸</span>"))
			{
				ret += "<span>接口尺寸</span>";
			}
			if (cts.get(i).getMaxbore() != 0 && !ret.contains("<span>镗孔上限</span>"))
			{
				ret += "<span>镗孔上限</span>";
			}
			if (cts.get(i).getMinbore() != 0 && !ret.contains("<span>镗孔下限</span>"))
			{
				ret += "<span>镗孔下限</span>";
			}
			if (cts.get(i).getNecklength() != 0 && !ret.contains("<span>颈长</span>"))
			{
				ret += "<span>颈长</span>";
			}
		}
		return ret;
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
				ret += "<li><img src=\"" + pics[i] + "\" /></li>";
			}
			return ret;
		} else
		{
			return "<li><img src=\"" + pic + "\" /></li>";
		}
	}

	public static String getCategoryHtml(String code, boolean lastislink)
	{
		String ret = "";
		if (code.length() == 6)
		{
			if (Integer.valueOf(code.substring(0, 2)) != 0)
			{
				ret += "<a href=\"/category/" + code.substring(0, 2) + "\">"
						+ level1Map.get(code.substring(0, 2)) + "</a>";
			}
			if (Integer.valueOf(code.substring(2, 4)) != 0)
			{
				ret += "-><a href=\"/category/" + code.substring(0, 4) + "\">"
						+ level2Map.get(code.substring(0, 4)) + "</a>";
			}
			if (Integer.valueOf(code.substring(4, 6)) != 0)
			{
				if (lastislink)
					ret += "-><a href=\"/category/" + code.substring(0, 6)
							+ "\">" + level3Map.get(code.substring(0, 6))
							+ "</a>";
				else
					ret += "->" + level3Map.get(code.substring(0, 6));
			}
			return ret;
		} else if (code.length() == 4)
		{
			if (Integer.valueOf(code.substring(0, 2)) != 0)
			{
				ret += "<a href=\"/category/" + code.substring(0, 2) + "\">"
						+ level1Map.get(code.substring(0, 2)) + "</a>";
			}
			if (Integer.valueOf(code.substring(2, 4)) != 0)
			{
				if (lastislink)
					ret += "-><a href=\"/category/" + code.substring(0, 4)
							+ "\">" + level2Map.get(code.substring(0, 4))
							+ "</a>";
				else
					ret += "->" + level2Map.get(code.substring(0, 4));
			}
			return ret;
		} else
		{
			if (Integer.valueOf(code.substring(0, 2)) != 0)
			{
				if (lastislink)
					ret += "<a href=\"/category/" + code.substring(0, 2)
							+ "\">" + level1Map.get(code.substring(0, 2))
							+ "</a>";
				else
					ret += level1Map.get(code.substring(0, 2));
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
		} else
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
		return categories;
	}

	public static String getType(String code)
	{
		String ret = level1Map.get(code.substring(0, 2));
		if (StringUtil.isNotEmpty(level2Map.get(code.substring(0, 4))))
		{
			ret += " -> " + level2Map.get(code.substring(0, 4));
			if (StringUtil.isNotEmpty(level3Map.get(code.substring(0, 6))))
			{
				ret += " -> " + level3Map.get(code.substring(0, 6));
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
