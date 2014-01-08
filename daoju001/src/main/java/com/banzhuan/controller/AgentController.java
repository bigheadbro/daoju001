package com.banzhuan.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.banzhuan.common.Account;
import com.banzhuan.common.Result;
import com.banzhuan.entity.AgentEntity;
import com.banzhuan.entity.BuyerEntity;
import com.banzhuan.form.RegForm;
import com.banzhuan.form.LoginForm;
import com.banzhuan.service.AgentService;
import com.qq.connect.QQConnectException;
import com.qq.connect.oauth.Oauth;

@Controller
@RequestMapping("/agent")
@SessionAttributes({"account"})
public class AgentController extends BaseController{
	private Logger logger = LoggerFactory.getLogger(AgentController.class);
	@Autowired
	@Qualifier("agentService")
	private AgentService agentService;
	
	/**
	 * 通用URL跳转， 统一将  /agent/*** 等未映射的URL重定向到login页面
	 * @return
	 */
	@RequestMapping(value = "/*")
	public ModelAndView common()
	{
		return new ModelAndView(new RedirectView("agent/mainpage")); 
	}
	
	@RequestMapping(value="/reg")
	public ModelAndView reg(final HttpServletRequest request,
			final HttpServletResponse response, @ModelAttribute("form")RegForm form, BindingResult result) 
	{
		if(isDoSubmit(request))
		{
			Result re = agentService.register(form, result);
			
			if(!result.hasErrors())
			{
				AgentEntity user = (AgentEntity)re.get("agent");
				Account account = new Account();
				account.setLogin(true); // 登录成功标识
				account.setUserName(user.getCompanyName()); // 用户登录名
				account.setUserId(user.getId()); // 用户ID
				account.setBuyer(false);
				account.setAgent(true);
				request.getSession().setAttribute("account", account);
				// 注册成功， 跳转到登陆页面
				return new ModelAndView(new RedirectView("/agent/log")); 
			}
			else
			{
				// 注册失败， 返回注册页面，并显示出错提示信息
				ModelAndView model = new ModelAndView("/agent/reg");
				return model;
			}
		}
		else
		{
			ModelAndView model = new ModelAndView("/agent/reg");
			return model;
		}
	}
	
	@RequestMapping(value="/log")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("form")LoginForm form, BindingResult result) {
		if(isDoSubmit(request))
		{
			Result re = agentService.checkLogin(form, result);
			
			if(!result.hasErrors())
			{
				AgentEntity user = (AgentEntity)re.get("agent");
				Account account = new Account();
				account.setLogin(true); // 登录成功标识
				account.setUserName(user.getCompanyName()); // 用户登录名
				account.setUserId(user.getId()); // 用户ID
				account.setMail(user.getMail()); // 邮箱
				account.setLogo(user.getLogo()); // 邮箱
				account.setBuyer(false);
				account.setAgent(true);
				//set cookie
				if(form.getRememberme() != null && form.getRememberme())
				{
					this.addCookie(response, "mail", user.getMail(), Integer.MAX_VALUE);
				}
				//设置头像
				account.setLogo(agentService.getAgentEntity(account.getUserId()).getLogo());
				request.getSession().setAttribute("account", account);
				// 登陆成功， 跳转到登陆页面
				return new ModelAndView(new RedirectView("/agent/main"));
			}
		}
		return new ModelAndView("/agent/log");
	}
	
	@RequestMapping(value = "/logoff")
	public ModelAndView logoff(final HttpServletRequest request,final HttpServletResponse response, Model model) {
		request.getSession().invalidate();
		model.asMap().remove("account");
		return new ModelAndView(new RedirectView("/common/index"));
		
	}
	
	/**
	 * @return
	 */
	@RequestMapping(value = "/qqlogin")
	public void qqLogin(final HttpServletRequest request,final HttpServletResponse response) {
	 	try
	 	{
			response.sendRedirect(new Oauth().getAuthorizeURL(request));
		} catch (QQConnectException e) {
			logger.error("QQConnectException:"+e.getMessage());
		} catch (IOException e) {
			logger.error("qqLoginException:"+e.getMessage());
		}
			
	}
	
	@RequestMapping(value="/changepwd")
	public ModelAndView changepwd(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("account")Account account, 
			@ModelAttribute("form")RegForm form, BindingResult result)
	{ 
		ModelAndView view = new ModelAndView("agent/changepwd");
		// 非表单提交，直接显示页面
		if(!isDoSubmit(request))
			return view;
		
		form.setUserid(account.getUserId());
		agentService.changePwd(form, result);
		return view;
	}

	@RequestMapping(value="/accnt")
	public ModelAndView accnt(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("agent/accnt");
		return mv;
	}
	
	@RequestMapping(value="/profile")
	public ModelAndView profile(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("agent/profile");
		return mv;
	}
	
	@RequestMapping(value="/main")
	public ModelAndView main(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("agent/mainpage");
		return mv;
	}
	
	@RequestMapping(value="/mymsg")
	public ModelAndView mymsg(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("agent/mymsg");
		return mv;
	}
	
	@RequestMapping(value="/newquestion")
	public ModelAndView newquestion(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("agent/newquestion");
		return mv;
	}
	
	@RequestMapping(value="/answer")
	public ModelAndView oldquestion(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("agent/answer");
		return mv;
	}
	
	@RequestMapping(value="/draft")
	public ModelAndView draft(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("agent/draft");
		return mv;
	}
	
	@RequestMapping(value="/goodcase")
	public ModelAndView goodcase(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("agent/goodcase");
		return mv;
	}
	
	@RequestMapping(value="/uploadgc")
	public ModelAndView uploadgc(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("agent/uploadgc");
		return mv;
	}
	
	@RequestMapping(value="/mysample")
	public ModelAndView mysample(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("agent/mysample");
		return mv;
	}
}
