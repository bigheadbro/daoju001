package com.banzhuan.controller;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.banzhuan.common.Account;
import com.banzhuan.common.Constant;
import com.banzhuan.common.Result;
import com.banzhuan.entity.AgentEntity;
import com.banzhuan.entity.SampleEntity;
import com.banzhuan.form.CommentForm;
import com.banzhuan.form.GoodcaseForm;
import com.banzhuan.form.LoginForm;
import com.banzhuan.form.ProfessionalAnswerForm;
import com.banzhuan.form.QuestionForm;
import com.banzhuan.service.AgentService;
import com.banzhuan.service.CommonService;
import com.banzhuan.util.JsonUtil;
import com.banzhuan.util.StringUtil;
import com.banzhuan.util.Util;

@Controller
@RequestMapping("/")
@SessionAttributes({"account"})
public class CommonController extends BaseController{
	private Logger logger = LoggerFactory.getLogger(CommonController.class);
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
		
		int page = 1;
		if(request.getParameter("page") != null)
		{
			page = Integer.valueOf(request.getParameter("page"));
		}
		
		Result result = commonService.getAllquestions(form,new RowBounds((page-1)*Constant.ALL_QUESTION_PAGE_SIZE, Constant.ALL_QUESTION_PAGE_SIZE));
		mv.addObject("questions", result.get("questions"));
		int total= commonService.getAllquestionsCount(form);
		int totalPage=0;
		if(total % Constant.ALL_QUESTION_PAGE_SIZE == 0)
			totalPage=total/Constant.ALL_QUESTION_PAGE_SIZE;
		else
			totalPage=total/Constant.ALL_QUESTION_PAGE_SIZE+1;
		totalPage=totalPage==0?1:totalPage;
		mv.addObject("page", page);
		mv.addObject("total", total);
		mv.addObject("totalPage", totalPage);

		result = commonService.getMaingoodcases();
		mv.addObject("goodcases", result.get("goodcases"));
		return mv;
		
	}
	
	@RequestMapping(value = "questions/{qid}")
	public ModelAndView question(final HttpServletRequest request,final HttpServletResponse response, @PathVariable String qid,
			@ModelAttribute("answerForm")ProfessionalAnswerForm answerForm, @ModelAttribute("form")CommentForm form)
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
		
		result = commonService.getCommentsByPid(questionid);
		mv.addObject("commentsCnt", result.get("commentsCnt"));
		mv.addObject("comments", result.get("comments"));
		
		return mv;
		
	}
	
	@RequestMapping(value="/addcomment")
	public void addcomment(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("account")Account account
			, @ModelAttribute("form")CommentForm form, BindingResult result) 
	{
		if(isDoSubmit(request))
		{
			if(form.getContent() == "")
			{
				JsonUtil.showAlert(response, "回复内容不能为空", "", "确定", "", "");
				return;
			}
			int ret = commonService.insertComment(form);
			if(ret >= 0)
			{
				Date now = new Date();
				SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				String aa = time.format(now);
				JsonUtil.sendComment(response, form.getContent(), form.getUserName(), form.getUserLogo(), form.getBrandName(), form.getVerifiedLink(), StringUtil.formatDate(aa));
			}
		}
		return; 
	}
	
	@RequestMapping(value = "goodcases")
	public ModelAndView allcase(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("form")GoodcaseForm form) {
		ModelAndView mv = new ModelAndView("/common/goodcases");

		int page = 1;
		if(request.getParameter("page") != null)
		{
			page = Integer.valueOf(request.getParameter("page"));
		}
		
		Result result = commonService.getAllGoodcases(form,new RowBounds((page-1)*Constant.ALL_GOOD_CASE_PAGE_SIZE, Constant.ALL_GOOD_CASE_PAGE_SIZE));
		mv.addObject("goodcases", result.get("goodcases"));
		int total= commonService.getGoodcaseCountByType(form);
		int totalPage=0;
		if(total % Constant.ALL_GOOD_CASE_PAGE_SIZE == 0)
			totalPage=total/Constant.ALL_GOOD_CASE_PAGE_SIZE;
		else
			totalPage=total/Constant.ALL_GOOD_CASE_PAGE_SIZE+1;
		totalPage=totalPage==0?1:totalPage;
		mv.addObject("page", page);
		mv.addObject("total", total);
		mv.addObject("totalPage", totalPage);
		return mv;
		
	}
	
	@RequestMapping(value = "agents")
	public ModelAndView allagents(final HttpServletRequest request,final HttpServletResponse response)
	{
		ModelAndView mv = new ModelAndView("/common/agents");
		
		Map<Integer,List<AgentEntity>> agentMap = commonService.getAllAgents();

		//对key进行排序--字母
		List<Map.Entry<Integer,List<AgentEntity>>> infoIds = new ArrayList<Map.Entry<Integer,List<AgentEntity>>>(agentMap.entrySet());
		Collections.sort(infoIds, new Comparator<Map.Entry<Integer,List<AgentEntity>>>() {   
		    public int compare(Map.Entry<Integer,List<AgentEntity>> o1, Map.Entry<Integer,List<AgentEntity>> o2) {      
		        return (o1.getKey()-o2.getKey());
		    }
		}); 

		mv.addObject("agentMap", infoIds);
		
		return mv;
		
	}
	
	@RequestMapping(value = "samples")
	public ModelAndView allsamples(final HttpServletRequest request,final HttpServletResponse response)
	{
		ModelAndView mv = new ModelAndView("/common/samples");
		
		Map<Integer,Map<Integer,List<SampleEntity>>> sampleMap = commonService.getAllSamples();

		//对key进行排序--字母
		List<Map.Entry<Integer,Map<Integer,List<SampleEntity>>>> infoIds = new ArrayList<Map.Entry<Integer,Map<Integer,List<SampleEntity>>>>(sampleMap.entrySet());
		Collections.sort(infoIds, new Comparator<Map.Entry<Integer,Map<Integer,List<SampleEntity>>>>() {   
		    public int compare(Map.Entry<Integer,Map<Integer,List<SampleEntity>>> o1, Map.Entry<Integer,Map<Integer,List<SampleEntity>>> o2) {      
		        return (o1.getKey()-o2.getKey());
		    }
		}); 
		
		mv.addObject("sampleMap", infoIds);
		
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
		result = agentService.querySamplesByUserid(userid, new RowBounds(0, Integer.MAX_VALUE));
		mv.addObject("samples", result.get("samples"));
		
		return mv;
		
	}
}
