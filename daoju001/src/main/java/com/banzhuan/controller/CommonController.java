package com.banzhuan.controller;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.banzhuan.common.Result;
import com.banzhuan.entity.AgentEntity;
import com.banzhuan.form.GoodcaseForm;
import com.banzhuan.form.LoginForm;
import com.banzhuan.service.AgentService;
import com.banzhuan.service.CommonService;

@Controller
@RequestMapping("/")
@SessionAttributes({"account"})
public class CommonController extends BaseController{
	@Autowired
	@Qualifier("commonService")
	private CommonService commonService;
	@Autowired
	@Qualifier("agentService")
	private AgentService agentService;
	/**
	 * 其他未识别的URL都统一到
	 * @return
	 */
	@RequestMapping(value="*")
	public ModelAndView otherEnter(final HttpServletResponse response,@ModelAttribute("form")LoginForm form)
	{
		ModelAndView mv = new ModelAndView("/common/index");
		return mv;
	}

	/**
	 * 其他未识别的URL都统一到
	 * @return
	 */
	@RequestMapping(value="/common/index")
	public ModelAndView index(final HttpServletResponse response,@ModelAttribute("form")LoginForm form)
	{
		ModelAndView mv = new ModelAndView("/common/index");
		return mv;
	}

	@RequestMapping(value = "/question")
	public ModelAndView allquestion(final HttpServletRequest request,final HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/common/question");
		return mv;
		
	}
	
	@RequestMapping(value = "goodcases")
	public ModelAndView allcase(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("form")GoodcaseForm form) {
		ModelAndView mv = new ModelAndView("/common/goodcases");
		
		Result result = commonService.getAllGoodcases(form);
		mv.addObject("goodcases", result.get("goodcases"));
		return mv;
		
	}
	
	@RequestMapping(value = "agents")
	public ModelAndView allagents(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("form")GoodcaseForm form)
	{
		ModelAndView mv = new ModelAndView("/common/agents");
		
		Map<Integer,List<AgentEntity>> agentMap = commonService.getAllAgents();
		mv.addObject("agentMap", agentMap);
		
		return mv;
		
	}
	
	@RequestMapping(value = "agent/{id}")
	public ModelAndView agent(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("aid") final Object aid)
	{
		ModelAndView mv = new ModelAndView("/common/agent");
		Result result = new Result();
		int userid = Integer.parseInt(aid.toString());
		//add agent
		AgentEntity agent = agentService.getAgentEntity(userid);
		mv.addObject("agent", agent);
		//add good cases
		result = agentService.queryGoodcasesByUserid(userid);
		mv.addObject("goodcases", result.get("goodcases"));
		//add good cases
		result = agentService.querySamplesByUserid(userid);
		mv.addObject("samples", result.get("samples"));
		
		return mv;
		
	}
}
