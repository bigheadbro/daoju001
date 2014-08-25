package com.banzhuan.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;


import com.banzhuan.service.WeixinService;
import com.banzhuan.util.StringUtil;

@Controller
@RequestMapping("/")
@SessionAttributes({ "account" })
public class WeixinController {
	private Logger logger = LoggerFactory.getLogger(WeixinController.class);
	
	public WeixinController()
	{
		super();
	}
	
	@RequestMapping(value = "/weixin")
	@ResponseBody
	public void validate(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		logger.error("enter validate\n");
		if(StringUtil.isEqual(request.getMethod(), "GET"))
		{
			WeixinService.getInstance().doGet(request, response);
		}
		else if(StringUtil.isEqual(request.getMethod(), "POST"))
		{
			WeixinService.getInstance().doPost(request, response);
		}
	}
	
	@RequestMapping(value = "/serve")
	@ResponseBody
	public void serve(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		logger.error("enter validate\n");
		if(StringUtil.isEqual(request.getMethod(), "GET"))
		{
			WeixinService.getInstance2().doGet(request, response);
		}
		else if(StringUtil.isEqual(request.getMethod(), "POST"))
		{
			WeixinService.getInstance2().doPost(request, response);
		}
	}
	
}
