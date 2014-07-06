package com.banzhuan.controller;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.ui.ModelMap;
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

import com.banzhuan.common.Constant;
import com.banzhuan.common.Result;
import com.banzhuan.entity.AddressEntity;
import com.banzhuan.entity.AgentEntity;
import com.banzhuan.entity.BrandEntity;
import com.banzhuan.entity.BuyerEntity;
import com.banzhuan.entity.ItemEntity;
import com.banzhuan.entity.OrderEntity;
import com.banzhuan.entity.ProductEntity;
import com.banzhuan.form.AddressForm;
import com.banzhuan.form.BuyerProfileForm;
import com.banzhuan.form.ProductForm;
import com.banzhuan.form.RegForm;
import com.banzhuan.form.LoginForm;
import com.banzhuan.form.QuestionForm;
import com.banzhuan.service.BuyerService;
import com.banzhuan.service.CommonService;
import com.banzhuan.util.CookieUtil;
import com.banzhuan.util.JsonUtil;
import com.banzhuan.util.StringUtil;
import com.banzhuan.util.Util;
import com.banzhuan.common.Account;

@Controller
@RequestMapping("/buyer")
@SessionAttributes({"account"})
public class BuyerController extends BaseController{
	private Logger logger = LoggerFactory.getLogger(BuyerController.class);
	@Autowired
	@Qualifier("buyerService")
	private BuyerService buyerService;

	@Autowired
	@Qualifier("commonService")
	private CommonService commonService;
	
	/**
	 * 通用URL跳转， 统一将  /buyer/*** 等未映射的URL重定向到login页面
	 * @return
	 */
	@RequestMapping(value = "/*")
	public ModelAndView common()
	{
		return new ModelAndView(new RedirectView("/buyer/main")); 
	}
	
	@RequestMapping(value="/log")
	public ModelAndView login(final HttpServletRequest request,
			final HttpServletResponse response, @ModelAttribute("form")LoginForm form, BindingResult result) throws IOException 
	{
		return new ModelAndView(new RedirectView("/log"));
		
	}

