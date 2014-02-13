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
import java.util.UUID;

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
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.WebUtils;

import com.banzhuan.common.Account;
import com.banzhuan.common.Constant;
import com.banzhuan.common.Result;
import com.banzhuan.entity.AgentEntity;
import com.banzhuan.entity.BuyerEntity;
import com.banzhuan.entity.ProfessionalAnswerEntity;
import com.banzhuan.entity.SampleEntity;
import com.banzhuan.form.CommentForm;
import com.banzhuan.form.GoodcaseForm;
import com.banzhuan.form.LoginForm;
import com.banzhuan.form.ProfessionalAnswerForm;
import com.banzhuan.form.QuestionForm;
import com.banzhuan.service.AgentService;
import com.banzhuan.service.BuyerService;
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
	@Autowired
	@Qualifier("buyerService")
	private BuyerService buyerService;
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

	@RequestMapping(value="/reg")
	public ModelAndView reg(final HttpServletResponse response)
	{
		ModelAndView mv = new ModelAndView("/common/reg");
		
		return mv;
	}
	
	@RequestMapping(value="/forgetpwd")
	public ModelAndView forgetpwd(final HttpServletRequest request, final HttpServletResponse response, @ModelAttribute("form")LoginForm form, BindingResult result)
	{
		ModelAndView mv = new ModelAndView("/common/forgetpwd");
		if(isDoSubmit(request))
		{
			String rec[] ={form.getMail()};
			String pwd = UUID.randomUUID().toString().substring(0,6);
			String date = StringUtil.getCurrentDate();
			String content = "亲爱的"+form.getMail()+":\n\n欢迎使用刀师傅的找回密码功能，您的新密码是"+pwd+"，请使用该密码登录刀师傅后，在账户设置中修改此密码\n\n请勿回复此邮件\n\n此致\n\n刀师傅敬上\n"+date;
			commonService.changpwd(form.getMail(), pwd, result);
			if(!result.hasErrors())
			{
				Util.sendEmail("noreply@daoshifu.com","cisco123",rec,"找回密码", content, null, "", "UTF-8");
			}
			else
			{
				return mv;
			}
			return new ModelAndView(new RedirectView("/log"));
		}
		return mv;
	}
	
	@RequestMapping(value="/log")
	public ModelAndView log(final HttpServletRequest request,
			final HttpServletResponse response, @ModelAttribute("form")LoginForm form, BindingResult result) throws IOException 
	{
		ModelAndView mv = new ModelAndView("/common/log");
		if(isDoSubmit(request))
		{
			Result re = commonService.checkLogin(form, result);
			
			if(!result.hasErrors())
			{
				int userType = Integer.parseInt(re.get("userType").toString());
				Account account = new Account();
				if(userType == 1)
				{
					BuyerEntity user = (BuyerEntity)re.get("user");
					int unreadMsgCount = buyerService.getUnreadMsgCount(user.getId());
					int questionCnt = buyerService.getUserQuestionCount(user.getId());
					account.setUserId(user.getId());
					account.setLogin(true); // 登录成功标识
					account.setUserName(user.getUsername()); // 用户登录名
					account.setCompanyName(user.getCompanyName());
					account.setUserId(user.getId()); // 用户ID
					account.setMail(user.getEmail()); // 邮箱
					account.setLogo(user.getLogo()); // 邮箱
					account.setBuyer(true);
					account.setUnreadMsgCount(unreadMsgCount);
					account.setQuestionCnt(questionCnt);
					//set cookie
					if(form.getRememberme() != null && form.getRememberme())
					{
						this.addCookie(response, Constant.LOGIN_MAIL, user.getEmail(), Integer.MAX_VALUE);
						this.addCookie(response, Constant.REMEMBER_ME, "1", Integer.MAX_VALUE);
					}
					//设置头像
					account.setLogo(user.getLogo());
					request.getSession().setAttribute("account", account);
					// 登陆成功， 跳转到登陆页面
					return new ModelAndView(new RedirectView("/buyer/main"));
				}
				else
				{
					AgentEntity user = (AgentEntity)re.get("user");
					int unreadMsgCount = agentService.getUnreadMsgCount(user.getId());
					int answerCnt = agentService.getAnswerCount(user.getId());
					int sampleCnt = agentService.getSampleCount(user.getId());
					int gcCnt = agentService.getGoodcaseCount(user.getId());
					account.setLogin(true); // 登录成功标识
					account.setUserName(user.getCompanyName()); // 用户登录名
					account.setCompanyName(user.getCompanyName());
					account.setUserId(user.getId()); // 用户ID
					account.setMail(user.getMail()); // 邮箱
					account.setLogo(user.getLogo()); // logo
					account.setBrandName(user.getBrandName());
					account.setBuyer(false);
					account.setAgent(true);
					account.setVerified(user.isVerified());
					account.setVerifiedLink(user.getVerifiedLink());
					account.setUnreadMsgCount(unreadMsgCount);
					account.setSampleCnt(sampleCnt);
					account.setGcCnt(gcCnt);
					account.setQuestionCnt(answerCnt);
					//set cookie
					if(form.getRememberme() != null && form.getRememberme())
					{
						this.addCookie(response, Constant.LOGIN_MAIL, user.getMail(), Integer.MAX_VALUE);
						this.addCookie(response, Constant.REMEMBER_ME, "1", Integer.MAX_VALUE);
					}
					//设置头像
					account.setLogo(user.getLogo());
					request.getSession().setAttribute("account", account);
					// 登陆成功， 跳转到登陆页面
					return new ModelAndView(new RedirectView("/agent/main"));
				}
			}
		}
		return mv;
	}
	
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
		mv.addObject("answers", result.get("answers"));
		
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
	public void addcomment(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("form")CommentForm form, BindingResult result) 
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
				JsonUtil.sendComment(response, form.getContent(), form.getUserName(), form.getUserLogo(), form.getBrandName(), form.getVerifiedLink(), StringUtil.formatDate(aa),ret);
			}
		}
		return; 
	}
	
	@RequestMapping(value="/setread")
	public void setread(HttpServletRequest request, HttpServletResponse response) 
	{
		int msgid = Integer.parseInt(request.getParameter("msgid"));
		commonService.setMsgAsRead(msgid);
		Account account = (Account) WebUtils.getSessionAttribute(request, "account");
		if(account.isAgent())
		{
			
			int unreadMsgCount = agentService.getUnreadMsgCount(account.getUserId());
			account.setUnreadMsgCount(unreadMsgCount);
		}
		else
		{
			int unreadMsgCount = buyerService.getUnreadMsgCount(account.getUserId());
			account.setUnreadMsgCount(unreadMsgCount);
		}
		

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
	
	@RequestMapping(value = "users/{aid}")
	public ModelAndView buyer(final HttpServletRequest request,final HttpServletResponse response, @PathVariable String aid)
	{
		ModelAndView mv = new ModelAndView("/common/buyer");
		int userid = Integer.parseInt(aid);
		//add agent
		BuyerEntity buyer = buyerService.getBuyerEntity(userid);
		if(buyer == null)
		{
			return new ModelAndView(new RedirectView("/index")); 
		}
		mv.addObject("buyer", buyer);

		
		return mv;
		
	}
	
	@RequestMapping(value="/ask")
	public void ask(HttpServletRequest request, HttpServletResponse response) 
	{
		Account account = (Account) WebUtils.getSessionAttribute(request, "account");
		if(account == null)
		{
			JsonUtil.checkAskStatus(response, 1);
			return;
		}
		else
		{
			if(account.isLogin())
			{
				if(account.isBuyer())
				{
					JsonUtil.checkAskStatus(response, 2);
				}
				if(account.isAgent())
				{
					JsonUtil.checkAskStatus(response, 3);
				}
			}
		}
	}
}
