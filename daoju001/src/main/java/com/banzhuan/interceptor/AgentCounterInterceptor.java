package com.banzhuan.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.view.RedirectView;

import com.banzhuan.service.AgentService;


public class AgentCounterInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	@Qualifier("agentService")
	private AgentService agentService;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		ModelAndView modelAndView = new ModelAndView(new RedirectView("/index"));
		String url = request.getServletPath();
		try
		{
			int cntRead = Integer.parseInt(url.split("/")[url.split("/").length-1]);
			agentService.updateAgentReadCountById(cntRead);
		}
		catch(NumberFormatException e) { 
			throw new ModelAndViewDefiningException(modelAndView);
		}
		return true;
	}
}
