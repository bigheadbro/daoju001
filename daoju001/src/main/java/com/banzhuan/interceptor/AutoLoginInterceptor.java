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
import com.banzhuan.common.Constant;

import com.banzhuan.dao.QuestionDAO;
import com.banzhuan.dao.UserDAO;

import com.banzhuan.entity.UserEntity;

import com.banzhuan.service.UserService;
import com.banzhuan.util.CookieUtil;
import com.banzhuan.util.StringUtil;

public class AutoLoginInterceptor extends HandlerInterceptorAdapter
{
	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Autowired
	@Qualifier("userDAO")
	private UserDAO userDAO;
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Account account = (Account) WebUtils.getSessionAttribute(request, "account");
		if (account == null) {
			String mail = CookieUtil.getCookie(request, Constant.LOGIN_MAIL);
			String rememberme = CookieUtil.getCookie(request, Constant.REMEMBER_ME);
			if(rememberme != null && StringUtil.isEqual(rememberme, "1"))
			{
				UserEntity user = userDAO.queryUserEntityByMail(mail);
				if(user != null)
				{
					int unreadMsgCount = userService.getUnreadMsgCount(user.getId());
					int answerCnt = userService.getAnswerCount(user.getId());
					int sampleCnt = userService.getSampleCount(user.getId());
					int gcCnt = userService.getGoodcaseCount(user.getId());
					account = new Account();
					account.setLogin(true); // 登录成功标识
					account.setUserName(user.getNick()); // 用户登录名
					account.setUserId(user.getId()); // 用户ID
					account.setMail(user.getMail()); // 邮箱
					account.setLogo(user.getLogo()); // logo
					account.setBrandName(user.getBrand());
					account.setVerifiedLink(user.getVerifiedLink());
					account.setAuthority(user.getAuthority());
					account.setProductlimit(user.getProductlimit());
					account.setUnreadMsgCount(unreadMsgCount);
					account.setSampleCnt(sampleCnt);
					account.setGcCnt(gcCnt);
					account.setQuestionCnt(answerCnt);
					//设置头像
					account.setLogo(user.getLogo());
					request.getSession().setAttribute("account", account);
				}
			}
			return true;

		}
		else {
			return true;
		}
	}
}
