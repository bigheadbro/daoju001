package com.banzhuan.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.WebUtils;

import com.banzhuan.common.Account;
import com.banzhuan.common.Result;
import com.banzhuan.entity.AgentEntity;
import com.banzhuan.entity.GoodcaseEntity;
import com.banzhuan.entity.SampleEntity;
import com.banzhuan.form.AgentProfileForm;
import com.banzhuan.form.GoodcaseForm;
import com.banzhuan.form.ProfessionalAnswerForm;
import com.banzhuan.form.RegForm;
import com.banzhuan.form.LoginForm;
import com.banzhuan.form.SampleForm;
import com.banzhuan.service.AgentService;
import com.banzhuan.service.BuyerService;
import com.banzhuan.util.JsonUtil;
import com.banzhuan.util.StringUtil;
import com.banzhuan.util.Util;
import com.qq.connect.QQConnectException;
import com.qq.connect.oauth.Oauth;

@Controller
@RequestMapping("/agent")
@SessionAttributes({"account"})
public class AgentController extends BaseController{
	private Logger logger = LoggerFactory.getLogger(AgentController.class);
	@Autowired
	@Qualifier("agentService")
	private AgentService agentService;
	@Autowired
	@Qualifier("buyerService")
	private BuyerService buyerService;
	/**
	 * 通用URL跳转， 统一将  /agent/*** 等未映射的URL重定向到login页面
	 * @return
	 */
	@RequestMapping(value = "/*")
	public ModelAndView common()
	{
		return new ModelAndView(new RedirectView("agent/mainpage")); 
	}
	
	@RequestMapping(value="/reg")
	public ModelAndView reg(final HttpServletRequest request,
			final HttpServletResponse response, @ModelAttribute("form")RegForm form, BindingResult result) 
	{
		if(isDoSubmit(request))
		{
			Result re = agentService.register(form, result);
			
			if(!result.hasErrors())
			{
				AgentEntity user = (AgentEntity)re.get("agent");
				Account account = new Account();
				account.setLogin(true); // 登录成功标识
				account.setUserName(user.getCompanyName()); // 用户登录名
				account.setUserId(user.getId()); // 用户ID
				account.setBuyer(false);
				account.setAgent(true);
				request.getSession().setAttribute("account", account);
				// 注册成功， 跳转到登陆页面
				return new ModelAndView(new RedirectView("/agent/log")); 
			}
			else
			{
				// 注册失败， 返回注册页面，并显示出错提示信息
				ModelAndView model = new ModelAndView("/agent/reg");
				return model;
			}
		}
		else
		{
			ModelAndView model = new ModelAndView("/agent/reg");
			return model;
		}
	}
	
