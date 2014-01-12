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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.banzhuan.common.Result;
import com.banzhuan.entity.BuyerEntity;
import com.banzhuan.form.GoodcaseForm;
import com.banzhuan.form.RegForm;
import com.banzhuan.form.LoginForm;
import com.banzhuan.service.BuyerService;
import com.banzhuan.service.CommonService;
import com.banzhuan.util.JsonUtil;
import com.banzhuan.common.Account;

@Controller
@RequestMapping("/")
@SessionAttributes({"account"})
public class CommonController extends BaseController{
	@Autowired
	@Qualifier("commonService")
	private CommonService commonService;
	/**
	 * 其他未识别的URL都统一到
	 * @return
	 */
	@RequestMapping(value="*")
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

	@RequestMapping(value = "/question")
	public ModelAndView allquestion(final HttpServletRequest request,final HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/common/question");
		return mv;
		
	}
	
	@RequestMapping(value = "goodcases")
	public ModelAndView allcase(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("form")GoodcaseForm form) {
		ModelAndView mv = new ModelAndView("/common/goodcases");
		
		Result result = commonService.getAllGoodcases(form);
		mv.addObject("goodcases", result.get("goodcases"));
		return mv;
		
	}
}
