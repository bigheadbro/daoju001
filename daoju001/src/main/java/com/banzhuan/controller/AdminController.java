package com.banzhuan.controller;



import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.banzhuan.common.Account;
import com.banzhuan.common.Constant;
import com.banzhuan.dao.AgentDAO;
import com.banzhuan.dao.ArticleDAO;
import com.banzhuan.dao.EventDAO;
import com.banzhuan.dao.ItemDAO;
import com.banzhuan.dao.ProductDAO;
import com.banzhuan.dao.QuestionDAO;
import com.banzhuan.dao.QuickrequestDAO;
import com.banzhuan.dao.SampleDAO;
import com.banzhuan.dao.UserDAO;
import com.banzhuan.entity.AgentEntity;
import com.banzhuan.entity.ArticleEntity;
import com.banzhuan.entity.BrandEntity;
import com.banzhuan.entity.EventEntity;
import com.banzhuan.entity.ItemEntity;
import com.banzhuan.entity.ProductEntity;
import com.banzhuan.entity.QuestionEntity;
import com.banzhuan.entity.QuickrequestEntity;
import com.banzhuan.entity.SampleEntity;
import com.banzhuan.entity.UserEntity;
import com.banzhuan.form.ItemForm;
import com.banzhuan.form.RegForm;
import com.banzhuan.util.StringUtil;
import com.banzhuan.util.Util;

@Controller
@RequestMapping("/admin")
@SessionAttributes({"isadmin"})
public class AdminController extends BaseController{

	@Autowired
	@Qualifier("userDAO")
	private UserDAO userDAO;
	
	@Autowired
	@Qualifier("sampleDAO")
	private SampleDAO sampleDAO;

	@Autowired
	@Qualifier("questionDAO")
	private QuestionDAO questionDAO;
	
	@Autowired
	@Qualifier("eventDAO")
	private EventDAO eventDAO;
	
	@Autowired
	@Qualifier("itemDAO")
	private ItemDAO itemDAO;
	
	@Autowired
	@Qualifier("productDAO")
	private ProductDAO productDAO;
	
	@Autowired
	@Qualifier("quickrequestDAO")
	private QuickrequestDAO quickrequestDAO;
	
	@Autowired
	@Qualifier("articleDAO")
	private ArticleDAO articleDAO;
	
	@RequestMapping(value="/log")
	public ModelAndView log(final HttpServletRequest request, final HttpServletResponse response)
	{
		ModelAndView mv = new ModelAndView("/admin/log");
		if(isDoSubmit(request))
		{
			String pwd = request.getParameter("adminpwd");
			if(StringUtil.isEqual(pwd, "sglycjcqy"))
			{
				request.getSession().setAttribute("isadmin", true);
				return new ModelAndView(new RedirectView("/admin/main"));
			}
		}
		return mv;
	}
	
	@RequestMapping(value="/requests")
	public ModelAndView requests(final HttpServletRequest request, final HttpServletResponse response)
	{
		ModelAndView mv = new ModelAndView("/admin/requests");
		List<QuickrequestEntity> requests = quickrequestDAO.queryQuickrequests(0, new RowBounds());
		mv.addObject("requests",requests);
		return mv;
	}
	
	@RequestMapping(value="/delrequest/{id}")
	public void delrequest(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) throws IOException 
	{
		int rid = Integer.parseInt(id);
		quickrequestDAO.delQuickrequest(rid);
	}
	
	@RequestMapping(value="/main")
	public ModelAndView main(final HttpServletRequest request, final HttpServletResponse response)
	{
		ModelAndView mv = new ModelAndView("/admin/main");
		//user
		int userCount = userDAO.getUsersCount(false);
		int userCountToday = userDAO.getUsersCount(true);
		mv.addObject("usercount",userCount);
		mv.addObject("userCountToday",userCountToday);
		
		//question
		int quescount = questionDAO.getQuestionCount(false);
		int quescounttoday = questionDAO.getQuestionCount(true);
		mv.addObject("quescount",quescount);
		mv.addObject("quescounttoday",quescounttoday);
		
		//products
		int productcount = productDAO.getProductCount(false);
		int productcounttoday = productDAO.getProductCount(true);
		mv.addObject("productcount",productcount);
		mv.addObject("productcounttoday",productcounttoday);
		
		//items
		int itemcount = itemDAO.getItemCountByType(null);
		mv.addObject("itemcount",itemcount);
		
		return mv;
	}
	
