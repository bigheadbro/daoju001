package com.banzhuan.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.banzhuan.entity.AddressEntity;
import com.banzhuan.entity.UserEntity;
import com.banzhuan.entity.BrandEntity;
import com.banzhuan.entity.GoodcaseEntity;
import com.banzhuan.entity.ItemEntity;
import com.banzhuan.entity.OrderEntity;
import com.banzhuan.entity.ProductEntity;
import com.banzhuan.entity.ProfessionalAnswerEntity;
import com.banzhuan.entity.SampleEntity;
import com.banzhuan.form.AddressForm;
import com.banzhuan.form.UserProfileForm;
import com.banzhuan.form.GoodcaseForm;
import com.banzhuan.form.ProductForm;
import com.banzhuan.form.ProfessionalAnswerForm;
import com.banzhuan.form.QuestionForm;
import com.banzhuan.form.RegForm;
import com.banzhuan.form.SampleForm;
import com.banzhuan.service.CommonService;
import com.banzhuan.service.UserService;
import com.banzhuan.util.ChineseSpelling;
import com.banzhuan.util.CookieUtil;
import com.banzhuan.util.JsonUtil;
import com.banzhuan.util.StringUtil;
import com.banzhuan.util.Util;

@Controller
@RequestMapping("/user")
@SessionAttributes({"account"})
public class UserController extends BaseController{
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	@Autowired
	@Qualifier("commonService")
	private CommonService commonService;
	/**
	 * 通用URL跳转， 统一将  /user/*** 等未映射的URL重定向到login页面
	 * @return
	 */
	@RequestMapping(value = "/*")
	public ModelAndView common()
	{
		return new ModelAndView(new RedirectView("user/main")); 
	}

	@RequestMapping(value = "/logoff")
	public ModelAndView logoff(final HttpServletRequest request,final HttpServletResponse response, Model model) {
		CookieUtil.removeCookie(request, response, Constant.REMEMBER_ME);
		request.getSession().invalidate();
		model.asMap().remove("account");
		return new ModelAndView(new RedirectView("/index"));
		
	}
	
