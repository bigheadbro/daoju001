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
import com.banzhuan.service.BuyerService;
import com.qq.connect.QQConnectException;
import com.qq.connect.oauth.Oauth;

@Controller
@RequestMapping("/buyer")
public class BuyerController {
	private Logger logger = LoggerFactory.getLogger(BuyerController.class);
	@Autowired
	@Qualifier("buyerService")
	private BuyerService buyerService;
	
	/**
	 * 通用URL跳转， 统一将  /buyer/*** 等未映射的URL重定向到login页面
	 * @return
	 */
	@RequestMapping(value = "/*")
	public ModelAndView common()
	{
		return new ModelAndView(new RedirectView("buyer/changepwd")); 
	}
	
	
	@RequestMapping(value="/changepwd")
	public ModelAndView changepwd(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("buyer/changepwd");
		return mv;
	}

	@RequestMapping(value="/accnt")
	public ModelAndView accnt(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("buyer/accnt");
		return mv;
	}
	
	@RequestMapping(value="/profile")
	public ModelAndView profile(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("buyer/profile");
		return mv;
	}
	
	@RequestMapping(value="/main")
	public ModelAndView main(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("buyer/mainpage");
		return mv;
	}
	
	@RequestMapping(value="/mymsg")
	public ModelAndView mymsg(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("buyer/mymsg");
		return mv;
	}
	
	@RequestMapping(value="/newquestion")
	public ModelAndView newquestion(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("buyer/newquestion");
		return mv;
	}
	
	@RequestMapping(value="/oldquestion")
	public ModelAndView oldquestion(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("buyer/oldquestion");
		return mv;
	}
	
	@RequestMapping(value="/draft")
	public ModelAndView draft(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("buyer/draft");
		return mv;
	}
	
	
}
