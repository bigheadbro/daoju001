package com.banzhuan.controller;



import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.banzhuan.common.Account;
import com.banzhuan.common.Constant;
import com.banzhuan.dao.AgentDAO;
import com.banzhuan.dao.EventDAO;
import com.banzhuan.dao.ItemDAO;
import com.banzhuan.dao.QuestionDAO;
import com.banzhuan.dao.SampleDAO;
import com.banzhuan.dao.UserDAO;
import com.banzhuan.entity.AgentEntity;
import com.banzhuan.entity.BrandEntity;
import com.banzhuan.entity.EventEntity;
import com.banzhuan.entity.ItemEntity;
import com.banzhuan.entity.QuestionEntity;
import com.banzhuan.entity.SampleEntity;
import com.banzhuan.entity.UserEntity;
import com.banzhuan.util.StringUtil;
import com.banzhuan.util.Util;

@Controller
@RequestMapping("/admin")
@SessionAttributes({"account"})
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
	
	@RequestMapping(value="/lghlmclyhblsqtitem")
	public ModelAndView item(final HttpServletRequest request, final HttpServletResponse response)
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
			item.setBrand(request.getParameter("brand"));
			item.setType(request.getParameter("type"));
			item.setDetailtype(request.getParameter("detailtype"));
			item.setWorkmaterial(request.getParameter("workmaterial"));
			item.setMaterial(request.getParameter("material"));
			item.setVersion(request.getParameter("version"));
			item.setPrice(Double.parseDouble(request.getParameter("price")));
			item.setPicture(request.getParameter("picture"));
			item.setCover(request.getParameter("cover"));
			item.setDescription(request.getParameter("description"));
			item.setLimitq(Integer.parseInt(request.getParameter("limit")));
			itemDAO.insertItemEntity(item);
			return mv;
		}
		return mv;
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
	
	@RequestMapping(value="/lghlmclyhblsqtagent")
	public ModelAndView agent(final HttpServletResponse response)
	{
		ModelAndView mv = new ModelAndView("/admin/admin");
		List<UserEntity> agents = userDAO.getUsersByAuth(4);
		mv.addObject("agents", agents);
		return mv;
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