	@RequestMapping(value="/reg")
	public ModelAndView reg(final HttpServletRequest request,
			final HttpServletResponse response, @ModelAttribute("form")RegForm form, BindingResult result) 
	{
		Account accnt = (Account) WebUtils.getSessionAttribute(request, "account");
		if(accnt != null && accnt.isLogin())
		{
			return new ModelAndView(new RedirectView("/user/main")); 
		}
		if(isDoSubmit(request))
		{
			Result re = userService.register(form, result);
			
			if(!result.hasErrors())
			{
				UserEntity user = (UserEntity)re.get("user");
				Account account = new Account();
				account.setLogin(true); // 登录成功标识
				account.setUserName(user.getNick()); // 用户登录名
				account.setMail(user.getMail());
				account.setUserId(user.getId()); // 用户ID
				account.setLogo(user.getLogo());
				account.setCompanyName(user.getCompanyName());
				account.setPhone(user.getContactPhone());
				account.setQq(user.getContactQq());
				account.setProductlimit(user.getProductlimit());
				request.getSession().setAttribute("account", account);
				// 注册成功， 跳转到登陆页面
				return new ModelAndView(new RedirectView("/user/main")); 
			}
			else
			{
				// 注册失败， 返回注册页面，并显示出错提示信息
				ModelAndView model = new ModelAndView("/user/reg");
				return model;
			}
		}
		else
		{
			ModelAndView model = new ModelAndView("/user/reg");
			return model;
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
					UserEntity user = new UserEntity();
					user.setId(account.getUserId());
					user.setLogo(account.getLogo());
					userService.updateUserAccnt(null, null, user, null, result);
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
		ModelAndView view = new ModelAndView("user/changepwd");
		// 非表单提交，直接显示页面
		if(!isDoSubmit(request))
			return view;
		
		form.setUserid(account.getUserId());
		userService.changePwd(form, result);
		if(result.hasErrors())
		{
			JsonUtil.showAlert(response, "旧密码错误", "旧密码输入错误，请重新输入", "确定", "", "#");
			return view;
		}
		JsonUtil.showAlert(response, "修改密码", "修改密码成功~~", "确定", "", "#");
		return view;
	}
	
	@RequestMapping(value="/profile")
	public ModelAndView profile(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("account")Account account, 
			 @ModelAttribute("form")UserProfileForm form, BindingResult result)
	{
		ModelAndView mv = new ModelAndView("user/profile");
		List<BrandEntity> brands = new ArrayList<BrandEntity>();
		for(int i = 1;i<=Constant.BRAND_CNT;i++)
		{
			BrandEntity brand = new BrandEntity();
			if(StringUtil.isNotEmpty(StringUtil.getBrand(i)))
			{
				brand.setKey(i);
				brand.setName(StringUtil.getBrand(i));
				brand.setLink(StringUtil.getBrandLogo(i));
				brand.setCountry(StringUtil.getBrandCountry(i));
				brands.add(brand);
			}
		}
		Collections.sort(brands,new Comparator<BrandEntity>() {   
		    public int compare(BrandEntity o1, BrandEntity o2) {      
		        return (ChineseSpelling.letterToNum(ChineseSpelling.getFirstLetter(o1.getName()))-ChineseSpelling.letterToNum(ChineseSpelling.getFirstLetter(o2.getName())));
		    }
		}); 
		mv.addObject("brands", brands);
		UserEntity user = (UserEntity)userService.getUserEntity(account.getUserId()).get("user");
		if(!isDoSubmit(request))
		{
			userService.setUserProfileFormWithUserEntity(form, user);
		}
		else
		{
			
			if(result.hasErrors())
			{
				return mv;
			}
			if(userService.updateUserAccnt(request, form, user, account, result) == 0)
			{
				JsonUtil.showAlert(response, "更新资料失败", "公司名已存在，请重新输入！", "确定", "", "#");
				return mv;
			}
			JsonUtil.showAlert(response, "更新资料", "公司资料更新成功~~", "确定", "", "#");
		}
		return mv;
	}
	
	@RequestMapping(value="/main")
	public ModelAndView main(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("account")Account account) {
		ModelAndView mv = new ModelAndView("user/mainpage");
		UserEntity user = (UserEntity)userService.getUserEntity(account.getUserId()).get("user");
		mv.addObject("user", user);
		mv.addObject("qCnt",account.getQuestionCnt());
		mv.addObject("productCnt",userService.getProductCount(account.getUserId()));
		int total= commonService.getOrdersCount(account.getUserId(), 1);
		mv.addObject("total", total);
		if(total > 0)
		{
			List<OrderEntity> orders = commonService.getOrders(account.getUserId(), 0, new RowBounds(0,3));
			for(int i = 0; i< orders.size(); i++)
			{
				orders.get(i).setItem(commonService.getItem(orders.get(i).getItemid()));
			}
			mv.addObject("orders",orders);
		}
		return mv;
	}
	
	@RequestMapping(value="/mymsg")
	public ModelAndView mymsg(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("account")Account account) {
		ModelAndView mv = new ModelAndView("user/mymsg");
		int page = 1;
		if(request.getParameter("page") != null)
		{
			page = Integer.valueOf(request.getParameter("page"));
		}
		int userId = account.getUserId();
		Result result = new Result();
		
		int total=userService.getMsgCount(userId);
		int totalPage=0;
		if(total % Constant.LIST_PAGE_SIZE == 0)
			totalPage=total/Constant.LIST_PAGE_SIZE;
		else
			totalPage=total/Constant.LIST_PAGE_SIZE+1;
		totalPage=totalPage==0?1:totalPage;
		mv.addObject("page", page);
		mv.addObject("total", total);
		mv.addObject("totalPage", totalPage);

		result = userService.getAllMsgs(userId, new RowBounds((page-1)*Constant.LIST_PAGE_SIZE, Constant.LIST_PAGE_SIZE));
		
		mv.addObject("msgs", result.get("msgs"));
		return mv;
	}
	
	@RequestMapping(value="/newquestion")
	public ModelAndView newquestion(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("account")Account account, 
			@ModelAttribute("questionForm")QuestionForm form, BindingResult result, @ModelAttribute("questionid") final Object questionid) 
	{
		ModelAndView mv = new ModelAndView("user/newquestion");
		
		if(!isDoSubmit(request))
		{
			Map<String,?> map = RequestContextUtils.getInputFlashMap(request);
			if(map != null)
			{
				int qid = Integer.parseInt(questionid.toString());
				if(qid > 0)
				{
					userService.setQuestionFormWithQid(form,qid);
					form.setIsEdit(1);
					form.setQid(qid);
				}
			}
			return mv;
		}

		if(userService.getTodayQuestionCount(account.getUserId()) >= 3)
		{
			JsonUtil.showAlert(response, "无法提问", "每位用户每天最多提问3次", "确定", "", "#");
			return mv;
		}
		
		form.setUserid(account.getUserId());
		form.setContent(form.getContent().replace('"', '\''));
		if(form.getIsEdit() > 0)
		{
			userService.updateQuestionById(form, result);
			if(result.hasErrors())
			{
				return mv;
			}
			if(form.getState() == 1)
			{
				account.setQuestionCnt(userService.getUserQuestionCount(account.getUserId()));
				JsonUtil.showAlert(response, "编辑问题", "问题内容更新成功~~", "确定", "", "#");
			}
			else
			{
				JsonUtil.showAlert(response, "编辑问题", "更新内容已保存到草稿~~", "确定", "", "#");
			}
		}
		else
		{
			userService.insertQuestion(form, account, result);
			if(result.hasErrors())
			{
				return mv;
			}
			if(form.getState() == 0)
			{
				JsonUtil.showAlert(response, "新建草稿", "问题已保存到草稿~~", "确定", "", "#");
			}
			else
			{
				account.setQuestionCnt(userService.getUserQuestionCount(account.getUserId()));
				JsonUtil.showAlert(response, "新建问题", "问题新建成功~~", "确定", "", "#");
			}
		}
		return mv;
	}
	
	@RequestMapping(value="/oldquestion")
	public ModelAndView oldquestion(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("account")Account account) {
		ModelAndView mv = new ModelAndView("user/oldquestion");
		int page = 1;
		if(request.getParameter("page") != null)
		{
			page = Integer.valueOf(request.getParameter("page"));
		}
		int userId = account.getUserId();
		Result result = new Result();
		
		int total=userService.getUserQuestionCount(userId);
		int totalPage=0;
		if(total % Constant.LIST_PAGE_SIZE == 0)
			totalPage=total/Constant.LIST_PAGE_SIZE;
		else
			totalPage=total/Constant.LIST_PAGE_SIZE+1;
		totalPage=totalPage==0?1:totalPage;
		mv.addObject("page", page);
		mv.addObject("total", total);
		mv.addObject("totalPage", totalPage);

		result = userService.queryQuestionsByUserId(userId, new RowBounds((page-1)*Constant.LIST_PAGE_SIZE, Constant.LIST_PAGE_SIZE));
		
		mv.addObject("questions", result.get("questions"));
		return mv;
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
	
	@RequestMapping(value="/answered")
	public ModelAndView answered(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("answerForm")ProfessionalAnswerForm answerForm) {
		ModelAndView mv = new ModelAndView("user/answered");
		
		int page = 1;
		if(request.getParameter("page") != null)
		{
			page = Integer.valueOf(request.getParameter("page"));
		}
		int userId = account.getUserId();
		Result result = new Result();
		
		int total=userService.getAnswerCount(userId);
		int totalPage=0;
		if(total % Constant.LIST_PAGE_SIZE == 0)
			totalPage=total/Constant.LIST_PAGE_SIZE;
		else
			totalPage=total/Constant.LIST_PAGE_SIZE+1;
		totalPage=totalPage==0?1:totalPage;
		mv.addObject("page", page);
		mv.addObject("total", total);
		mv.addObject("totalPage", totalPage);

		result = userService.queryAnswersByUserid(userId, new RowBounds((page-1)*Constant.LIST_PAGE_SIZE, Constant.LIST_PAGE_SIZE));
		mv.addObject("answers", result.get("answers"));
		return mv;
	}
	
	@RequestMapping(value="/answer")
	public void answer(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("account")Account account,
			@ModelAttribute("answerForm")ProfessionalAnswerForm answerForm,BindingResult result) 
	{
		if(answerForm.isHasEdit())
		{
			userService.updateAnswer(answerForm);
		}
		else
		{
			userService.insertAnswer(answerForm, account);
		}
		JsonUtil.sendImg(response, "");
	}
	
	@RequestMapping(value="/editanswer")
	public void editanswer(HttpServletRequest request, HttpServletResponse response) 
	{
		int answerid = Integer.parseInt(request.getParameter("answerid"));
		ProfessionalAnswerEntity answer = userService.queryProfessionalAnswerEntityById(answerid);
		
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
				if(account.getAuthority() < 3)
				{
					JsonUtil.checkAnswerStatus(response, 2);
				}
				JsonUtil.checkAnswerStatus(response, 4);
			}
			else
			{
				JsonUtil.checkAnswerStatus(response, 1);
			}
		}
	}
	
