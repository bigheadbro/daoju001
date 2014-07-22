package com.banzhuan.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	private WeixinService weixinService;
	
	public WeixinController()
	{
		super();
		weixinService = new WeixinService();
	}
	
	@RequestMapping(value = "/weixin")
	@ResponseBody
	public void validate(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		logger.error("enter validate\n");
		if(StringUtil.isEqual(request.getMethod(), "GET"))
		{
			weixinService.doGet(request, response);
		}
		else if(StringUtil.isEqual(request.getMethod(), "POST"))
		{
			weixinService.doPost(request, response);
		}
	}
	
}
