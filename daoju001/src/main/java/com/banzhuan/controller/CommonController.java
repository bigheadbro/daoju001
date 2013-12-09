package com.banzhuan.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.banzhuan.common.Result;
import com.banzhuan.form.BuyerRegForm;

@Controller
@RequestMapping("/")
public class CommonController {
	/**
	 * 其他未识别的URL都统一到
	 * @return
	 */
	@RequestMapping(value="/*")
	public ModelAndView otherEnter(final HttpServletResponse response,@ModelAttribute("form")BuyerRegForm form)
	{
		ModelAndView mv = new ModelAndView("index/index");
		return mv;
	}

}
