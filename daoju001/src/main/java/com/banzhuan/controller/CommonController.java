package com.banzhuan.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.banzhuan.common.Result;
import com.banzhuan.entity.BuyerEntity;
import com.banzhuan.form.BuyerRegForm;
import com.banzhuan.form.LoginForm;
import com.banzhuan.service.BuyerService;
import com.banzhuan.common.Account;

@Controller
@RequestMapping("/")
public class CommonController extends BaseController{
	@Autowired
	@Qualifier("buyerService")
	private BuyerService buyerService;
	/**
	 * 其他未识别的URL都统一到
	 * @return
	 */
	@RequestMapping(value="/*")
	public ModelAndView otherEnter(final HttpServletResponse response,@ModelAttribute("form")LoginForm form)
	{
		ModelAndView mv = new ModelAndView("/common/index");
		return mv;
	}

	/**
	 * 其他未识别的URL都统一到
	 * @return
	 */
	@RequestMapping(value="/common/index")
	public ModelAndView index(final HttpServletResponse response,@ModelAttribute("form")LoginForm form)
	{
		ModelAndView mv = new ModelAndView("/common/index");
		return mv;
	}

	@RequestMapping(value="/common/reg")
	public ModelAndView reg(final HttpServletRequest request,
			final HttpServletResponse response, @ModelAttribute("form")BuyerRegForm form, BindingResult result) 
	{
		if(isDoSubmit(request))
		{
			Result re = buyerService.register(form, result);
			
			if(!result.hasErrors())
			{
				BuyerEntity user = (BuyerEntity)re.get("buyer");
				Account account = new Account();
				account.setLogin(true); // 登录成功标识
				account.setUserName(user.getUsername()); // 用户登录名
				account.setUserId(user.getId()); // 用户ID
				account.setBuyer(false);
				request.getSession().setAttribute("account", account);
				// 注册成功， 跳转到登陆页面
				return new ModelAndView(new RedirectView("/common/index")); 
			}
			else
			{
				// 注册失败， 返回注册页面，并显示出错提示信息
				ModelAndView model = new ModelAndView("/common/reg");
				return model;
			}
		}
		else
		{
			ModelAndView model = new ModelAndView("/common/reg");
			return model;
		}
	}

	@RequestMapping(value="/common/login")
	public ModelAndView login(final HttpServletRequest request,
			final HttpServletResponse response, @ModelAttribute("form")LoginForm form, BindingResult result) 
	{
		if(isDoSubmit(request))
		{
			Result re = buyerService.checkLogin(form, result);
			
			if(!result.hasErrors())
			{
				BuyerEntity user = (BuyerEntity)re.get("buyer");
				Account account = new Account();
				account.setLogin(true); // 登录成功标识
				account.setUserName(user.getUsername()); // 用户登录名
				account.setUserId(user.getId()); // 用户ID
				account.setMail(user.getEmail()); // 邮箱
				account.setLogo(user.getLogo()); // 邮箱
				account.setBuyer(true);
				//设置头像
				account.setLogo(buyerService.getBuyerEntity(account.getUserId()).getLogo());
				request.getSession().setAttribute("account", account);
				// 注册成功， 跳转到登陆页面
				return new ModelAndView(new RedirectView("/buyer/profile")); 
			}
			else
			{
				// 注册失败， 返回注册页面，并显示出错提示信息
				ModelAndView model = new ModelAndView(request.getRequestURI());
				return model;
			}
		}
		else
		{
			ModelAndView model = new ModelAndView("/index");
			return model;
		}
	}
	
	/**
	 * @return
	 */
	@RequestMapping(value = "/common/logoff")
	public ModelAndView logoff(final HttpServletRequest request,final HttpServletResponse response) {
		request.getSession().invalidate();
		return new ModelAndView(new RedirectView("/common/index"));
		
	}
}