	@RequestMapping(value="/draft")
	public ModelAndView draft(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("account")Account account) {
		ModelAndView mv = new ModelAndView("user/draft");
		int page = 1;
		if(request.getParameter("page") != null)
		{
			page = Integer.valueOf(request.getParameter("page"));
		}
		int userId = account.getUserId();
		Result result = new Result();

		int total=userService.getUserDraftCount(userId);
		int totalPage=0;
		if(total % Constant.LIST_PAGE_SIZE == 0)
			totalPage=total/Constant.LIST_PAGE_SIZE;
		else
			totalPage=total/Constant.LIST_PAGE_SIZE+1;
		totalPage=totalPage==0?1:totalPage;
		mv.addObject("page", page);
		mv.addObject("total", total);
		mv.addObject("totalPage", totalPage);

		result = userService.queryDraftsByUserId(userId, new RowBounds((page-1)*Constant.LIST_PAGE_SIZE, Constant.LIST_PAGE_SIZE));
		
		mv.addObject("questions", result.get("questions"));
		return mv;
	}
	
	@RequestMapping(value="/answerdraft")
	public ModelAndView answerdraft(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("answerForm")ProfessionalAnswerForm answerForm) {
		ModelAndView mv = new ModelAndView("user/answerdraft");
		int page = 1;
		if(request.getParameter("page") != null)
		{
			page = Integer.valueOf(request.getParameter("page"));
		}
		int userId = account.getUserId();
		Result result = new Result();
		
		int total=userService.getAnswerCount(userId);
		int totalPage=0;
		if(total % Constant.LIST_PAGE_SIZE == 0)
			totalPage=total/Constant.LIST_PAGE_SIZE;
		else
			totalPage=total/Constant.LIST_PAGE_SIZE+1;
		totalPage=totalPage==0?1:totalPage;
		mv.addObject("page", page);
		mv.addObject("total", total);
		mv.addObject("totalPage", totalPage);

		result = userService.queryDraftsByUserid(userId, new RowBounds((page-1)*Constant.LIST_PAGE_SIZE, Constant.LIST_PAGE_SIZE));

		mv.addObject("answers", result.get("answers"));
		return mv;
	}
	