	@RequestMapping(value="/lghlmclyhblsqtitem")
	public ModelAndView item(final HttpServletRequest request, final HttpServletResponse response,@ModelAttribute("form")ItemForm form)
	{
		ModelAndView mv = new ModelAndView("/admin/newitem");
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
		
		if(isDoSubmit(request))
		{
			ItemEntity item = new ItemEntity();
			item.setProvider(form.getProvider());
			item.setBrand(form.getBrand());
			item.setType(form.getType());
			item.setDetailtype(form.getDetailtype());
			item.setWorkmaterial(form.getWorkmaterial());
			item.setMaterial(form.getMaterial());
			item.setVersion(form.getVersion());
			item.setPrice(form.getPrice());
			item.setPicture(form.getPicture());
			item.setCover(form.getCover());
			item.setFeature(form.getFeature());
			item.setDescription(form.getDescription());
			item.setLimitq(form.getLimitq());
			item.setBrand(form.getBrand());
			itemDAO.insertItemEntity(item);
			return mv;
		}
		return mv;
	}
	
	@RequestMapping(value="/lghlmclyhblsqtitem/{id}")
	public ModelAndView updateItem(final HttpServletRequest request, final HttpServletResponse response,@PathVariable String id, @ModelAttribute("form")ItemForm form)
	{
		ModelAndView mv = new ModelAndView("/admin/newitem");
		int itemid = Integer.parseInt(id);
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
		
		if(isDoSubmit(request))
		{
			ItemEntity item = new ItemEntity();
			item.setId(itemid);
			item.setProvider(form.getProvider());
			item.setBrand(form.getBrand());
			item.setType(form.getType());
			item.setDetailtype(form.getDetailtype());
			item.setWorkmaterial(form.getWorkmaterial());
			item.setMaterial(form.getMaterial());
			item.setVersion(form.getVersion());
			item.setPrice(form.getPrice());
			item.setPicture(form.getPicture());
			item.setCover(form.getCover());
			item.setFeature(form.getFeature());
			item.setDescription(form.getDescription());
			item.setLimitq(form.getLimitq());
			item.setBrand(form.getBrand());
			itemDAO.updateItemById(item);
			return new ModelAndView(new RedirectView("/admin/lghlmclyhblsqtitems"));
		}
		else
		{
			//set form
			ItemEntity oldone = itemDAO.queryItemEntityById(itemid);
			form.setProvider(oldone.getProvider());
			form.setBrand(oldone.getBrand());
			form.setType(oldone.getType());
			form.setDetailtype(oldone.getDetailtype());
			form.setWorkmaterial(oldone.getWorkmaterial());
			form.setMaterial(oldone.getMaterial());
			form.setVersion(oldone.getVersion());
			form.setPrice(oldone.getPrice());
			form.setPicture(oldone.getPicture());
			form.setCover(oldone.getCover());
			form.setFeature(oldone.getFeature());
			form.setDescription(oldone.getDescription());
			form.setLimitq(oldone.getLimitq());
			form.setBrand(oldone.getBrand());
		}
		return mv;
	}
	
	@RequestMapping(value="/lghlmclyhblsqtitems")
	public ModelAndView items(final HttpServletRequest request, final HttpServletResponse response)
	{
		ModelAndView mv = new ModelAndView("/admin/items");
		List<ItemEntity> items = itemDAO.getAllItemsByType(null);
		mv.addObject("items", items);
		return mv;
	}
	
