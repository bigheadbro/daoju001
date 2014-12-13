package com.banzhuan.controller;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.banzhuan.common.Account;
import com.banzhuan.common.Constant;
import com.banzhuan.dao.AgentDAO;
import com.banzhuan.dao.ArticleDAO;
import com.banzhuan.dao.EventDAO;
import com.banzhuan.dao.CuttingToolDAO;
import com.banzhuan.dao.ItemDAO;
import com.banzhuan.dao.OrderDAO;
import com.banzhuan.dao.ProductDAO;
import com.banzhuan.dao.QuestionDAO;
import com.banzhuan.dao.QuickrequestDAO;
import com.banzhuan.dao.SampleDAO;
import com.banzhuan.dao.StatisticsDAO;
import com.banzhuan.dao.UserDAO;
import com.banzhuan.entity.AgentEntity;
import com.banzhuan.entity.ArticleEntity;
import com.banzhuan.entity.BrandEntity;
import com.banzhuan.entity.EventEntity;
import com.banzhuan.entity.CuttingToolEntity;
import com.banzhuan.entity.ItemEntity;
import com.banzhuan.entity.OrderEntity;
import com.banzhuan.entity.ProductEntity;
import com.banzhuan.entity.QuestionEntity;
import com.banzhuan.entity.QuickrequestEntity;
import com.banzhuan.entity.SampleEntity;
import com.banzhuan.entity.StatisticsEntity;
import com.banzhuan.entity.TempEntity;
import com.banzhuan.entity.UserEntity;
import com.banzhuan.form.ItemForm;
import com.banzhuan.form.RegForm;
import com.banzhuan.util.CuttingToolsConfiguration;
import com.banzhuan.util.StringUtil;
import com.banzhuan.util.Util;

@Controller
@RequestMapping("/admin")
@SessionAttributes({"isadmin"})
public class AdminController extends BaseController{

	@Autowired
	@Qualifier("userDAO")
	private UserDAO userDAO;
	
	@Autowired
	@Qualifier("sampleDAO")
	private SampleDAO sampleDAO;

	@Autowired
	@Qualifier("questionDAO")
	private QuestionDAO questionDAO;
	
	@Autowired
	@Qualifier("eventDAO")
	private EventDAO eventDAO;
	
	@Autowired
	@Qualifier("itemDAO")
	private ItemDAO itemDAO;
	
	@Autowired
	@Qualifier("productDAO")
	private ProductDAO productDAO;
	
	@Autowired
	@Qualifier("quickrequestDAO")
	private QuickrequestDAO quickrequestDAO;
	
	@Autowired
	@Qualifier("articleDAO")
	private ArticleDAO articleDAO;
	
	@Autowired
	@Qualifier("orderDAO")
	private OrderDAO orderDAO;
	
	@Autowired
	@Qualifier("ctDAO")
	private CuttingToolDAO ctDAO;

	@Autowired
	@Qualifier("stDAO")
	private StatisticsDAO stDAO;
	
