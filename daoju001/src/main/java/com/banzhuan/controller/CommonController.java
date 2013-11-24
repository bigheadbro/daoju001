package com.banzhuan.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class CommonController {
	/**
	 * 其他未识别的URL都统一到
	 * @return
	 */
	@RequestMapping(value="/*")
	public void otherEnter(final HttpServletResponse response)
	{
		try {
			response.sendRedirect("index.html");
		} catch (IOException e) {
		}
	}

}