	@RequestMapping(value = "uploadfile_item", produces="text/plain;charset=UTF-8")  
	@ResponseBody
	public String uploadfile_item(HttpServletRequest request, HttpServletResponse response)
	{
		String responseStr="";  
		MultipartHttpServletRequest r = (MultipartHttpServletRequest) request;
		  
        MultipartFile f = r.getFile("productlink");    
        String type = f.getOriginalFilename().substring(f.getOriginalFilename().lastIndexOf(".")).toLowerCase();
        if(!StringUtil.isProperImageFile(type))
        {
        	return "";
        }
        String path = request.getSession().getServletContext().getRealPath("/item");
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
	
	
	@RequestMapping(value="/lghlmclyhblsqtarticle")
	public ModelAndView newarticle(final HttpServletRequest request, final HttpServletResponse response)
	{
		ModelAndView mv = new ModelAndView("/admin/newarticle");
		
		if(isDoSubmit(request))
		{
			ArticleEntity article = new ArticleEntity();
			article.setCover(request.getParameter("picture"));
			article.setLink(request.getParameter("link"));
			article.setTitle(request.getParameter("title"));
			article.setOutline(request.getParameter("outline"));
			articleDAO.insertArticleEntity(article);
			return mv;
		}
		return mv;
	}
	
	@RequestMapping(value = "uploadfile_article", produces="text/plain;charset=UTF-8")  
	@ResponseBody
	public String uploadfile_article(HttpServletRequest request, HttpServletResponse response)
	{
		String responseStr="";  
		MultipartHttpServletRequest r = (MultipartHttpServletRequest) request;
		  
        MultipartFile f = r.getFile("productlink");    
        String type = f.getOriginalFilename().substring(f.getOriginalFilename().lastIndexOf(".")).toLowerCase();
        if(!StringUtil.isProperImageFile(type))
        {
        	return "";
        }
        String path = request.getSession().getServletContext().getRealPath("/article");
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
	
	@RequestMapping(value="/lghlmclyhblsqtagent")
	public ModelAndView agent(final HttpServletResponse response)
	{
		ModelAndView mv = new ModelAndView("/admin/admin");
		List<UserEntity> agents = userDAO.getUsers();
		mv.addObject("agents", agents);
		return mv;
	}
	
	@RequestMapping(value="/updateuser/{id}")
	public void updateuser(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) throws IOException 
	{
		int userid = Integer.parseInt(id);
		String type = request.getParameter("type");
		UserEntity agent = userDAO.queryUserEntityById(userid);
		if(StringUtil.isEqual(type, "1"))
		{
			agent.setAuthority(4);
			agent.setProductlimit(10);
		}
		else if(StringUtil.isEqual(type, "2"))
		{
			agent.setAuthority(3);
			agent.setProductlimit(2);
		}
		else if(StringUtil.isEqual(type, "3"))
		{
			agent.setAuthority(1);
			agent.setProductlimit(2);
		}
		else if(StringUtil.isEqual(type, "4"))
		{
			agent.setAuthority(5);
			agent.setProductlimit(10);
		}
		userDAO.updateUserEntityById(agent);
	}
	
	@RequestMapping(value="/lghlmclyhblsqtproduct")
	public ModelAndView products(final HttpServletResponse response)
	{
		ModelAndView mv = new ModelAndView("/admin/products");
		List<ProductEntity> products = productDAO.getAllProducts();
		mv.addObject("products", products);
		return mv;
	}
	
	
	@RequestMapping(value="/passproduct/{id}")
	public void passproduct(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) throws IOException 
	{
		int pid = Integer.parseInt(id);
		ProductEntity product = new ProductEntity();
		product.setId(pid);
		product.setState(1);
		productDAO.updateProductById(product);
	}
	
	@RequestMapping(value="/failproduct/{id}")
	public void failproduct(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) throws IOException 
	{
		int pid = Integer.parseInt(id);
		ProductEntity product = new ProductEntity();
		product.setId(pid);
		product.setState(2);
		productDAO.updateProductById(product);
	}
	
	@RequestMapping(value="/cancel/{id}")
	public void cancelauth(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) throws IOException 
	{
		int sid = Integer.parseInt(id);
		UserEntity agent = new UserEntity();
		agent.setId(sid);
		agent.setVerified(false);
		userDAO.updateUserEntityById(agent);
	}
	
	@RequestMapping(value="/add/{id}")
	public void addauth(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) throws IOException 
	{
		int sid = Integer.parseInt(id);
		UserEntity agent = new UserEntity();
		agent.setId(sid);
		agent.setVerified(true);
		userDAO.updateUserEntityById(agent);
	}
	
	
	@RequestMapping(value="/lghlmclyhblsqtsample")
	public ModelAndView sample(final HttpServletResponse response)
	{
		ModelAndView mv = new ModelAndView("/admin/sample");
		List<SampleEntity> samples = sampleDAO.getAllsamples();
		mv.addObject("samples", samples);
		return mv;
	}
	
	@RequestMapping(value="/delsample/{id}")
	public void delsample(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) throws IOException 
	{
		int sid = Integer.parseInt(id);
		sampleDAO.delSample(sid);
	}
	
	
	@RequestMapping(value="/lghlmclyhblsqtquestion")
	public ModelAndView question(final HttpServletResponse response)
	{
		ModelAndView mv = new ModelAndView("/admin/questions");
		QuestionEntity question = new QuestionEntity();
		List<QuestionEntity> questions = questionDAO.getAllquestions(question);
		mv.addObject("questions", questions);
		return mv;
	}
	
	@RequestMapping(value="/lghlmclyhblsqtevent")
	public ModelAndView event(final HttpServletResponse response)
	{
		ModelAndView mv = new ModelAndView("/admin/events");
		List<EventEntity> events = eventDAO.getAllevents();
		mv.addObject("events", events);
		return mv;
	}
	
	@RequestMapping(value="/delquestion/{id}")
	public void delquestion(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) throws IOException 
	{
		int sid = Integer.parseInt(id);
		questionDAO.delQuestion(sid);
	}
	
	
	@RequestMapping(value="/brysjhhrhl")
	public ModelAndView edm(final HttpServletResponse response)
	{
		ModelAndView mv = new ModelAndView("/admin/admin");
		HashSet<String> set = Util.readFileByLines("EDM/cut35-mail.txt");
		String rec[] = new String[set.size()];
		set.toArray(rec);
		String tmp[] = new String[10];
		for(int i = 1;i<rec.length;i++)
		{
			tmp[i%10] = rec[i];
			if(i%10 == 0)
			{
				//Util.EDM("noreply@daoshifu.com","cisco123",tmp,"刀师傅-第一家刀具在线交流平台", "", null, "", "UTF-8");
			}
		}
		return mv;
	}
}
