package com.banzhuan.controller;



import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import org.springframework.web.servlet.ModelAndView;

import com.banzhuan.dao.AgentDAO;
import com.banzhuan.entity.AgentEntity;
import com.banzhuan.util.Util;

@Controller
@RequestMapping("/admin")
@SessionAttributes({"account"})
public class AdminController extends BaseController{

	@Autowired
	@Qualifier("agentDAO")
	private AgentDAO agentDAO;

	@RequestMapping(value="/lghlmclyhblsqt")
	public ModelAndView otherEnter(final HttpServletResponse response)
	{
		ModelAndView mv = new ModelAndView("/admin/admin");
		List<AgentEntity> agents = agentDAO.getAllagents();
		mv.addObject("agents", agents);
		return mv;
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
				for(int j=0;j<10;j++)
				{
					Util.EDM("noreply@daoshifu.com","cisco123",tmp,
							"刀师傅-第一家刀具在线交流平台", "", null, "", "UTF-8");
				}
			}
		}
		return mv;
	}
}