	@RequestMapping(value="/goodcase")
	public ModelAndView goodcase(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("account")Account account) {
		ModelAndView mv = new ModelAndView("user/goodcase");
		int page = 1;
		if(request.getParameter("page") != null)
		{
			page = Integer.valueOf(request.getParameter("page"));
		}
		int userId = account.getUserId();
		Result result = new Result();
		
		int total=userService.getGoodcaseCount(userId);
		int totalPage=0;
		if(total % Constant.LIST_PAGE_SIZE == 0)
			totalPage=total/Constant.LIST_PAGE_SIZE;
		else
			totalPage=total/Constant.LIST_PAGE_SIZE+1;
		totalPage=totalPage==0?1:totalPage;
		mv.addObject("page", page);
		mv.addObject("total", total);
		mv.addObject("totalPage", totalPage);

		result = userService.queryGoodcasesByUserid(userId, new RowBounds((page-1)*Constant.LIST_PAGE_SIZE, Constant.LIST_PAGE_SIZE));

		mv.addObject("goodcases", result.get("goodcases"));
		return mv;
	}
	
	@RequestMapping(value="/question/{id}")
	public ModelAndView question(HttpServletRequest request, HttpServletResponse response,@PathVariable String id, @ModelAttribute("account")Account account,
			final RedirectAttributes redirectAttributes) {
		
		int questionId = Integer.parseInt(id);
		
		ModelAndView mv = new ModelAndView(new RedirectView("/user/newquestion"));
		redirectAttributes.addFlashAttribute("questionid",questionId);
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
	
	@RequestMapping(value = "uploadfile_product", produces="text/plain;charset=UTF-8")  
	@ResponseBody
	public String uploadfile_product(HttpServletRequest request, HttpServletResponse response)
	{
		String responseStr="";  
		MultipartHttpServletRequest r = (MultipartHttpServletRequest) request;
		  
        MultipartFile f = r.getFile("productlink");    
        String type = f.getOriginalFilename().substring(f.getOriginalFilename().lastIndexOf(".")).toLowerCase();
        if(!StringUtil.isProperImageFile(type))
        {
        	return "";
        }
        String path = request.getSession().getServletContext().getRealPath("/product");
		String link = StringUtil.getTodayString() + "/" + f.getOriginalFilename();
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
	
	/*@RequestMapping(value="/uploadgc")
	public ModelAndView uploadgc(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("account")Account account, 
			@ModelAttribute("form")GoodcaseForm form, BindingResult result, @ModelAttribute("gcid") final Object gcid)
	{
		ModelAndView mv = new ModelAndView("user/uploadgc");
		if(isDoSubmit(request))
		{
			GoodcaseEntity gc = new GoodcaseEntity();
			gc.setUserId(account.getUserId());
			gc.setUserLogo(account.getLogo());
			gc.setUserName(account.getUserName());
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
				userService.updateGoodcaseById(form, gc, result);
				
				if(result.hasErrors())
				{
					mv = new ModelAndView("user/uploadgc");
					return mv;
				}


				JsonUtil.showAlert(response, "更新案例", "成功案例更新成功~~", "确定", "", "");
			}
			else
			{
				userService.insertGoodcase(form, gc, result);
				
				if(result.hasErrors())
				{
					mv = new ModelAndView("user/uploadgc");
					return mv;
				}

				int gcCnt = userService.getGoodcaseCount(account.getUserId());
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
					userService.setGoodcaseFormByGcid(form, id);
				}
			}
			return mv;
		}
	}*/
	
	@RequestMapping(value="/mysample")
	public ModelAndView mysample(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("account")Account account) {
		ModelAndView mv = new ModelAndView("user/mysample");
		int page = 1;
		if(request.getParameter("page") != null)
		{
			page = Integer.valueOf(request.getParameter("page"));
		}
		int userId = account.getUserId();
		Result result = new Result();
		
		int total=userService.getSampleCount(userId);
		int totalPage=0;
		if(total % Constant.COMMON_PAGE_SIZE == 0)
			totalPage=total/Constant.COMMON_PAGE_SIZE;
		else
			totalPage=total/Constant.COMMON_PAGE_SIZE+1;
		totalPage=totalPage==0?1:totalPage;
		mv.addObject("page", page);
		mv.addObject("total", total);
		mv.addObject("totalPage", totalPage);

		result = userService.querySamplesByUserid(userId, new RowBounds((page-1)*Constant.COMMON_PAGE_SIZE, Constant.COMMON_PAGE_SIZE));
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
		
		file.getParentFile().mkdirs();  
		
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
		ModelAndView mv = new ModelAndView("user/uploadsample");
		
		if(isDoSubmit(request))
		{
			SampleEntity sample = new SampleEntity();
 			sample.setAgentId(account.getUserId());
			sample.setAgentLogo(account.getLogo());
			sample.setAgentName(account.getUserName());
			sample.setBrandName(account.getBrandName());
			sample.setVerifiedLink(account.getVerifiedLink());
			
			if(form.getLink().substring(5).split("/")[2].length() > 100)
			{
				JsonUtil.showAlert(response, "上传失败", "文件名称过长，请重命名该文件", "确定", "", "#");
				return mv;
			}
			if(form.getIsEdit() > 0)
			{
				userService.updateSampleById(form, sample, result);
				
				if(result.hasErrors())
				{
					mv = new ModelAndView("user/uploadsample");
					return mv;
				}

				JsonUtil.showAlert(response, "上传样本", "更新样本成功~~", "确定", "", "#");
				
			}
			else
			{
				try
				{
					logger.info("before");
					userService.insertSample(form, sample, result);
					logger.info("after");
					if(result.hasErrors())
					{
						mv = new ModelAndView("user/uploadsample");
						return mv;
					}
					
					int sampleCnt = userService.getSampleCount(account.getUserId());
					account.setSampleCnt(sampleCnt);
					JsonUtil.showAlert(response, "上传样本", "上传样本成功~~", "确定", "", "#");
				}
				catch(Exception e){
					logger.info("sampleissue:"+String.valueOf(result.getErrorCount())+";ex:"+e.toString());
				}
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
					userService.setSampleFormBySid(form, id);
				}
			}
			return mv;
		}
	}

	@RequestMapping(value="/deladdr/{id}")
	public void deladdr(HttpServletRequest request, HttpServletResponse response, @PathVariable String id, @ModelAttribute("account")Account account) throws IOException 
	{
		String url = request.getHeader("Referer");
		int sid = Integer.parseInt(id);
		userService.delAddr(sid);
		response.sendRedirect(url);
	}
	
	@RequestMapping(value="/delsample/{id}")
	public void delsample(HttpServletRequest request, HttpServletResponse response, @PathVariable String id, @ModelAttribute("account")Account account) throws IOException 
	{
		String url = request.getHeader("Referer");
		int sid = Integer.parseInt(id);
		userService.delSample(sid);
		account.setSampleCnt(userService.getSampleCount(account.getUserId()));
		response.sendRedirect(url);
	}
	
	@RequestMapping(value="/delgc/{id}")
	public void delgc(HttpServletRequest request, HttpServletResponse response, @PathVariable String id, @ModelAttribute("account")Account account) throws IOException 
	{
		String url = request.getHeader("Referer");
		int gid = Integer.parseInt(id);
		userService.delGoodcase(gid);
		account.setGcCnt(userService.getGoodcaseCount(account.getUserId()));
		response.sendRedirect(url);
	}
	
	@RequestMapping(value="/sample/{id}")
	public ModelAndView sample(HttpServletRequest request, HttpServletResponse response,@PathVariable String id, @ModelAttribute("account")Account account,
			final RedirectAttributes redirectAttributes) {
		
		int sid = Integer.parseInt(id);
		
		ModelAndView mv = new ModelAndView(new RedirectView("/user/uploadsample"));
		redirectAttributes.addFlashAttribute("sid",sid);
		return mv;
	}
	
	@RequestMapping(value="/product/{id}")
	public ModelAndView product(HttpServletRequest request, HttpServletResponse response,@PathVariable String id, @ModelAttribute("account")Account account,
			final RedirectAttributes redirectAttributes) {
		
		int productid = Integer.parseInt(id);
		
		ModelAndView mv = new ModelAndView(new RedirectView("/user/newproduct"));
		redirectAttributes.addFlashAttribute("productid",productid);
		return mv;
	}
	
	@RequestMapping(value="/myproduct")
	public ModelAndView myproduct(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("account")Account account) {
		ModelAndView mv = new ModelAndView("user/myproduct");
		int userId = account.getUserId();
		Result result = new Result();
		result = userService.queryProductByUserid(userId);
		mv.addObject("products", result.get("products"));
		return mv;
	}
	
	@RequestMapping(value="/newproduct")
	public ModelAndView newproduct(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("account")Account account, 
			@ModelAttribute("productForm")ProductForm form, BindingResult result, @ModelAttribute("productid") final Object productid) 
	{
		ModelAndView mv = new ModelAndView("user/newproduct");
		
		List<BrandEntity> brands = new ArrayList<BrandEntity>();
		for(int i = 1;i<=Constant.BRAND_CNT;i++)
		{
			BrandEntity brand = new BrandEntity();
			if(StringUtil.isNotEmpty(StringUtil.getBrand(i)))
			{
				brand.setKey(i);
				brand.setName(StringUtil.getBrand(i));
				brand.setLink(StringUtil.getBrandLogo(i));
				brand.setCountry(StringUtil.getBrandCountry(i));
				brands.add(brand);
			}
		}
		Collections.sort(brands,new Comparator<BrandEntity>() {   
		    public int compare(BrandEntity o1, BrandEntity o2) {      
		        return (ChineseSpelling.letterToNum(ChineseSpelling.getFirstLetter(o1.getName()))-ChineseSpelling.letterToNum(ChineseSpelling.getFirstLetter(o2.getName())));
		    }
		}); 
		mv.addObject("brands", brands);
		if(!isDoSubmit(request))
		{
			Map<String,?> map = RequestContextUtils.getInputFlashMap(request);
			if(map != null)
			{
				int pid = Integer.parseInt(productid.toString());
				if(pid > 0)
				{
					userService.setProductFormWithPid(form,pid);
					form.setIsEdit(1);
					form.setPid(pid);
					mv.addObject("brandid", form.getBrandid());
				}
			}
			UserEntity user = (UserEntity)userService.getUserEntity(account.getUserId()).get("user");
			int errormsg = 0;
			if(form.getIsEdit() == 0)
			{
				if(!userService.isPersonalInfoGood(user))
				{
					errormsg = 1;
				}
				if(userService.getProductCount(account.getUserId()) >= account.getProductlimit())
				{
					errormsg = account.getProductlimit();
				}
			}
			mv.addObject("errormsg",errormsg);
			return mv;
		}
		
		if(form.getIsEdit() > 0)
		{
			if(StringUtil.calStrNum(form.getName()) > 30)
			{
				JsonUtil.showAlert(response, "编辑刀具失败", "上传失败，刀具名称太长~", "确定", "", "#");
			}
			if(form.getProcessMethod() == 0)
			{
				JsonUtil.showAlert(response, "编辑刀具失败", "上传失败，请选择加工方式~", "确定", "", "#");
			}
			if(StringUtil.isEmpty(form.getPicture()))
			{
				JsonUtil.showAlert(response, "编辑刀具失败", "上传失败，请选择刀具配图~", "确定", "", "#");
			}
			userService.updateProductById(form, result);
			
			if(result.hasErrors())
			{
				return mv;
			}
			
			//account.setQuestionCnt(buyerService.getUserQuestionCount(account.getUserId()));
			JsonUtil.showAlert(response, "编辑刀具", "刀具内容更新成功~~", "确定", "", "#");
		}
		else
		{
			if(StringUtil.calStrNum(form.getName()) > 50)
			{
				JsonUtil.showAlert(response, "新建刀具失败", "上传失败，刀具名称太长~", "确定", "", "#");
				return mv;
			}
			if(form.getProcessMethod() == 0)
			{
				JsonUtil.showAlert(response, "新建刀具失败", "上传失败，请选择加工方式~", "确定", "", "#");
				return mv;
			}
			if(StringUtil.isEmpty(form.getPicture()))
			{
				JsonUtil.showAlert(response, "新建刀具失败", "上传失败，请选择刀具配图~", "确定", "", "#");
				return mv;
			}
			ProductEntity product = new ProductEntity();
			product.setUserId(account.getUserId());
			product.setUserLogo(account.getLogo());
			product.setUserName(account.getUserName());
			userService.insertProduct(form, product, result);
			if(result.hasErrors())
			{
				return mv;
			}

			JsonUtil.showAlert(response, "新建刀具", "发布成功，请等待管理员审核", "确定", "", "#");

		}
		return mv;
	}
	
	@RequestMapping(value="/delproduct/{id}")
	public void delproduct(HttpServletRequest request, HttpServletResponse response, @PathVariable String id, @ModelAttribute("account")Account account) throws IOException 
	{
		String url = request.getHeader("Referer");
		int pid = Integer.parseInt(id);
		userService.delProduct(pid);
		//todo:account.setSampleCnt(userService.getSampleCount(account.getUserId()));
		response.sendRedirect(url);
	}
	
	@RequestMapping(value="/myaddr")
	public ModelAndView myaddr(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")AddressForm form) {
		ModelAndView mv = new ModelAndView("common/myaddr");
		int userId = account.getUserId();
		Result result = new Result();
		
		result = userService.queryAdressesByUserid(userId);

		mv.addObject("addresses", result.get("addresses"));
		return mv;
	}

	@RequestMapping(value="/myorder")
	public ModelAndView myorder(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("account")Account account) {
		ModelAndView mv = new ModelAndView("common/myorder");
		int page = 1;
		if(request.getParameter("page") != null)
		{
			page = Integer.valueOf(request.getParameter("page"));
		}
		
		int total= commonService.getOrdersCount(account.getUserId(), 1);
		int totalPage=0;
		if(total % Constant.ORDER_PAGE_SIZE == 0)
			totalPage=total/Constant.ORDER_PAGE_SIZE;
		else
			totalPage=total/Constant.ORDER_PAGE_SIZE+1;
		totalPage=totalPage==0?1:totalPage;
		mv.addObject("page", page);
		mv.addObject("total", total);
		mv.addObject("totalPage", totalPage);
		List<OrderEntity> orders = commonService.getOrders(account.getUserId(), 0, new RowBounds((page-1)*Constant.ORDER_PAGE_SIZE, Constant.ORDER_PAGE_SIZE));
		for(int i = 0; i< orders.size(); i++)
		{
			orders.get(i).setItem(commonService.getItem(orders.get(i).getItemid()));
		}
		mv.addObject("orders",orders);
		return mv;
	}
	
	@RequestMapping(value="/order/{id}")
	public ModelAndView myorder(HttpServletRequest request, HttpServletResponse response, @PathVariable String id, @ModelAttribute("account")Account account) {
		ModelAndView mv = new ModelAndView("common/purchase_return");
		int orderid = Integer.parseInt(id);
		OrderEntity order = commonService.getOrder(orderid);
		if(order.getUserid() == account.getUserId())
		{
			mv.addObject("orderid",order.getId());
			AddressEntity addr = commonService.getAddressById(order.getAddressid());
			mv.addObject("receiverinfo",addr.getName() + "," + addr.getPca() + "," + addr.getAddr());
			mv.addObject("total",order.getPrice());
			ItemEntity item = commonService.getItem(order.getItemid());
			mv.addObject("itemid",item.getId());
			mv.addObject("itemName",item.getBrand()+item.getType() +item.getVersion());
			mv.addObject("price",item.getPrice());
			mv.addObject("quantity",order.getQuantity());
			mv.addObject("cover",item.getCover());
			mv.addObject("orderno", order.getLogNumber());
			
			mv.addObject("timeSubmit",StringUtil.convertDate(order.getGmtSubmitOrder()));
			
			mv.addObject("timePay",StringUtil.convertDate(order.getGmtPay()));
			
			mv.addObject("timeSell",StringUtil.convertDate(order.getGmtSell()));
			
			mv.addObject("timeAssure",StringUtil.convertDate(order.getGmtAssure()));
			
			mv.addObject("state", order.getState());
			mv.addObject("orderno", order.getLogNumber());
			return mv;
		}
		else
		{
			return new ModelAndView("buyer/mainpage");
		}
	}

}
