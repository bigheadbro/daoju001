package com.banzhuan.interceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.WebUtils;

import com.banzhuan.common.Account;

public class AgentLoginInterceptor extends HandlerInterceptorAdapter {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		
		Account account = (Account) WebUtils.getSessionAttribute(request, "account");
		if (account == null || !account.isLogin() || !account.isAgent()) {
			ModelAndView modelAndView = new ModelAndView(new RedirectView("/log"));
			String host=request.getLocalAddr();
			int port = request.getLocalPort();
			String contextPath = request.getContextPath();
			String url = request.getServletPath();
			String query = request.getQueryString();
			if (query != null) {
				modelAndView.addObject("redirect", "http://"+host+":"+port+contextPath+url+"?"+query);
			}
			else {
				modelAndView.addObject("redirect", "http://"+host+":"+port+contextPath+url);
			}
			throw new ModelAndViewDefiningException(modelAndView);
			// 临时不走QQ CONNECT 
			//ModelAndView modelAndView = new ModelAndView(new RedirectView("mylogin"));
			//throw new ModelAndViewDefiningException(modelAndView);
		}
		else {
			return true;
		}
	}
}
