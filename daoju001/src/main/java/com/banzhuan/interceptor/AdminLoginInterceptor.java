package com.banzhuan.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.WebUtils;

import com.banzhuan.common.Account;
import com.banzhuan.service.UserService;


public class AdminLoginInterceptor extends HandlerInterceptorAdapter {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Object isadmin = WebUtils.getSessionAttribute(request, "isadmin");
		if (isadmin != null) {
			return true;
		}
		else {
			//JsonUtil.showAlert(response, "登录后才可以回复", "", "登录", "/log", "问题详情>>回复");
			ModelAndView modelAndView = new ModelAndView(new RedirectView("/admin/log"));
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
		}
	}
}