	@RequestMapping(value="/log")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("form")LoginForm form, BindingResult result) {
		if(isDoSubmit(request))
		{
			Result re = agentService.checkLogin(form, result);
			
			if(!result.hasErrors())
			{
				AgentEntity user = (AgentEntity)re.get("agent");
				int unreadMsgCount = agentService.getUnreadMsgCount(user.getId());
				int answerCnt = agentService.getAnswerCount(user.getId());
				int sampleCnt = agentService.getSampleCount(user.getId());
				int gcCnt = agentService.getGoodcaseCount(user.getId());
				Account account = new Account();
				account.setLogin(true); // 登录成功标识
				account.setUserName(user.getCompanyName()); // 用户登录名
				account.setUserId(user.getId()); // 用户ID
				account.setMail(user.getMail()); // 邮箱
				account.setLogo(user.getLogo()); // logo
				account.setBrandName(user.getBrandName());
				account.setBuyer(false);
				account.setAgent(true);
				account.setVerified(user.isVerified());
				account.setVerifiedLink(user.getVerifiedLink());
				account.setUnreadMsgCount(unreadMsgCount);
				account.setSampleCnt(sampleCnt);
				account.setGcCnt(gcCnt);
				account.setQuestionCnt(answerCnt);
				
				//set cookie
				if(form.getRememberme() != null && form.getRememberme())
				{
					this.addCookie(response, "mail", user.getMail(), Integer.MAX_VALUE);
				}
				//设置头像
				account.setLogo(user.getLogo());
				request.getSession().setAttribute("account", account);
				// 登陆成功， 跳转到登陆页面
				return new ModelAndView(new RedirectView("/agent/main"));
			}
		}
		return new ModelAndView("/agent/log");
	}
	
	@RequestMapping(value = "/logoff")
	public ModelAndView logoff(final HttpServletRequest request,final HttpServletResponse response, Model model) {
		request.getSession().invalidate();
		model.asMap().remove("account");
		return new ModelAndView(new RedirectView("/index"));
		
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
	
	@RequestMapping(value="/uploadlogo")
	public ModelAndView uploadlogo(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("account")Account account, BindingResult result)
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
					AgentEntity agent = new AgentEntity();
					agent.setId(account.getUserId());
					agent.setLogo(account.getLogo());
					agentService.updateAgentAccnt(null, null, agent, result);
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
	
		return new ModelAndView(new RedirectView("main")); 
	}
	
	@RequestMapping(value="/changepwd")
	public ModelAndView changepwd(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("account")Account account, 
			@ModelAttribute("form")RegForm form, BindingResult result)
	{ 
		ModelAndView view = new ModelAndView("agent/changepwd");
		// 非表单提交，直接显示页面
		if(!isDoSubmit(request))
			return view;
		
		form.setUserid(account.getUserId());
		agentService.changePwd(form, result);
		if(result.hasErrors())
		{
			return view;
		}
		JsonUtil.showAlert(response, "修改密码", "修改密码成功~~", "确定", "", "");
		return view;
	}
	
	@RequestMapping(value="/profile")
	public ModelAndView profile(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("account")Account account, 
			@ModelAttribute("form")AgentProfileForm form, BindingResult result)
	{
		ModelAndView mv = new ModelAndView("agent/profile");
		
		AgentEntity agent = (AgentEntity)agentService.getAgentEntity(account.getUserId()).get("agent");
		if(!isDoSubmit(request))
		{
			agentService.setAgentProfileFormWithBuyerEntity(form, agent);
		}
		else
		{
			agentService.updateAgentAccnt(request, form, agent, result);
			if(result.hasErrors())
			{
				return mv;
			}
			JsonUtil.showAlert(response, "更新资料", "公司资料更新成功~~", "确定", "", "");
		}
		return mv;
	}
	
	@RequestMapping(value="/main")
	public ModelAndView main(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("agent/mainpage");
		return mv;
	}
	
	@RequestMapping(value="/mymsg")
	public ModelAndView mymsg(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("agent/mymsg");
		return mv;
	}
	
	@RequestMapping(value="/newquestion")
	public ModelAndView newquestion(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("agent/newquestion");
		Result result = agentService.getAllquestions();
		mv.addObject("questions", result.get("questions"));
		return mv;
	}
	
	@RequestMapping(value="/answer")
	public ModelAndView answer(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("account")Account account,
			@ModelAttribute("answerForm")ProfessionalAnswerForm answerForm,BindingResult result) 
	{
		ModelAndView mv = new ModelAndView("agent/answer");
		agentService.insertAnswer(answerForm, account);
		return mv;
	}
	
	@RequestMapping(value="/showanswer")
	public void showanswer(HttpServletRequest request, HttpServletResponse response) 
	{
		Account account = (Account) WebUtils.getSessionAttribute(request, "account");
		if(account == null)
		{
			JsonUtil.checkAnswerStatus(response, 1);
			return;
		}
		else
		{
			if(account.isLogin())
			{
				if(account.isBuyer())
				{
					JsonUtil.checkAnswerStatus(response, 2);
				}
				if(account.isAgent())
				{
					if(!account.isVerified())
					{
						JsonUtil.checkAnswerStatus(response, 3);
					}
					else
					{
						JsonUtil.checkAnswerStatus(response, 4);
					}
				}
			}
		}
	}
	
	@RequestMapping(value="/draft")
	public ModelAndView draft(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("agent/draft");
		return mv;
	}
	
	@RequestMapping(value="/goodcase")
	public ModelAndView goodcase(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("account")Account account) {
		ModelAndView mv = new ModelAndView("agent/goodcase");
		int userId = account.getUserId();
		Result result = agentService.queryGoodcasesByUserid(userId);
		mv.addObject("goodcases", result.get("goodcases"));
		return mv;
	}
	
	@RequestMapping(value="/gc/{id}")
	public ModelAndView question(HttpServletRequest request, HttpServletResponse response,@PathVariable String id, @ModelAttribute("account")Account account,
			final RedirectAttributes redirectAttributes) {
		
		int gcId = Integer.parseInt(id);
		
		ModelAndView mv = new ModelAndView(new RedirectView("/agent/uploadgc"));
		redirectAttributes.addFlashAttribute("gcid",gcId);
		return mv;
	}
	
	@RequestMapping(value = "uploadfile_gc")  
	@ResponseBody
	public String uploadfile_gc(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("account")Account account)
	{
		String responseStr="";  
		MultipartHttpServletRequest r = (MultipartHttpServletRequest) request;
		  
        MultipartFile f = r.getFile("sampleLink");    
        String path = request.getSession().getServletContext().getRealPath("/goodcase");
		String link = "../goodcase/" + String.valueOf(account.getUserId()) + "/" + StringUtil.getTodayString() + "/" + f.getOriginalFilename();
		path += "/" + String.valueOf(account.getUserId()) + "/" + StringUtil.getTodayString() + "/";
		File file = new File(path + f.getOriginalFilename());
		file.getParentFile().mkdirs();  
		file.getParentFile().mkdirs();  

		try 
		{
			FileCopyUtils.copy(f.getBytes(), file);
			responseStr = link;

		} catch (IOException e) {

		}

		return responseStr;
	}
	
	@RequestMapping(value="/uploadgc")
	public ModelAndView uploadgc(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("account")Account account, 
			@ModelAttribute("form")GoodcaseForm form, BindingResult result, @ModelAttribute("gcid") final Object gcid)
	{
		ModelAndView mv = new ModelAndView("agent/uploadgc");
		if(isDoSubmit(request))
		{
			GoodcaseEntity gc = new GoodcaseEntity();
			gc.setAgentId(account.getUserId());
			gc.setAgentLogo(account.getLogo());
			gc.setAgentName(account.getUserName());
			gc.setBrandName(account.getBrandName());
			gc.setVerified(account.isVerified());
			gc.setVerifiedLink(account.getVerifiedLink());
			
			if(form.getIsEdit() > 0)
			{
				agentService.updateGoodcaseById(form, gc, result);

				JsonUtil.showAlert(response, "更新案例", "成功案例更新成功~~", "确定", "", "");
			}
			else
			{
				agentService.insertGoodcase(form, gc, result);

				JsonUtil.showAlert(response, "上传案例", "上传案例成功~~", "确定", "", "");
			}
			return mv;
		}
		else
		{
			Map<String,?> map = RequestContextUtils.getInputFlashMap(request);
			if(map != null)
			{
				int id = Integer.parseInt(gcid.toString());
				if(id > 0)
				{
					form.setEdit(1);
					form.setGcid(id);
					agentService.setGoodcaseFormByGcid(form, id);
				}
			}
			return mv;
		}
	}
	
	@RequestMapping(value="/mysample")
	public ModelAndView mysample(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("account")Account account) {
		ModelAndView mv = new ModelAndView("agent/mysample");
		int userId = account.getUserId();
		Result result = agentService.queryGoodcasesByUserid(userId);
		mv.addObject("samples", result.get("samples"));
		return mv;
	}
	
	@RequestMapping(value = "uploadfile_sample")  
	@ResponseBody
	public String uploadfile_sample(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("account")Account account)
	{
		String responseStr = "";
		MultipartHttpServletRequest r = (MultipartHttpServletRequest) request;
		  
        MultipartFile f = r.getFile("sampleLink");    
        String path = request.getSession().getServletContext().getRealPath("/sample");
		String link = "../sample/" + String.valueOf(account.getUserId()) + "/" + StringUtil.getTodayString() + "/" + f.getOriginalFilename();
		path += "/" + String.valueOf(account.getUserId()) + "/" + StringUtil.getTodayString() + "/";
		File file = new File(path + f.getOriginalFilename());
		
		file.getParentFile().mkdirs();  

		try 
		{
			FileCopyUtils.copy(f.getBytes(), file);
			responseStr = link;

		} catch (IOException e) {

		}

		return responseStr;
	}
	
	@RequestMapping(value="/uploadsample")
	public ModelAndView uploadsample(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("account")Account account,
			@ModelAttribute("form")SampleForm form, BindingResult result, @ModelAttribute("sid") final Object sid) 
	{
		ModelAndView mv = new ModelAndView("agent/uploadsample");
		
		if(isDoSubmit(request))
		{
			SampleEntity sample = new SampleEntity();
 			sample.setAgentId(account.getUserId());
			sample.setAgentLogo(account.getLogo());
			sample.setAgentName(account.getUserName());
			sample.setBrandName(account.getBrandName());
			sample.setVerified(account.isVerified());
			sample.setVerifiedLink(account.getVerifiedLink());
			
			if(form.getIsEdit() > 0)
			{
				agentService.updateSampleById(form, sample, result);

				JsonUtil.showAlert(response, "上传样本", "更新样本成功~~", "确定", "", "");
				
			}
			else
			{
				agentService.insertSample(form, sample, result);

				JsonUtil.showAlert(response, "上传样本", "上传样本成功~~", "确定", "", "");
			}
			if(result.hasErrors())
			{
				mv = new ModelAndView("agent/uploadsample");
				return mv;
			}
			return mv;
		}
		else
		{
			Map<String,?> map = RequestContextUtils.getInputFlashMap(request);
			if(map != null)
			{
				int id = Integer.parseInt(sid.toString());
				if(id > 0)
				{
					form.setIsEdit(1);
					form.setSid(id);
					agentService.setSampleFormBySid(form, id);
				}
			}
			return mv;
		}
	}

	@RequestMapping(value="/sample/{id}")
	public ModelAndView sample(HttpServletRequest request, HttpServletResponse response,@PathVariable String id, @ModelAttribute("account")Account account,
			final RedirectAttributes redirectAttributes) {
		
		int sid = Integer.parseInt(id);
		
		ModelAndView mv = new ModelAndView(new RedirectView("/agent/uploadsample"));
		redirectAttributes.addFlashAttribute("sid",sid);
		return mv;
	}

}