	@RequestMapping(value="/updatebyxls")
	public ModelAndView updatebyxls(final HttpServletRequest request, final HttpServletResponse response)
	{
		ModelAndView mv = new ModelAndView("/admin/test");
		try {
            Workbook book = Workbook.getWorkbook(new File("C:/data/update.xls"));
            Sheet sheet = book.getSheet(2);      
            for(int j = 3; j < sheet.getRows(); j++)
            {
            	CuttingToolEntity ct = new CuttingToolEntity();
            	String tmp = sheet.getCell(0, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	tmp = sheet.getCell(0, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setBrand(tmp);
            	}
            	tmp = sheet.getCell(1, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setSeriesname(tmp);
            	}
            	tmp = sheet.getCell(2, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setWorkingtype(tmp);
            	}
            	ctDAO.updateCuttingToolBySn(ct);
            }
            book.close();
        } catch (Exception e) {
            System.out.println(e);
        }
		return mv;
	}
	
	@RequestMapping(value="/generateSameColume/{id}")
	public ModelAndView generateSameColume(final HttpServletRequest request, final HttpServletResponse response, @PathVariable String id)
	{
		ModelAndView mv = new ModelAndView("/admin/test");
		List<CuttingToolEntity> series = ctDAO.getAllSeries();
		for(int i = 0; i < series.size(); i++)
		{
			if(StringUtil.isNotEqual(series.get(i).getBrand(),id))
			{
				continue;
			}
			List<CuttingToolEntity> versions = ctDAO.getVersionsBySeries(series.get(i).getSeriesname()); 
			HashSet<String> brand= new HashSet<String>();
			HashSet<String> material= new HashSet<String>();
			HashSet<String> usage= new HashSet<String>();
			HashSet<String> shank= new HashSet<String>();
			HashSet<String> shanktype= new HashSet<String>();
			HashSet<String> shape= new HashSet<String>();
			HashSet<String> workingtool= new HashSet<String>();
			HashSet<String> coatingtype= new HashSet<String>();
			HashSet<String> diameterratio= new HashSet<String>();
			HashSet<String> slotshape= new HashSet<String>();
			HashSet<String> handlenorm= new HashSet<String>();
			HashSet<String> taptype= new HashSet<String>();
			HashSet<String> screwtype= new HashSet<String>();
			HashSet<String> axistype= new HashSet<String>();
			HashSet<String> axisdetail= new HashSet<String>();
			HashSet<String> interfacesize= new HashSet<String>();
			HashSet<String> handledsize= new HashSet<String>();
			HashSet<String> screwsize= new HashSet<String>();
			HashSet<String> screwdistance= new HashSet<String>();
			HashSet<String> accuracy= new HashSet<String>();
			HashSet<String> collet= new HashSet<String>();
			
			HashSet<Integer> ctcount= new HashSet<Integer>();
			HashSet<Integer>angle= new HashSet<Integer>();
			HashSet<Integer>backangle= new HashSet<Integer>();
			HashSet<Integer>edgeno= new HashSet<Integer>();
			HashSet<Integer>cujing= new HashSet<Integer>();
			HashSet<Integer>direction= new HashSet<Integer>();
			HashSet<Integer>innercooling= new HashSet<Integer>();
			
			HashSet<Double> usefullength= new HashSet<Double>();
			HashSet<Double> pipesize= new HashSet<Double>();
			HashSet<String> diameter= new HashSet<String>();
			HashSet<String> edgelength= new HashSet<String>();
			HashSet<String> totallength= new HashSet<String>();
			HashSet<String> screwangle= new HashSet<String>();
			HashSet<String> rangle= new HashSet<String>();
			HashSet<Double> minworkdiameter= new HashSet<Double>();
			HashSet<Double> thickness= new HashSet<Double>();
			HashSet<Double> maxslotdepth= new HashSet<Double>();
			HashSet<Double> maxbore= new HashSet<Double>();
			HashSet<Double> minbore= new HashSet<Double>();
			HashSet<String> necklength= new HashSet<String>();
			HashSet<Double> taper= new HashSet<Double>();
			HashSet<Double> slotwidth= new HashSet<Double>();
			HashSet<Double> pointdiameter= new HashSet<Double>();
			HashSet<Double> width= new HashSet<Double>();
			HashSet<Double> height= new HashSet<Double>();
			HashSet<String> grooverange= new HashSet<String>();
			HashSet<String> drillrange= new HashSet<String>();
			String ret = "";
			
			for(int j = 0; j < versions.size(); j++)
			{
				brand.add(versions.get(j).getBrand());
				material.add(versions.get(j).getMaterial());
				usage.add(versions.get(j).getUsage());
				shank.add(versions.get(j).getShank());
				shanktype.add(versions.get(j).getShanktype());
				shape.add(versions.get(j).getShape());
				workingtool.add(versions.get(j).getWorkingtool());
				coatingtype.add(versions.get(j).getCoatingtype());
				diameterratio.add(versions.get(j).getDiameterratio());
				slotshape.add(versions.get(j).getSlotshape());
				handlenorm.add(versions.get(j).getHandlenorm());
				taptype.add(versions.get(j).getTaptype());
				screwtype.add(versions.get(j).getScrewtype());
				axistype.add(versions.get(j).getAxistype());
				axisdetail.add(versions.get(j).getAxisdetail());
				interfacesize.add(versions.get(j).getInterfacesize());
				handledsize.add(versions.get(j).getHandledsize());
				screwsize.add(versions.get(j).getScrewsize());
				screwdistance.add(versions.get(j).getScrewdistance());
				accuracy.add(versions.get(j).getAccuracy());
				collet.add(versions.get(j).getRelativecollet());
				ctcount.add(versions.get(j).getCtcount());
				angle.add(versions.get(j).getAngle());
				backangle.add(versions.get(j).getBackangle());
				edgeno.add(versions.get(j).getEdgeno());
				cujing.add(versions.get(j).getCujing());
				direction.add(versions.get(j).getDirection());
				innercooling.add(versions.get(j).getInnercooling());
				usefullength.add(versions.get(j).getUsefullength());
				pipesize.add(versions.get(j).getPipesize());
				diameter.add(versions.get(j).getDiameter());
				edgelength.add(versions.get(j).getEdgelength());
				totallength.add(versions.get(j).getTotallength());
				screwangle.add(versions.get(j).getScrewangle());
				rangle.add(versions.get(j).getRangle());
				minworkdiameter.add(versions.get(j).getMinworkdiameter());
				thickness.add(versions.get(j).getThickness());
				maxslotdepth.add(versions.get(j).getMaxslotdepth());
				maxbore.add(versions.get(j).getMaxbore());
				minbore.add(versions.get(j).getMinbore());
				necklength.add(versions.get(j).getNecklength());
				taper.add(versions.get(j).getTaper());
				slotwidth.add(versions.get(j).getSlotwidth());
				pointdiameter.add(versions.get(j).getPointdiameter());
				width.add(versions.get(j).getWidth());
				height.add(versions.get(j).getHeight());
				grooverange.add(versions.get(j).getGrooverange());
				drillrange.add(versions.get(j).getDrillrange());
			}
			if(brand.size() == 1 && brand.toArray()[0] != null)
			{
				ret = "brand";
			}
			if(angle.size() == 1 && StringUtil.isNotEmpty(angle.toArray()[0].toString()))
			{
				ret += ",angle";
			}
			if(ctcount.size() == 1 && StringUtil.isNotEmpty(ctcount.toArray()[0].toString()))
			{
				ret += ",ctcount";
			}
			if(material.size() == 1 && material.toArray()[0] != null)
			{
				ret += ",material";
			}
			if(diameter.size() == 1 &&diameter.toArray()[0] != null)
			{
				ret += ",diameter";
			}
			if(usage.size() == 1 && usage.toArray()[0] != null)
			{
				ret += ",usage";
			}
			if(cujing.size() == 1 && StringUtil.isNotEmpty(cujing.toArray()[0].toString()))
			{
				ret += ",cujing";
			}
			if(usefullength.size() == 1 && StringUtil.isNotEmpty(usefullength.toArray()[0].toString()))
			{
				ret += ",usefullength";
			}
			if(pipesize.size() == 1 && StringUtil.isNotEmpty(pipesize.toArray()[0].toString()))
			{
				ret += ",pipesize";
			}
			if(shank.size() == 1 && shank.toArray()[0] != null)
			{
				ret += ",shank";
			}
			if(shanktype.size() == 1 && shanktype.toArray()[0] != null)
			{
				ret += ",shanktype";
			}
			if(shape.size() == 1 && shape.toArray()[0] != null)
			{
				ret += ",shape";
			}
			if(backangle.size() == 1 && StringUtil.isNotEmpty(backangle.toArray()[0].toString()))
			{
				ret += ",backangle";
			}
			if(workingtool.size() == 1 && workingtool.toArray()[0] != null)
			{
				ret += ",workingtool";
			}
			if(edgeno.size() == 1 && StringUtil.isNotEmpty(edgeno.toArray()[0].toString()))
			{
				ret += ",edgeno";
			}
			if(edgelength.size() == 1 && edgelength.toArray()[0] != null)
			{
				ret += ",edgelength";
			}
			if(totallength.size() == 1 && totallength.toArray()[0] != null)
			{
				ret += ",totallength";
			}
			if(screwangle.size() == 1 && screwangle.toArray()[0] != null)
			{
				ret += ",screwangle";
			}
			if(coatingtype.size() == 1 && coatingtype.toArray()[0] != null)
			{
				ret += ",coatingtype";
			}
			if(rangle.size() == 1 && rangle.toArray()[0] != null)
			{
				ret += ",rangle";
			}
			if(direction.size() == 1 && StringUtil.isNotEmpty(direction.toArray()[0].toString()))
			{
				ret += ",direction";
			}
			if(minworkdiameter.size() == 1 && StringUtil.isNotEmpty(minworkdiameter.toArray()[0].toString()))
			{
				ret += ",minworkdiameter";
			}
			if(innercooling.size() == 1 && StringUtil.isNotEmpty(innercooling.toArray()[0].toString()))
			{
				ret += ",innercooling";
			}
			if(diameterratio.size() == 1 && diameterratio.toArray()[0] != null)
			{
				ret += ",diameterratio";
			}
			if(slotshape.size() == 1 && slotshape.toArray()[0] != null)
			{
				ret += ",slotshape";
			}
			if(handlenorm.size() == 1 && handlenorm.toArray()[0] != null)
			{
				ret += ",handlenorm";
			}
			if(taptype.size() == 1 && taptype.toArray()[0] != null)
			{
				ret += ",taptype";
			}
			if(screwtype.size() == 1 && screwtype.toArray()[0] != null)
			{
				ret += ",screwtype";
			}
			if(axistype.size() == 1 && axistype.toArray()[0] != null)
			{
				ret += ",axistype";
			}
			if(axisdetail.size() == 1 && axisdetail.toArray()[0] != null)
			{
				ret += ",axisdetail";
			}
			if(thickness.size() == 1 && StringUtil.isNotEmpty(thickness.toArray()[0].toString()))
			{
				ret += ",thickness";
			}
			if(maxslotdepth.size() == 1 && StringUtil.isNotEmpty(maxslotdepth.toArray()[0].toString()))
			{
				ret += ",maxslotdepth";
			}
			if(taper.size() == 1 && StringUtil.isNotEmpty(taper.toArray()[0].toString()))
			{
				ret += ",taper";
			}
			if(slotwidth.size() == 1 && StringUtil.isNotEmpty(slotwidth.toArray()[0].toString()))
			{
				ret += ",slotwidth";
			}
			if(pointdiameter.size() == 1 && StringUtil.isNotEmpty(pointdiameter.toArray()[0].toString()))
			{
				ret += ",pointdiameter";
			}
			if(handledsize.size() == 1 && handledsize.toArray()[0] != null)
			{
				ret += ",handledsize";
			}
			if(screwsize.size() == 1 && screwsize.toArray()[0] != null)
			{
				ret += ",screwsize";
			}
			if(screwdistance.size() == 1 && screwdistance.toArray()[0] != null)
			{
				ret += ",screwdistance";
			}
			if(accuracy.size() == 1 && accuracy.toArray()[0] != null)
			{
				ret += ",accuracy";
			}
			if(collet.size() == 1 && collet.toArray()[0] != null)
			{
				ret += ",relativecollet";
			}
			if(interfacesize.size() == 1 && interfacesize.toArray()[0] != null)
			{
				ret += ",interfacesize";
			}
			if(maxbore.size() == 1 && StringUtil.isNotEmpty(maxbore.toArray()[0].toString()))
			{
				ret += ",maxbore";
			}
			if(minbore.size() == 1 && StringUtil.isNotEmpty(minbore.toArray()[0].toString()))
			{
				ret += ",minbore";
			}
			if(necklength.size() == 1 && necklength.toArray()[0] != null)
			{
				ret += ",necklength";
			}
			if(width.size() == 1 && StringUtil.isNotEmpty(width.toArray()[0].toString()))
			{
				ret += ",width";
			}
			if(height.size() == 1 && StringUtil.isNotEmpty(height.toArray()[0].toString()))
			{
				ret += ",height";
			}
			if(grooverange.size() == 1 && grooverange.toArray()[0] != null)
			{
				ret += ",grooverange";
			}
			if(drillrange.size() == 1 && drillrange.toArray()[0] != null)
			{
				ret += ",drillrange";
			}
			CuttingToolEntity update = new CuttingToolEntity();
			update.setSeriesname(series.get(i).getSeriesname());
			update.setSamecolume(ret);
			ctDAO.updateCuttingToolBySn(update);
		}
		return mv;
	}
	@RequestMapping(value="/test")
	public ModelAndView test(final HttpServletRequest request, final HttpServletResponse response)
	{
		ModelAndView mv = new ModelAndView("/admin/test");
		try {
            Workbook book = Workbook.getWorkbook(new File("D:/data/z.xls"));
            Sheet sheet2= book.getSheet(1);
            Map<String, TempEntity> seriesMap = new HashMap<String, TempEntity>();
            for(int j = 2; j < sheet2.getRows(); j++)
            {
            	String sn = sheet2.getCell(1, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isEmpty(sn))
            	{
            		break;
            	}
            	TempEntity tmp = new TempEntity();
            	tmp.outline = (sheet2.getCell(2, j)!=null)?sheet2.getCell(2, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")"):""; 
            	tmp.info = (sheet2.getCell(3, j)!=null)?sheet2.getCell(3, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")"):""; 
            	//tmp.suitcase = (sheet2.getCell(4, j)==null)?sheet2.getCell(4, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")"):""; 
            	//tmp.cover = "/img/series/" + sn + ".png";
            	File root = new File("D:/workspace/daoju001/daoju001/src/main/webapp/img/series");
                File[] files = root.listFiles();
				for (File file : files) {
					if (StringUtil.isEqual(file.getName().split("[.]")[0],sn)) {
						tmp.cover = "" + file.getName();
						break;
					}
					else
					{
						tmp.cover ="";
					}

				}         
            	root = new File("D:/workspace/daoju001/daoju001/src/main/webapp/img/sample");
                files = root.listFiles();
				for (File file : files) {
					if (StringUtil.isEqual(file.getName().split("[.]")[0].split("-")[0],sn)) {
						if (file.getName().contains("-1")) 
						{
							tmp.pic = "";
						}
						else if (file.getName().contains("-2")) 
						{
							tmp.pic = "" + sn + "-1.jpg|"
									+ "" + sn + "-2.jpg";
							;
						}
						else if (file.getName().contains("-3")) 
						{
							tmp.pic = "" + sn + "-1.jpg|"
									+ "" + sn + "-2.jpg|"
									+ "" + sn + "-3.jpg";
						}
						else if (file.getName().contains("-4")) 
						{
							tmp.pic = "" + sn + "-1.jpg|"
									+ "" + sn + "-2.jpg|"
									+ "" + sn + "-3.jpg|"
									+ "" + sn + "-4.jpg";
						}
						else if (file.getName().contains("-5")) 
						{
							tmp.pic = "" + sn + "-1.jpg|"
									+ "" + sn + "-2.jpg|"
									+ "" + sn + "-3.jpg|"
									+ "" + sn + "-4.jpg|"
									+ "" + sn + "-5.jpg";
						}
						else if (file.getName().contains("-6")) 
						{
							tmp.pic = "" + sn + "-1.jpg|"
									+ "" + sn + "-2.jpg|"
									+ "" + sn + "-3.jpg|"
									+ "" + sn + "-4.jpg|"
									+ "" + sn + "-5.jpg|"
									+ "" + sn + "-6.jpg";
						}
						else if (file.getName().contains("-7")) 
						{
							tmp.pic = "" + sn + "-1.jpg|"
									+ "" + sn + "-2.jpg|"
									+ "" + sn + "-3.jpg|"
									+ "" + sn + "-4.jpg|"
									+ "" + sn + "-5.jpg|"
									+ "" + sn + "-6.jpg|"
									+ "" + sn + "-7.jpg";
						}
						else if (file.getName().contains("-8")) 
						{
							tmp.pic = "" + sn + "-1.jpg|"
									+ "" + sn + "-2.jpg|"
									+ "" + sn + "-3.jpg|"
									+ "" + sn + "-4.jpg|"
									+ "" + sn + "-5.jpg|"
									+ "" + sn + "-6.jpg|"
									+ "" + sn + "-7.jpg|"
									+ "" + sn + "-8.jpg";
						}
						else
						{
							tmp.pic = "" + file.getName();
						}
					}
				}         
            	seriesMap.put(sn, tmp);
            }
            
           
            if(book.getNumberOfSheets()==3)
            {
            	 Sheet sheet3= book.getSheet(2);
	            for(int j = 1; j < sheet3.getRows(); j++)
	            {
	            	String sn = sheet3.getCell(0, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
	            	Iterator it = seriesMap.entrySet().iterator();  
            	    while (it.hasNext()) {  
            	  
            	        Map.Entry entry = (Map.Entry) it.next();  
            	  
            	        String key = entry.getKey().toString();
            	        if(StringUtil.isEqual(key, sn))
            	        {
            	        	seriesMap.get(sn).videolink = sheet3.getCell(1, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	        	seriesMap.get(sn).videoinfo = sheet3.getCell(2, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	        }
            	    }
	            }
            }
            // 获得第一个工作表对象
            Sheet sheet= book.getSheet(0);
            
            for(int j = 1; j < sheet.getRows(); j++)
            {
            	CuttingToolEntity ct = new CuttingToolEntity();
            	String tmp = sheet.getCell(0, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	tmp = sheet.getCell(0, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setCode(CuttingToolsConfiguration.getCodeValue(tmp));
            	}
            	tmp = sheet.getCell(1, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setBrand(tmp);
            	}
            	tmp = sheet.getCell(2, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setSeriesname(tmp);
            	}
            	tmp = sheet.getCell(3, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setVersion(tmp);
            	}
            	tmp = sheet.getCell(4, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setMaterial(tmp);
            	}
            	tmp = sheet.getCell(5, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setAngle(Integer.valueOf(tmp));
            	}
            	tmp = sheet.getCell(6, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setCtcount(Integer.valueOf(tmp));
            	}
            	tmp = sheet.getCell(7, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setDiameter(tmp);
            	}
            	tmp = sheet.getCell(8, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setUsage(tmp.replace("；", ";"));
            	}
            	tmp = sheet.getCell(9, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setCujing(Integer.valueOf(tmp.replace("粗加工", "3").replace("粗", "3").replace("精", "2").replace("一般", "1")));
            	}
            	tmp = sheet.getCell(10, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setUsefullength(Double.valueOf(tmp));
            	}
            	tmp = sheet.getCell(11, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setPipesize(Double.valueOf(tmp));
            	}
            	tmp = sheet.getCell(12, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setShank(tmp);
            	}
            	tmp = sheet.getCell(13, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setShanktype(tmp);
            	}
            	tmp = sheet.getCell(14, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setShape(tmp);
            	}
            	
            	tmp = sheet.getCell(15, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setBackangle(Integer.valueOf(tmp));
            	}
            	tmp = sheet.getCell(16, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setWorkingtool(tmp);
            	}
            	tmp = sheet.getCell(17, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setEdgeno(Integer.valueOf(tmp));
            	}
            	tmp = sheet.getCell(18, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setEdgelength(tmp);
            	}
            	tmp = sheet.getCell(19, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setTotallength(tmp);
            	}
            	tmp = sheet.getCell(20, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setScrewangle(tmp);
            	}
            	tmp = sheet.getCell(21, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setCoatingtype(tmp);
            	}
            	tmp = sheet.getCell(22, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setRangle(tmp);
            	}
            	tmp = sheet.getCell(23, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setDirection(Integer.valueOf(tmp.replace("右手", "3").replace("左手", "2").replace("通用", "1")));
            	}
            	tmp = sheet.getCell(24, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setMinworkdiameter(Double.valueOf(tmp));
            	}
            	tmp = sheet.getCell(25, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setInnercooling(Integer.valueOf(tmp.replace("外冷", "3").replace("内冷", "2").replace("一般", "1")));
            	}
            	tmp = sheet.getCell(26, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setDiameterratio(tmp);
            	}
            	tmp = sheet.getCell(27, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setSlotshape(tmp);
            	}
            	tmp = sheet.getCell(28, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setHandlenorm(tmp);
            	}
            	tmp = sheet.getCell(29, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setTaptype(tmp);
            	}
            	tmp = sheet.getCell(30, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setScrewtype(tmp);
            	}
            	tmp = sheet.getCell(31, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setAxistype(tmp);
            	}
            	tmp = sheet.getCell(32, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setAxisdetail(tmp);
            	}
            	tmp = sheet.getCell(33, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setInterfacesize(tmp);
            	}
            	tmp = sheet.getCell(34, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setNecklength(tmp);
            	}
            	tmp = sheet.getCell(35, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setThickness(Double.parseDouble(tmp));
            	}
            	tmp = sheet.getCell(36, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setTaper(Double.parseDouble(tmp));
            	}
            	tmp = sheet.getCell(37, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setPointdiameter((Double.parseDouble(tmp)));
            	}
            	tmp = sheet.getCell(38, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setSlotwidth(Double.parseDouble(tmp));
            	}
            	tmp = sheet.getCell(39, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setMaxslotdepth(Double.parseDouble(tmp));
            	}
            	tmp = sheet.getCell(41, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setMaxbore(Double.parseDouble(tmp));
            	}
            	tmp = sheet.getCell(40, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setMinbore(Double.parseDouble(tmp));
            	}
            	tmp = sheet.getCell(42, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setScrewsize(tmp);
            	}
            	tmp = sheet.getCell(43, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setScrewdistance(tmp);
            	}
            	tmp = sheet.getCell(44, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setAccuracy(tmp);
            	}
            	tmp = sheet.getCell(45, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setHandledsize(tmp);
            	}
            	tmp = sheet.getCell(46, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setRelativecollet(tmp);
            	}
            	tmp = sheet.getCell(47, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setHeight(Double.parseDouble(tmp));
            	}
            	tmp = sheet.getCell(48, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setWidth(Double.parseDouble(tmp));
            	}
            	tmp = sheet.getCell(49, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setGrooverange(tmp);
            	}
            	tmp = sheet.getCell(50, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setDrillrange(tmp);
            	}
            	tmp = sheet.getCell(51, j).getContents().trim().replace("；", ";").replace("（", "(").replace("）", ")");
            	if(StringUtil.isNotEmpty(tmp))
            	{
            		ct.setScrewdirection(tmp);
            	}
            	ct.setIshot(1);
            	if(seriesMap.get(ct.getSeriesname()) != null)
            	{
	            	ct.setInfo(seriesMap.get(ct.getSeriesname()).info);
	            	ct.setOutline(seriesMap.get(ct.getSeriesname()).outline);
	            	ct.setCover(seriesMap.get(ct.getSeriesname()).cover);
	            	ct.setPic(seriesMap.get(ct.getSeriesname()).pic);
	            	ct.setVideolink(seriesMap.get(ct.getSeriesname()).videolink);
	            	ct.setVideoinfo(seriesMap.get(ct.getSeriesname()).videoinfo);
            	}
            	ctDAO.insertCuttingToolEntity(ct);
            }
            book.close();
        } catch (Exception e) {
            System.out.println(e);
        }
		return mv;
	}
	
	@RequestMapping(value="/updateprovider")
	public void updateprovider(HttpServletRequest request, HttpServletResponse response)
	{
		String brand = request.getParameter("brand");
		String sn = request.getParameter("seriesname");
		String provider = request.getParameter("provider");
		if(StringUtil.isNotEmpty(brand))
		{
			CuttingToolEntity ct = new CuttingToolEntity();
			ct.setBrand(brand);
			ct.setProvider(provider.replace("，",","));
			ctDAO.updateCuttingToolByBrand(ct);
			return;
		}
		if(StringUtil.isNotEmpty(sn))
		{
			CuttingToolEntity ct = new CuttingToolEntity();
			ct.setSeriesname(sn);
			ct.setProvider(provider.replace("，",","));
			ctDAO.updateCuttingToolBySn(ct);
			return;
		}
	}
	
	@RequestMapping(value="/statistics")
	public ModelAndView statistics(final HttpServletRequest request, final HttpServletResponse response)
	{
		ModelAndView mv = new ModelAndView("/admin/statistics");
		List<StatisticsEntity> contacts = stDAO.getStatistcisByType(2);
		List<StatisticsEntity> searches = stDAO.getStatistcisByType(3);
		
		mv.addObject("contacts",contacts);
		mv.addObject("searches",searches);
		return mv;
	}
	
	@RequestMapping(value="/cuttingtoolsgroupbyseries")
	public ModelAndView cuttingtoolsgroupbyseries(final HttpServletRequest request, final HttpServletResponse response)
	{
		ModelAndView mv = new ModelAndView("/admin/cuttingtools");
		List<CuttingToolEntity> cts = ctDAO.getAllSeries();
		mv.addObject("type",2);
		mv.addObject("cts",cts);
		return mv;
	}
	
	@RequestMapping(value="/cuttingtoolsgroupbybrand")
	public ModelAndView cuttingtoolsgroupbybrand(final HttpServletRequest request, final HttpServletResponse response)
	{
		ModelAndView mv = new ModelAndView("/admin/cuttingtools");
		List<CuttingToolEntity> cts = ctDAO.getCuttingtoolsGroupbyBrand();
		mv.addObject("type",1);
		mv.addObject("cts",cts);
		return mv;
	}
	
	@RequestMapping(value="/updateseries/{id}")
	public ModelAndView updateseries(final HttpServletRequest request, final HttpServletResponse response, String id)
	{
		ModelAndView mv = new ModelAndView("/admin/cuttingtools");
		int ctid = Integer.parseInt(id);
		CuttingToolEntity ct = ctDAO.queryCuttingToolById(ctid);
		ct.setProvider(request.getParameter("provider"));
		ctDAO.updateCuttingToolBySn(ct);
		return mv;
	}
	
	@RequestMapping(value="/allitems")
	public ModelAndView allitems(final HttpServletRequest request, final HttpServletResponse response)
	{
		ModelAndView mv = new ModelAndView("/admin/allitems");
		List<CuttingToolEntity> cts = ctDAO.getAllItems();
		mv.addObject("cts",cts);
		return mv;
	}
	
	@RequestMapping(value="/log")
	public ModelAndView log(final HttpServletRequest request, final HttpServletResponse response)
	{
		ModelAndView mv = new ModelAndView("/admin/log");
		if(isDoSubmit(request))
		{
			String pwd = request.getParameter("adminpwd");
			if(StringUtil.isEqual(pwd, "sglycjcqy"))
			{
				request.getSession().setAttribute("isadmin", true);
				return new ModelAndView(new RedirectView("/admin/main"));
			}
		}
		return mv;
	}
	
	@RequestMapping(value="/requests")
	public ModelAndView requests(final HttpServletRequest request, final HttpServletResponse response)
	{
		ModelAndView mv = new ModelAndView("/admin/requests");
		List<QuickrequestEntity> requests = quickrequestDAO.queryQuickrequests(0, new RowBounds());
		mv.addObject("requests",requests);
		return mv;
	}
	
	@RequestMapping(value="/delrequest/{id}")
	public void delrequest(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) throws IOException 
	{
		int rid = Integer.parseInt(id);
		quickrequestDAO.delQuickrequest(rid);
	}
	
	@RequestMapping(value="/main")
	public ModelAndView main(final HttpServletRequest request, final HttpServletResponse response)
	{
		ModelAndView mv = new ModelAndView("/admin/main");
		//user
		int userCount = userDAO.getUsersCount(false);
		int userCountToday = userDAO.getUsersCount(true);
		mv.addObject("usercount",userCount);
		mv.addObject("userCountToday",userCountToday);
		
		//question
		int quescount = questionDAO.getQuestionCount(false);
		int quescounttoday = questionDAO.getQuestionCount(true);
		mv.addObject("quescount",quescount);
		mv.addObject("quescounttoday",quescounttoday);
		
		//products
		int productcount = productDAO.getProductCount(false);
		int productcounttoday = productDAO.getProductCount(true);
		mv.addObject("productcount",productcount);
		mv.addObject("productcounttoday",productcounttoday);
		
		//items
		int itemcount = itemDAO.getItemCountByType(null);
		mv.addObject("itemcount",itemcount);
		
		List<StatisticsEntity> joinuses = stDAO.getStatistcisByType(4);
		mv.addObject("joinus",joinuses.size());
		return mv;
	}
	
	@RequestMapping(value="/lghlmclyhblsqtitem")
	public ModelAndView item(final HttpServletRequest request, final HttpServletResponse response,@ModelAttribute("form")ItemForm form)
	{
		ModelAndView mv = new ModelAndView("/admin/newitem");
		List<BrandEntity> brands = new ArrayList<BrandEntity>();
		for(int i = 1;i<=Constant.BRAND_CNT;i++)
		{
			BrandEntity brand = new BrandEntity();
			brand.setKey(i);
			brand.setName(StringUtil.getBrand(i));
			brand.setLink(StringUtil.getBrandLogo(i));
			brand.setCountry(StringUtil.getBrandCountry(i));
			brands.add(brand);
		}
		mv.addObject("brands", brands);
		
		if(isDoSubmit(request))
		{
			ItemEntity item = new ItemEntity();
			item.setProvider(form.getProvider());
			item.setBrand(form.getBrand());
			item.setType(form.getType());
			item.setDetailtype(form.getDetailtype());
			item.setWorkmaterial(form.getWorkmaterial());
			item.setMaterial(form.getMaterial());
			item.setVersion(form.getVersion());
			item.setPrice(form.getPrice());
			item.setPicture(form.getPicture());
			item.setCover(form.getCover());
			item.setFeature(form.getFeature());
			item.setDescription(form.getDescription());
			item.setLimitq(form.getLimitq());
			item.setBrand(form.getBrand());
			itemDAO.insertItemEntity(item);
			return mv;
		}
		return mv;
	}
	
	@RequestMapping(value="/lghlmclyhblsqtitem/{id}")
	public ModelAndView updateItem(final HttpServletRequest request, final HttpServletResponse response,@PathVariable String id, @ModelAttribute("form")ItemForm form)
	{
		ModelAndView mv = new ModelAndView("/admin/newitem");
		int itemid = Integer.parseInt(id);
		List<BrandEntity> brands = new ArrayList<BrandEntity>();
		for(int i = 1;i<=Constant.BRAND_CNT;i++)
		{
			BrandEntity brand = new BrandEntity();
			brand.setKey(i);
			brand.setName(StringUtil.getBrand(i));
			brand.setLink(StringUtil.getBrandLogo(i));
			brand.setCountry(StringUtil.getBrandCountry(i));
			brands.add(brand);
		}
		mv.addObject("brands", brands);
		
		if(isDoSubmit(request))
		{
			ItemEntity item = new ItemEntity();
			item.setId(itemid);
			item.setProvider(form.getProvider());
			item.setBrand(form.getBrand());
			item.setType(form.getType());
			item.setDetailtype(form.getDetailtype());
			item.setWorkmaterial(form.getWorkmaterial());
			item.setMaterial(form.getMaterial());
			item.setVersion(form.getVersion());
			item.setPrice(form.getPrice());
			item.setPicture(form.getPicture());
			item.setCover(form.getCover());
			item.setFeature(form.getFeature());
			item.setDescription(form.getDescription());
			item.setLimitq(form.getLimitq());
			item.setBrand(form.getBrand());
			itemDAO.updateItemById(item);
			return new ModelAndView(new RedirectView("/admin/lghlmclyhblsqtitems"));
		}
		else
		{
			//set form
			ItemEntity oldone = itemDAO.queryItemEntityById(itemid);
			form.setProvider(oldone.getProvider());
			form.setBrand(oldone.getBrand());
			form.setType(oldone.getType());
			form.setDetailtype(oldone.getDetailtype());
			form.setWorkmaterial(oldone.getWorkmaterial());
			form.setMaterial(oldone.getMaterial());
			form.setVersion(oldone.getVersion());
			form.setPrice(oldone.getPrice());
			form.setPicture(oldone.getPicture());
			form.setCover(oldone.getCover());
			form.setFeature(oldone.getFeature());
			form.setDescription(oldone.getDescription());
			form.setLimitq(oldone.getLimitq());
			form.setBrand(oldone.getBrand());
		}
		return mv;
	}
	
	@RequestMapping(value="/lghlmclyhblsqtitems")
	public ModelAndView items(final HttpServletRequest request, final HttpServletResponse response)
	{
		ModelAndView mv = new ModelAndView("/admin/items");
		List<ItemEntity> items = itemDAO.getAllItemsByType(null);
		mv.addObject("items", items);
		return mv;
	}
	
	@RequestMapping(value="/lghlmclyhblsqtorder")
	public ModelAndView orders(final HttpServletRequest request, final HttpServletResponse response)
	{
		ModelAndView mv = new ModelAndView("/admin/orders");
		List<OrderEntity> orders = orderDAO.getAllOrders(null);
		for(int i = 0; i< orders.size(); i++)
		{
			orders.get(i).setItem(itemDAO.queryItemEntityById(orders.get(i).getItemid()));
		}
		mv.addObject("orders", orders);
		return mv;
	}
	
	@RequestMapping(value="/updateorder")
	public ModelAndView updateorder(final HttpServletRequest request, final HttpServletResponse response)
	{
		String lognumber = request.getParameter("lognumber");
		int orderid = Integer.valueOf(request.getParameter("orderid"));
		OrderEntity order = orderDAO.queryOrderEntityById(orderid);
		order.setState(3);
		order.setLogNumber(lognumber);
		order.setGmtSell(StringUtil.getCurrentTime());
		orderDAO.updateOrder(order);
		return new ModelAndView(new RedirectView("/admin/lghlmclyhblsqtorder"));
	}
	
	@RequestMapping(value = "uploadfile_item", produces="text/plain;charset=UTF-8")  
	@ResponseBody
	public String uploadfile_item(HttpServletRequest request, HttpServletResponse response)
	{
		String responseStr="";  
		MultipartHttpServletRequest r = (MultipartHttpServletRequest) request;
		  
        MultipartFile f = r.getFile("productlink");    
        String type = f.getOriginalFilename().substring(f.getOriginalFilename().lastIndexOf(".")).toLowerCase();
        if(!StringUtil.isProperImageFile(type))
        {
        	return "";
        }
        String path = request.getSession().getServletContext().getRealPath("/item");
		String link = StringUtil.getTodayString() + "/" + f.getOriginalFilename();
		path += "/" + StringUtil.getTodayString() + "/";
		File file = new File(path + f.getOriginalFilename());
		file.getParentFile().mkdirs();  
		file.getParentFile().mkdirs();  

		try 
		{
			FileCopyUtils.copy(f.getBytes(), file);
			responseStr = link;

		} catch (IOException e) {

		}

		return responseStr;
	}
	
	
	@RequestMapping(value="/lghlmclyhblsqtarticle")
	public ModelAndView newarticle(final HttpServletRequest request, final HttpServletResponse response)
	{
		ModelAndView mv = new ModelAndView("/admin/newarticle");
		
		if(isDoSubmit(request))
		{
			ArticleEntity article = new ArticleEntity();
			article.setCover(request.getParameter("picture"));
			article.setLink(request.getParameter("link"));
			article.setTitle(request.getParameter("title"));
			article.setOutline(request.getParameter("outline"));
			articleDAO.insertArticleEntity(article);
			return mv;
		}
		return mv;
	}
	
	@RequestMapping(value = "uploadfile_article", produces="text/plain;charset=UTF-8")  
	@ResponseBody
	public String uploadfile_article(HttpServletRequest request, HttpServletResponse response)
	{
		String responseStr="";  
		MultipartHttpServletRequest r = (MultipartHttpServletRequest) request;
		  
        MultipartFile f = r.getFile("productlink");    
        String type = f.getOriginalFilename().substring(f.getOriginalFilename().lastIndexOf(".")).toLowerCase();
        if(!StringUtil.isProperImageFile(type))
        {
        	return "";
        }
        String path = request.getSession().getServletContext().getRealPath("/article");
		String link = StringUtil.getTodayString() + "/" + f.getOriginalFilename();
		path += "/" + StringUtil.getTodayString() + "/";
		File file = new File(path + f.getOriginalFilename());
		file.getParentFile().mkdirs();  
		file.getParentFile().mkdirs();  

		try 
		{
			FileCopyUtils.copy(f.getBytes(), file);
			responseStr = link;

		} catch (IOException e) {

		}

		return responseStr;
	}
	
	@RequestMapping(value="/lghlmclyhblsqtagent")
	public ModelAndView agent(final HttpServletResponse response)
	{
		ModelAndView mv = new ModelAndView("/admin/admin");
		List<UserEntity> agents = userDAO.getUsers();
		mv.addObject("agents", agents);
		return mv;
	}
	
	@RequestMapping(value="/updateuser/{id}")
	public void updateuser(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) throws IOException 
	{
		int userid = Integer.parseInt(id);
		String type = request.getParameter("type");
		UserEntity agent = userDAO.queryUserEntityById(userid);
		if(StringUtil.isEqual(type, "1"))
		{
			agent.setAuthority(4);
			agent.setProductlimit(10);
		}
		else if(StringUtil.isEqual(type, "2"))
		{
			agent.setAuthority(3);
			agent.setProductlimit(2);
		}
		else if(StringUtil.isEqual(type, "3"))
		{
			agent.setAuthority(1);
			agent.setProductlimit(2);
		}
		else if(StringUtil.isEqual(type, "4"))
		{
			agent.setAuthority(5);
			agent.setProductlimit(10);
		}
		userDAO.updateUserEntityById(agent);
	}
	
	@RequestMapping(value="/lghlmclyhblsqtproduct")
	public ModelAndView products(final HttpServletResponse response)
	{
		ModelAndView mv = new ModelAndView("/admin/products");
		List<ProductEntity> products = productDAO.getAllProducts();
		mv.addObject("products", products);
		return mv;
	}
	
	
	@RequestMapping(value="/passproduct/{id}")
	public void passproduct(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) throws IOException 
	{
		int pid = Integer.parseInt(id);
		ProductEntity product = new ProductEntity();
		product.setId(pid);
		product.setState(1);
		productDAO.updateProductById(product);
	}
	
	@RequestMapping(value="/failproduct/{id}")
	public void failproduct(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) throws IOException 
	{
		int pid = Integer.parseInt(id);
		ProductEntity product = new ProductEntity();
		product.setId(pid);
		product.setState(2);
		productDAO.updateProductById(product);
	}
	
	@RequestMapping(value="/cancel/{id}")
	public void cancelauth(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) throws IOException 
	{
		int sid = Integer.parseInt(id);
		UserEntity agent = new UserEntity();
		agent.setId(sid);
		agent.setVerified(false);
		userDAO.updateUserEntityById(agent);
	}
	
	@RequestMapping(value="/add/{id}")
	public void addauth(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) throws IOException 
	{
		int sid = Integer.parseInt(id);
		UserEntity agent = new UserEntity();
		agent.setId(sid);
		agent.setVerified(true);
		userDAO.updateUserEntityById(agent);
	}
	
	
	@RequestMapping(value="/lghlmclyhblsqtsample")
	public ModelAndView sample(final HttpServletResponse response)
	{
		ModelAndView mv = new ModelAndView("/admin/sample");
		List<SampleEntity> samples = sampleDAO.getAllsamples();
		mv.addObject("samples", samples);
		return mv;
	}
	
	@RequestMapping(value="/delsample/{id}")
	public void delsample(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) throws IOException 
	{
		int sid = Integer.parseInt(id);
		sampleDAO.delSample(sid);
	}
	
	
	@RequestMapping(value="/lghlmclyhblsqtquestion")
	public ModelAndView question(final HttpServletResponse response)
	{
		ModelAndView mv = new ModelAndView("/admin/questions");
		QuestionEntity question = new QuestionEntity();
		List<QuestionEntity> questions = questionDAO.getAllquestions(question);
		mv.addObject("questions", questions);
		return mv;
	}
	
	@RequestMapping(value="/lghlmclyhblsqtevent")
	public ModelAndView event(final HttpServletResponse response)
	{
		ModelAndView mv = new ModelAndView("/admin/events");
		List<EventEntity> events = eventDAO.getAllevents();
		mv.addObject("events", events);
		return mv;
	}
	
	@RequestMapping(value="/delquestion/{id}")
	public void delquestion(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) throws IOException 
	{
		int sid = Integer.parseInt(id);
		questionDAO.delQuestion(sid);
	}
	
	
	@RequestMapping(value="/brysjhhrhl")
	public ModelAndView edm(final HttpServletResponse response)
	{
		ModelAndView mv = new ModelAndView("/admin/admin");
		HashSet<String> set = Util.readFileByLines("EDM/cut35-mail.txt");
		String rec[] = new String[set.size()];
		set.toArray(rec);
		String tmp[] = new String[10];
		for(int i = 1;i<rec.length;i++)
		{
			tmp[i%10] = rec[i];
			if(i%10 == 0)
			{
				//Util.EDM("noreply@daoshifu.com","cisco123",tmp,"刀师傅-第一家刀具在线交流平台", "", null, "", "UTF-8");
			}
		}
		return mv;
	}
}
