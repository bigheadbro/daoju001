package com.banzhuan.controller;



import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import org.springframework.web.servlet.ModelAndView;

import com.banzhuan.common.Account;
import com.banzhuan.dao.AgentDAO;
import com.banzhuan.dao.EventDAO;
import com.banzhuan.dao.QuestionDAO;
import com.banzhuan.dao.SampleDAO;
import com.banzhuan.entity.AgentEntity;
import com.banzhuan.entity.EventEntity;
import com.banzhuan.entity.QuestionEntity;
import com.banzhuan.entity.SampleEntity;
import com.banzhuan.util.Util;

@Controller
@RequestMapping("/admin")
@SessionAttributes({"account"})
public class AdminController extends BaseController{

	@Autowired
	@Qualifier("agentDAO")
	private AgentDAO agentDAO;
	
	@Autowired
	@Qualifier("sampleDAO")
	private SampleDAO sampleDAO;

	@Autowired
	@Qualifier("questionDAO")
	private QuestionDAO questionDAO;
	
	@Autowired
	@Qualifier("eventDAO")
	private EventDAO eventDAO;
	
	@RequestMapping(value="/lghlmclyhblsqtagent")
	public ModelAndView agent(final HttpServletResponse response)
	{
		ModelAndView mv = new ModelAndView("/admin/admin");
		List<AgentEntity> agents = agentDAO.getAllagents(0);
		mv.addObject("agents", agents);
		return mv;
	}
	
	@RequestMapping(value="/cancel/{id}")
	public void cancelauth(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) throws IOException 
	{
		int sid = Integer.parseInt(id);
		AgentEntity agent = new AgentEntity();
		agent.setId(sid);
		agent.setVerified(false);
		agentDAO.updateAgentEntityById(agent);
	}
	
	@RequestMapping(value="/add/{id}")
	public void addauth(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) throws IOException 
	{
		int sid = Integer.parseInt(id);
		AgentEntity agent = new AgentEntity();
		agent.setId(sid);
		agent.setVerified(true);
		agentDAO.updateAgentEntityById(agent);
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
				Util.EDM("noreply@daoshifu.com","cisco123",tmp,"刀师傅-第一家刀具在线交流平台", "", null, "", "UTF-8");
			}
		}
		return mv;
	}
}
