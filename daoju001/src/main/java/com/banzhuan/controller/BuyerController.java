package com.banzhuan.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.banzhuan.common.Result;
import com.banzhuan.entity.BuyerEntity;
import com.banzhuan.form.BuyerProfileForm;
import com.banzhuan.form.BuyerRegForm;
import com.banzhuan.form.LoginForm;
import com.banzhuan.form.QuestionForm;
import com.banzhuan.service.BuyerService;
import com.banzhuan.util.JsonUtil;
import com.banzhuan.util.StringUtil;
import com.banzhuan.util.Util;
import com.qq.connect.QQConnectException;
import com.qq.connect.oauth.Oauth;
import com.banzhuan.common.Account;

@Controller
@RequestMapping("/buyer")
@SessionAttributes({"account"})
public class BuyerController extends BaseController{
	private Logger logger = LoggerFactory.getLogger(BuyerController.class);
	@Autowired
	@Qualifier("buyerService")
	private BuyerService buyerService;
	
	/**
	 * 通用URL跳转， 统一将  /buyer/*** 等未映射的URL重定向到login页面
	 * @return
	 */
	@RequestMapping(value = "/*")
	public ModelAndView common()
	{
		return new ModelAndView(new RedirectView("/buyer/main")); 
	}
	
	
	@RequestMapping(value="/reg")
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
				ModelAndView model = new ModelAndView("/buyer/reg");
				return model;
			}
		}
		else
		{
			ModelAndView model = new ModelAndView("/buyer/reg");
			return model;
		}
	}
	
	@RequestMapping(value="/log")
	public ModelAndView login(final HttpServletRequest request,
			final HttpServletResponse response, @ModelAttribute("form")LoginForm form, BindingResult result) throws IOException 
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
				//set cookie
				if(form.getRememberme() != null && form.getRememberme())
				{
					this.addCookie(response, "mail", user.getEmail(), Integer.MAX_VALUE);
				}
				//设置头像
				account.setLogo(buyerService.getBuyerEntity(account.getUserId()).getLogo());
				request.getSession().setAttribute("account", account);
				// 登陆成功， 跳转到登陆页面
				return new ModelAndView(new RedirectView("/buyer/main"));
			}
		}
		return new ModelAndView("/buyer/log");
		
	}
	
	@RequestMapping(value="/changepwd")
	public ModelAndView changepwd(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("account")Account account, 
			@ModelAttribute("form")BuyerRegForm form, BindingResult result) 
	{
		ModelAndView view = new ModelAndView("buyer/changepwd");
		// 非表单提交，直接显示页面
		if(!isDoSubmit(request))
			return view;
		
		form.setUserid(account.getUserId());
		buyerService.changePwd(form, result);
		return view;
	}
	
	@RequestMapping(value="/profile")
	public ModelAndView profile(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("account")Account account, 
			@ModelAttribute("form")BuyerProfileForm form, BindingResult result)
	{
		ModelAndView mv = new ModelAndView("buyer/profile");
		
		BuyerEntity buyer = buyerService.getBuyerEntity(account.getUserId());
		if(!isDoSubmit(request))
		{
			buyerService.setBuyerProfileFormWithBuyerEntity(form, buyer);
		}
		else
		{
			buyerService.updateBuyerAccnt(form, buyer);
		}
		return mv;
	}
	
	@RequestMapping(value="/uploadlogo")
	public void uploadlogo(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("account")Account account, BindingResult result)
	{
		String size = request.getParameter("crop");
		
		// /////////////////////////////////////////////////////////////获取上传的图片///////////////////////////////////
		if (request instanceof DefaultMultipartHttpServletRequest) 
		{
			DefaultMultipartHttpServletRequest r = (DefaultMultipartHttpServletRequest) request;
			List<MultipartFile> files = r.getMultiFileMap().get("logo");
			if (files != null && files.size() > 0) {
				MultipartFile f = files.get(0);
				if (StringUtil.isNotEmpty(f.getOriginalFilename()))
				{
					String path = request.getSession().getServletContext().getRealPath("/uploadfile");
					File file = new File(path + "/" + f.getOriginalFilename());
					account.setLogo(Util.genRandomName(f.getContentType().toString().split("/")[1]));
					BuyerEntity buyer = new BuyerEntity();
					buyer.setId(account.getUserId());
					buyer.setLogo(account.getLogo());
					buyerService.updateBuyerAccnt(null, buyer);
					try 
					{
						FileCopyUtils.copy(f.getBytes(), file);
				
						Util.cropImage(f.getContentType().toString().split("/")[1], file.getPath(), Integer.parseInt(size.split(",")[0]),
								Integer.parseInt(size.split(",")[1]), Integer.parseInt(size.split(",")[2]),
								Integer.parseInt(size.split(",")[3]), path + "/" + account.getLogo());

					} catch (IOException e) {
						logger.error("upload company logo error:"
								+ e.getMessage());
					}
				}
			}
		}
	
		return; 
	}
	
	@RequestMapping(value="/addimg")
	public void addimg(HttpServletRequest request, HttpServletResponse response)
	{
		String fileName = "";
		// /////////////////////////////////////////////////////////////获取上传的图片///////////////////////////////////
		if (request instanceof DefaultMultipartHttpServletRequest) 
		{
			DefaultMultipartHttpServletRequest r = (DefaultMultipartHttpServletRequest) request;
			List<MultipartFile> files = r.getMultiFileMap().get("file_upload");
			if (files != null && files.size() > 0) {
				MultipartFile f = files.get(0);
				if (StringUtil.isNotEmpty(f.getOriginalFilename()))
				{
					String path = request.getSession().getServletContext().getRealPath("/uploadfile");
					fileName = Util.genRandomName(f.getContentType().toString().split("/")[1]);
					File file = new File(path + "/" + fileName);
					try 
					{
						FileCopyUtils.copy(f.getBytes(), file);
					} catch (IOException e) {
						logger.error("upload company logo error:"
								+ e.getMessage());
					}
				}
			}
		}
		JsonUtil.sendImg(response, fileName);
		return ; 
	}
	
	@RequestMapping(value="/main")
	public ModelAndView main(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/buyer/mainpage");
		return mv;
	}
	
	@RequestMapping(value="/mymsg")
	public ModelAndView mymsg(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("buyer/mymsg");
		return mv;
	}
	
	@RequestMapping(value="/newquestion")
	public ModelAndView newquestion(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("account")Account account, 
			@ModelAttribute("form")QuestionForm form, BindingResult result) 
	{
		ModelAndView mv = new ModelAndView("buyer/newquestion");
		if(!isDoSubmit(request))
			return mv;
		
		form.setUserid(account.getUserId());
		buyerService.insertQuestion(form, result);
		return mv;
	}
	
	@RequestMapping(value="/oldquestion")
	public ModelAndView oldquestion(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("account")Account account) {
		ModelAndView mv = new ModelAndView("buyer/oldquestion");
		int userId = account.getUserId();
		Result result = buyerService.queryQuestionsByUserId(userId);
		mv.addObject("questions", result.get("questions"));
		return mv;
	}
	
	@RequestMapping(value="/question/{id}")
	public ModelAndView question(HttpServletRequest request, HttpServletResponse response,@PathVariable String id, @ModelAttribute("account")Account account,
			BindingResult result) {
		
		int questionId = Integer.parseInt(id);
		
		QuestionForm form = buyerService.getQuestionEntity(questionId);

		//return newquestion(request, response, account, form, result);
		ModelAndView mv = new ModelAndView(new RedirectView("/buyer/newquestion"));
		return mv;
	}
	
	@RequestMapping(value="/draft")
	public ModelAndView draft(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("buyer/draft");
		return mv;
	}
	
	
}
