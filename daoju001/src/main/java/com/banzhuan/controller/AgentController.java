package com.banzhuan.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

import org.apache.ibatis.session.RowBounds;
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
import com.banzhuan.common.Constant;
import com.banzhuan.common.Result;
import com.banzhuan.entity.AgentEntity;
import com.banzhuan.entity.BrandEntity;
import com.banzhuan.entity.GoodcaseEntity;
import com.banzhuan.entity.ProfessionalAnswerEntity;
import com.banzhuan.entity.SampleEntity;
import com.banzhuan.form.AgentProfileForm;
import com.banzhuan.form.GoodcaseForm;
import com.banzhuan.form.ProfessionalAnswerForm;
import com.banzhuan.form.RegForm;
import com.banzhuan.form.LoginForm;
import com.banzhuan.form.SampleForm;
import com.banzhuan.service.AgentService;
import com.banzhuan.util.CookieUtil;
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
	/**
	 * 通用URL跳转， 统一将  /agent/*** 等未映射的URL重定向到login页面
	 * @return
	 */
	@RequestMapping(value = "/*")
	public ModelAndView common()
	{
		return new ModelAndView(new RedirectView("agent/main")); 
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
				account.setMail(user.getMail());
				account.setBrandName(user.getBrand());
				account.setUserId(user.getId()); // 用户ID
				account.setBuyer(false);
				account.setAgent(true);
				request.getSession().setAttribute("account", account);
				// 注册成功， 跳转到登陆页面
				return new ModelAndView(new RedirectView("/agent/main")); 
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
		return new ModelAndView(new RedirectView("/log"));
	}
	
	@RequestMapping(value = "/logoff")
	public ModelAndView logoff(final HttpServletRequest request,final HttpServletResponse response, Model model) {
		CookieUtil.removeCookie(request, response, Constant.REMEMBER_ME);
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
	public void uploadlogo(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("account")Account account, BindingResult result) throws IOException
	{
		String url = request.getHeader("Referer");
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
					agentService.updateAgentAccnt(null, null, agent, null, result);
					try 
					{
						FileCopyUtils.copy(f.getBytes(), file);
				
						Util.cropImage(f.getContentType().split("/")[1], file, size, path + "/" + account.getLogo());

					} catch (IOException e) {
						logger.error("upload company logo error:"
								+ e.getMessage());
					}
				}
			}
		}
	
		response.sendRedirect(url);
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
			JsonUtil.showAlert(response, "旧密码错误", "旧密码输入错误，请重新输入", "确定", "", "");
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
		List<BrandEntity> brands = new ArrayList<BrandEntity>();
		for(int i = 1;i<=43;i++)
		{
			BrandEntity brand = new BrandEntity();
			brand.setKey(i);
			brand.setName(StringUtil.getBrand(i));
			brand.setLink(StringUtil.getBrandLogo(i));
			brand.setCountry(StringUtil.getBrandCountry(i));
			brands.add(brand);
		}
		mv.addObject("brands", brands);
		AgentEntity agent = (AgentEntity)agentService.getAgentEntity(account.getUserId()).get("agent");
		if(!isDoSubmit(request))
		{
			agentService.setAgentProfileFormWithBuyerEntity(form, agent);
		}
		else
		{
			agentService.updateAgentAccnt(request, form, agent, account, result);
			if(result.hasErrors())
			{
				return mv;
			}
			JsonUtil.showAlert(response, "更新资料", "公司资料更新成功~~", "确定", "", "");
		}
		return mv;
	}
	
	@RequestMapping(value="/main")
	public ModelAndView main(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("account")Account account) {
		ModelAndView mv = new ModelAndView("agent/mainpage");
		mv.addObject("name", account.getUserName());
		mv.addObject("mail", account.getMail());
		mv.addObject("brand", account.getBrandName());
		return mv;
	}
	
	@RequestMapping(value="/mymsg")
	public ModelAndView mymsg(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("account")Account account) {
		ModelAndView mv = new ModelAndView("agent/mymsg");
		int page = 1;
		if(request.getParameter("page") != null)
		{
			page = Integer.valueOf(request.getParameter("page"));
		}
		int userId = account.getUserId();
		Result result = new Result();
		
		int total=agentService.getMsgCount(userId);
		int totalPage=0;
		if(total % Constant.LIST_PAGE_SIZE == 0)
			totalPage=total/Constant.LIST_PAGE_SIZE;
		else
			totalPage=total/Constant.LIST_PAGE_SIZE+1;
		totalPage=totalPage==0?1:totalPage;
		mv.addObject("page", page);
		mv.addObject("total", total);
		mv.addObject("totalPage", totalPage);

		result = agentService.getAllMsgs(userId, new RowBounds((page-1)*Constant.LIST_PAGE_SIZE, Constant.LIST_PAGE_SIZE));
		
		mv.addObject("msgs", result.get("msgs"));
		return mv;
	}
	
	@RequestMapping(value="/newquestion")
	public ModelAndView newquestion(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("agent/newquestion");
		
		int page = 1;
		if(request.getParameter("page") != null)
		{
			page = Integer.valueOf(request.getParameter("page"));
		}
		
		Result result = new Result();
		
		int total=agentService.getAllquestionsCount();
		int totalPage=0;
		if(total % Constant.LIST_PAGE_SIZE == 0)
			totalPage=total/Constant.LIST_PAGE_SIZE;
		else
			totalPage=total/Constant.LIST_PAGE_SIZE+1;
		totalPage=totalPage==0?1:totalPage;
		mv.addObject("page", page);
		mv.addObject("total", total);
		mv.addObject("totalPage", totalPage);

		result = agentService.getAllquestions(new RowBounds((page-1)*Constant.LIST_PAGE_SIZE, Constant.LIST_PAGE_SIZE));
		
		mv.addObject("questions", result.get("questions"));
		return mv;
	}
	
	@RequestMapping(value="/oldquestion")
	public ModelAndView oldquestion(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("answerForm")ProfessionalAnswerForm answerForm) {
		ModelAndView mv = new ModelAndView("agent/oldquestion");
		
		int page = 1;
		if(request.getParameter("page") != null)
		{
			page = Integer.valueOf(request.getParameter("page"));
		}
		int userId = account.getUserId();
		Result result = new Result();
		
		int total=agentService.getAnswerCount(userId);
		int totalPage=0;
		if(total % Constant.LIST_PAGE_SIZE == 0)
			totalPage=total/Constant.LIST_PAGE_SIZE;
		else
			totalPage=total/Constant.LIST_PAGE_SIZE+1;
		totalPage=totalPage==0?1:totalPage;
		mv.addObject("page", page);
		mv.addObject("total", total);
		mv.addObject("totalPage", totalPage);

		result = agentService.queryAnswersByUserid(userId, new RowBounds((page-1)*Constant.LIST_PAGE_SIZE, Constant.LIST_PAGE_SIZE));
		mv.addObject("answers", result.get("answers"));
		return mv;
	}
	
	@RequestMapping(value="/answer")
	public void answer(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("account")Account account,
			@ModelAttribute("answerForm")ProfessionalAnswerForm answerForm,BindingResult result) 
	{
		if(answerForm.isHasEdit())
		{
			agentService.updateAnswer(answerForm);
		}
		else
		{
			agentService.insertAnswer(answerForm, account);
		}
		JsonUtil.sendImg(response, "");
	}
	
	@RequestMapping(value="/editanswer")
	public void editanswer(HttpServletRequest request, HttpServletResponse response) 
	{
		int answerid = Integer.parseInt(request.getParameter("answerid"));
		ProfessionalAnswerEntity answer = agentService.queryProfessionalAnswerEntityById(answerid);
		
		JsonUtil.sendAnswernt(response, answer.getState(), answer.getContent(), answer.getPrice(), answer.isFreeUse());
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
	public ModelAndView draft(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("answerForm")ProfessionalAnswerForm answerForm) {
		ModelAndView mv = new ModelAndView("agent/draft");
		int page = 1;
		if(request.getParameter("page") != null)
		{
			page = Integer.valueOf(request.getParameter("page"));
		}
		int userId = account.getUserId();
		Result result = new Result();
		
		int total=agentService.getAnswerCount(userId);
		int totalPage=0;
		if(total % Constant.LIST_PAGE_SIZE == 0)
			totalPage=total/Constant.LIST_PAGE_SIZE;
		else
			totalPage=total/Constant.LIST_PAGE_SIZE+1;
		totalPage=totalPage==0?1:totalPage;
		mv.addObject("page", page);
		mv.addObject("total", total);
		mv.addObject("totalPage", totalPage);

		result = agentService.queryDraftsByUserid(userId, new RowBounds((page-1)*Constant.LIST_PAGE_SIZE, Constant.LIST_PAGE_SIZE));

		mv.addObject("answers", result.get("answers"));
		return mv;
	}
	
	@RequestMapping(value="/goodcase")
	public ModelAndView goodcase(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("account")Account account) {
		ModelAndView mv = new ModelAndView("agent/goodcase");
		int page = 1;
		if(request.getParameter("page") != null)
		{
			page = Integer.valueOf(request.getParameter("page"));
		}
		int userId = account.getUserId();
		Result result = new Result();
		
		int total=agentService.getGoodcaseCount(userId);
		int totalPage=0;
		if(total % Constant.LIST_PAGE_SIZE == 0)
			totalPage=total/Constant.LIST_PAGE_SIZE;
		else
			totalPage=total/Constant.LIST_PAGE_SIZE+1;
		totalPage=totalPage==0?1:totalPage;
		mv.addObject("page", page);
		mv.addObject("total", total);
		mv.addObject("totalPage", totalPage);

		result = agentService.queryGoodcasesByUserid(userId, new RowBounds((page-1)*Constant.LIST_PAGE_SIZE, Constant.LIST_PAGE_SIZE));

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
	
	@RequestMapping(value = "uploadfile_gc", produces="text/plain;charset=UTF-8")  
	@ResponseBody
	public String uploadfile_gc(HttpServletRequest request, HttpServletResponse response)
	{
		Account account = (Account) WebUtils.getSessionAttribute(request, "account");
		String responseStr="";  
		MultipartHttpServletRequest r = (MultipartHttpServletRequest) request;
		  
        MultipartFile f = r.getFile("gcLink");    
        String path = request.getSession().getServletContext().getRealPath("/goodcase");
		String link = "../goodcase/" + StringUtil.getTodayString() + "/" + f.getOriginalFilename();
		path += "/" + StringUtil.getTodayString() + "/";
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
			
			if(form.getLink().substring(5).split("/")[2].length() > 100)
			{
				JsonUtil.showAlert(response, "上传失败", "文件名称过长，请重命名该文件", "确定", "", "");
				return mv;
			}
			
			if(form.getEdit() > 0)
			{
				agentService.updateGoodcaseById(form, gc, result);
				
				if(result.hasErrors())
				{
					mv = new ModelAndView("agent/uploadgc");
					return mv;
				}


				JsonUtil.showAlert(response, "更新案例", "成功案例更新成功~~", "确定", "", "");
			}
			else
			{
				agentService.insertGoodcase(form, gc, result);
				
				if(result.hasErrors())
				{
					mv = new ModelAndView("agent/uploadgc");
					return mv;
				}

				int gcCnt = agentService.getGoodcaseCount(account.getUserId());
				account.setGcCnt(gcCnt);
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
		int page = 1;
		if(request.getParameter("page") != null)
		{
			page = Integer.valueOf(request.getParameter("page"));
		}
		int userId = account.getUserId();
		Result result = new Result();
		
		int total=agentService.getSampleCount(userId);
		int totalPage=0;
		if(total % Constant.COMMON_PAGE_SIZE == 0)
			totalPage=total/Constant.COMMON_PAGE_SIZE;
		else
			totalPage=total/Constant.COMMON_PAGE_SIZE+1;
		totalPage=totalPage==0?1:totalPage;
		mv.addObject("page", page);
		mv.addObject("total", total);
		mv.addObject("totalPage", totalPage);

		result = agentService.querySamplesByUserid(userId, new RowBounds((page-1)*Constant.COMMON_PAGE_SIZE, Constant.COMMON_PAGE_SIZE));
		mv.addObject("samples", result.get("samples"));
		return mv;
	}
	
	@RequestMapping(value = "uploadfile_sample", produces="text/plain;charset=UTF-8")  
	@ResponseBody
	public String uploadfile_sample(HttpServletRequest request, HttpServletResponse response)
	{
		String responseStr = "";
		MultipartHttpServletRequest r = (MultipartHttpServletRequest) request;
		  
        MultipartFile f = r.getFile("sampleLink");    
        String path = request.getSession().getServletContext().getRealPath("/sample");

		String link = "../sample/"  + StringUtil.getTodayString() + "/" + f.getOriginalFilename();
		path += "/"  + StringUtil.getTodayString() + "/";
		File file = new File(path + f.getOriginalFilename());
		logger.info("before mkdir");
		file.getParentFile().mkdirs();  
		logger.info("after mkdir");
		try 
		{
			FileCopyUtils.copy(f.getBytes(), file);
			responseStr = link;
			

		} catch (IOException e) {
			logger.error(e.getMessage());
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
			
			if(form.getLink().substring(5).split("/")[2].length() > 100)
			{
				JsonUtil.showAlert(response, "上传失败", "文件名称过长，请重命名该文件", "确定", "", "");
				return mv;
			}
			if(form.getIsEdit() > 0)
			{
				agentService.updateSampleById(form, sample, result);
				
				if(result.hasErrors())
				{
					mv = new ModelAndView("agent/uploadsample");
					return mv;
				}

				JsonUtil.showAlert(response, "上传样本", "更新样本成功~~", "确定", "", "");
				
			}
			else
			{
				agentService.insertSample(form, sample, result);

				if(result.hasErrors())
				{
					mv = new ModelAndView("agent/uploadsample");
					return mv;
				}
				
				int sampleCnt = agentService.getSampleCount(account.getUserId());
				account.setSampleCnt(sampleCnt);
				JsonUtil.showAlert(response, "上传样本", "上传样本成功~~", "确定", "", "");
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

	@RequestMapping(value="/delsample/{id}")
	public void delsample(HttpServletRequest request, HttpServletResponse response, @PathVariable String id, @ModelAttribute("account")Account account) throws IOException 
	{
		String url = request.getHeader("Referer");
		int sid = Integer.parseInt(id);
		agentService.delSample(sid);
		account.setSampleCnt(agentService.getSampleCount(account.getUserId()));
		response.sendRedirect(url);
	}
	
	@RequestMapping(value="/delgc/{id}")
	public void delgc(HttpServletRequest request, HttpServletResponse response, @PathVariable String id, @ModelAttribute("account")Account account) throws IOException 
	{
		String url = request.getHeader("Referer");
		int gid = Integer.parseInt(id);
		agentService.delGoodcase(gid);
		account.setGcCnt(agentService.getGoodcaseCount(account.getUserId()));
		response.sendRedirect(url);
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
