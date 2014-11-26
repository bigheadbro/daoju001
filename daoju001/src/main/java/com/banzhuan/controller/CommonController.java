package com.banzhuan.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.WebUtils;

import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipayNotify;
import com.alipay.util.AlipaySubmit;
import com.banzhuan.common.Account;
import com.banzhuan.common.Constant;
import com.banzhuan.common.Result;
import com.banzhuan.entity.AddressEntity;
import com.banzhuan.entity.ArticleEntity;
import com.banzhuan.entity.CategoryEntity;
import com.banzhuan.entity.ComplainEntity;
import com.banzhuan.entity.CuttingToolEntity;
import com.banzhuan.entity.EventEntity;
import com.banzhuan.entity.ItemEntity;
import com.banzhuan.entity.OrderEntity;
import com.banzhuan.entity.ProductEntity;
import com.banzhuan.entity.ProfessionalAnswerEntity;
import com.banzhuan.entity.QuestionEntity;
import com.banzhuan.entity.QuickrequestEntity;
import com.banzhuan.entity.RelationEntity;
import com.banzhuan.entity.SampleEntity;
import com.banzhuan.entity.StatisticsEntity;
import com.banzhuan.entity.StockEntity;
import com.banzhuan.entity.UserEntity;
import com.banzhuan.form.AddressForm;
import com.banzhuan.form.CommentForm;
import com.banzhuan.form.GoodcaseForm;
import com.banzhuan.form.ItemForm;
import com.banzhuan.form.LoginForm;
import com.banzhuan.form.OrderForm;
import com.banzhuan.form.ProductForm;
import com.banzhuan.form.ProfessionalAnswerForm;
import com.banzhuan.form.QuestionForm;
import com.banzhuan.form.RegForm;
import com.banzhuan.form.RelationForm;
import com.banzhuan.form.RequestForm;
import com.banzhuan.service.CommonService;
import com.banzhuan.service.UserService;
import com.banzhuan.service.WeixinService;
import com.banzhuan.util.CuttingToolsConfiguration;
import com.banzhuan.util.JsonUtil;
import com.banzhuan.util.StringUtil;
import com.banzhuan.util.TwoDimensionCode;
import com.banzhuan.util.Util;
import com.cjc.weixinmp.WeixinException;
import com.cjc.weixinmp.bean.Openid;
import com.cjc.weixinmp.bean.Users;
import com.cjc.weixinmp.bean.WeixinmpUser;

