package com.banzhuan.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.banzhuan.common.Constant;
import com.banzhuan.common.Result;
import com.banzhuan.entity.BuyerEntity;
import com.banzhuan.form.BuyerProfileForm;
import com.banzhuan.form.RegForm;
import com.banzhuan.form.LoginForm;
import com.banzhuan.form.QuestionForm;
import com.banzhuan.service.BuyerService;
import com.banzhuan.util.JsonUtil;
import com.banzhuan.util.StringUtil;
import com.banzhuan.util.Util;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
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
			final HttpServletResponse response, @ModelAttribute("form")RegForm form, BindingResult result) 
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
				account.setBuyer(true);
				account.setAgent(false);
				request.getSession().setAttribute("account", account);
				// 注册成功， 跳转到登陆页面
				return new ModelAndView(new RedirectView("/buyer/log")); 
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
					this.addCookie(response, Constant.LOGIN_MAIL, user.getEmail(), Integer.MAX_VALUE);
				}
				//设置头像
				account.setLogo(buyerService.getBuyerEntity(account.getUserId()).getLogo());
				request.getSession().setAttribute("account", account);
				// 登陆成功， 跳转到登陆页面
				return new ModelAndView(new RedirectView("/buyer/newquestion"));
			}
		}
		return new ModelAndView("/buyer/log");
		
	}
	
	/**
	 * @return
	 */
	@RequestMapping(value = "/qqlogin")
	public void qqLogin(final HttpServletRequest request,final HttpServletResponse response) {
	 	try
	 	{
			response.sendRedirect(new Oauth().getAuthorizeURL(request));
		} catch (QQConnectException e) {
			logger.error("QQConnectException:"+e.getMessage());
		} catch (IOException e) {
			logger.error("qqLoginException:"+e.getMessage());
		}
			
	}
	
	/**
	 * qq connect 回调接口
	 * 
	 * @return
	 */
	@RequestMapping(value = "/qq_connect")
	public ModelAndView qqConnect(final HttpServletRequest request, final HttpServletResponse response, final ModelMap modelMap) {
		try {
			AccessToken accessTokenObj = (new Oauth())
					.getAccessTokenByRequest(request);
			String accessToken = null, openID = null;
			long tokenExpireIn = 0L;

			if (accessTokenObj.getAccessToken().equals("")) {
				return new ModelAndView(new RedirectView("error")); // 定向到统一的错误页面
			} else {
				accessToken = accessTokenObj.getAccessToken();
				tokenExpireIn = accessTokenObj.getExpireIn();
				//this.addCookie(response, Constant.TKF_QQ_ACCESS_TOKET, accessToken, (int)tokenExpireIn);
				// 利用获取到的accessToken 去获取当前用的openid -------- start
				OpenID openIDObj = new OpenID(accessToken);
				openID = openIDObj.getUserOpenID();
				UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);
                UserInfoBean userInfo = qzoneUserInfo.getUserInfo();
                String nick = userInfo.getNickname();
                
                BuyerEntity user = buyerService.doQqConnectEnter(openID, nick);
				Account account = new Account();
				account.setQqConnectId(openID);
				account.setAccessToken(accessToken);
				account.setUserName(nick);
				account.setUserId(user.getId());
				account.setLogin(true);
				account.setBuyer(true);

				//account.setLogo(resume.getHeadUrl());
				request.getSession().setAttribute("account", account);
				return new ModelAndView(new RedirectView("profile"));
			}
		} catch (Exception e) {
			logger.error("qqConnect:"+e.getMessage());
			return new ModelAndView(new RedirectView("error")); // 定向到统一的错误页面
		}
	}
	
	/**
	 * @return
	 */
	@RequestMapping(value = "/logoff")
	public ModelAndView logoff(final HttpServletRequest request,final HttpServletResponse response, Model model) {
		request.getSession().invalidate();
		model.asMap().remove("account");
		return new ModelAndView(new RedirectView("/index"));
		
	}
	@RequestMapping(value="/changepwd")
	public ModelAndView changepwd(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("account")Account account, 
			@ModelAttribute("form")RegForm form, BindingResult result) 
	{
		ModelAndView view = new ModelAndView("buyer/changepwd");
		// 非表单提交，直接显示页面
		if(!isDoSubmit(request))
			return view;
		
		form.setUserid(account.getUserId());
		buyerService.changePwd(form, result);
		if(result.hasErrors())
		{
			return view;
		}
		JsonUtil.showAlert(response, "修改密码", "修改密码成功~~", "确定", "", "");
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
			if(result.hasErrors())
			{
				return mv;
			}
			JsonUtil.showAlert(response, "更新资料", "个人资料更新成功~~", "确定", "", "");
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
					String path = request.getSession().getServletContext().getRealPath("/logo");
					File file = new File(path + "/" + f.getOriginalFilename());
					account.setLogo(Util.genRandomName(f.getOriginalFilename().substring(f.getOriginalFilename().lastIndexOf("."))));
					BuyerEntity buyer = new BuyerEntity();
					buyer.setId(account.getUserId());
					buyer.setLogo(account.getLogo());
					buyerService.updateBuyerAccnt(null, buyer);
					try 
					{
						FileCopyUtils.copy(f.getBytes(), file);
				
						Util.cropImage(f.getContentType().split("/")[1], file.getPath(), Integer.parseInt(size.split(",")[0]),
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
					fileName = Util.genRandomName(f.getOriginalFilename().substring(f.getOriginalFilename().lastIndexOf(".")));
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
			@ModelAttribute("questionForm")QuestionForm form, @ModelAttribute("questionid") final Object questionid, BindingResult result) 
	{
		ModelAndView mv = new ModelAndView("buyer/newquestion");
		if(!isDoSubmit(request))
		{
			Map<String,?> map = RequestContextUtils.getInputFlashMap(request);
			if(map != null)
			{
				int qid = Integer.parseInt(questionid.toString());
				if(qid > 0)
				{
					buyerService.setQuestionFormWithQid(form,qid);
					form.setIsEdit(1);
					form.setQid(qid);
				}
			}
			return mv;
		}

		form.setUserid(account.getUserId());
		form.setContent(form.getContent().replace('"', '\''));
		if(form.getIsEdit() > 0)
		{
			buyerService.updateQuestionById(form, result);
			if(result.hasErrors())
			{
				return mv;
			}
			JsonUtil.showAlert(response, "编辑问题", "问题内容更新成功~~", "确定", "", "");
		}
		else
		{
			buyerService.insertQuestion(form, result);
			if(result.hasErrors())
			{
				return mv;
			}
			JsonUtil.showAlert(response, "新建问题", "问题新建成功~~", "确定", "", "");
		}
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
			final RedirectAttributes redirectAttributes) {
		
		int questionId = Integer.parseInt(id);
		
		ModelAndView mv = new ModelAndView(new RedirectView("/buyer/newquestion"));
		redirectAttributes.addFlashAttribute("questionid",questionId);
		return mv;
	}
	
	@RequestMapping(value="/draft")
	public ModelAndView draft(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("account")Account account) {
		ModelAndView mv = new ModelAndView("buyer/draft");
		int userId = account.getUserId();
		Result result = buyerService.queryDraftsByUserId(userId);
		mv.addObject("questions", result.get("questions"));
		return mv;
	}
	
	
}
