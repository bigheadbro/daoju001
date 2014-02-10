package com.banzhuan.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.util.WebUtils;

import com.banzhuan.common.Account;
import com.banzhuan.common.Constant;
import com.banzhuan.util.StringUtil;;


public class BaseController {
	
	public String getPath(final HttpServletRequest request)
	{
		return "http://"+request.getLocalAddr()+":"+request.getLocalPort()+request.getContextPath();
	}
	
	/**
	 * 判断是否提交表单
	 * @param request
	 * @return
	 */
	public boolean isDoSubmit(HttpServletRequest request) {
		if (StringUtil.isNotEmpty(request.getParameter("do_submit"))
				&& StringUtil.isEqual(request.getParameter("do_submit"),
						"true")) {
			return true;
		}
		return false;
	}
	
	/**
	 * 检查用户是否登录
	 * @param request
	 * @return
	 */
	public boolean checkLogin(final HttpServletRequest request)
	{
		Object object = WebUtils.getSessionAttribute(request, "account");
		if(object == null)
			return false;
		Account account = (Account)object;
		if (!account.isLogin()) {
			return false;
		}
		return true;
	}
	
	
	/**
	 * 从cookie中获取唯一标志身份的userID
	 * @param request
	 * @return
	 */
	public String getJobUserIdFromCookie(HttpServletRequest request)
	{
		Cookie[] cookies = request.getCookies();
		if(cookies == null)
			return null;
		for(Cookie cookie:cookies)
		{
			if(StringUtil.isEqual(cookie.getName(), Constant.TKF_COOKIE_UUID_KEY))
			{
				return cookie.getValue();
			}
		}
		return null;
	}
	
	/**
	 * 从cookie中获取唯一标志身份的userID
	 * @param request
	 * @return
	 */
	public String getQqConnectIdFromCookie(HttpServletRequest request)
	{
		Cookie[] cookies = request.getCookies();
		if(cookies == null)
			return null;
		for(Cookie cookie:cookies)
		{
			if(StringUtil.isEqual(cookie.getName(), Constant.TKF_COOKIE_UUID_KEY))
			{
				return cookie.getValue();
			}
		}
		return null;
	}
	
	/**
	 * 从cookie中获取唯一标志身份的userID
	 * @param request
	 * @return
	 */
	public String getCcookie(HttpServletRequest request, String key)
	{
		Cookie[] cookies = request.getCookies();
		if(cookies == null)
			return null;
		for(Cookie cookie:cookies)
		{
			if(StringUtil.isEqual(cookie.getName(), key))
			{
				return cookie.getValue();
			}
		}
		return null;
	}
	
	/**
	 * 向客户端浏览器植入cookie
	 * @param response
	 * @param key
	 * @param value
	 * @param expiry
	 */
	public void addCookie(HttpServletResponse response, String key, String value, int expiry)
	{
		Cookie cookie = new Cookie(key, value);
		cookie.setPath("/");
		cookie.setMaxAge(expiry);
		response.addCookie(cookie);
	}
	
//	/**  
//     * 用于处理异常的  
//     * @return  
//     */  
//    @ExceptionHandler({Exception.class})   
//    public String exception(Exception e) {   
//        System.out.println(e.getMessage());   
//        e.printStackTrace();
//        return "error";
//    }
	
}