@Controller
@RequestMapping("/")
@SessionAttributes({"account"})
public class CommonController extends BaseController{
	private Logger logger = LoggerFactory.getLogger(CommonController.class);
	@Autowired
	@Qualifier("commonService")
	private CommonService commonService;
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	@RequestMapping(value="/downloadfile")
	public void download(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{  
        response.setCharacterEncoding("utf-8");  
        response.setContentType("multipart/form-data");  
        String fileName = request.getParameter("file");
        response.setHeader("Content-Disposition", "attachment;fileName="+java.net.URLEncoder.encode(fileName, "UTF-8"));  
        String path = request.getSession().getServletContext().getRealPath("/resource")+"/"+ fileName;
        try {  
            File file=new File(path);  
            response.setContentLength((int)(long)file.length());
            InputStream inputStream=new FileInputStream(file);  
            OutputStream os=response.getOutputStream();  
            byte[] b=new byte[1024];  
            int length;  
            while((length=inputStream.read(b))>0){  
                os.write(b,0,length);  
            }  
            inputStream.close();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
	
	@RequestMapping(value="/downloadsample")
	public void downloadFile(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{  
        response.setCharacterEncoding("utf-8");  
        response.setContentType("multipart/form-data");  
        String fileName = request.getParameter("file");
        response.setHeader("Content-Disposition", "attachment;fileName="+java.net.URLEncoder.encode(fileName.split("/")[3], "UTF-8"));  
        String path = request.getSession().getServletContext().getRealPath("/sample")+ fileName.substring(9);
        try {  
            File file=new File(path);  
            response.setContentLength((int)(long)file.length());
            InputStream inputStream=new FileInputStream(file);  
            OutputStream os=response.getOutputStream();  
            byte[] b=new byte[1024];  
            int length;  
            while((length=inputStream.read(b))>0){  
                os.write(b,0,length);  
            }  
            inputStream.close();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
	
	@RequestMapping(value="/newrequest")
	public void newrequest(HttpServletRequest request,HttpServletResponse response,@ModelAttribute("form")RequestForm form){  
		commonService.addRequest(form);
    }  
	
	@RequestMapping(value="/downloadgc")
	public void downloadgc(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{  
        response.setCharacterEncoding("utf-8");  
        response.setContentType("multipart/form-data");  
        String fileName = request.getParameter("file");
        response.setHeader("Content-Disposition", "attachment;fileName="+java.net.URLEncoder.encode(fileName.split("/")[3], "UTF-8"));  
        String path = request.getSession().getServletContext().getRealPath("/goodcase")+ fileName.substring(11);
        try {  
            File file=new File(path);  
            response.setContentLength((int)(long)file.length());
            InputStream inputStream=new FileInputStream(file);  
            OutputStream os=response.getOutputStream();  
            byte[] b=new byte[1024];  
            int length;  
            while((length=inputStream.read(b))>0){  
                os.write(b,0,length);  
            }  
            inputStream.close();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
	
	@RequestMapping(value="/getProviderInfo")
	public void getProviderInfo(final HttpServletRequest request,final HttpServletResponse response)
	{
		int userid = Integer.parseInt(request.getParameter("userid"));
		StatisticsEntity st = new StatisticsEntity();
		st.setType(2);
		Account account = (Account) WebUtils.getSessionAttribute(request, "account");
		if(account != null)
		{
			st.setInfo("["+account.getUserName()+","+account.getCompanyName()+","+account.getUserId()+"]查看" + String.valueOf(userid)+"的联系方式");
		}
		else
		{
			st.setInfo("[未登录用户]查看" + String.valueOf(userid)+"的联系方式");
		}
		commonService.addstatistics(st);
	}
	
	@RequestMapping(value="/addvote")
	public void addvote(final HttpServletRequest request,final HttpServletResponse response)
	{
		StatisticsEntity st = new StatisticsEntity();
		st.setType(1);
		st.setInfo("点击进入问卷");
		commonService.addstatistics(st);
	}
	
	@RequestMapping(value="/index")
	public ModelAndView index(final HttpServletRequest request,final HttpServletResponse response)
	{
		ModelAndView mv = new ModelAndView("/common/index4");

		return mv;
	}
	
	/**
	 * 其他未识别的URL都统一到
	 * @return
	 */
	@RequestMapping(value="*")
	public ModelAndView otherEnter(final HttpServletRequest request,final HttpServletResponse response,@ModelAttribute("form")RequestForm form)
	{
		Account account = (Account) WebUtils.getSessionAttribute(request, "account");
		if(account != null)
			commonService.setRequestFormWithAccount(form, account);
		ModelAndView mv = new ModelAndView("/common/index3");
		
		List<QuickrequestEntity> requests = commonService.getMainRequests();
		mv.addObject("requests", requests);
		
		List<ProductEntity> products = new ArrayList<ProductEntity>();
		products.add(commonService.getProduct(150));
		products.add(commonService.getProduct(149));
		products.add(commonService.getProduct(151));
		products.add(commonService.getProduct(148));
		mv.addObject("products", products);
		
		List<QuestionEntity> questions = new ArrayList<QuestionEntity>();
		questions.add(commonService.getQuestion(165));
		questions.add(commonService.getQuestion(161));
		questions.add(commonService.getQuestion(160));
		mv.addObject("questions", questions);

		List<ItemEntity> items = new ArrayList<ItemEntity>();
		items.add(commonService.getItem(48));
		items.add(commonService.getItem(18));
		items.add(commonService.getItem(19));
		items.add(commonService.getItem(22));
		items.add(commonService.getItem(27));
		items.add(commonService.getItem(54));
		mv.addObject("items", items);
		
		return mv;
	}
	
	@RequestMapping(value="/forgetpwd")
	public ModelAndView forgetpwd(final HttpServletRequest request, final HttpServletResponse response, @ModelAttribute("form")LoginForm form, BindingResult result)
	{
		ModelAndView mv = new ModelAndView("/common/forgetpwd");
		if(isDoSubmit(request))
		{
			String rec[] ={form.getMail()};
			String pwd = UUID.randomUUID().toString().substring(0,6);
			String date = StringUtil.getCurrentDate();
			String content = "亲爱的"+form.getMail()+":\n\n欢迎使用刀师傅的找回密码功能，您的新密码是"+pwd+"，请使用该密码登录刀师傅后，在账户设置中修改此密码\n\n请勿回复此邮件\n\n此致\n\n刀师傅敬上\n"+date;
			commonService.changpwd(form.getMail(), pwd, result);
			if(!result.hasErrors())
			{
				Util.sendEmail("noreply@daoshifu.com","cisco123",rec,"找回密码", content, null, "", "UTF-8");
			}
			else
			{
				return mv;
			}
			return new ModelAndView(new RedirectView("/log"));
		}
		return mv;
	}
	
	@RequestMapping(value="/log")
	public ModelAndView log(final HttpServletRequest request,
			final HttpServletResponse response, @ModelAttribute("form")LoginForm form, BindingResult result) throws IOException 
	{
		Account accnt = (Account) WebUtils.getSessionAttribute(request, "account");
		if(accnt != null && accnt.isLogin())
		{
			return new ModelAndView(new RedirectView("/user/main")); 
		}
		ModelAndView mv = new ModelAndView("/common/log");
		if(isDoSubmit(request))
		{
			Result re = commonService.checkLogin(form, result);
			
			if(!result.hasErrors())
			{
				Account account = new Account();
				UserEntity user = (UserEntity)re.get("user");
				int unreadMsgCount = userService.getUnreadMsgCount(user.getId());
				int answerCnt = userService.getAnswerCount(user.getId());
				int sampleCnt = userService.getSampleCount(user.getId());
				int questionCnt = userService.getUserQuestionCount(user.getId());
				account.setLogin(true); // 登录成功标识
				account.setUserName(user.getNick()); // 用户登录名
				account.setCompanyName(user.getCompanyName());
				account.setUserId(user.getId()); // 用户ID
				account.setMail(user.getMail()); // 邮箱
				account.setLogo(user.getLogo()); // logo
				account.setBrandName(user.getBrand());
				account.setAuthority(user.getAuthority());
				account.setVerifiedLink(user.getVerifiedLink());
				account.setUnreadMsgCount(unreadMsgCount);
				account.setSampleCnt(sampleCnt);
				account.setAnwserCnt(answerCnt);
				account.setProductlimit(user.getProductlimit());
				account.setQuestionCnt(questionCnt);
				account.setQq(user.getContactQq());
				account.setPhone(user.getContactPhone());
				account.setArea(user.getPca());
				//set cookie
				if(form.getRememberme() != null && form.getRememberme())
				{
					this.addCookie(response, Constant.LOGIN_MAIL, user.getMail(), Integer.MAX_VALUE);
					this.addCookie(response, Constant.REMEMBER_ME, "1", Integer.MAX_VALUE);
				}
				//设置头像
				account.setLogo(user.getLogo());
				request.getSession().setAttribute("account", account);
				// 登陆成功， 跳转到登陆页面
				return new ModelAndView(new RedirectView("/user/main"));
			}
		}
		return mv;
	}
	
	@RequestMapping(value="/browser")
	public ModelAndView browser(final HttpServletResponse response)
	{
		ModelAndView mv = new ModelAndView("/common/browser");
		
		return mv;
	}

	@RequestMapping(value="/about")
	public ModelAndView us(final HttpServletRequest request,final HttpServletResponse response)
	{
		ModelAndView mv = new ModelAndView("/common/about");
		return mv;
	}
	
	@RequestMapping(value="/articles")
	public ModelAndView articles(final HttpServletRequest request,final HttpServletResponse response)
	{
		ModelAndView mv = new ModelAndView("/common/articles");
		List<ArticleEntity> articles = commonService.getAllarticles();
		mv.addObject("articles",articles);
		return mv;
	}
	
	@RequestMapping(value="/event")
	public ModelAndView event(final HttpServletRequest request,final HttpServletResponse response)
	{
		ModelAndView view = new ModelAndView("/common/event2");
		if(!isDoSubmit(request))
			return view;
		
		EventEntity event = new EventEntity();
		event.setCompany(String.valueOf(request.getParameter("company")));
		event.setEventid(2);
		event.setMail(String.valueOf(request.getParameter("mail")));
		event.setAddress(String.valueOf(request.getParameter("address")));
		event.setName(String.valueOf(request.getParameter("name")));
		event.setPhone(String.valueOf(request.getParameter("phone")));
		event.setType(Integer.valueOf(request.getParameter("type")));
		event.setNote(String.valueOf(request.getParameter("note")));
		if(commonService.insertEvent(event) > 0)
		{
			JsonUtil.showAlert(response, "申请成功", "我们的工作人员会尽快联系您。", "逛逛刀师傅", "http://www.daoshifu.com", "");
		}
		else
		{
			JsonUtil.showAlert(response, "申请失败", "如有任何问题，请联系我们。", "确定", "", "");
		}
		return view;

	}
	
	
	@RequestMapping(value = "/questions")
	public ModelAndView allquestion(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("form")QuestionForm form, 
			@ModelAttribute("answerForm")ProfessionalAnswerForm answerForm) 
	{
		ModelAndView mv = new ModelAndView("/common/questions");
		
		int page = 1;
		if(request.getParameter("page") != null)
		{
			page = Integer.valueOf(request.getParameter("page"));
		}
		
		Result result = commonService.getAllquestions(form,new RowBounds((page-1)*Constant.ALL_QUESTION_PAGE_SIZE, Constant.ALL_QUESTION_PAGE_SIZE));
		mv.addObject("questions", result.get("questions"));
		int total= commonService.getAllquestionsCount(form);
		int totalPage=0;
		if(total % Constant.ALL_QUESTION_PAGE_SIZE == 0)
			totalPage=total/Constant.ALL_QUESTION_PAGE_SIZE;
		else
			totalPage=total/Constant.ALL_QUESTION_PAGE_SIZE+1;
		totalPage=totalPage==0?1:totalPage;
		mv.addObject("page", page);
		mv.addObject("total", total);
		mv.addObject("totalPage", totalPage);

		result = commonService.getMainagents();
		mv.addObject("agents", result.get("agents"));
		return mv;
		
	}
	
	@RequestMapping(value = "/products/{id}")
	public ModelAndView products(final HttpServletRequest request,final HttpServletResponse response, @PathVariable String id) 
	{
		ModelAndView mv = new ModelAndView("/common/product");
		int productid = Integer.parseInt(id);
		ProductEntity product = commonService.getProduct(productid);
		if(product == null)
		{
			return new ModelAndView(new RedirectView("/products"));  
		}
		commonService.addProductCount(product.getId(), product.getCount() + 1);
		mv.addObject("product", product);
		String[] str = product.getPicture().substring(1).split("[|]");
		UserEntity user = commonService.getUser(product.getUserId());
		mv.addObject("pictures",str);
		mv.addObject("user",user);
		String path = request.getSession().getServletContext().getRealPath("/qrcode");
		String qrcode = Util.genRandomName("") + ".png";
		TwoDimensionCode.encoderQRCode("http://www.daoshifu.com/wxproducts/" + id,path +"/"+ qrcode);
		mv.addObject("qrcode",qrcode);
		return mv;
	}
	
	
	@RequestMapping(value = "/updaterequest")
	public void updaterequest(final HttpServletRequest request,final HttpServletResponse response)
	{
		int rid = Integer.parseInt(request.getParameter("rid"));
		commonService.updateRequests(rid);
	}
	
	@RequestMapping(value = "/requests")
	public ModelAndView requests(final HttpServletRequest request,final HttpServletResponse response,@ModelAttribute("form")RequestForm form)
	{
		int type = 0;
		if(StringUtil.isNotEmpty(request.getParameter("type")))
		{
			type = Integer.parseInt(request.getParameter("type"));
		}
		
		ModelAndView mv = new ModelAndView("/common/requests");
		int page = 1;
		if(request.getParameter("page") != null)
		{
			page = Integer.valueOf(request.getParameter("page"));
		}
		
		List<QuickrequestEntity> requests = commonService.getAllRequests(type,new RowBounds((page-1)*Constant.ALL_REQUEST_PAGE_SIZE, Constant.ALL_REQUEST_PAGE_SIZE));

		int total= commonService.getAllRequestsCount(type);
		int totalPage=0;
		if(total % Constant.ALL_REQUEST_PAGE_SIZE == 0)
			totalPage=total/Constant.ALL_REQUEST_PAGE_SIZE;
		else
			totalPage=total/Constant.ALL_REQUEST_PAGE_SIZE+1;
		totalPage=totalPage==0?1:totalPage;
		mv.addObject("page", page);
		mv.addObject("total", total);
		mv.addObject("totalPage", totalPage);
		mv.addObject("requests", requests);
		List<ItemEntity> items = commonService.getMainItems();
		mv.addObject("items", items);
		return mv;
	}
	
	@RequestMapping(value = "/items")
	public ModelAndView items(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("form")ItemForm form) 
	{
		ModelAndView mv = new ModelAndView("/common/items");
		ItemEntity item = new ItemEntity();
		List<ItemEntity> items = commonService.getItems(form,item);
		Set<String> detailtypes = new LinkedHashSet<String>();
		Set<String> materials = new LinkedHashSet<String>();
		Set<String> workmaterials = new LinkedHashSet<String>();
		Set<String> brands = new LinkedHashSet<String>();
		for(int i = 0;i<items.size();i++)
		{
			detailtypes.add(items.get(i).getDetailtype());
			materials.add(items.get(i).getMaterial());
			workmaterials.add(items.get(i).getWorkmaterial());
			brands.add(items.get(i).getBrand());
		}
		
		mv.addObject("items", items);
		mv.addObject("detailtypes", detailtypes);
		mv.addObject("materials", materials);
		mv.addObject("workmaterials", workmaterials);
		mv.addObject("brands", brands);
		return mv;
		
	}
	
	@RequestMapping(value = "/item/{id}")
	public ModelAndView item(final HttpServletRequest request,final HttpServletResponse response, @PathVariable String id) 
	{
		ModelAndView mv = new ModelAndView("/common/item");
		int itemid = Integer.parseInt(id);
		ItemEntity item = commonService.getItem(itemid);
		mv.addObject("item", item);
		String[] str = item.getPicture().substring(1).split("[|]");
		mv.addObject("pictures",str);
		return mv;
		
	}
	
	@RequestMapping(value = "/test")
	public ModelAndView test(final HttpServletRequest request,final HttpServletResponse response)
	{
		ModelAndView mv = new ModelAndView("/common/purchase_return");
		return mv;
	}
	
	@RequestMapping(value = "/purchase_return")
	public ModelAndView purchase_return(final HttpServletRequest request,final HttpServletResponse response)
	{
		ModelAndView mv = new ModelAndView("/common/purchase_return");
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			valueStr = new String(valueStr);
			params.put(name, valueStr);
		}
		
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		//商户订单号
	
		String out_trade_no = new String(request.getParameter("out_trade_no"));
	
		//支付宝交易号
	
		String trade_no = new String(request.getParameter("trade_no"));
	
		//交易状态
		String trade_status = new String(request.getParameter("trade_status"));
	
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
		
		//计算得出通知验证结果
		boolean verify_result = AlipayNotify.verify(params);
		
		if(verify_result){//验证成功
			//////////////////////////////////////////////////////////////////////////////////////////
			//请在这里加上商户的业务逻辑程序代码
			
			//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
			
			if(trade_status.equals("WAIT_SELLER_SEND_GOODS")){
				//判断该笔订单是否在商户网站中已经做过处理
				//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				//如果有做过处理，不执行商户的业务程序
				OrderEntity order = commonService.getOrder(Integer.parseInt(out_trade_no.substring(3)));
				order.setState(2);
				order.setGmtPay(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				commonService.updateOrder(order);
				mv.addObject("orderid",out_trade_no);
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
				mv.addObject("timeSubmit",order.getGmtSubmitOrder());
				mv.addObject("timePay",order.getGmtPay());
				mv.addObject("state",2);
				return mv;
			}
			
			//该页面可做页面美工编辑
			System.out.println("验证成功<br />");
			
			//——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
	
			//////////////////////////////////////////////////////////////////////////////////////////
		}else{
			//该页面可做页面美工编辑
			System.out.println("验证失败");
		}
		return mv;
	}
	
	@RequestMapping(value = "/purchase")
	public ModelAndView purchase(HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("form")AddressForm form)
	{
		ModelAndView mv = new ModelAndView("/common/purchase");
		Account account = (Account) WebUtils.getSessionAttribute(request, "account");
		List<AddressEntity> addresses = commonService.getAddresses(account.getUserId(), 0);
		String name = request.getParameter("itemname");
		String cover = request.getParameter("cover");
		String itemid = request.getParameter("itemid");
		double price = Double.parseDouble(request.getParameter("price"));
		int quatity = Integer.parseInt(request.getParameter("quatity"));
		boolean hasdefault = false;
		for(int i = 0;i<addresses.size();i++)
		{
			if(addresses.get(i).getDefaulte())
			{
				hasdefault = true;
				break;
			}
		}
		mv.addObject("hasdefault", hasdefault);
		mv.addObject("addresses", addresses);
		mv.addObject("itemName", name);
		mv.addObject("price", price);
		mv.addObject("quatity", quatity);
		mv.addObject("total", price*quatity);
		mv.addObject("cover", cover);
		mv.addObject("itemid", itemid);
		return mv;
	}
	
	@RequestMapping(value = "/payorder")
	public ModelAndView payorder(HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("form")OrderForm form, Model model)
	{
		ModelAndView mv = new ModelAndView("/common/payorder");
		Account account = (Account) WebUtils.getSessionAttribute(request, "account");

		//插入订单
		OrderEntity order = new OrderEntity();
		order.setItemAddr(form.getItemAddr());
		order.setItemid(form.getItemid());
		order.setPrice(form.getPrice());
		order.setState(1);
		order.setUserid(account.getUserId());
		order.setAddressid(form.getAddressid());
		order.setQuantity(form.getQuantity());
		order.setItemAddr("http://www.daoshifu.com/item/"+request.getParameter("itemid"));
		int id = commonService.insertOrder(order);
		
		form.setItemAddr("http://www.daoshifu.com/item/"+request.getParameter("itemid"));
		form.setItemid(Integer.parseInt(request.getParameter("itemid")));
		form.setPrice(Double.parseDouble(request.getParameter("price")));
		form.setState(1);
		form.setUserid(account.getUserId());
		form.setAddressid(Integer.parseInt(request.getParameter("addressid")));
		form.setQuantity(Integer.parseInt(request.getParameter("quantity")));
		form.setId(id);
		form.setTradeNumber("DSF" + String.valueOf(id));
		mv.addObject("price",form.getPrice());
		mv.addObject("number","DSF" + String.valueOf(id));
		return mv;
	}
	
	@RequestMapping(value = "/notify_url")
	public void notify_url(final HttpServletRequest request,final HttpServletResponse response)
	{
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		//商户订单号

		String out_trade_no = new String(request.getParameter("out_trade_no"));

		//支付宝交易号

		String trade_no = new String(request.getParameter("trade_no"));

		//交易状态
		String trade_status = new String(request.getParameter("trade_status"));
		
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

		if(AlipayNotify.verify(params)){//验证成功
			//////////////////////////////////////////////////////////////////////////////////////////
			//请在这里加上商户的业务逻辑程序代码
			//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
			if(trade_status.equals("WAIT_BUYER_PAY")){
				//该判断表示买家已在支付宝交易管理中产生了交易记录，但没有付款
				//判断该笔订单是否在商户网站中已经做过处理
				//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				//如果有做过处理，不执行商户的业务程序

				//price
				String price = new String(request.getParameter("total_fee"));
				OrderEntity order = commonService.getOrder(Integer.parseInt(out_trade_no.substring(3)));
				if(price != null)
				{
					order.setPrice(Double.parseDouble(price));
				}
				if(commonService.updateOrder(order) > 0)
				{
					logger.error("WAIT_BUYER_PAY, Update orders successfully, order number is" + out_trade_no);
				}
			} else if(trade_status.equals("WAIT_SELLER_SEND_GOODS")){
				//该判断表示买家已在支付宝交易管理中产生了交易记录且付款成功，但卖家没有发货
				//判断该笔订单是否在商户网站中已经做过处理
				//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				//如果有做过处理，不执行商户的业务程序
				
				OrderEntity order = commonService.getOrder(Integer.parseInt(out_trade_no.substring(3)));
				logger.error("WAIT_SELLER_SEND_GOODS,state:"+String.valueOf(order.getState()));
				if(order.getState() < 2)
				{
					order.setState(2);
					order.setGmtPay(StringUtil.getCurrentTime());
					if(commonService.updateOrder(order) > 0)
					{
						logger.error("WAIT_SELLER_SEND_GOODS, Update orders successfully, order number is" + out_trade_no);
					}
				}
			} else if(trade_status.equals("WAIT_BUYER_CONFIRM_GOODS")){
				//该判断表示卖家已经发了货，但买家还没有做确认收货的操作
				//判断该笔订单是否在商户网站中已经做过处理
				//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				//如果有做过处理，不执行商户的业务程序

				OrderEntity order = commonService.getOrder(Integer.parseInt(out_trade_no.substring(3)));
				logger.error("WAIT_BUYER_CONFIRM_GOODS, state:"+String.valueOf(order.getState()));
				if(order.getState() < 3)
				{
					order.setState(3);
					order.setGmtSell(StringUtil.getCurrentTime());
					logger.error("before");
					if(commonService.updateOrder(order) > 0)
					{
						logger.error("WAIT_BUYER_CONFIRM_GOODS, Update orders successfully, order number is" + out_trade_no);
					}
					logger.error("after");
				}
			} else if(trade_status.equals("TRADE_FINISHED")){
				//该判断表示买家已经确认收货，这笔交易完成
				//判断该笔订单是否在商户网站中已经做过处理
				//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				//如果有做过处理，不执行商户的业务程序
				
				OrderEntity order = commonService.getOrder(Integer.parseInt(out_trade_no.substring(3)));
				if(order.getState() < 4)
				{
					order.setState(4);
					order.setGmtAssure(StringUtil.getCurrentTime());
					if(commonService.updateOrder(order) > 0)
					{
						logger.error("TRADE_FINISHED,Update orders successfully, order number is" + out_trade_no);
					}
				}
			}
			else {
				//out.println("success");	//请不要修改或删除
			}

			//——请根据您的业务逻辑来编写程序（以上代码仅作参考）——

			//////////////////////////////////////////////////////////////////////////////////////////
		}else{//验证失败
			//out.println("fail");
		}
	}
	
	@RequestMapping(value = "/purchase_handler")
	public ModelAndView purchase_handler(final HttpServletRequest request,final HttpServletResponse response, Model model, @ModelAttribute("form")OrderForm form)
	{
		ModelAndView mv = new ModelAndView("/common/purchase_handler");

		Account account = (Account) WebUtils.getSessionAttribute(request, "account");

		OrderEntity order = new OrderEntity();
		order.setItemAddr(form.getItemAddr());
		order.setItemid(form.getItemid());
		order.setPrice(form.getPrice());
		order.setState(1);
		order.setUserid(account.getUserId());
		order.setAddressid(form.getAddressid());
		order.setQuantity(form.getQuantity());
		order.setType(form.getType());
		order.setId(form.getId());
		
		if(form.getType() == 2)
		{
			commonService.updateOrder(order);
			return new ModelAndView(new RedirectView("/user/order/"+order.getId()));  
		}
		else if(form.getType() == 3)
		{
			order.setState(2);
			order.setGmtPay(StringUtil.getCurrentTime());
			commonService.updateOrder(order);
			return new ModelAndView(new RedirectView("/user/order/"+order.getId()));  
		}
		AddressEntity addr = commonService.getAddressById(form.getAddressid());
		ItemEntity item = commonService.getItem(form.getItemid());
		//支付类型
		String payment_type = "1";
		//必填，不能修改
		//服务器异步通知页面路径
		String notify_url = "http://www.daoshifu.com/notify_url";
		//需http://格式的完整路径，不能加?id=123这类自定义参数

		//页面跳转同步通知页面路径
		String return_url = "http://www.daoshifu.com/purchase_return";
		//需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/

		//卖家支付宝帐户
		String seller_email = new String("salyncious@aliyun.com");
		//必填
		
		//商户订单号
		String out_trade_no = form.getTradeNumber();
		//商户网站订单系统中唯一订单号，必填

		//订单名称
		String subject = new String(item.getBrand()+item.getType()+item.getVersion());
		//必填

		//付款金额
		//String price = new String("0.01");
		String price = new String(String.valueOf(order.getPrice()));
		//必填

		//商品数量
		String quantity = "1";
		//必填，建议默认为1，不改变值，把一次交易看成是一次下订单而非购买一件商品
		//物流费用
		String logistics_fee = "0.00";
		//必填，即运费
		//物流类型
		String logistics_type = "EXPRESS";
		//必填，三个值可选：EXPRESS（快递）、POST（平邮）、EMS（EMS）
		//物流支付方式
		String logistics_payment = "SELLER_PAY";
		//必填，两个值可选：SELLER_PAY（卖家承担运费）、BUYER_PAY（买家承担运费）
		//订单描述

		String body = new String("no description");
		//商品展示地址
		String show_url = new String("http://www.daoshifu.com/item/"+ String.valueOf(order.getItemid()));
		//需以http://开头的完整路径，如：http://www.xxx.com/myorder.html

		//收货人姓名
		String receive_name = new String(addr.getName());
		//如：张三

		//收货人地址
		String receive_address = new String(addr.getAddr());
		//如：XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号

		//收货人邮编
		String receive_zip = new String(addr.getZip());
		//如：123456

		//收货人电话号码
		String receive_phone = new String("");
		//如：0571-88158090

		//收货人手机号码
		String receive_mobile = new String(addr.getPhone());
		//如：13312341234
		
		
		//////////////////////////////////////////////////////////////////////////////////
		
		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "create_partner_trade_by_buyer");
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("payment_type", payment_type);
		sParaTemp.put("notify_url", notify_url);
		sParaTemp.put("return_url", return_url);
		sParaTemp.put("seller_email", seller_email);
		sParaTemp.put("out_trade_no", out_trade_no);
		sParaTemp.put("subject", subject);
		sParaTemp.put("price", price);
		sParaTemp.put("quantity", quantity);
		sParaTemp.put("logistics_fee", logistics_fee);
		sParaTemp.put("logistics_type", logistics_type);
		sParaTemp.put("logistics_payment", logistics_payment);
		sParaTemp.put("body", body);
		sParaTemp.put("show_url", show_url);
		sParaTemp.put("receive_name", receive_name);
		sParaTemp.put("receive_address", receive_address);
		sParaTemp.put("receive_zip", receive_zip);
		sParaTemp.put("receive_phone", receive_phone);
		sParaTemp.put("receive_mobile", receive_mobile);
		 
		//建立请求
		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp,"get","确认");
		model.addAttribute(sHtmlText);
		mv.addObject("alipay",sHtmlText);
		return mv;		
	}
	
	@RequestMapping(value = "/membership_handler")
	public ModelAndView membership_handler(final HttpServletRequest request,final HttpServletResponse response, Model model)
	{
		ModelAndView mv = new ModelAndView("/common/purchase_handler");

		Account account = (Account) WebUtils.getSessionAttribute(request, "account");
		
		//支付类型
		String payment_type = "1";
		//必填，不能修改
		//服务器异步通知页面路径
		String notify_url = "http://www.daoshifu.com/notify_url";
		//需http://格式的完整路径，不能加?id=123这类自定义参数

		//页面跳转同步通知页面路径
		String return_url = "http://www.daoshifu.com/purchase_return";
		//需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/

		//卖家支付宝帐户
		String seller_email = new String("salyncious@aliyun.com");
		//必填

		OrderEntity order = new OrderEntity();
		order.setItemAddr("http://www.daoshifu.com/membership");
		if(Integer.parseInt(request.getParameter("mid")) == 1)
		{
			order.setItemid(1);
		}
		else
		{
			order.setItemid(2);
		}
		order.setPrice(Double.parseDouble(request.getParameter("price")));
		order.setState(1);
		order.setUserid(account.getUserId());
		order.setAddressid(1);
		order.setQuantity(1);
		int id = commonService.insertOrder(order);
		//商户订单号
		String out_trade_no = "DSF" + String.valueOf(id);
		//商户网站订单系统中唯一订单号，必填

		//订单名称
		String subject = new String("会员年费");
		//必填

		//付款金额
		//String price = new String("0.01");
		String price = new String(request.getParameter("price"));
		//必填

		//商品数量
		String quantity = "1";
		//必填，建议默认为1，不改变值，把一次交易看成是一次下订单而非购买一件商品
		//物流费用
		String logistics_fee = "0.00";
		//必填，即运费
		//物流类型
		String logistics_type = "EXPRESS";
		//必填，三个值可选：EXPRESS（快递）、POST（平邮）、EMS（EMS）
		//物流支付方式
		String logistics_payment = "SELLER_PAY";
		//必填，两个值可选：SELLER_PAY（卖家承担运费）、BUYER_PAY（买家承担运费）
		//订单描述

		String body = new String("no description");
		//商品展示地址
		String show_url = new String("http://www.daoshifu.com/membership");
		//需以http://开头的完整路径，如：http://www.xxx.com/myorder.html

		//收货人姓名
		String receive_name = new String(account.getUserName());
		//如：张三

		//收货人地址
		String receive_address = new String("");
		//如：XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号

		//收货人邮编
		String receive_zip = new String("");
		//如：123456

		//收货人电话号码
		String receive_phone = new String("");
		//如：0571-88158090

		//收货人手机号码
		String receive_mobile = new String("");
		//如：13312341234
		
		
		//////////////////////////////////////////////////////////////////////////////////
		
		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "create_partner_trade_by_buyer");
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("payment_type", payment_type);
		sParaTemp.put("notify_url", notify_url);
		sParaTemp.put("return_url", return_url);
		sParaTemp.put("seller_email", seller_email);
		sParaTemp.put("out_trade_no", out_trade_no);
		sParaTemp.put("subject", subject);
		sParaTemp.put("price", price);
		sParaTemp.put("quantity", quantity);
		sParaTemp.put("logistics_fee", logistics_fee);
		sParaTemp.put("logistics_type", logistics_type);
		sParaTemp.put("logistics_payment", logistics_payment);
		sParaTemp.put("body", body);
		sParaTemp.put("show_url", show_url);
		sParaTemp.put("receive_name", receive_name);
		sParaTemp.put("receive_address", receive_address);
		sParaTemp.put("receive_zip", receive_zip);
		sParaTemp.put("receive_phone", receive_phone);
		sParaTemp.put("receive_mobile", receive_mobile);
		 
		//建立请求
		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp,"get","确认");
		model.addAttribute(sHtmlText);
		mv.addObject("alipay",sHtmlText);
		return mv;		
	}
	
	@RequestMapping(value = "/products")
	public ModelAndView products(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("form")ProductForm form) 
	{
		ModelAndView mv = new ModelAndView("/common/products");
		int page = 1;
		if(request.getParameter("page") != null)
		{
			page = Integer.valueOf(request.getParameter("page"));
		}
		
		Result result = commonService.getAllproducts(form,new RowBounds((page-1)*Constant.ALL_PRODUCT_PAGE_SIZE, Constant.ALL_PRODUCT_PAGE_SIZE));
	
		int total= commonService.getProductsCount();
		int totalPage=0;
		if(total % Constant.ALL_PRODUCT_PAGE_SIZE == 0)
			totalPage=total/Constant.ALL_PRODUCT_PAGE_SIZE;
		else
			totalPage=total/Constant.ALL_PRODUCT_PAGE_SIZE+1;
		totalPage=totalPage==0?1:totalPage;
		mv.addObject("page", page);
		mv.addObject("total", total);
		mv.addObject("totalPage", totalPage);
		mv.addObject("products", result.get("products"));
		result = commonService.getMainquestions();
		mv.addObject("questions", result.get("questions"));
		
	
		return mv;
		
	}

	@RequestMapping(value = "/membership_handler/{id}")
	public ModelAndView membership_handler(final HttpServletRequest request,final HttpServletResponse response, @PathVariable String id, Model model)
	{
		ModelAndView mv = new ModelAndView("/common/purchase_handler");
		int orderid = Integer.parseInt(id);
		Account account = (Account) WebUtils.getSessionAttribute(request, "account");
		
		//支付类型
		String payment_type = "1";
		//必填，不能修改
		//服务器异步通知页面路径
		String notify_url = "http://www.daoshifu.com/notify_url";
		//需http://格式的完整路径，不能加?id=123这类自定义参数

		//页面跳转同步通知页面路径
		String return_url = "http://www.daoshifu.com/purchase_return";
		//需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/

		//卖家支付宝帐户
		String seller_email = new String("salyncious@aliyun.com");
		//必填

		OrderEntity order = commonService.getOrder(orderid);
		//商户订单号
		String out_trade_no = "DSF" + String.valueOf(order.getId());
		//商户网站订单系统中唯一订单号，必填

		//订单名称
		String subject = new String("会员年费");
		//必填

		//付款金额
		//String price = new String("0.01");
		String price = String.valueOf(order.getPrice());
		//必填

		//商品数量
		String quantity = "1";
		//必填，建议默认为1，不改变值，把一次交易看成是一次下订单而非购买一件商品
		//物流费用
		String logistics_fee = "0.00";
		//必填，即运费
		//物流类型
		String logistics_type = "EXPRESS";
		//必填，三个值可选：EXPRESS（快递）、POST（平邮）、EMS（EMS）
		//物流支付方式
		String logistics_payment = "SELLER_PAY";
		//必填，两个值可选：SELLER_PAY（卖家承担运费）、BUYER_PAY（买家承担运费）
		//订单描述

		String body = new String("no description");
		//商品展示地址
		String show_url = new String("http://www.daoshifu.com/membership");
		//需以http://开头的完整路径，如：http://www.xxx.com/myorder.html

		//收货人姓名
		String receive_name = new String(account.getUserName());
		//如：张三

		//收货人地址
		String receive_address = new String("");
		//如：XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号

		//收货人邮编
		String receive_zip = new String("");
		//如：123456

		//收货人电话号码
		String receive_phone = new String("");
		//如：0571-88158090

		//收货人手机号码
		String receive_mobile = new String("");
		//如：13312341234
		
		
		//////////////////////////////////////////////////////////////////////////////////
		
		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "create_partner_trade_by_buyer");
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("payment_type", payment_type);
		sParaTemp.put("notify_url", notify_url);
		sParaTemp.put("return_url", return_url);
		sParaTemp.put("seller_email", seller_email);
		sParaTemp.put("out_trade_no", out_trade_no);
		sParaTemp.put("subject", subject);
		sParaTemp.put("price", price);
		sParaTemp.put("quantity", quantity);
		sParaTemp.put("logistics_fee", logistics_fee);
		sParaTemp.put("logistics_type", logistics_type);
		sParaTemp.put("logistics_payment", logistics_payment);
		sParaTemp.put("body", body);
		sParaTemp.put("show_url", show_url);
		sParaTemp.put("receive_name", receive_name);
		sParaTemp.put("receive_address", receive_address);
		sParaTemp.put("receive_zip", receive_zip);
		sParaTemp.put("receive_phone", receive_phone);
		sParaTemp.put("receive_mobile", receive_mobile);
		 
		//建立请求
		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp,"get","确认");
		model.addAttribute(sHtmlText);
		mv.addObject("alipay",sHtmlText);
		return mv;		
	}
	
	@RequestMapping(value = "/cancelorder/{id}")
	public void cancelorder(final HttpServletRequest request,final HttpServletResponse response, @PathVariable String id,Model model)
	{
		int orderid = Integer.parseInt(id);
		OrderEntity order = commonService.getOrder(orderid);
		order.setState(5);
		order.setGmtCancel(StringUtil.getCurrentTime());
		commonService.updateOrder(order);
		
	}
	
	@RequestMapping(value="/assureorder/{id}")
	public void assureorder(HttpServletRequest request, HttpServletResponse response, @PathVariable String id)
	{
		int orderid = Integer.parseInt(id);
		OrderEntity order = commonService.getOrder(orderid);
		order.setState(4);
		order.setGmtAssure(StringUtil.getCurrentTime());
		commonService.updateOrder(order);
	}
	
	@RequestMapping(value = "/purchase_handler/{id}")
	public ModelAndView purchase_handler_by_id(final HttpServletRequest request,final HttpServletResponse response, @PathVariable String id,Model model, @ModelAttribute("form")OrderForm form)
	{
		ModelAndView mv = new ModelAndView("/common/payorder");
		int orderid = Integer.parseInt(id);
		Account account = (Account) WebUtils.getSessionAttribute(request, "account");

		OrderEntity order = commonService.getOrder(orderid);

		form.setItemAddr("http://www.daoshifu.com/item/"+order.getItemid());
		form.setItemid(order.getItemid());
		form.setPrice(order.getPrice());
		form.setState(1);
		form.setUserid(account.getUserId());
		form.setAddressid(order.getAddressid());
		form.setQuantity(order.getQuantity());
		form.setId(orderid);
		form.setTradeNumber("DSF" + String.valueOf(orderid));
		mv.addObject("price",form.getPrice());
		mv.addObject("number","DSF" + String.valueOf(orderid));
		return mv;	
	}
	
	@RequestMapping(value = "questions/{qid}")
	public ModelAndView question(final HttpServletRequest request,final HttpServletResponse response, @PathVariable String qid,
			@ModelAttribute("answerForm")ProfessionalAnswerForm answerForm, @ModelAttribute("form")CommentForm form)
	{
		ModelAndView mv = new ModelAndView("/common/question");
		Result result = new Result();
		int questionid = Integer.parseInt(qid);
		result = userService.queryQuestionById(questionid);
		if(result.get("question") == null)
		{
			return new ModelAndView(new RedirectView("/questions")); 
		}
		mv.addObject("question", result.get("question"));
		
		result = userService.queryAnswersByQid(questionid);
		mv.addObject("answers", result.get("answers"));
		
		result = commonService.getMaingoodcases();
		mv.addObject("goodcases", result.get("goodcases"));
		
		result = commonService.getCommentsInQuesByPid(questionid);
		mv.addObject("commentsCnt", result.get("commentsCnt"));
		mv.addObject("comments", result.get("comments"));
		
		return mv;
		
	}
	
	@RequestMapping(value="/addcomment")
	public void addcomment(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("form")CommentForm form, BindingResult result) 
	{
		if(isDoSubmit(request))
		{
			if(form.getContent() == "")
			{
				JsonUtil.showAlert(response, "回复内容不能为空", "", "确定", "", "");
				return;
			}
			Account account = (Account) WebUtils.getSessionAttribute(request, "account");
			if(!StringUtil.isNotEmpty(account.getVerifiedLink()))
			{
				form.setVerifiedLink(null);
			}
			int ret = commonService.insertComment(form);
			if(ret >= 0)
			{
				Date now = new Date();
				SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				String aa = time.format(now);
				JsonUtil.sendComment(response, form.getContent(), form.getUserName(), form.getUserLogo(), StringUtil.getBrand(form.getBrandName()), form.getVerifiedLink(), StringUtil.formatDate(aa),ret);
			}
		}
		return; 
	}
	
	@RequestMapping(value="/setread")
	public void setread(HttpServletRequest request, HttpServletResponse response) 
	{
		int msgid = Integer.parseInt(request.getParameter("msgid"));
		commonService.setMsgAsRead(msgid);
		Account account = (Account) WebUtils.getSessionAttribute(request, "account");
		if(account.isAgent())
		{
			
			int unreadMsgCount = userService.getUnreadMsgCount(account.getUserId());
			account.setUnreadMsgCount(unreadMsgCount);
		}
		else
		{
			int unreadMsgCount = userService.getUnreadMsgCount(account.getUserId());
			account.setUnreadMsgCount(unreadMsgCount);
		}
		
		JsonUtil.sendImg(response, "");
	}
	
	@RequestMapping(value = "goodcases")
	public ModelAndView allcase(final HttpServletRequest request,final HttpServletResponse response, @ModelAttribute("form")GoodcaseForm form) {
		ModelAndView mv = new ModelAndView("/common/goodcases");

		int page = 1;
		if(request.getParameter("page") != null)
		{
			page = Integer.valueOf(request.getParameter("page"));
		}
		
		Result result = commonService.getAllGoodcases(form,new RowBounds((page-1)*Constant.ALL_GOOD_CASE_PAGE_SIZE, Constant.ALL_GOOD_CASE_PAGE_SIZE));
		mv.addObject("goodcases", result.get("goodcases"));
		int total= commonService.getGoodcaseCountByType(form);
		int totalPage=0;
		if(total % Constant.ALL_GOOD_CASE_PAGE_SIZE == 0)
			totalPage=total/Constant.ALL_GOOD_CASE_PAGE_SIZE;
		else
			totalPage=total/Constant.ALL_GOOD_CASE_PAGE_SIZE+1;
		totalPage=totalPage==0?1:totalPage;
		mv.addObject("page", page);
		mv.addObject("total", total);
		mv.addObject("totalPage", totalPage);
		return mv;
		
	}
	
	@RequestMapping(value = "agents")
	public ModelAndView allagents(final HttpServletRequest request,final HttpServletResponse response)
	{
		ModelAndView mv = new ModelAndView("/common/agents");
		
		Map<Integer,Map<Integer,List<UserEntity>>> agentMap = commonService.getAllAgents();

		//对key进行排序--字母
		/*List<Map.Entry<Integer,List<AgentEntity>>> infoIds = new ArrayList<Map.Entry<Integer,List<AgentEntity>>>(agentMap.entrySet());
		Collections.sort(infoIds, new Comparator<Map.Entry<Integer,List<AgentEntity>>>() {   
		    public int compare(Map.Entry<Integer,List<AgentEntity>> o1, Map.Entry<Integer,List<AgentEntity>> o2) {      
		        return (o1.getKey()-o2.getKey());
		    }
		}); */
		List<Map.Entry<Integer,Map<Integer,List<UserEntity>>>> infoIds = new ArrayList<Map.Entry<Integer,Map<Integer,List<UserEntity>>>>(agentMap.entrySet());
		Collections.sort(infoIds, new Comparator<Map.Entry<Integer,Map<Integer,List<UserEntity>>>>() {   
		    public int compare(Map.Entry<Integer,Map<Integer,List<UserEntity>>> o1, Map.Entry<Integer,Map<Integer,List<UserEntity>>> o2) {      
		        return (o1.getKey()-o2.getKey());
		    }
		}); 

		mv.addObject("agentMap", infoIds);
		
		return mv;
		
	}
	
	@RequestMapping(value = "samples")
	public ModelAndView allsamples(final HttpServletRequest request,final HttpServletResponse response)
	{
		ModelAndView mv = new ModelAndView("/common/samples");
		
		Map<Integer,Map<Integer,Map<Integer,List<SampleEntity>>>> sampleMap = commonService.getAllSamples();
		//对key进行排序--字母
		List<Map.Entry<Integer,Map<Integer,Map<Integer,List<SampleEntity>>>>> infoIds = new ArrayList<Map.Entry<Integer,Map<Integer,Map<Integer,List<SampleEntity>>>>>(sampleMap.entrySet());
		Collections.sort(infoIds, new Comparator<Map.Entry<Integer,Map<Integer,Map<Integer,List<SampleEntity>>>>>() {   
		    public int compare(Map.Entry<Integer,Map<Integer,Map<Integer,List<SampleEntity>>>> o1, Map.Entry<Integer,Map<Integer,Map<Integer,List<SampleEntity>>>> o2) {      
		        return (o1.getKey()-o2.getKey());
		    }
		}); 
		
		mv.addObject("sampleMap", infoIds);
		
		return mv;
		
	}
	
	@RequestMapping(value = "agents/{aid}")
	public ModelAndView agent(final HttpServletRequest request,final HttpServletResponse response, @PathVariable String aid)
	{
		ModelAndView mv = new ModelAndView("/common/agent");
		Result result = new Result();
		int userid = Integer.parseInt(aid);
		//add agent
		result = userService.getUserEntity(userid);
		if(result.get("user") == null)
		{
			return new ModelAndView(new RedirectView("/agents")); 
		}
		mv.addObject("agent", result.get("user"));
		
		result = userService.querySamplesByUserid(userid, new RowBounds(0, Integer.MAX_VALUE));
		mv.addObject("samples", result.get("samples"));
		return mv;
		
	}
	
	@RequestMapping(value = "users/{aid}")
	public ModelAndView buyer(final HttpServletRequest request,final HttpServletResponse response, @PathVariable String aid)
	{
		ModelAndView mv = new ModelAndView("/common/buyer");
		int userid = Integer.parseInt(aid);
		//add agent
		UserEntity buyer = (UserEntity)userService.getUserEntity(userid).get("user");
		if(buyer == null)
		{
			return new ModelAndView(new RedirectView("/index")); 
		}
		if(buyer.getAuthority() == 4)
		{
			return new ModelAndView(new RedirectView("/agents/"+aid));  
		}
		mv.addObject("buyer", buyer);
		return mv;
		
	}
	
	@RequestMapping(value="/ask")
	public void ask(HttpServletRequest request, HttpServletResponse response) 
	{
		Account account = (Account) WebUtils.getSessionAttribute(request, "account");
		if(account == null)
		{
			JsonUtil.checkAskStatus(response, 1);
			return;
		}
		else
		{
			if(account.isLogin())
			{

				JsonUtil.checkAskStatus(response, 2);
			}
		}
	}
	
	@RequestMapping(value="/getangetinfo")
	public void getangetinfo(HttpServletRequest request, HttpServletResponse response) 
	{
		String aid = request.getParameter("id");
		
		if(aid != null && aid.length() > 0)
		{
			int id = Integer.parseInt(aid);
			UserEntity agent = (UserEntity)userService.getUserEntity(id).get("user");
			JsonUtil.sendAgentInfo(response, agent.getCompanyName(), agent.getLogo(), StringUtil.getBrand(agent.getBrand()), StringUtil.getBrandLogo(agent.getBrand()), 
					agent.isVerified(), agent.getCntAnswer(), agent.getCntSample(), agent.getContactPhone(), agent.getContactQq());
		}
	}
	
	@RequestMapping(value="/getMsgCount")
	public void getMsgCount(HttpServletRequest request, HttpServletResponse response) 
	{
		Account account = (Account) WebUtils.getSessionAttribute(request, "account");
		if(account == null)
		{
			JsonUtil.sendMsgCount(response, 0);
			return;
		}
		else
		{
			if(account.isLogin())
			{
				if(account.isBuyer())
				{
					account.setUnreadMsgCount(userService.getUnreadMsgCount(account.getUserId()));
					JsonUtil.sendMsgCount(response, account.getUnreadMsgCount());
				}
				if(account.isAgent())
				{
					account.setUnreadMsgCount(userService.getUnreadMsgCount(account.getUserId()));
					JsonUtil.sendMsgCount(response, account.getUnreadMsgCount());
				}
			}
		}
	}
	
	@RequestMapping(value="/complaint")
	public void complaint(HttpServletRequest request, HttpServletResponse response) 
	{
		Account account = (Account) WebUtils.getSessionAttribute(request, "account");
		String content = request.getParameter("content").toString();
		ComplainEntity en = new ComplainEntity();
		en.setContent(content);
		if(account == null)
		{
			commonService.insertComplain(en);
			return;
		}
		else
		{
			if(account.isLogin())
			{
				if(account.isBuyer())
				{
					en.setUserType(0);
					en.setUserid(account.getUserId());
					commonService.insertComplain(en);
					return;
				}
				if(account.isAgent())
				{
					en.setUserType(1);
					en.setUserid(account.getUserId());
					commonService.insertComplain(en);
					return;
				}
			}
		}
	}
	
	@RequestMapping(value="/addaddr")
	public void addaddr(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("form")AddressForm form) {
		Account account = (Account) WebUtils.getSessionAttribute(request, "account");
		AddressEntity address = new AddressEntity();
		address.setUid(account.getUserId());
		address.setPca(form.getPca());
		address.setAddr(form.getDetail());
		address.setName(form.getUsername());
		address.setPhone(form.getPhone());
		address.setZip(form.getZip());
		if(form.getIsdefault() != null && form.getIsdefault())
		{
			address.setDefaulte(true);
		}
		else
		{
			address.setDefaulte(false);
		}
		if(form.getIsEdit() > 0)
		{
			int addressid = form.getId();
			address.setId(addressid);
			if(commonService.updateAddress(address) > 0)
			{
				JsonUtil.sendAddress(response, address.getPca(), address.getAddr(), address.getName(), address.getZip(), address.getPhone(), addressid, address.getDefaulte(), true);
			}
		}
		else
		{
			int id = commonService.insertAddress(address);
			if(id > 0)
			{
				JsonUtil.sendAddress(response, address.getPca(), address.getAddr(), address.getName(), address.getZip(), address.getPhone(), id, address.getDefaulte(),false);
			}
		}
	}
	
	@RequestMapping(value="/membership")
	public ModelAndView membership(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView view = new ModelAndView("common/membership");
		return view;
	}
	
	@RequestMapping(value="/paymembership")
	public void paymembership(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView view = new ModelAndView("common/paymembership");
		int mid = Integer.parseInt(request.getParameter("mid"));
		Account account = (Account) WebUtils.getSessionAttribute(request, "account");
		if(account == null)
		{
			JsonUtil.checkAuthStatus(response, 1);
		}
		else
		{
			if(account.isLogin())
			{
				if((account.getAuthority() == 3 && mid == 1) || (account.getAuthority() == 4) )
				{
					JsonUtil.checkAuthStatus(response, 2);
				}
				if(mid == 1)
				{
					JsonUtil.checkAuthStatus(response, 3);
				}
				else
				{
					JsonUtil.checkAuthStatus(response, 4);
				}
			}
		}
	}
	
	@RequestMapping(value="/paymembership/{id}")
	public ModelAndView paymembership(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) {
		ModelAndView view = new ModelAndView("common/paymembership");
		int mid = Integer.parseInt(id);
		view.addObject("mid",mid);
		return view;
	}
	
	@RequestMapping(value="/wxnewrequest")
	public ModelAndView wxnewrequest(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("form")RequestForm form) {
		ModelAndView view = new ModelAndView("wx/wxnewrequest");
		return view;
	}

	@RequestMapping(value="/wxstockhome")
	public ModelAndView wxstockhome(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("form")RequestForm form) {
		ModelAndView view = new ModelAndView("wx/stockhome");
		return view;
	}

	@RequestMapping(value="/stocks")
	public ModelAndView stocks(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("form")RequestForm form) {
		ModelAndView view = new ModelAndView("common/stocklist");
		List<StockEntity> stocks = commonService.getAllstocks();
		for(int i=0;i<stocks.size();i++)
		{
			stocks.get(i).setContent(stocks.get(i).getContent().replace("\n", "<br/>"));
		}
		view.addObject("stocks",stocks);
		List<ItemEntity> items = commonService.getMainItems();
		view.addObject("items", items);
		return view;
	}

	@RequestMapping(value="/searchresult")
	public ModelAndView searchresult(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView view = new ModelAndView("common/series");
		if(isDoSubmit(request))
		{
			String param = request.getParameter("searchparam");
			List<CuttingToolEntity> cts = commonService.searchCuttingTool(StringUtil.prehandleParam(param));
			view.addObject("cts",cts);
		}
		return view;
	}
	
	@RequestMapping(value = "category/{pid}")
	public ModelAndView category(final HttpServletRequest request,final HttpServletResponse response, @PathVariable String pid)
	{
		ModelAndView view = new ModelAndView("/common/categories");
		//末端分类
		if(CuttingToolsConfiguration.isLeaf(pid))
		{
			view = new ModelAndView("/common/series");
			List<CuttingToolEntity> cts = commonService.getCategorySeries(pid);
			view.addObject("category", CuttingToolsConfiguration.getCategoryHtml(pid,false));
			view.addObject("cts",cts);
			return view;
		}
		else
		{
			List<CategoryEntity> categories = CuttingToolsConfiguration.getNextCategories(pid);
			view.addObject("category", CuttingToolsConfiguration.getCategoryHtml(pid,false));
			view.addObject("categories", categories);
		}
		return view;
		
	}
	
	@RequestMapping(value="/getsearch")
	public void getsearch(HttpServletRequest request, HttpServletResponse response)
	{
		String param = request.getParameter("searchparam");
		List<CuttingToolEntity> cts = commonService.searchCuttingTool(StringUtil.prehandleParam(param));
		JsonUtil.sendCts(response, cts);
	}
	
	@RequestMapping(value="/detail/{id}")
	public ModelAndView detail(HttpServletRequest request, HttpServletResponse response, @PathVariable String id)
	{
		int detailid = Integer.valueOf(id);
		ModelAndView view = new ModelAndView("common/detail");
		CuttingToolEntity ct = commonService.getCuttingToolByid(detailid);
		List<CuttingToolEntity> cts = commonService.getVersionsBySeries(ct.getSeriesname());
		List<UserEntity> users = commonService.getProviders(ct.getProvider());
		view.addObject("users",users);
		view.addObject("ct",ct);
		view.addObject("cts",cts);
		return view;
	}
	
	@RequestMapping(value="/wxstocklist")
	public ModelAndView wxstocklist(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("form")RequestForm form) {
		ModelAndView view = new ModelAndView("wx/stocklist");
		List<StockEntity> stocks = commonService.getAllstocks();
		for(int i=0;i<stocks.size();i++)
		{
			stocks.get(i).setContent(stocks.get(i).getContent().replace("\n", "<br/>"));
		}
		view.addObject("stocks",stocks);
		return view;
	}

	@RequestMapping(value="/wxstockadd")
	public ModelAndView wxstockadd(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView view = new ModelAndView("wx/stockadd");
		if(isDoSubmit(request))
		{
			String company = request.getParameter("company");
			String content = request.getParameter("content");
			String phone = request.getParameter("phone");
			StockEntity stock = new StockEntity();
			stock.setCompany(company);
			stock.setContent(content);
			stock.setPhone(phone);
			commonService.addStock(stock);
		}
		return view;
	}
	
	@RequestMapping(value="/wxcard")
	public ModelAndView wxcard(HttpServletRequest request, HttpServletResponse response) throws WeixinException, IOException {
		ModelAndView view = new ModelAndView("wx/wxcard");
		String code = request.getParameter("code");
		
		if(StringUtil.isEmpty(code)){
			Account account = (Account) WebUtils.getSessionAttribute(request, "account");
			if(account == null)
			{
				return new ModelAndView(new RedirectView("/wxindex"));
			}
			logger.error("44");
			logger.error(account.getWxid());
			UserEntity user = commonService.getUserByWxid(account.getWxid());
			view.addObject("user",user);
			view.addObject("relationcount",commonService.getRelationCount(user.getId()));
			view.addObject("rank",commonService.queryUserRank(user.getId()));
			view.addObject("isFeed",commonService.isFeed(account.getWxid()));
			
			return view;
		}
		else//To Do
		{
			Account account = (Account) WebUtils.getSessionAttribute(request, "account");
			if(account == null)
			{
				Openid openid = WeixinService.getInstance2().getUserManagerService().getUserOpenid(code);
				
				UserEntity user = new UserEntity();
				account = new Account();
				account.setWxid(openid.openid);
				WeixinmpUser wxUser = WeixinService.getInstance2().getUserManagerService().getUser(openid.openid);
				account.setWxlogo(wxUser.headimgurl);
				if(wxUser.province == null)
				{
					wxUser.province="";
				}
				if(wxUser.city == null)
				{
					wxUser.city="";
				}
				account.setArea(wxUser.province+wxUser.city);
				request.getSession().setAttribute("account", account);
				if(StringUtil.isNotEmpty(openid.openid))
				{
					user = commonService.getUserByWxid(openid.openid);
				}
				if(user == null)//未绑定微信id
				{
					logger.error("go to log1");
					return new ModelAndView(new RedirectView("/wxindex"));
				}
				else
				{
					account.setUserId(user.getId());
					userService.updateUserAccnt(account);
					userService.updateUserReadCountById(user.getId());
					view.addObject("user",user);
					view.addObject("wxid",account.getWxid());
					view.addObject("relationcount",commonService.getRelationCount(user.getId()));
					view.addObject("rank",commonService.queryUserRank(user.getId()));
					view.addObject("isFeed",commonService.isFeed(account.getWxid()));
					return view;
				}
			}
			else
			{
				logger.error("test");
				UserEntity user = commonService.getUserByWxid(account.getWxid());
				if(user != null)
				{
					view.addObject("user",user);
					userService.updateUserReadCountById(user.getId());
					view.addObject("relationcount",commonService.getRelationCount(user.getId()));
					view.addObject("rank",commonService.queryUserRank(user.getId()));
					view.addObject("isFeed",commonService.isFeed(account.getWxid()));
					return view;
				}
				else
				{
					logger.error("go to log2");
					return new ModelAndView(new RedirectView("/wxindex"));
				}
			}
		}
		
		
	}

	@RequestMapping(value="/wxilike")
	public ModelAndView wxilike(HttpServletRequest request, HttpServletResponse response) throws WeixinException, IOException {
		ModelAndView view = new ModelAndView("wx/wxilike");
		Account account = (Account) WebUtils.getSessionAttribute(request, "account");
		logger.error("test");
		List<RelationEntity> relations = commonService.getIlike(account.getUserId());
		view.addObject("relations",relations);
		
		return view;
	}
	
	@RequestMapping(value="/wxlikeme")
	public ModelAndView wxlikeme(HttpServletRequest request, HttpServletResponse response) throws WeixinException, IOException {
		ModelAndView view = new ModelAndView("wx/wxlikeme");
		Account account = (Account) WebUtils.getSessionAttribute(request, "account");
		logger.error("likeme:"+account.getWxid());
		List<RelationEntity> relations = commonService.getLikeme(account.getUserId());
		view.addObject("relations",relations);
		
		return view;
	}
	
	@RequestMapping(value="/wxilike/{id}")
	public ModelAndView wxilike(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) throws WeixinException, IOException {
		ModelAndView view = new ModelAndView("wx/wxilike");
		int userid = Integer.valueOf(id);
		List<RelationEntity> relations = commonService.getIlike(userid);
		view.addObject("relations",relations);
		view.addObject("id",id);
		return view;
	}
	
	@RequestMapping(value="/wxlikeme/{id}")
	public ModelAndView wxlikeme(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) throws WeixinException, IOException {
		ModelAndView view = new ModelAndView("wx/wxlikeme");
		int userid = Integer.valueOf(id);
		List<RelationEntity> relations = commonService.getLikeme(userid);
		view.addObject("relations",relations);
		view.addObject("id",id);
		return view;
	}
	
	@RequestMapping(value="/wxrank")
	public ModelAndView wxrank(HttpServletRequest request, HttpServletResponse response) throws WeixinException, IOException {
		ModelAndView view = new ModelAndView("wx/wxrank");
		Account account = (Account) WebUtils.getSessionAttribute(request, "account");
		logger.error("enter wxrank");
		logger.error("wxrank"+account.getUserId());
		int rank = commonService.queryUserRank(account.getUserId());
		//通过自己的排名，获取前后几个人
		Map<String,Object> bound = new HashMap<String,Object>();
		if(rank<4)
		{
			bound.put("offset", 0);
			bound.put("limit", 5);
		}
		else
		{
			bound.put("offset", rank-3);
			bound.put("limit", 5);
		}
		UserEntity me = commonService.getUser(account.getUserId());
		view.addObject("me",me);
		List<UserEntity> users = commonService.queryUserEntityOrderByScore(bound);
		view.addObject("users",users);
		view.addObject("rank",rank);
		return view;
	}
	
	@RequestMapping(value="/wxothercard/{wxid}")
	public ModelAndView wxothercardwxid(HttpServletRequest request, HttpServletResponse response, @PathVariable String wxid) throws WeixinException, IOException {
		logger.error("sfasfaf");
		UserEntity user = commonService.getUserByWxid(wxid);
		logger.error("userid:"+String.valueOf(user.getId()));
		return new ModelAndView(new RedirectView("/wxcard/"+String.valueOf(user.getId())));
	}
		
	@RequestMapping(value="/wxcard/{id}")
	public ModelAndView wxothercard(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("form")RelationForm form, @PathVariable String id) throws WeixinException, IOException {
		ModelAndView view = new ModelAndView("wx/wxothercard");
		int userid = Integer.parseInt(id);
		
		Account account = (Account) WebUtils.getSessionAttribute(request, "account");
		UserEntity me;
		if(account == null)
		{
			String code = request.getParameter("code");
			Openid openid = WeixinService.getInstance2().getUserManagerService().getUserOpenid(code);
			me = commonService.getUserByWxid(openid.openid);
			account = new Account();
			account.setWxid(openid.openid);
			if(me!=null)
			{
				account.setUserId(me.getId());
			}
			request.getSession().setAttribute("account", account);
		}
		else
		{
			me = commonService.getUserByWxid(account.getWxid());
		}
		logger.error("11");
		if(me != null)//当前进入微名片的人，已经注册微名片
		{
			account = (Account) WebUtils.getSessionAttribute(request, "account");
			if(account == null)
			{
				account = new Account();
			}
			account.setWxid(me.getWxid());
			if(me!=null)
			{
				account.setUserId(me.getId());
			}
			request.getSession().setAttribute("account", account);
			logger.error("22");
			UserEntity user = commonService.getUser(userid);

			if(user.getId() == me.getId())//是自己的名片
			{
				logger.error("33");
				return new ModelAndView(new RedirectView("/wxcard"));
			}
			else
			{
				view.addObject("user",user);
				//是否已经收藏
				RelationEntity tmp = new RelationEntity();
				tmp.setUserid(me.getId());
				tmp.setUserid2(user.getId());
				logger.error(user.getId()+","+me.getId());
				RelationEntity relation = commonService.queryRelationByRelation(tmp);
				if(relation != null)
				{
					form.setRelation(relation.getRelation());
					form.setWxid(me.getWxid());
					form.setWxid2(user.getWxid());
					form.setUserid(me.getId());
					form.setUserid2(user.getId());
					form.setWxname(relation.getWxname());
					form.setWxname2(relation.getWxname2());
					form.setWxcompany(relation.getWxcompany());
					form.setWxcompany2(relation.getWxcompany2());
					view.addObject("islike",1);
				}
				else
				{
					form.setRelation(0);
					form.setWxid(me.getWxid());
					form.setWxid2(user.getWxid());
					form.setUserid(me.getId());
					form.setUserid2(user.getId());
					form.setWxname(StringUtil.isEmpty(me.getContactName())?me.getNick():me.getContactName());
					form.setWxname2(StringUtil.isEmpty(user.getContactName())?user.getNick():user.getContactName());
					form.setWxcompany(me.getCompanyName());
					form.setWxcompany2(user.getCompanyName());
				}
				userService.updateUserReadCountById(user.getId());
				view.addObject("relationcount",commonService.getRelationCount(user.getId()));
				view.addObject("rank",commonService.queryUserRank(user.getId()));
				return view;
			}	
		}
		else
		{
			logger.error("me is nulL:"+userid);
			UserEntity user = commonService.getUser(userid);
			logger.error("22");
			view.addObject("user",user);
			userService.updateUserReadCountById(user.getId());
			logger.error("33");
			view.addObject("relationcount",commonService.getRelationCount(user.getId()));
			logger.error("44");
			view.addObject("rank",commonService.queryUserRank(user.getId()));
			logger.error("55");
			return view;
		}
	}
	
	@RequestMapping(value="/wxcardcollect")
	public void wxcardcollect(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("form")RelationForm form) throws WeixinException, IOException {
		logger.error("enter card collect");
		if(form.getIslike() == 1)
		{
			logger.error("before delete");
			logger.error("delete relation:"+form.getWxid()+":"+form.getWxid2());
			commonService.delRelation(form);
		}
		else
		{
			logger.error("before add");
			logger.error("add relation:"+form.getWxid()+":"+form.getWxid2());
			RelationEntity relation = new RelationEntity();
			relation.setUserid(form.getUserid());
			relation.setUserid2(form.getUserid2());
			if(commonService.queryRelationByRelation(relation) == null)
			{
				if(form.getWxid2() != null)
				{
					WeixinService.getInstance2().getMessageService().sendTemplate(
						form.getWxid2(), 
						"t8UeZw6qUAKTDNgPlvMS01g9HhraLhkBQ4aZCuK3jUc", 
						"您好，有一位用户收藏了您的名片", form.getWxname2()+", "+form.getWxname()+"刚刚收藏了您的麦辛刀具名片", StringUtil.getCurrentTime(), "点击查看他的名片", 
						"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb5f0887883f34822&redirect_uri=http://www.daoshifu.com/wxcard/"
						+ form.getUserid() +"&response_type=code&scope=snsapi_base&state=s1#wechat_redirect");
				}
				commonService.addRelation(form);
			}
		}
	}
	
	@RequestMapping(value="/wxtest")
	public ModelAndView wxtest(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("form")RelationForm form) throws WeixinException, IOException {
		/*ModelAndView view = new ModelAndView("wx/wxcard");
		logger.error("test");
		UserEntity user = commonService.getUser(332);
		view.addObject("user",user);
		userService.updateUserReadCountById(user.getId());
		view.addObject("relationcount",commonService.getRelationCount(user.getId()));
		view.addObject("rank",commonService.queryUserRank(user.getId()));
		view.addObject("isFeed",commonService.isFeed(user.getWxid()));
		
		ModelAndView view = new ModelAndView("wx/wxothercard");
		UserEntity user = commonService.getUser(447);
		logger.error("22");
		view.addObject("user",user);
		userService.updateUserReadCountById(user.getId());
		logger.error("33");
		view.addObject("relationcount",commonService.getRelationCount(user.getId()));
		logger.error("44");
		view.addObject("rank",commonService.queryUserRank(user.getId()));
		logger.error("55");
		//ModelAndView view = new ModelAndView("wx/wxindex");
		
		return view;*/
		//WeixinService.getInstance2().getMessageService().sendTemplate("oCB4ds29h0lB9E5rw7V3d4DFL5Lo", "t8UeZw6qUAKTDNgPlvMS01g9HhraLhkBQ4aZCuK3jUc", "有一位新用户收藏了您的名片", "时寅超刚刚收藏了您的名片", StringUtil.getCurrentTime(), "点击查看详情", "http://www.daoshifu.com");
		//return new ModelAndView(new RedirectView("http://www.baidu.com")); 
		ModelAndView view = new ModelAndView("wx/wxrank");
		int rank = 332;
		//通过自己的排名，获取前后几个人
		Map<String,Object> bound = new HashMap<String,Object>();
		if(rank<4)
		{
			bound.put("offset", 0);
			bound.put("limit", 5);
		}
		else
		{
			bound.put("offset", rank-3);
			bound.put("limit", 5);
		}
		UserEntity me = commonService.getUser(332);
		view.addObject("me",me);
		List<UserEntity> users = commonService.queryUserEntityOrderByScore(bound);
		view.addObject("users",users);
		view.addObject("rank",rank);
		return view;
	}
	
	@RequestMapping(value = "/wxproducts/{id}")
	public ModelAndView wxproduct(final HttpServletRequest request,final HttpServletResponse response, @PathVariable String id) 
	{
		ModelAndView mv = new ModelAndView("/wx/wxproduct");
		int productid = Integer.parseInt(id);
		ProductEntity product = commonService.getProduct(productid);
		if(product == null)
		{
			return new ModelAndView(new RedirectView("/products"));  
		}
		commonService.addProductCount(product.getId(), product.getCount() + 1);
		mv.addObject("product", product);
		String[] str = product.getPicture().substring(1).split("[|]");
		UserEntity user = commonService.getUser(product.getUserId());
		mv.addObject("pictures",str);
		mv.addObject("user",user);
		return mv;
	}
	
	@RequestMapping(value = "/wxproducts")
	public ModelAndView wxproducts(final HttpServletRequest request,final HttpServletResponse response) 
	{
		ModelAndView mv = new ModelAndView("/wx/wxproducts");
		Account account = (Account) WebUtils.getSessionAttribute(request, "account");
		logger.error("enter products");
		logger.error("userid:"+account.getUserId());
		List<ProductEntity> products = commonService.getProductsByUserid(account.getUserId());
		if(products.isEmpty())
		{
			mv.addObject("empty",1);
			return mv;
		}
		mv.addObject("products", products);
		return mv;
	}
	
	@RequestMapping(value="/wxrank50")
	public ModelAndView wxrank50(HttpServletRequest request, HttpServletResponse response) throws WeixinException, IOException {
		ModelAndView view = new ModelAndView("wx/wxrank50");
		//通过自己的排名，获取前后几个人
		Map<String,Object> bound = new HashMap<String,Object>();
		bound.put("offset", 0);
		bound.put("limit", 50);
		List<UserEntity> users = commonService.queryUserEntityOrderByScore(bound);
		view.addObject("users",users);
		return view;
	}
	
	@RequestMapping(value="/wxindex")
	public ModelAndView wxindex(HttpServletRequest request, HttpServletResponse response) throws WeixinException, IOException {
		ModelAndView view = new ModelAndView("wx/wxindex");
		String code = request.getParameter("code");
		Object target = request.getParameter("target");
		if(target != null)
		{
			logger.error(target.toString());
		}
		request.getSession().setAttribute("target", target);
		Account account = (Account) WebUtils.getSessionAttribute(request, "account");
		if(account == null)
		{
			Openid openid = WeixinService.getInstance2().getUserManagerService().getUserOpenid(code);
			
			UserEntity user = new UserEntity();
			account = new Account();
			account.setWxid(openid.openid);
			WeixinmpUser wxUser = WeixinService.getInstance2().getUserManagerService().getUser(openid.openid);
			account.setWxlogo(wxUser.headimgurl);
			request.getSession().setAttribute("account", account);
			if(StringUtil.isNotEmpty(openid.openid))
			{
				user = commonService.getUserByWxid(openid.openid);
			}
			if(user == null)//未绑定微信id
			{
				request.getSession().setAttribute("account", account);
				return view;
			}
			else
			{
				account.setUserId(user.getId());
				userService.updateUserAccnt(account);
				userService.updateUserReadCountById(user.getId());
				request.getSession().setAttribute("account", account);
				view.addObject("user",user);
				view.addObject("relationcount",commonService.getRelationCount(user.getId()));
				view.addObject("rank",commonService.queryUserRank(user.getId()));
				return new ModelAndView(new RedirectView("/wxcard"));
			}
		}
		else
		{
			UserEntity user = commonService.getUserByWxid(account.getWxid());
			if(user != null)
			{
				view.addObject("user",user);
				userService.updateUserReadCountById(user.getId());
				view.addObject("relationcount",commonService.getRelationCount(user.getId()));
				view.addObject("rank",commonService.queryUserRank(user.getId()));
				return new ModelAndView(new RedirectView("/wxcard"));
			}
			else
			{
				return view;
			}
		}
	}
	
	@RequestMapping(value="/wxbrand/{userid}")
	public ModelAndView wxbrand(HttpServletRequest request, HttpServletResponse response, @PathVariable String userid) throws WeixinException, IOException {
		ModelAndView view = new ModelAndView("wx/wxbrand");
		int id = Integer.parseInt(userid);
		UserEntity user = commonService.getUser(id);
		view.addObject("brand",user.getWxbrand());
		return view;
	}
	
	@RequestMapping(value="/wxsearch")
	public ModelAndView wxsearch(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("form")RegForm form) throws WeixinException, IOException {
		ModelAndView view = new ModelAndView("wx/wxsearch");

		return view;
	}
	
	@RequestMapping(value="/wxsearchresult")
	public ModelAndView wxsearchresult(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("form")RegForm form) throws WeixinException, IOException {
		logger.error("enter search result");
		ModelAndView view = new ModelAndView("wx/wxsearchresult");
		String keyword = request.getParameter("wxbrand");
		String pca =  request.getParameter("pca");
		UserEntity user = new UserEntity();
		if(StringUtil.isNotEmpty(keyword))
		{
			user.setWxbrand(keyword);
		}
		if(StringUtil.isNotEmpty(pca))
		{
			user.setPca(pca);
		}
		List<UserEntity> users = commonService.searchUser(user);
		int count = users.size();
		view.addObject("count",count);
		view.addObject("users",users);
		return view;
	}
	
	
	@RequestMapping(value="/wxlog")
	public ModelAndView wxlog(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("form")LoginForm form, BindingResult result) {
		ModelAndView view = new ModelAndView("wx/wxlog");
		Account account = (Account) WebUtils.getSessionAttribute(request, "account");
		view.addObject("wxid",account.getWxid());
		if(isDoSubmit(request))
		{
			Result re = commonService.checkLogin(form, result);
			
			if(!result.hasErrors())
			{
				UserEntity user = (UserEntity)re.get("user");
				//int unreadMsgCount = userService.getUnreadMsgCount(user.getId());
				//int answerCnt = userService.getAnswerCount(user.getId());
				//int sampleCnt = userService.getSampleCount(user.getId());
				//int questionCnt = userService.getUserQuestionCount(user.getId());
				account.setLogin(true); // 登录成功标识
				account.setUserName(user.getNick()); // 用户登录名
				account.setCompanyName(user.getCompanyName());
				account.setUserId(user.getId()); // 用户ID
				account.setMail(user.getMail()); // 邮箱
				account.setBrandName(user.getBrand());
				account.setAuthority(user.getAuthority());
				account.setVerifiedLink(user.getVerifiedLink());
				//account.setUnreadMsgCount(unreadMsgCount);
				//account.setSampleCnt(sampleCnt);
				//account.setAnwserCnt(answerCnt);
				account.setProductlimit(user.getProductlimit());
				//account.setQuestionCnt(questionCnt);
				account.setQq(user.getContactQq());
				account.setPhone(user.getContactPhone());
				userService.updateUserAccnt(account);
				logger.error("ss");
				request.getSession().setAttribute("account", account);
				logger.error("go to card");
				Object target = WebUtils.getSessionAttribute(request, "target");
				if(target!=null)
				{
					return new ModelAndView(new RedirectView(target.toString())); 
				}
				// 登陆成功， 跳转到登陆页面
				return new ModelAndView(new RedirectView("/wxcard"));
			}
		}

		return view;
	}
	
	@RequestMapping(value="/wxreg")
	public ModelAndView wxreg(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("form")RegForm form, BindingResult result) {
		
		if(isDoSubmit(request))
		{
			logger.error("wxreg:before wxregister");
			Result re = userService.wxregister(form, result);
			logger.error("wxreg:after wxregister");
			if(!result.hasErrors())
			{
				logger.error("wxreg:go to reg2");
				Account account = (Account) WebUtils.getSessionAttribute(request, "account");
				if(account == null)
				{
					account = new Account();
				}
				UserEntity user = (UserEntity)re.get("user");
				account.setLogin(true); // 登录成功标识
				account.setUserName(user.getNick()); // 用户登录名
				account.setMail(user.getMail());
				account.setUserId(user.getId()); // 用户ID
				account.setLogo(user.getLogo());
				account.setCompanyName(user.getCompanyName());
				account.setPhone(user.getPhone());
				account.setMobile(user.getContactPhone());
				request.getSession().setAttribute("account", account);
				// 注册成功， 跳转到登陆页面
				return new ModelAndView(new RedirectView("/wxreg2")); 
			}
			else
			{
				logger.error("wxreg:reg fail");
				// 注册失败， 返回注册页面，并显示出错提示信息
				ModelAndView view = new ModelAndView("wx/wxreg");
				return view;
			}
		}
		else
		{
			logger.error("wxreg:common page");
			ModelAndView view = new ModelAndView("wx/wxreg");
			return view;
		}
	}

	@RequestMapping(value="/wxreg2")
	public ModelAndView wxreg2(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("form")RegForm form, BindingResult result) throws WeixinException {
		if(isDoSubmit(request))
		{
			if(!result.hasErrors())
			{
				Account account = (Account) WebUtils.getSessionAttribute(request, "account");
				logger.error(String.valueOf(account.getUserId()));
				userService.updateUserAccnt(form, account);
				// 注册成功， 跳转到登陆页面
				Object target = WebUtils.getSessionAttribute(request, "target");
				if(target!=null)
				{
					return new ModelAndView(new RedirectView(target.toString())); 
				}
				return new ModelAndView(new RedirectView("/wxcard")); 
			}
			else
			{
				// 注册失败， 返回注册页面，并显示出错提示信息
				ModelAndView view = new ModelAndView("wx/wxreg2");
				return view;
			}
		}
		else
		{
			ModelAndView view = new ModelAndView("wx/wxreg2");
			return view;
		}
	}
	
	@RequestMapping(value="/wxprofile")
	public ModelAndView wxprofile(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("form")RegForm form, BindingResult result) {
		ModelAndView view = new ModelAndView("wx/wxprofile");
		if(isDoSubmit(request))
		{
			Account account = (Account) WebUtils.getSessionAttribute(request, "account");
			UserEntity user = commonService.getUserByWxid(account.getWxid());
			user.setContactName(form.getName());
			user.setCompanyName(form.getCompany());
			user.setPhone(form.getPhone());
			user.setContactPhone(form.getMobile());
			user.setPosition(form.getPosition());
			user.setWxbrand(form.getWxbrand());
			userService.updateUser(user);
			return new ModelAndView(new RedirectView("/wxcard")); 
		}
		else
		{
			Account account = (Account) WebUtils.getSessionAttribute(request, "account");
			UserEntity user = commonService.getUserByWxid(account.getWxid());
			form.setName(user.getContactName());
			form.setCompany(user.getCompanyName());
			form.setPhone(user.getPhone());
			form.setMobile(user.getContactPhone());
			form.setPosition(user.getPosition());
			form.setWxbrand(user.getWxbrand());
			return view;
		}
	}
	
}
