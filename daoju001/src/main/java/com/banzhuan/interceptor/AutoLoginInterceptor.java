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
import com.banzhuan.dao.AgentDAO;
import com.banzhuan.dao.BuyerDAO;
import com.banzhuan.dao.QuestionDAO;
import com.banzhuan.entity.AgentEntity;
import com.banzhuan.entity.BuyerEntity;
import com.banzhuan.service.AgentService;
import com.banzhuan.service.BuyerService;
import com.banzhuan.util.CookieUtil;
import com.banzhuan.util.StringUtil;

public class AutoLoginInterceptor extends HandlerInterceptorAdapter
{
	@Autowired
	@Qualifier("agentService")
	private AgentService agentService;
	@Autowired
	@Qualifier("buyerService")
	private BuyerService buyerService;
	@Autowired
	@Qualifier("buyerDAO")
	private BuyerDAO buyerDAO;
	
	@Autowired
	@Qualifier("agentDAO")
	private AgentDAO agentDAO;
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Account account = (Account) WebUtils.getSessionAttribute(request, "account");
		if (account == null) {
			String mail = CookieUtil.getCookie(request, Constant.LOGIN_MAIL);
			String rememberme = CookieUtil.getCookie(request, Constant.REMEMBER_ME);
			if(rememberme != null && StringUtil.isEqual(rememberme, "1"))
			{
				BuyerEntity buyer = buyerDAO.queryBuyerEntityByMail(mail);
				if(buyer == null)
				{
					AgentEntity agent = agentDAO.queryAgentEntityByMail(mail);
					if(agent != null)
					{
						int unreadMsgCount = agentService.getUnreadMsgCount(agent.getId());
						int answerCnt = agentService.getAnswerCount(agent.getId());
						int sampleCnt = agentService.getSampleCount(agent.getId());
						int gcCnt = agentService.getGoodcaseCount(agent.getId());
						account = new Account();
						account.setLogin(true); // 登录成功标识
						account.setUserName(agent.getCompanyName()); // 用户登录名
						account.setUserId(agent.getId()); // 用户ID
						account.setMail(agent.getMail()); // 邮箱
						account.setLogo(agent.getLogo()); // logo
						account.setBrandName(agent.getBrand());
						account.setBuyer(false);
						account.setAgent(true);
						account.setVerified(agent.isVerified());
						account.setVerifiedLink(agent.getVerifiedLink());
						account.setUnreadMsgCount(unreadMsgCount);
						account.setSampleCnt(sampleCnt);
						account.setGcCnt(gcCnt);
						account.setQuestionCnt(answerCnt);
						//设置头像
						account.setLogo(agent.getLogo());
						request.getSession().setAttribute("account", account);
					}
				}
				else
				{
					int unreadMsgCount = buyerService.getUnreadMsgCount(buyer.getId());
					int questionCnt = buyerService.getUserQuestionCount(buyer.getId());
					account = new Account();
					account.setUserId(buyer.getId());
					account.setLogin(true); // 登录成功标识
					account.setUserName(buyer.getUsername()); // 用户登录名
					account.setUserId(buyer.getId()); // 用户ID
					account.setMail(buyer.getEmail()); // 邮箱
					account.setLogo(buyer.getLogo()); // 邮箱
					account.setBuyer(true);
					account.setUnreadMsgCount(unreadMsgCount);
					account.setQuestionCnt(questionCnt);
					//设置头像
					account.setLogo(buyer.getLogo());
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
