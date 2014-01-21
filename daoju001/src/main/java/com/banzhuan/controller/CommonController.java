package com.banzhuan.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.servlet.view.RedirectView;

import com.banzhuan.common.Result;
import com.banzhuan.entity.AgentEntity;
import com.banzhuan.form.GoodcaseForm;
import com.banzhuan.form.LoginForm;
import com.banzhuan.form.ProfessionalAnswerForm;
import com.banzhuan.form.QuestionForm;
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
		ModelAndView mv = new ModelAndView("/common/index2");
		
		return mv;
	}

	/**
	 * 其他未识别的URL都统一到
	 * @return
	 */
	@RequestMapping(value="/index")
	public ModelAndView index(final HttpServletResponse response)
	{
		ModelAndView mv = new ModelAndView("/common/index");
		
		Result result = new Result();
		
		result = commonService.getMainagents();
		mv.addObject("agents", result.get("agents"));
		
		result = commonService.getMaingoodcases();
		mv.addObject("goodcases", result.get("goodcases"));
		
		result = commonService.getMainquestions();
		mv.addObject("questions", result.get("questions"));
		
		
		return mv;
	}

	@RequestMapping(value = "/questions")
	public ModelAndView allquestion(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("form")QuestionForm form, 
			@ModelAttribute("answerForm")ProfessionalAnswerForm answerForm) 
	{
		ModelAndView mv = new ModelAndView("/common/questions");
		
		Result result = commonService.getAllquestions(form);
		mv.addObject("questions", result.get("questions"));
		
		result = commonService.getMainagents();
		mv.addObject("agents", result.get("agents"));
		
		result = commonService.getMaingoodcases();
		mv.addObject("goodcases", result.get("goodcases"));
		return mv;
		
	}
	
	@RequestMapping(value = "questions/{qid}")
	public ModelAndView question(final HttpServletRequest request,final HttpServletResponse response, @PathVariable String qid,
			@ModelAttribute("answerForm")ProfessionalAnswerForm answerForm)
	{
		ModelAndView mv = new ModelAndView("/common/question");
		Result result = new Result();
		int questionid = Integer.parseInt(qid);
		result = agentService.queryQuestionById(questionid);
		if(result.get("question") == null)
		{
			return new ModelAndView(new RedirectView("/questions")); 
		}
		mv.addObject("question", result.get("question"));
		
		result = agentService.queryAnswersByQid(questionid);
		mv.addObject("answers", result.get("answers"));
		
		result = commonService.getMaingoodcases();
		mv.addObject("goodcases", result.get("goodcases"));
		
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
	
	@RequestMapping(value = "agents/{aid}")
	public ModelAndView agent(final HttpServletRequest request,final HttpServletResponse response, @PathVariable String aid)
	{
		ModelAndView mv = new ModelAndView("/common/agent");
		Result result = new Result();
		int userid = Integer.parseInt(aid);
		//add agent
		result = agentService.getAgentEntity(userid);
		if(result.get("agent") == null)
		{
			return new ModelAndView(new RedirectView("/agents")); 
		}
		mv.addObject("agent", result.get("agent"));
		//add good cases
		result = agentService.queryGoodcasesByUserid(userid);
		mv.addObject("goodcases", result.get("goodcases"));
		//add good cases
		result = agentService.querySamplesByUserid(userid);
		mv.addObject("samples", result.get("samples"));
		
		return mv;
		
	}
}