	/**
	 * @return
	 */
	@RequestMapping(value = "/logoff")
	public ModelAndView logoff(final HttpServletRequest request,final HttpServletResponse response, Model model) {
		CookieUtil.removeCookie(request, response, Constant.REMEMBER_ME);
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
			JsonUtil.showAlert(response, "旧密码错误", "旧密码输入错误，请重新输入", "确定", "", "");
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
			if(result.hasErrors())
			{
				return mv;
			}
			
			if(buyerService.updateBuyerAccnt(form, buyer, result) == 0)
			{
				JsonUtil.showAlert(response, "更新资料失败", "用户名已存在，请重新输入！", "确定", "", "");
			}
			if(buyer.getUsername() != null)
			{
				account.setUserName(buyer.getUsername());
			}
			if(buyer.getCompanyName() != null)
			{
				account.setCompanyName(buyer.getCompanyName());
			}
			JsonUtil.showAlert(response, "更新资料", "资料更新成功~~", "确定", "", "");
		}
		return mv;
	}
	
	@RequestMapping(value="/uploadlogo")
	public void uploadlogo(HttpServletRequest request, HttpServletResponse response) throws IOException
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
					Account account = (Account) WebUtils.getSessionAttribute(request, "account");
					account.setLogo(Util.genRandomName(f.getOriginalFilename().substring(f.getOriginalFilename().lastIndexOf("."))));
					BuyerEntity buyer = new BuyerEntity();
					buyer.setId(account.getUserId());
					buyer.setLogo(account.getLogo());
					buyerService.updateBuyerAccnt(null, buyer, null);
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
		String url = request.getHeader("Referer");
		response.sendRedirect(url);
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
	public ModelAndView main(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("account")Account account) 
	{
		ModelAndView mv = new ModelAndView("/buyer/mainpage");
		mv.addObject("name", account.getUserName());
		mv.addObject("mail", account.getMail());
		mv.addObject("company", account.getCompanyName());
		return mv;
	}
	
	@RequestMapping(value="/mymsg")
	public ModelAndView mymsg(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("account")Account account) {
		ModelAndView mv = new ModelAndView("buyer/mymsg");
		int page = 1;
		if(request.getParameter("page") != null)
		{
			page = Integer.valueOf(request.getParameter("page"));
		}
		int userId = account.getUserId();
		Result result = new Result();
		
		int total=buyerService.getMsgCount(userId);
		int totalPage=0;
		if(total % Constant.LIST_PAGE_SIZE == 0)
			totalPage=total/Constant.LIST_PAGE_SIZE;
		else
			totalPage=total/Constant.LIST_PAGE_SIZE+1;
		totalPage=totalPage==0?1:totalPage;
		mv.addObject("page", page);
		mv.addObject("total", total);
		mv.addObject("totalPage", totalPage);

		result = buyerService.getAllMsgs(userId, new RowBounds((page-1)*Constant.LIST_PAGE_SIZE, Constant.LIST_PAGE_SIZE));
		
		mv.addObject("msgs", result.get("msgs"));
		return mv;
	}
	
	@RequestMapping(value="/newquestion")
	public ModelAndView newquestion(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("account")Account account, 
			@ModelAttribute("questionForm")QuestionForm form, BindingResult result, @ModelAttribute("questionid") final Object questionid) 
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

		if(buyerService.getTodayQuestionCount(account.getUserId()) >= 3)
		{
			JsonUtil.showAlert(response, "无法提问", "每位用户每天最多提问3次", "确定", "", "");
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
			if(form.getState() == 1)
			{
				account.setQuestionCnt(buyerService.getUserQuestionCount(account.getUserId()));
				JsonUtil.showAlert(response, "编辑问题", "问题内容更新成功~~", "确定", "", "");
			}
			else
			{
				JsonUtil.showAlert(response, "编辑问题", "更新内容已保存到草稿~~", "确定", "", "");
			}
		}
		else
		{
			buyerService.insertQuestion(form, account, result);
			if(result.hasErrors())
			{
				return mv;
			}
			if(form.getState() == 0)
			{
				JsonUtil.showAlert(response, "新建草稿", "问题已保存到草稿~~", "确定", "", "");
			}
			else
			{
				account.setQuestionCnt(buyerService.getUserQuestionCount(account.getUserId()));
				JsonUtil.showAlert(response, "新建问题", "问题新建成功~~", "确定", "", "");
			}
		}
		return mv;
	}
	
	@RequestMapping(value="/oldquestion")
	public ModelAndView oldquestion(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("account")Account account) {
		ModelAndView mv = new ModelAndView("buyer/oldquestion");
		int page = 1;
		if(request.getParameter("page") != null)
		{
			page = Integer.valueOf(request.getParameter("page"));
		}
		int userId = account.getUserId();
		Result result = new Result();
		
		int total=buyerService.getUserQuestionCount(userId);
		int totalPage=0;
		if(total % Constant.LIST_PAGE_SIZE == 0)
			totalPage=total/Constant.LIST_PAGE_SIZE;
		else
			totalPage=total/Constant.LIST_PAGE_SIZE+1;
		totalPage=totalPage==0?1:totalPage;
		mv.addObject("page", page);
		mv.addObject("total", total);
		mv.addObject("totalPage", totalPage);

		result = buyerService.queryQuestionsByUserId(userId, new RowBounds((page-1)*Constant.LIST_PAGE_SIZE, Constant.LIST_PAGE_SIZE));
		
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
		int page = 1;
		if(request.getParameter("page") != null)
		{
			page = Integer.valueOf(request.getParameter("page"));
		}
		int userId = account.getUserId();
		Result result = new Result();

		int total=buyerService.getUserDraftCount(userId);
		int totalPage=0;
		if(total % Constant.LIST_PAGE_SIZE == 0)
			totalPage=total/Constant.LIST_PAGE_SIZE;
		else
			totalPage=total/Constant.LIST_PAGE_SIZE+1;
		totalPage=totalPage==0?1:totalPage;
		mv.addObject("page", page);
		mv.addObject("total", total);
		mv.addObject("totalPage", totalPage);

		result = buyerService.queryDraftsByUserId(userId, new RowBounds((page-1)*Constant.LIST_PAGE_SIZE, Constant.LIST_PAGE_SIZE));
		
		mv.addObject("questions", result.get("questions"));
		return mv;
	}
	
	@RequestMapping(value="/myaddr")
	public ModelAndView myaddr(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("account")Account account, @ModelAttribute("form")AddressForm form) {
		ModelAndView mv = new ModelAndView("common/myaddr");
		int userId = account.getUserId();
		Result result = new Result();
		
		result = buyerService.queryAdressesByBuyerid(userId);

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
		
		int total= commonService.getOrdersCount(account.getUserId(), 0);
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
		if(order.getUserid() == account.getUserId() && order.getUsertype() == (account.isAgent()?1:0))
		{
			mv.addObject("orderid",order.getId());
			AddressEntity addr = commonService.getAddressById(order.getAddressid());
			mv.addObject("receiverinfo",addr.getName() + "," + addr.getPca() + "," + addr.getAddr());
			mv.addObject("total",order.getPrice());
			ItemEntity item = commonService.getItem(order.getItemid());
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
	
	@RequestMapping(value="/product/{id}")
	public ModelAndView product(HttpServletRequest request, HttpServletResponse response,@PathVariable String id, @ModelAttribute("account")Account account,
			final RedirectAttributes redirectAttributes) {
		
		int productid = Integer.parseInt(id);
		
		ModelAndView mv = new ModelAndView(new RedirectView("/buyer/newproduct"));
		redirectAttributes.addFlashAttribute("productid",productid);
		return mv;
	}
	
	@RequestMapping(value="/myproduct")
	public ModelAndView myproduct(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("account")Account account) {
		ModelAndView mv = new ModelAndView("buyer/myproduct");
		int userId = account.getUserId();
		Result result = new Result();
		result = buyerService.queryProductByUserid(userId);
		mv.addObject("products", result.get("products"));
		return mv;
	}
	
	@RequestMapping(value="/newproduct")
	public ModelAndView newproduct(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("account")Account account, 
			@ModelAttribute("productForm")ProductForm form, BindingResult result, @ModelAttribute("productid") final Object productid) 
	{
		ModelAndView mv = new ModelAndView("buyer/newproduct");
		List<BrandEntity> brands = new ArrayList<BrandEntity>();
		for(int i = 1;i<=Constant.BRAND_CNT;i++)
		{
			BrandEntity brand = new BrandEntity();
			brand.setKey(i);
			brand.setName(StringUtil.getBrand(i));
			brand.setLink(StringUtil.getBrandLogo(i));
			brand.setCountry(StringUtil.getBrandCountry(i));
			brands.add(brand);
		}
		mv.addObject("brands", brands);
		if(!isDoSubmit(request))
		{
			Map<String,?> map = RequestContextUtils.getInputFlashMap(request);
			if(map != null)
			{
				int pid = Integer.parseInt(productid.toString());
				if(pid > 0)
				{
					buyerService.setProductFormWithPid(form,pid);
					form.setIsEdit(1);
					form.setPid(pid);
					mv.addObject("brandid", form.getBrandid());
				}
			}
			BuyerEntity buyer = buyerService.getBuyerEntity(account.getUserId());
			String errormsg = null;
			if(!buyerService.isPersonalInfoGood(buyer))
			{
				errormsg = "上传特色刀具前，请先完善您的个人资料";
			}
			if(buyerService.getProductCount(account.getUserId()) >= account.getProductlimit())
			{
				errormsg = "每位用户最多上传2个特色刀具，如需要上传更多，请联系我们，400-042-1145";
			}
			mv.addObject("errormsg",errormsg);
			return mv;
		}
		
		if(form.getIsEdit() > 0)
		{
			if(StringUtil.calStrNum(form.getName()) > 50)
			{
				JsonUtil.showAlert(response, "编辑刀具失败", "上传失败，刀具名称太长~", "确定", "", "");
			}
			if(form.getProcessMethod() == 0)
			{
				JsonUtil.showAlert(response, "编辑刀具失败", "上传失败，请选择加工方式~", "确定", "", "");
			}
			if(StringUtil.isEmpty(form.getPicture()))
			{
				JsonUtil.showAlert(response, "编辑刀具失败", "上传失败，请选择刀具配图~", "确定", "", "");
			}
			buyerService.updateProductById(form, result);
			
			if(result.hasErrors())
			{
				return mv;
			}
			
			//account.setQuestionCnt(buyerService.getUserQuestionCount(account.getUserId()));
			JsonUtil.showAlert(response, "编辑刀具", "刀具内容更新成功~~", "确定", "", "");
		}
		else
		{
			if(StringUtil.calStrNum(form.getName()) > 50)
			{
				JsonUtil.showAlert(response, "新建刀具失败", "上传失败，刀具名称太长~", "确定", "", "");
			}
			if(form.getProcessMethod() == 0)
			{
				JsonUtil.showAlert(response, "新建刀具失败", "上传失败，请选择加工方式~", "确定", "", "");
			}
			if(StringUtil.isEmpty(form.getPicture()))
			{
				JsonUtil.showAlert(response, "新建刀具失败", "上传失败，请选择刀具配图~", "确定", "", "");
			}
			ProductEntity product = new ProductEntity();
			product.setUserId(account.getUserId());
			product.setUserLogo(account.getLogo());
			product.setUserName(account.getUserName());
			product.setUsertype(1);
			buyerService.insertProduct(form, product, result);
			if(result.hasErrors())
			{
				return mv;
			}

			JsonUtil.showAlert(response, "新建刀具", "刀具新建成功~~", "确定", "", "");

		}
		return mv;
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
	
	@RequestMapping(value="/delproduct/{id}")
	public void delproduct(HttpServletRequest request, HttpServletResponse response, @PathVariable String id, @ModelAttribute("account")Account account) throws IOException 
	{
		String url = request.getHeader("Referer");
		int pid = Integer.parseInt(id);
		buyerService.delProduct(pid);
		//todo:account.setSampleCnt(buyerService.getSampleCount(account.getUserId()));
		response.sendRedirect(url);
	}
}
