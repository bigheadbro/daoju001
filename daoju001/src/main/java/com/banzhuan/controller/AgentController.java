package com.banzhuan.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.banzhuan.common.Result;
import com.banzhuan.form.LoginForm;
import com.banzhuan.service.AgentService;
import com.qq.connect.QQConnectException;
import com.qq.connect.oauth.Oauth;

@Controller
@RequestMapping("/agent")
public class AgentController {
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
	
	
	@RequestMapping(value="/log")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("form")LoginForm form, BindingResult result1) {
		ModelAndView mv = new ModelAndView("/agent/login");
		Result result = agentService.test(form, result1);
		if(result1.hasErrors())
		{
			return mv;
		}

		return mv;
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
	public ModelAndView changepwd(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("agent/changepwd");
		return mv;
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
	
	@RequestMapping(value="/oldquestion")
	public ModelAndView oldquestion(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("agent/oldquestion");
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
}
