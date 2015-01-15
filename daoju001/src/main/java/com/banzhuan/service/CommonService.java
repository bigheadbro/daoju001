package com.banzhuan.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import com.banzhuan.dao.AddressDAO;
import com.banzhuan.dao.AgentDAO;
import com.banzhuan.dao.ArticleDAO;
import com.banzhuan.dao.BuyerDAO;
import com.banzhuan.dao.CommentDAO;
import com.banzhuan.dao.ComplainDAO;
import com.banzhuan.dao.CuttingToolDAO;
import com.banzhuan.dao.EventDAO;
import com.banzhuan.dao.GoodcaseDAO;
import com.banzhuan.dao.ItemDAO;
import com.banzhuan.dao.MsgDAO;
import com.banzhuan.dao.OrderDAO;
import com.banzhuan.dao.ProductDAO;
import com.banzhuan.dao.ProfessionalAnswerDAO;
import com.banzhuan.dao.QuestionDAO;
import com.banzhuan.dao.QuickrequestDAO;
import com.banzhuan.dao.RelationDAO;
import com.banzhuan.dao.SampleDAO;
import com.banzhuan.dao.StatisticsDAO;
import com.banzhuan.dao.StockDAO;
import com.banzhuan.dao.UserDAO;
import com.banzhuan.entity.AddressEntity;
import com.banzhuan.entity.AgentEntity;
import com.banzhuan.entity.ArticleEntity;
import com.banzhuan.entity.BuyerEntity;
import com.banzhuan.entity.CommentEntity;
import com.banzhuan.entity.ComplainEntity;
import com.banzhuan.entity.CuttingToolEntity;
import com.banzhuan.entity.EventEntity;
import com.banzhuan.entity.GoodcaseEntity;
import com.banzhuan.entity.IndexEntity;
import com.banzhuan.entity.ItemEntity;
import com.banzhuan.entity.MessageEntity;
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
import com.banzhuan.common.Account;
import com.banzhuan.common.Result;
import com.banzhuan.controller.CommonController;
import com.banzhuan.form.CommentForm;
import com.banzhuan.form.GoodcaseForm;
import com.banzhuan.form.ItemForm;
import com.banzhuan.form.LoginForm;
import com.banzhuan.form.ProductForm;
import com.banzhuan.form.QuestionForm;
import com.banzhuan.form.RelationForm;
import com.banzhuan.form.RequestForm;
import com.banzhuan.util.ChineseSpelling;
import com.banzhuan.util.CuttingToolsConfiguration;
import com.banzhuan.util.StringUtil;
import com.banzhuan.util.Util;
import com.cjc.weixinmp.WeixinException;
import com.cjc.weixinmp.bean.Users;
import com.cjc.weixinmp.bean.WeixinmpUser;

/**
 * @author guichaoqun
 *
 */
@Service("commonService")
public class CommonService {

	private Logger logger = LoggerFactory.getLogger(CommonService.class);
	@Autowired
	@Qualifier("questionDAO")
	private QuestionDAO questionDAO;
	
	@Autowired
	@Qualifier("gcDAO")
	private GoodcaseDAO gcDAO;
	
	@Autowired
	@Qualifier("sampleDAO")
	private SampleDAO sampleDAO;
	
	@Autowired
	@Qualifier("commentDAO")
	private CommentDAO commentDAO;
	
	@Autowired
	@Qualifier("msgDAO")
	private MsgDAO msgDAO;
	
	@Autowired
	@Qualifier("paDAO")
	private ProfessionalAnswerDAO paDAO;
	
	@Autowired
	@Qualifier("complainDAO")
	private ComplainDAO complainDAO;
	
	@Autowired
	@Qualifier("eventDAO")
	private EventDAO eventDAO;
	
	@Autowired
	@Qualifier("productDAO")
	private ProductDAO productDAO;
	
	@Autowired
	@Qualifier("addressDAO")
	private AddressDAO addressDAO;
	
	@Autowired
	@Qualifier("itemDAO")
	private ItemDAO itemDAO;
	
	@Autowired
	@Qualifier("orderDAO")
	private OrderDAO orderDAO;
	
	@Autowired
	@Qualifier("userDAO")
	private UserDAO userDAO;
	
	@Autowired
	@Qualifier("quickrequestDAO")
	private QuickrequestDAO quickrequestDAO;
	
	@Autowired
	@Qualifier("articleDAO")
	private ArticleDAO articleDAO;
	
	@Autowired
	@Qualifier("stockDAO")
	private StockDAO stockDAO;
	
	@Autowired
	@Qualifier("relationDAO")
	private RelationDAO relationDAO;
	
	@Autowired
	@Qualifier("ctDAO")
	private CuttingToolDAO ctDAO;
	
	@Autowired
	@Qualifier("stDAO")
	private StatisticsDAO stDAO;
	
	public Result checkLogin(LoginForm form, Errors errors)
	{
		Result result = new Result();
		if(StringUtil.isEmpty(form.getMail()))
		{
			errors.rejectValue("mail", "MAIL_IS_NOT_NULL"); // 邮箱不能为空
			return result;
		}
		
		
		UserEntity user = userDAO.queryUserEntityByMail(form.getMail());
		if(user == null)
		{
			errors.rejectValue("mail", "MAIL_IS_NOT_EXISTS"); // 邮箱不存在
			return result;
		}
		if(StringUtil.isNotEqual(user.getPassword(), StringUtil.encrypt(form.getPassword())))
		{
			errors.rejectValue("password", "PASSWORD_ERROR"); // 密码错误
			return result;
		}
		result.add("user", user);
		result.add("userType", 1);
		return result;
	}
	
	public Result changpwd(String mail, String pwd, Errors errors)
	{
		Result result = new Result();
		UserEntity user = userDAO.queryUserEntityByMail(mail);
		if(user == null)
		{
			errors.rejectValue("mail", "MAIL_IS_NOT_EXISTS"); // 邮箱不存在
			return result;
		}
		user.setPassword(StringUtil.encrypt(pwd)); 
		userDAO.updateUserPwdById(user);
		return result;
	}
	
	public int getGoodcaseCountByType(GoodcaseForm form)
	{
		GoodcaseEntity gc = new GoodcaseEntity();
		if(form.getIndustry() != 0)
    	{
    		gc.setIndustry(form.getIndustry());
    	}
    	if(form.getProcessMethod() != 0)
    	{
    		gc.setProcessMethod(form.getProcessMethod());
    	}
    	if(form.getWpHardness() != 0)
    	{
    		gc.setWorkSolidity(form.getWpHardness());
    	}
    	if(form.getWpMaterial() != 0)
    	{
    		gc.setWorkMaterial(form.getWpMaterial());
    	}
    	return gcDAO.getGoodcaseCountByType(gc);
	}
	
	public Result getAllGoodcases(GoodcaseForm form, RowBounds bound)
	{
		Result result = new Result();
		GoodcaseEntity gc = new GoodcaseEntity();
		if(form.getIndustry() != 0)
    	{
    		gc.setIndustry(form.getIndustry());
    	}
    	if(form.getProcessMethod() != 0)
    	{
    		gc.setProcessMethod(form.getProcessMethod());
    	}
    	if(form.getWpHardness() != 0)
    	{
    		gc.setWorkSolidity(form.getWpHardness());
    	}
    	if(form.getWpMaterial() != 0)
    	{
    		gc.setWorkMaterial(form.getWpMaterial());
    	}
		List<GoodcaseEntity> goodcases;
		if(bound == null)
    	{
			goodcases = gcDAO.getAllGoodcasesByType(gc);
    	}
    	else
    	{
    		goodcases = gcDAO.getAllGoodcasesByType(gc, bound);
    	}
		result.add("goodcases", goodcases);
		return result;
	}
	
	public int getAllquestionsCount(QuestionForm form)
	{
		QuestionEntity ques = new QuestionEntity();
		if(form.getIndustry() != 0)
    	{
			ques.setIndustry(form.getIndustry());
    	}
    	if(form.getProcessMethod() != 0)
    	{
    		ques.setProcessMethod(form.getProcessMethod());
    	}
    	if(form.getWpHardness() != 0)
    	{
    		ques.setWpHardness(form.getWpHardness());
    	}
    	if(form.getWpMaterial() != 0)
    	{
    		ques.setWpMaterial(form.getWpMaterial());
    	}
		return questionDAO.getAllQuestionCount(ques);
	}
	public Result getAllquestions(QuestionForm form, RowBounds bound)
	{
		Result result = new Result();
		QuestionEntity ques = new QuestionEntity();
		if(form.getIndustry() != 0)
    	{
			ques.setIndustry(form.getIndustry());
    	}
    	if(form.getProcessMethod() != 0)
    	{
    		ques.setProcessMethod(form.getProcessMethod());
    	}
    	if(form.getWpHardness() != 0)
    	{
    		ques.setWpHardness(form.getWpHardness());
    	}
    	if(form.getWpMaterial() != 0)
    	{
    		ques.setWpMaterial(form.getWpMaterial());
    	}
    	List<QuestionEntity> questions;
    	if(bound == null)
    	{
    		questions = questionDAO.getAllquestions(ques);
    	}
    	else
    	{
    		questions = questionDAO.getAllquestions(ques, bound);
    	}
		result.add("questions", questions);
		return result;
	}
	
	public Result getAllproducts(ProductForm form, RowBounds bound)
	{
		Result result = new Result();
		ProductEntity product = new ProductEntity();
		if(form.getIndustry() != 0)
    	{
			product.setIndustry(form.getIndustry());
    	}
    	if(form.getProcessMethod() != 0)
    	{
    		product.setProcessMethod(form.getProcessMethod());
    	}
    	if(form.getWpHardness() != 0)
    	{
    		product.setWpHardness(form.getWpHardness());
    	}
    	if(form.getWpMaterial() != 0)
    	{
    		product.setWpMaterial(form.getWpMaterial());
    	}
    	List<ProductEntity> products;
    	if(bound == null)
    	{
    		products = productDAO.getAllProductsByType(product);
    	}
    	else
    	{
    		products = productDAO.getAllProductsByType(product, bound);
    	}
		result.add("products", products);
		return result;
	}
	
	public int getProductsCount()
	{
		ProductEntity product = new ProductEntity();
		return productDAO.getProductCountByType(product);
	}
	
	public String getProviderMap()
	{
		String ret = "";
		List<UserEntity> users = userDAO.getUsersByAuth(3);
		for(int i = 0; i< users.size();i++)
		{
			UserEntity user = users.get(i);
			if(StringUtil.isEmpty(user.getAddress()))
			{
				continue;
			}
			ret += "[";
			ret += user.getLng();
			ret += "," + user.getLat();
			ret += ",\"<div>"
					+ "<h2 style='color: #535353;font-size: 16px;padding: 5px 5px 3px 0;margin-bottom: 7px;'>"
					+ "<a target='_blank' href='/agents/"+user.getId()+"'>"+user.getCompanyName()+"</a>"
					+ "</h2>"
					+ "<h3><span style='display: inline-block;text-align: center;color: #929292;padding: 1px 0px;margin-right: 5px;border-radius: 4px;font-size: 12px;'>供应品牌:</span>";
			if(user.getBrand() == user.getBrand2() && user.getBrand() == 0)
			{
				ret += "<span style='font-size:14px;padding:5px;'>不详</span>";
			}
			else if(user.getBrand() == user.getBrand2())
			{
				ret += "<span style='font-size:14px;padding:5px;'>" + StringUtil.getBrand(user.getBrand()) + "</span>";
			}
			else
			{
				ret += "<span style='font-size:14px;padding:5px;'>" + StringUtil.getBrand(user.getBrand()) + "</span>"
					+"<span style='font-size:14px;padding:5px;'>" + StringUtil.getBrand(user.getBrand2()) + "<span>";
			}
			ret += "</h3>"
					+ "<h5 style='font-weight: 100;margin: 2px 0 2px 0;white-space: nowrap;color: #ff8f17;'>"
					+ "<span style='display: inline-block;width: 25px;text-align: center;color: #929292;padding: 1px 0px;margin-right: 5px;border-radius: 4px;font-size: 12px;'>电话:</span>";
			
			if(StringUtil.isNotEmpty(user.getContactPhone()))
			{
				ret += user.getContactPhone();
			}
			else if(StringUtil.isNotEmpty(user.getPhone()))
			{
				ret += user.getPhone();
			}
			else 
			{
				ret += "未填写";
			}
			ret += "</h5>"
					+ "<h4 style='color: #606060;font-size: 12px;font-weight: 100;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;'>"
					+ "<span style='display: inline-block;width: 25px;text-align: center;color: #929292;padding: 1px 0px;margin-right: 5px;border-radius: 4px;font-size: 12px;'>地址:</span>"
					+ user.getAddress()
					+ "</h4></div>";
			ret += "\"],";
		}
		return "["+ret+"]";
	}
	
	public Map<Integer,Map<Integer,List<UserEntity>>> getAllAgents()
	{
		List<UserEntity> agents = userDAO.getUsersByAuth(3);

		Map<Integer,Map<Integer,List<UserEntity>>> agentMap = new HashMap<Integer, Map<Integer,List<UserEntity>>>();
		
		for(int i = 0;i < agents.size(); i++)
		{
			if(agents.get(i).getBrand() == 0)
			{
				continue;
			}
			int alpha = ChineseSpelling.letterToNum(ChineseSpelling.getFirstLetter(StringUtil.getBrand(((UserEntity)agents.get(i)).getBrand())));
			if(agents.get(i).getBrand() == 100)
			{
				alpha = 27;
			}
			Map<Integer,List<UserEntity>> tmpMap = agentMap.get(alpha);
			//是否存在该字母的代理商
			if(tmpMap == null)
			{
				tmpMap = new HashMap<Integer,List<UserEntity>>();
				List<UserEntity> tmp = new ArrayList<UserEntity>();
				tmp.add((UserEntity)agents.get(i));
				tmpMap.put(agents.get(i).getBrand(), tmp);
			}
			else
			{
				//是否包含样本的品牌
				if(tmpMap.containsKey(agents.get(i).getBrand()))
				{
					Iterator<Integer> iter = tmpMap.keySet().iterator(); 
					while (iter.hasNext()) 
					{ 
					    int key = Integer.valueOf(iter.next().toString()); 
					    List<UserEntity> val = tmpMap.get(key); 
					    if(val == null)
						{
					    	val = new ArrayList<UserEntity>();
						}
					    //如果是同一个品牌
					    if(key == agents.get(i).getBrand())
					    {
						    val.add((UserEntity)agents.get(i));
						    tmpMap.put(key, val);
						    break;
					    }
					} 
				}
				else
				{
					List<UserEntity> tmp = new ArrayList<UserEntity>();
					tmp.add((UserEntity)agents.get(i));
					tmpMap.put(agents.get(i).getBrand(), tmp);
				}
			}
			agentMap.put(alpha, tmpMap);
			
			if(agents.get(i).getBrand2() == 0 || agents.get(i).getBrand2() == agents.get(i).getBrand())
			{
				continue;
			}
			alpha = ChineseSpelling.letterToNum(ChineseSpelling.getFirstLetter(StringUtil.getBrand(((UserEntity)agents.get(i)).getBrand2())));
			if(agents.get(i).getBrand2() == 100)
			{
				alpha = 27;
			}
			tmpMap = agentMap.get(alpha);
			//是否存在该字母的代理商
			if(tmpMap == null)
			{
				tmpMap = new HashMap<Integer,List<UserEntity>>();
				List<UserEntity> tmp = new ArrayList<UserEntity>();
				tmp.add((UserEntity)agents.get(i));
				tmpMap.put(agents.get(i).getBrand2(), tmp);
			}
			else
			{
				//是否包含样本的品牌
				if(tmpMap.containsKey(agents.get(i).getBrand2()))
				{
					Iterator<Integer> iter = tmpMap.keySet().iterator(); 
					while (iter.hasNext()) 
					{ 
					    int key = Integer.valueOf(iter.next().toString()); 
					    List<UserEntity> val = tmpMap.get(key); 
					    if(val == null)
						{
					    	val = new ArrayList<UserEntity>();
						}
					    //如果是同一个品牌
					    if(key == agents.get(i).getBrand2())
					    {
						    val.add((UserEntity)agents.get(i));
						    tmpMap.put(key, val);
						    break;
					    }
					} 
				}
				else
				{
					List<UserEntity> tmp = new ArrayList<UserEntity>();
					tmp.add((UserEntity)agents.get(i));
					tmpMap.put(agents.get(i).getBrand2(), tmp);
				}
			}
			agentMap.put(alpha, tmpMap);
		}
		return agentMap;
	}
	
	public Map<Integer,Map<Integer,Map<Integer,List<SampleEntity>>>> getAllSamples()
	{
		List<SampleEntity> samples = sampleDAO.getAllsamples();
		
		Map<Integer,Map<Integer,Map<Integer,List<SampleEntity>>>> sampleMap = new HashMap<Integer, Map<Integer,Map<Integer,List<SampleEntity>>>>();
		
		for(int i = 0;i < samples.size(); i++)
		{
			int alpha = 0;
			int brandid = ((SampleEntity)samples.get(i)).getBrandid();
			if(brandid == 100)
			{
				alpha = 27;
			}
			else
			{
				alpha = ChineseSpelling.letterToNum(ChineseSpelling.getFirstLetter(StringUtil.getBrand(brandid)));
			}
			
 			Map<Integer,Map<Integer,List<SampleEntity>>> tmpMap2 = sampleMap.get(alpha);
			
			if(tmpMap2 == null )//第一个样本
			{
				Map<Integer,List<SampleEntity>> tmpMap = new HashMap<Integer,List<SampleEntity>>();
				List<SampleEntity> tmp = new ArrayList<SampleEntity>();
				tmp.add((SampleEntity)samples.get(i));
				tmpMap.put(samples.get(i).getAgentId(), tmp);
				tmpMap2 = new HashMap<Integer,Map<Integer,List<SampleEntity>>>();
				tmpMap2.put(samples.get(i).getBrandid(), tmpMap);
			}
			else//2-n
			{
				//是否包含样本的代理商id
				System.out.println(samples.get(i).getBrandid());
				if(sampleMap.containsKey(alpha))
				{
					Iterator<Integer> iter = tmpMap2.keySet().iterator(); 
					while (iter.hasNext()) 
					{ 
						int key = Integer.valueOf(iter.next().toString()); 
						Map<Integer,List<SampleEntity>> val = tmpMap2.get(key); 
					    if(val == null)
						{
					    	val = new HashMap<Integer,List<SampleEntity>>();
							List<SampleEntity> tmp = new ArrayList<SampleEntity>();
							tmp.add((SampleEntity)samples.get(i));
							val.put(samples.get(i).getAgentId(), tmp);
							tmpMap2.put(key, val);
							break;
						}
						else
						{
							//是否包含样本的代理商id
							if(val.containsKey(samples.get(i).getAgentId()))
							{
								Iterator<Integer> iter1 = val.keySet().iterator(); 
								while (iter1.hasNext()) 
								{ 
								    int key1 = Integer.valueOf(iter1.next().toString()); 
								    List<SampleEntity> val1 = val.get(key1); 
								    if(val1 == null)
									{
								    	val1 = new ArrayList<SampleEntity>();
									}
								    //如果是同一个代理商的样本
								    if(key1 == samples.get(i).getAgentId())
								    {
									    val1.add((SampleEntity)samples.get(i));
									    val.put(key1, val1);
									    break;
								    }
								} 
							}
							else
							{
								List<SampleEntity> tmp = new ArrayList<SampleEntity>();
								tmp.add((SampleEntity)samples.get(i));
								val.put(samples.get(i).getAgentId(), tmp);
							}
						}
					    tmpMap2.put(key, val);
					    break;
					} 
				}
				else//当前字母的一个品牌
				{
					Map<Integer,List<SampleEntity>> tmpMap = new HashMap<Integer,List<SampleEntity>>();
					List<SampleEntity> tmp = new ArrayList<SampleEntity>();
					tmp.add((SampleEntity)samples.get(i));
					tmpMap.put(samples.get(i).getAgentId(), tmp);
					
					tmpMap2.put(samples.get(i).getBrandid(), tmpMap);
				}
			}

			sampleMap.put(alpha, tmpMap2);

		}

		return sampleMap;
	}
	
	public Result getMainquestions()
	{
		Result result = new Result();
		List<QuestionEntity> questions = questionDAO.getMainquestions();
		List<ProfessionalAnswerEntity> answers = new ArrayList<ProfessionalAnswerEntity>();
		for(int i = 0;i< questions.size();i++)
		{
 			answers.add(paDAO.queryAnswersByQid(questions.get(i).getId()).get(0));
		}
		
		result.add("questions", questions);
		result.add("answers", answers);
		return result;
	}
	
	public Result getMainagents()
	{
		Result result = new Result();
		List<UserEntity> agents = userDAO.getMainagents();
		result.add("agents", agents);
		return result;
	}
	
	public Result getMaingoodcases()
	{
		Result result = new Result();
		List<GoodcaseEntity> goodcases = gcDAO.getMainGoodcasesByType();
		result.add("goodcases", goodcases);
		return result;
	}
	
	public Result getCommentsInQuesByPid(int qid)
	{
		Result result = new Result();
		List<CommentEntity> comments = commentDAO.getCommentsInQuesByParentid(qid);
		result.add("commentsCnt", comments.size());
		result.add("comments", comments);
		return result;
	}
	
	public int insertEvent(EventEntity e)
	{
		int result = eventDAO.insertEventEntity(e);
		return result;
	}
	
	public int insertComment(CommentForm form)
	{
		CommentEntity comment = new CommentEntity();
		
		if(form.getParentId() != 0)
		{
			comment.setParent(form.getParentId());
		}
		if(form.getCommentId() != 0)
		{
			comment.setCommentId(form.getCommentId());
		}
		if(form.getType() != 0)
		{
			comment.setType(form.getType());
		}
		if(form.getContent() != "")
		{
			comment.setContent(form.getContent());
		}
		
		if(form.getUserid() > 0)
		{
			comment.setUserid(form.getUserid());
		}
		if(form.getBrandName() > 0)
		{
			comment.setBrandName(form.getBrandName());
		}
		if(StringUtil.isNotEmpty(form.getVerifiedLink()))
		{
			comment.setLink(form.getVerifiedLink());
		}
		
		if(StringUtil.isNotEmpty(form.getUserLogo()))
		{
			comment.setUserAvatar(form.getUserLogo());
		}
		if(StringUtil.isNotEmpty(form.getUserName()))
		{
			comment.setUserName(form.getUserName());
		}


		int result = commentDAO.insertCommentEntity(comment);
		return result;
	}
	
	public void setMsgAsRead(int id)
	{
		msgDAO.setMsgAsRead(id);
	}
	
	public int insertComplain(ComplainEntity en)
	{
		int result = complainDAO.insertComplainEntity(en);
		return result;
	}
	
	public int insertAddress(AddressEntity address)
	{
		if(address.getDefaulte())
		{
			addressDAO.updateOtherAddressById(address);
		}
		int result = addressDAO.insertAddressEntity(address);
		return result;
	}
	
	public int updateAddress(AddressEntity address)
	{
		int result = addressDAO.updateAddressById(address);
		if(address.getDefaulte())
		{
			addressDAO.updateOtherAddressById(address);
		}
		return result;
	}
	
	public List<AddressEntity> getAddresses(int uid, int type)
	{
		return addressDAO.queryAddressByUserid(uid, type);
	}
	
	public AddressEntity getAddressById(int id)
	{
		return addressDAO.queryAddressById(id);
	}
	
	public List<ItemEntity> getMainItems()
	{
		return itemDAO.getMainItems();
	}
	
	public List<ProductEntity> getMainProducts()
	{
		return productDAO.getMainProducts();
	}
	
	
	public List<ItemEntity> getItems(ItemForm form, ItemEntity item)
	{
		if(StringUtil.isNotEmpty(form.getType()))
		{
			if(StringUtil.isEqual(form.getType(), "不限"))
			{
				item.setType(null);
			}
			else
			{
				item.setType(form.getType());
			}
		}
		
		if(StringUtil.isNotEmpty(form.getDetailtype()))
		{
			if(StringUtil.isEqual(form.getDetailtype(), "不限"))
			{
				item.setDetailtype(null);
			}
			else
			{
				item.setDetailtype(form.getDetailtype());
			}
		}
		
		if(StringUtil.isNotEmpty(form.getMaterial()))
		{
			if(StringUtil.isEqual(form.getMaterial(), "不限"))
			{
				item.setMaterial(null);
			}
			else
			{
				item.setMaterial(form.getMaterial());
			}
		}
		
		if(StringUtil.isNotEmpty(form.getWorkmaterial()))
		{
			if(StringUtil.isEqual(form.getWorkmaterial(), "不限"))
			{
				item.setWorkmaterial(null);
			}
			else
			{
				item.setWorkmaterial(form.getWorkmaterial());
			}
		}
		
		if(StringUtil.isNotEmpty(form.getBrand()))
		{
			if(StringUtil.isEqual(form.getBrand(), "不限"))
			{
				item.setBrand(null);
			}
			else
			{
				item.setBrand(form.getBrand());
			}
		}

		if(form.getOrder() != 0)
		{
			item.setOrder(form.getOrder());
		}
		
		return itemDAO.getAllItemsByType(item);
	}
	
	public List<String> getItemsType(int type)
	{
		return itemDAO.getItemTypeList(type);
	}
	
	public ItemEntity getItem(int id)
	{
		return itemDAO.queryItemEntityById(id);
	}
	
	public ProductEntity getProduct(int id)
	{
		return productDAO.queryProductEntityById(id);
	}
	
	public List<ProductEntity> getProductsByUserid(int userid)
	{
		return productDAO.queryProductEntityByUserid(userid);
	}
	
	public void addProductCount(int pid, int count)
	{
		ProductEntity product = new ProductEntity();
		product.setId(pid);
		product.setCount(count);
		productDAO.updateProductById(product);
	}
	
	public int insertOrder(OrderEntity order)
	{
		return orderDAO.insertOrderEntity(order);
	}
	
	public int updateOrder(OrderEntity order)
	{
		return orderDAO.updateOrder(order);
	}
	
	public OrderEntity getOrder(int id)
	{
		return orderDAO.queryOrderEntityById(id);
	}
	
	public List<OrderEntity> getOrders(int uid, int type, RowBounds bound)
	{
		return orderDAO.queryOrdersByUserid(uid, type, bound);
	}
	
	public int getOrdersCount(int uid, int type)
	{
		return orderDAO.getOrdersCount(uid, type);
	}
	
	public UserEntity getUser(int id)
	{
		return userDAO.queryUserEntityById(id);
	}
	
	public UserEntity getUserByWxid(String wxid)
	{
		return userDAO.queryUserEntityByWxid(wxid);
	}
	
	public QuestionEntity getQuestion(int id)
	{
		return questionDAO.queryQuestionEntityById(id);
	}
	
	public List<QuestionEntity> getLatestQuestion()
	{
		return questionDAO.getLatestQuestion();
	}
	
	public void setRequestFormWithAccount(RequestForm form, Account accnt)
	{
		if(accnt.isLogin())
		{
			if(StringUtil.isNotEmpty(accnt.getQq()))
			{
				form.setQq(accnt.getQq());
			}
			if(StringUtil.isNotEmpty(accnt.getPhone()))
			{
				form.setPhone(accnt.getPhone());
			}
			if(StringUtil.isNotEmpty(accnt.getArea()))
			{
				form.setArea(accnt.getArea());
			}
		}
	}
	
	public int addRequest(RequestForm form)
	{
		QuickrequestEntity request = new QuickrequestEntity();
		/*if(form.getType() == 0 || StringUtil.isEmpty(form.getContent()) || StringUtil.isEmpty(form.getArea()))
		{
			
		}*/
		request.setType(form.getType());
		request.setContent(form.getContent());
		request.setArea(form.getArea());
		if(StringUtil.isNotEmpty(form.getBrand()))
		{
			request.setBrand(form.getBrand());
		}
		if(StringUtil.isNotEmpty(form.getQq()))
		{
			request.setQq(form.getQq());
		}
		if(StringUtil.isNotEmpty(form.getPhone()))
		{
			request.setPhone(form.getPhone());
		}
		return quickrequestDAO.insertQuickrequestEntity(request);
	}
	
	public List<QuickrequestEntity> getMainRequests(){
		return quickrequestDAO.queryMainQuickrequests();
	}
	
	public List<QuickrequestEntity> getRequestsForwx(){
		return quickrequestDAO.queryQuickrequestsForwx();
	}
	public List<QuickrequestEntity> getAllRequests(int type, RowBounds bound){
		return quickrequestDAO.queryQuickrequests(type,bound);
	}
	
	public void updateRequests(int id)
	{
		quickrequestDAO.updateQuickrequestCountById(id);
	}
	
	public int getAllRequestsCount(int type)
	{
		return quickrequestDAO.getAllRequestsCount(type);
	}
	
	public List<ArticleEntity> getAllarticles()
	{
		return articleDAO.getAllarticles();
	}
	
	public List<StockEntity> getAllstocks()
	{
		return stockDAO.queryAllStock();
	}
	
	public int addStock(StockEntity stock)
	{
		return stockDAO.insertStockEntity(stock);
	}
	
	public boolean isLike(RelationEntity relation)
	{
		return relationDAO.queryRelationByRelation(relation)==null?false:true;
	}
	
	public RelationEntity queryRelationByRelation(RelationEntity relation)
	{
		return relationDAO.queryRelationByRelation(relation);
	}
	
	public void delRelation(RelationForm form)
	{
		RelationEntity relation = new RelationEntity();
		relation.setWxid(form.getWxid());
		relation.setWxid2(form.getWxid2());
		relation.setUserid(form.getUserid());
		relation.setUserid2(form.getUserid2());
		relationDAO.delRelation(relation);
	}
	
	public void addRelation(RelationForm form)
	{
		RelationEntity relation = new RelationEntity();
		relation.setRelation(form.getRelation());
		relation.setWxid(form.getWxid());
		relation.setWxid2(form.getWxid2());
		relation.setUserid(form.getUserid());
		relation.setUserid2(form.getUserid2());
		relation.setWxname(form.getWxname());
		relation.setWxname2(form.getWxname2());
		relation.setWxcompany(form.getWxcompany());
		relation.setWxcompany2(form.getWxcompany2());
		relationDAO.insertRelationEntity(relation);
	}
	
	public List<RelationEntity> getIlike(int userid)
	{
		return relationDAO.queryRelationByUserid(userid);
	}
	
	public List<RelationEntity> getLikeme(int userid2)
	{
		return relationDAO.queryRelationByUserid2(userid2);
	}
	
	public List<UserEntity> searchUser(UserEntity user)
	{
		return userDAO.searchUser(user);
	}
	
	public int getRelationCount(int userid)
	{
		return relationDAO.getRelationCount(userid);
	}
	
	public int queryUserRank(int id)
	{
		return userDAO.queryUserRank(id);
	}
	
	public List<UserEntity> queryUserEntityOrderByScore(Object bound)
	{
		return userDAO.queryUserEntityOrderByScore(bound);
	}
	
	public boolean isFeed(String mywxid)
	{
		try {
			WeixinmpUser user = WeixinService.getInstance2().getUserManagerService().getUser(mywxid);
			return user.subscribe==1?true:false;
		} catch (WeixinException e) {
			logger.error("isFeed error:"+e.toString());
		}
		return false;
	}
	
	public List<CuttingToolEntity> searchCuttingTool(String searchParam)
	{
		Map<String, String> param = new HashMap<String, String>();
		param.put("searchParam", searchParam);
		return ctDAO.searchCuttingTool(param);
	}
	
	public void addstatistics(StatisticsEntity st)
	{
		stDAO.insertStatisticsEntity(st);
	}
	
	public CuttingToolEntity getCuttingToolByid(int id)
	{
		return ctDAO.queryCuttingToolById(id);
	}
	
	public List<CuttingToolEntity> getVersionsBySeries(String sn)
	{
		return ctDAO.getVersionsBySeries(sn);
	}
	
	public List<CuttingToolEntity> getCategorySeries(String code)
	{
		if(CuttingToolsConfiguration.isLeaf(code))
		{
			return ctDAO.querySeriesByCode(code, true);
		}
		else
		{
			return ctDAO.querySeriesByCode(code, false);
		}
		
	}
	
	public String queryCuttingToolsByCode(String param, int type)
	{
		List<CuttingToolEntity> cts = new ArrayList<CuttingToolEntity>();
		String code = "";
		if(type == 1)//code
		{
			cts = ctDAO.queryCuttingToolByCode(param);
		}
		else if(type == 2)//detail
		{
			cts = ctDAO.getVersionsBySeries(param);
		}
		if(cts.size() > 0)
		{
			code = cts.get(0).getCode();
		}
		HashSet<String> brand= new HashSet<String>();
		HashSet<String> material= new HashSet<String>();
		HashSet<String> usage= new HashSet<String>();
		HashSet<String> shank= new HashSet<String>();
		HashSet<String> shanktype= new HashSet<String>();
		HashSet<String> shape= new HashSet<String>();
		HashSet<String> workingtool= new HashSet<String>();
		HashSet<String> workingtype= new HashSet<String>();
		HashSet<String> coatingtype= new HashSet<String>();
		HashSet<String> diameterratio= new HashSet<String>();
		HashSet<String> slotshape= new HashSet<String>();
		HashSet<String> handlenorm= new HashSet<String>();
		HashSet<String> taptype= new HashSet<String>();
		HashSet<String> screwtype= new HashSet<String>();
		HashSet<String> axistype= new HashSet<String>();
		HashSet<String> axisdetail= new HashSet<String>();
		HashSet<String> interfacesize= new HashSet<String>();
		HashSet<String> handledsize= new HashSet<String>();
		HashSet<String> screwsize= new HashSet<String>();
		HashSet<String> screwdistance= new HashSet<String>();
		HashSet<String> accuracy= new HashSet<String>();
		HashSet<String> collet= new HashSet<String>();
		
		HashSet<Integer> ctcount= new HashSet<Integer>();
		HashSet<Double>angle= new HashSet<Double>();
		HashSet<Integer>backangle= new HashSet<Integer>();
		HashSet<Integer>edgeno= new HashSet<Integer>();
		HashSet<Integer>cujing= new HashSet<Integer>();
		HashSet<Integer>direction= new HashSet<Integer>();
		HashSet<Integer>innercooling= new HashSet<Integer>();
		
		HashSet<Double> usefullength= new HashSet<Double>();
		HashSet<Double> pipesize= new HashSet<Double>();
		HashSet<String> diameter= new HashSet<String>();
		HashSet<String> edgelength= new HashSet<String>();
		HashSet<String> totallength= new HashSet<String>();
		HashSet<String> screwangle= new HashSet<String>();
		HashSet<String> rangle= new HashSet<String>();
		HashSet<Double> minworkdiameter= new HashSet<Double>();
		HashSet<Double> thickness= new HashSet<Double>();
		HashSet<Double> maxslotdepth= new HashSet<Double>();
		HashSet<String> necklength= new HashSet<String>();
		HashSet<Double> taper= new HashSet<Double>();
		HashSet<Double> slotwidth= new HashSet<Double>();
		HashSet<Double> pointdiameter= new HashSet<Double>();
		
		HashSet<String> width= new HashSet<String>();
		HashSet<String> height= new HashSet<String>();
		HashSet<String> grooverange= new HashSet<String>();
		HashSet<String> drillrange= new HashSet<String>();
		HashSet<String> screwdirection= new HashSet<String>();
		HashSet<String> workingrange= new HashSet<String>();
		HashSet<String> handledtype= new HashSet<String>();
		HashSet<String> relativescrewct= new HashSet<String>();
		for(int j = 0; j < cts.size(); j++)
		{
			brand.add(cts.get(j).getBrand());
			material.add(cts.get(j).getMaterial());
			usage.add(cts.get(j).getUsage());
			shank.add(cts.get(j).getShank());
			shanktype.add(cts.get(j).getShanktype());
			shape.add(cts.get(j).getShape());
			workingtool.add(cts.get(j).getWorkingtool());
			workingtype.add(cts.get(j).getWorkingtype());
			coatingtype.add(cts.get(j).getCoatingtype());
			diameterratio.add(cts.get(j).getDiameterratio());
			slotshape.add(cts.get(j).getSlotshape());
			handlenorm.add(cts.get(j).getHandlenorm());
			taptype.add(cts.get(j).getTaptype());
			screwtype.add(cts.get(j).getScrewtype());
			axistype.add(cts.get(j).getAxistype());
			axisdetail.add(cts.get(j).getAxisdetail());
			interfacesize.add(cts.get(j).getInterfacesize());
			handledsize.add(cts.get(j).getHandledsize());
			screwsize.add(cts.get(j).getScrewsize());
			screwdistance.add(cts.get(j).getScrewdistance());
			accuracy.add(cts.get(j).getAccuracy());
			collet.add(cts.get(j).getRelativecollet());
			ctcount.add(cts.get(j).getCtcount());
			angle.add(cts.get(j).getAngle());
			backangle.add(cts.get(j).getBackangle());
			edgeno.add(cts.get(j).getEdgeno());
			cujing.add(cts.get(j).getCujing());
			direction.add(cts.get(j).getDirection());
			innercooling.add(cts.get(j).getInnercooling());
			usefullength.add(cts.get(j).getUsefullength());
			pipesize.add(cts.get(j).getPipesize());
			diameter.add(cts.get(j).getDiameter());
			edgelength.add(cts.get(j).getEdgelength());
			totallength.add(cts.get(j).getTotallength());
			screwangle.add(cts.get(j).getScrewangle());
			rangle.add(cts.get(j).getRangle());
			minworkdiameter.add(cts.get(j).getMinworkdiameter());
			thickness.add(cts.get(j).getThickness());
			maxslotdepth.add(cts.get(j).getMaxslotdepth());
			necklength.add(cts.get(j).getNecklength());
			taper.add(cts.get(j).getTaper());
			slotwidth.add(cts.get(j).getSlotwidth());
			pointdiameter.add(cts.get(j).getPointdiameter());
			width.add(cts.get(j).getWidth());
			height.add(cts.get(j).getHeight());
			grooverange.add(cts.get(j).getGrooverange());
			drillrange.add(cts.get(j).getDrillrange());
			screwdirection.add(cts.get(j).getScrewdirection());
			workingrange.add(cts.get(j).getWorkingrange());
			handledtype.add(cts.get(j).getHandledtype());
			relativescrewct.add(cts.get(j).getRelativescrewct());
		}
		String ret = "";
		if(brand.size() > 1 || (!brand.contains(null) && brand.size() == 1))
		{
			ret += "<li set=0 param=\"brand\"><input type=\"hidden\" name=\"brand\" /><h1>品牌<a></a></h1><div class=\"param clearfix\" >";
			Iterator<String> iter = brand.iterator();
			while(iter.hasNext())
			{
				String tmp = iter.next();
				if(StringUtil.isNotEmpty(tmp))
					ret += "<span>"+tmp+"</span>";
			}
			ret += "</div></li>";
		}
		if(angle.size() > 1 || (!angle.contains(0d) && angle.size() == 1))
		{
			ret += "<li set=0 param=\"angle\"><input type=\"hidden\" name=\"angle\" /><h1>主偏角<a></a></h1><div class=\"param clearfix\" >";
			final List<Double> list = new ArrayList<Double>();  
			  
	        for(final double value : angle){  
	            list.add(value);  
	        }  
	        Collections.sort(list);  
	        for(int i = 0; i<list.size();i++)
	        {
	        	if(list.get(i)!=0)
					ret += "<span>"+list.get(i)+"</span>";
	        }
			ret += "</div></li>";
		}
		if(ctcount.size() > 1 || (!ctcount.contains(0) && ctcount.size() == 1))
		{
			ret += "<li set=0 param=\"ctcount\"><input type=\"hidden\" name=\"ctcount\"  /><h1>刀片个数<a></a></h1><div class=\"param clearfix\" >";
			final List<Integer> list = new ArrayList<Integer>();  
			  
	        for(final Integer value : ctcount){  
	            list.add(value);  
	        }  
	        Collections.sort(list);  
	        for(int i = 0; i<list.size();i++)
	        {
	        	if(list.get(i)!=0)
					ret += "<span>"+list.get(i)+"</span>";
	        }
			ret += "</div></li>";
		}
		if(material.size() > 1 || (!material.contains(null) && material.size() == 1))
		{
			ret += "<li set=0 param=\"material\"><input type=\"hidden\" name=\"material\" /><h1>材质<a></a></h1><div class=\"param clearfix\" >";
			Iterator<String> iter = material.iterator();
			while(iter.hasNext())
			{
				String mat = iter.next();
				if(StringUtil.isNotEmpty(mat))
					ret += "<span>"+mat+"</span>";
			}
			ret += "</div></li>";
		}
		if(diameter.size() > 1 || (!diameter.contains(null) && diameter.size() == 1))
		{
			ret += "<li set=0 param=\"diameter\"><input type=\"hidden\" name=\"diameter\" /><h1>直径<a></a></h1><div class=\"param clearfix\" >";
			List<List<String>> list = CuttingToolsConfiguration.sortSize(diameter);
			final List<String> list1 = list.get(0);
			final List<String> list2 = list.get(1);
			ret += CuttingToolsConfiguration.aggregateSpan(list1);
			if(list2.size() > 0 && list1.size() > 0)
			{
				ret += "<em></em>";
			}
			for(int i = 0; i<list2.size();i++)
	        {
				ret += "<span>"+list2.get(i)+"</span>";
	        }
			ret += "</div></li>";
		}
		if(usefullength.size() > 1 || (!usefullength.contains(0d) && usefullength.size() == 1))
		{
			ret += "<li set=0 param=\"usefullength\"><input type=\"hidden\" name=\"usefullength\" /><h1>有效长<a></a></h1><div class=\"param clearfix\" >";
			final List<Double> list = new ArrayList<Double>();  
	        for(final Double value : usefullength){  
	            list.add(value);  
	        }  
	        Collections.sort(list);  
	        for(int i = 0; i<list.size();i++)
	        {
	        	if(list.get(i)!=0)
					ret += "<span>"+list.get(i)+"</span>";
	        }
			ret += "</div></li>";
		}
		if(usage.size() > 1 || (!usage.contains(null) && usage.size() == 1))
		{
			ret += "<li set=0 param=\"usage\"><input type=\"hidden\" name=\"usage\" /><h1>加工用途<a></a></h1><div class=\"param clearfix\" >";
			List<String> list = CuttingToolsConfiguration.splitWorkingtoolOrUsage(usage);
			for(int i = 0;i<list.size();i++)
			{
				ret += "<span>"+list.get(i)+"</span>";
			}
			ret += "</div></li>";
		}
		if(cujing.size() > 1 || (!cujing.contains(0) && cujing.size() == 1))
		{
			ret += "<li set=0 param=\"cujing\"><input type=\"hidden\" name=\"cujing\" /><h1>光洁度<a></a></h1><div class=\"param clearfix\" >";
			Iterator<Integer> iter = cujing.iterator();
			while(iter.hasNext())
			{
				int tmp = iter.next();
				if(tmp == 2)
				{
					ret += "<span>精加工</span>";
				}
				else if(tmp == 3)
				{
					ret += "<span>粗加工</span>";
				}
				else if(tmp == 1)
				{
					ret += "<span>一般加工</span>";
				}
			}
			ret += "</div></li>";
		}
		if(pipesize.size() > 1 || (!pipesize.contains(0d) && pipesize.size() == 1))
		{
			ret += "<li set=0 param=\"pipesize\"><input type=\"hidden\" name=\"pipesize\" /><h1>安装孔尺寸<a></a></h1><div class=\"param clearfix\" >";
			final List<Double> list = new ArrayList<Double>();  
	        for(final Double value : pipesize){  
	            list.add(value);  
	        }  
	        Collections.sort(list);  
	        for(int i = 0; i<list.size();i++)
	        {
	        	if(list.get(i)!=0)
					ret += "<span>"+list.get(i)+"</span>";
	        }
			ret += "</div></li>";
		}
		if(shank.size() > 1 || (!shank.contains(null) && shank.size() == 1))
		{
			ret += "<li set=0 param=\"shank\"><input type=\"hidden\" name=\"shank\" /><h1>柄径<a></a></h1><div class=\"param clearfix\" >";
			List<List<String>> list = CuttingToolsConfiguration.sortSize(shank);
			final List<String> list1 = list.get(0);
			final List<String> list2 = list.get(1);
			final List<String> list3 = list.get(2);
			for(int i = 0; i<list1.size();i++)
	        {
				ret += "<span>"+list1.get(i)+"</span>";
	        }
			if(list2.size() > 0 && list1.size() > 0)
			{
				ret += "<em></em>";
			}
			for(int i = 0; i<list2.size();i++)
	        {
				ret += "<span>"+list2.get(i)+"</span>";
	        }
			if(list3.size() > 0 && (list2.size() > 0 || list1.size() > 0))
			{
				ret += "<em></em>";
			}
			for(int i = 0; i<list3.size();i++)
	        {
				ret += "<span>"+list3.get(i)+"</span>";
	        }
			ret += "</div></li>";
		}
		if(shanktype.size() > 1 || (!shanktype.contains(null) && shanktype.size() == 1))
		{
			ret += "<li set=0 param=\"shanktype\"><input type=\"hidden\" name=\"shanktype\" /><h1>柄部类型<a></a></h1><div class=\"param clearfix\" >";
			List<String> list = CuttingToolsConfiguration.splitWorkingtoolOrUsage(shanktype);
			for(int i = 0;i<list.size();i++)
			{
				ret += "<span>"+list.get(i)+"</span>";
			}
			ret += "</div></li>";
		}
		if(shape.size() > 1 || (!shape.contains(null) && shape.size() == 1))
		{
			ret += "<li set=0 param=\"shape\"><input type=\"hidden\" name=\"shape\" /><h1>形状(首字母)<a></a></h1><div class=\"param clearfix\" >";
			Iterator<String> iter = shape.iterator();
			while(iter.hasNext())
			{
				String str = iter.next();
				if(StringUtil.isNotEmpty(str))
				{
					if(StringUtil.isEqual(str, "H"))
					{
						ret += "<span>H(正六角形)</span>";
					}
					else if(StringUtil.isEqual(str, "O"))
					{
						ret += "<span>O(八角形)</span>";
					}
					else if(StringUtil.isEqual(str, "P"))
					{
						ret += "<span>P(五角型)</span>";
					}
					else if(StringUtil.isEqual(str, "S"))
					{
						ret += "<span>S(正方形)</span>";
					}
					else if(StringUtil.isEqual(str, "T"))
					{
						ret += "<span>T(三角形)</span>";
					}
					else if(StringUtil.isEqual(str, "C"))
					{
						ret += "<span>C(菱形80°)</span>";
					}
					else if(StringUtil.isEqual(str, "D"))
					{
						ret += "<span>D(菱形55°)</span>";
					}
					else if(StringUtil.isEqual(str, "E"))
					{
						ret += "<span>E(菱形75°)</span>";
					}
					else if(StringUtil.isEqual(str, "F"))
					{
						ret += "<span>F(菱形50°)</span>";
					}
					else if(StringUtil.isEqual(str, "M"))
					{
						ret += "<span>M(菱形86°)</span>";
					}
					else if(StringUtil.isEqual(str, "V"))
					{
						ret += "<span>V(菱形35°)</span>";
					}
					else if(StringUtil.isEqual(str, "W"))
					{
						ret += "<span>W(不等角六角形)</span>";
					}
					else if(StringUtil.isEqual(str, "L"))
					{
						ret += "<span>L(长方形90°)</span>";
					}
					else if(StringUtil.isEqual(str, "A"))
					{
						ret += "<span>A(四边形顶角85°)</span>";
					}
					else if(StringUtil.isEqual(str, "B"))
					{
						ret += "<span>B(四边形顶角82°)</span>";
					}
					else if(StringUtil.isEqual(str, "K"))
					{
						ret += "<span>K(四边形顶角55°)</span>";
					}
					else if(StringUtil.isEqual(str, "R"))
					{
						ret += "<span>R(圆形)</span>";
					}
					else
					{
						ret += "<span>非通用</span>";
					}
				}
			}
			ret += "</div></li>";
		}
		if(backangle.size() > 1 || (!backangle.contains(1000) && backangle.size() == 1))
		{
			ret += "<li set=0 param=\"backangle\"><input type=\"hidden\" name=\"backangle\" /><h1>后角<a></a></h1><div class=\"param clearfix\" >";
			final List<Integer> list = new ArrayList<Integer>();  
	        for(final Integer value : backangle){  
	            list.add(value);  
	        }  
	        Collections.sort(list);  
	        for(int i = 0; i<list.size();i++)
	        {
	        	if(list.get(i)!=1000)
	        	{
	        		switch(list.get(i))
	        		{
	        		case 3:
	        			ret += "<span>A(3°)</span>";
	        			break;
	        		case 5:
	        			ret += "<span>B(5°)</span>";
	        			break;
	        		case 7:
	        			ret += "<span>C(7°)</span>";
	        			break;
	        		case 15:
	        			ret += "<span>D(15°)</span>";
	        			break;
	        		case 20:
	        			ret += "<span>E(20°)</span>";
	        			break;
	        		case 25:
	        			ret += "<span>F(25°)</span>";
	        			break;
	        		case 30:
	        			ret += "<span>G(30°)</span>";
	        			break;
	        		case 0:
	        			ret += "<span>N(0°)</span>";
	        			break;
	        		case 11:
	        			ret += "<span>P(11°)</span>";
	        			break;
	        		}
	        	}
	        }
			ret += "</div></li>";
		}
		if(workingtool.size() > 1 || (!workingtool.contains(null) && workingtool.size() == 1))
		{
			ret += "<li set=0 param=\"workingtool\"><input type=\"hidden\" name=\"workingtool\" /><h1>适用工件<a></a></h1><div class=\"param clearfix\" >";
			List<String> list = CuttingToolsConfiguration.splitWorkingtoolOrUsage(workingtool);
			for(int i = 0;i<list.size();i++)
			{
				ret += "<span>"+list.get(i)+"</span>";
			}
			ret += "</div></li>";
		}
		if(workingtype.size() > 1 || (!workingtype.contains(null) && workingtype.size() == 1))
		{
			ret += "<li set=0 param=\"workingtype\"><input type=\"hidden\" name=\"workingtype\" /><h1>加工类型<a></a></h1><div class=\"param clearfix\" >";
			List<String> list = CuttingToolsConfiguration.splitWorkingtoolOrUsage(workingtype);
			for(int i = 0;i<list.size();i++)
			{
				ret += "<span>"+list.get(i)+"</span>";
			}
			ret += "</div></li>";
		}
		if(edgeno.size() > 1 || (!edgeno.contains(0) && edgeno.size() == 1))
		{
			ret += "<li set=0 param=\"edgeno\"><input type=\"hidden\" name=\"edgeno\" /><h1>刃数<a></a></h1><div class=\"param clearfix\" >";
			final List<Integer> list = new ArrayList<Integer>();  
	        for(final Integer value : edgeno){  
	            list.add(value);  
	        }  
	        Collections.sort(list);  
	        for(int i = 0; i<list.size();i++)
	        {
	        	if(list.get(i)!=0)
					ret += "<span>"+list.get(i)+"</span>";
	        }
			ret += "</div></li>";
		}
		if(edgelength.size() > 1 || (!edgelength.contains(null) && edgelength.size() == 1))
		{
			ret += "<li set=0 param=\"edgelength\"><input type=\"hidden\" name=\"edgelength\" /><h1>刃长<a></a></h1><div class=\"param clearfix\" >";
			List<List<String>> list = CuttingToolsConfiguration.sortSize(edgelength);
			final List<String> list1 = list.get(0);
			final List<String> list2 = list.get(1);
			for(int i = 0; i<list1.size();i++)
	        {
				ret += "<span>"+list1.get(i)+"</span>";
	        }
			if(list2.size() > 0 && list1.size() > 0)
			{
				ret += "<em></em>";
			}
			for(int i = 0; i<list2.size();i++)
	        {
				ret += "<span>"+list2.get(i)+"</span>";
	        }
			ret += "</div></li>";
		}
		if(totallength.size() > 1 || (!totallength.contains(null) && totallength.size() == 1))
		{
			ret += "<li set=0 param=\"totallength\"><input type=\"hidden\" name=\"totallength\" /><h1>总长<a></a></h1><div class=\"param clearfix\" >";
			List<List<String>> list = CuttingToolsConfiguration.sortSize(totallength);
			final List<String> list1 = list.get(0);
			final List<String> list2 = list.get(1);
			for(int i = 0; i<list1.size();i++)
	        {
				ret += "<span>"+list1.get(i)+"</span>";
	        }
			if(list2.size() > 0 && list1.size() > 0)
			{
				ret += "<em></em>";
			}
			for(int i = 0; i<list2.size();i++)
	        {
				ret += "<span>"+list2.get(i)+"</span>";
	        }
			ret += "</div></li>";
		}
		if(screwangle.size() > 1 || (!screwangle.contains(null) && screwangle.size() == 1))
		{
			ret += "<li set=0 param=\"screwangle\"><input type=\"hidden\" name=\"screwangle\" /><h1>螺旋角<a></a></h1><div class=\"param clearfix\" >";
			Iterator<String> iter = screwangle.iterator();
			while(iter.hasNext())
			{
				String tmp = iter.next();
				if(StringUtil.isNotEmpty(tmp))
					ret += "<span>"+tmp+"</span>";
			}
			ret += "</div></li>";
		}
		if(coatingtype.size() >1 || (!coatingtype.contains(null) && coatingtype.size() == 1))
		{
			ret += "<li set=0 param=\"coatingtype\"><input type=\"hidden\" name=\"coatingtype\" /><h1>涂层种类<a></a></h1><div class=\"param clearfix\" >";
			Iterator<String> iter = coatingtype.iterator();
			while(iter.hasNext())
			{
				String tmp = iter.next();
				if(StringUtil.isNotEmpty(tmp))
					ret += "<span>"+tmp+"</span>";
			}
			ret += "</div></li>";
		}
		if(rangle.size() >1 || (!rangle.contains(null) && rangle.size() == 1))
		{
			ret += "<li set=0 param=\"rangle\"><input type=\"hidden\" name=\"rangle\" /><h1>R角<a></a></h1><div class=\"param clearfix\" >";
			List<List<String>> list = CuttingToolsConfiguration.sortSize(rangle);
			final List<String> list1 = list.get(0);
			final List<String> list2 = list.get(1);
			for(int i = 0; i<list1.size();i++)
	        {
				ret += "<span>"+list1.get(i)+"</span>";
	        }
			if(list2.size() > 0 && list1.size() > 0)
			{
				ret += "<em></em>";
			}
			for(int i = 0; i<list2.size();i++)
	        {
				ret += "<span>"+list2.get(i)+"</span>";
	        }
			ret += "</div></li>";
		}
		if(direction.size() > 1 || (!direction.contains(0) && direction.size() == 1))
		{
			ret += "<li set=0 param=\"direction\"><input type=\"hidden\" name=\"direction\" /><h1>方向<a></a></h1><div class=\"param clearfix\" >";
			Iterator<Integer> iter = direction.iterator();
			while(iter.hasNext())
			{
				int tmp = iter.next();
				if(tmp == 2)
				{
					ret += "<span>左手</span>";
				}
				else if(tmp == 3)
				{
					ret += "<span>右手</span>";
				}
				else if(tmp == 1)
				{
					ret += "<span>通用槽</span>";
				}
			}
			ret += "</div></li>";
		}
		if(minworkdiameter.size() > 1 || (!minworkdiameter.contains(0d) && minworkdiameter.size() == 1))
		{
			ret += "<li set=0 param=\"minworkdiameter\"><input type=\"hidden\" name=\"minworkdiameter\" /><h1>最小加工直径<a></a></h1><div class=\"param clearfix\" >";
			final List<Double> list = new ArrayList<Double>();  
	        for(final Double value : minworkdiameter){  
	            list.add(value);  
	        }  
	        Collections.sort(list);  
	        for(int i = 0; i<list.size();i++)
	        {
	        	if(list.get(i)!=0)
					ret += "<span>"+list.get(i)+"</span>";
	        }
			ret += "</div></li>";
		}
		if(innercooling.size() >1 || (!innercooling.contains(0) && innercooling.size() == 1))
		{
			ret += "<li set=0 param=\"innercooling\"><input type=\"hidden\" name=\"innercooling\" /><h1>冷却方式<a></a></h1><div class=\"param clearfix\" >";
			Iterator<Integer> iter = innercooling.iterator();
			while(iter.hasNext())
			{
				int tmp = iter.next();
				if(tmp == 2)
				{	
					ret += "<span>内冷</span>";
				}
				else if(tmp == 3)
				{
					ret += "<span>外冷</span>";
				}
			}
			ret += "</div></li>";
		}
		if(diameterratio.size() >1 || (!diameterratio.contains(null) && diameterratio.size() == 1))
		{
			ret += "<li set=0 param=\"diameterratio\"><input type=\"hidden\" name=\"diameterratio\" /><h1>倍径比<a></a></h1><div class=\"param clearfix\" >";
			List<String> list = CuttingToolsConfiguration.sortDiameterratio(diameterratio);
			for(int i = 0;i<list.size();i++)
			{
				String tmp = list.get(i);
				if(StringUtil.isNotEmpty(tmp))
					ret += "<span>"+tmp+"</span>";
			}
			ret += "</div></li>";
		}
		if(slotshape.size() >1 || (!slotshape.contains(null) && slotshape.size() == 1))
		{
			ret += "<li set=0 param=\"slotshape\"><input type=\"hidden\" name=\"slotshape\" /><h1>槽型状<a></a></h1><div class=\"param clearfix\" >";
			Iterator<String> iter = slotshape.iterator();
			while(iter.hasNext())
			{
				String tmp = iter.next();
				if(StringUtil.isNotEmpty(tmp))
					ret += "<span>"+tmp+"</span>";
			}
			ret += "</div></li>";
		}
		if(handlenorm.size() >1 || (!handlenorm.contains(null) && handlenorm.size() == 1))
		{
			ret += "<li set=0 param=\"handlenorm\"><input type=\"hidden\" name=\"handlenorm\" /><h1>柄部规格<a></a></h1><div class=\"param clearfix\" >";
			Iterator<String> iter = handlenorm.iterator();
			while(iter.hasNext())
			{
				String tmp = iter.next();
				if(StringUtil.isNotEmpty(tmp))
					ret += "<span>"+tmp+"</span>";
			}
			ret += "</div></li>";
		}
		if(taptype.size() >1 || (!taptype.contains(null) && taptype.size() == 1))
		{
			ret += "<li set=0 param=\"taptype\"><input type=\"hidden\" name=\"taptype\" /><h1>丝锥类型<a></a></h1><div class=\"param clearfix\" >";
			Iterator<String> iter = taptype.iterator();
			while(iter.hasNext())
			{
				String tmp = iter.next();
				if(StringUtil.isNotEmpty(tmp))
					ret += "<span>"+tmp+"</span>";
			}
			ret += "</div></li>";
		}
		if(screwtype.size() >1 || (!screwtype.contains(null) && screwtype.size() == 1))
		{
			ret += "<li set=0 param=\"screwtype\"><input type=\"hidden\" name=\"screwtype\" /><h1>螺纹种类<a></a></h1><div class=\"param clearfix\" >";
			Iterator<String> iter = screwtype.iterator();
			while(iter.hasNext())
			{
				String tmp = iter.next();
				if(StringUtil.isNotEmpty(tmp))
					ret += "<span>"+tmp+"</span>";
			}
			ret += "</div></li>";
		}
		if(axistype.size() >1 || (!axistype.contains(null) && axistype.size() == 1))
		{
			ret += "<li set=0 param=\"axistype\"><input type=\"hidden\" name=\"axistype\" /><h1>主轴类型<a></a></h1><div class=\"param clearfix\" >";
			Iterator<String> iter = axistype.iterator();
			while(iter.hasNext())
			{
				String tmp = iter.next();
				if(StringUtil.isNotEmpty(tmp))
					ret += "<span>"+tmp+"</span>";
			}
			ret += "</div></li>";
		}
		if(axisdetail.size() >1 || (!axisdetail.contains(null) && axisdetail.size() == 1))
		{
			ret += "<li set=0 param=\"axisdetail\"><input type=\"hidden\" name=\"axisdetail\" /><h1>主轴细分<a></a></h1><div class=\"param clearfix\" >";
			Iterator<String> iter = axisdetail.iterator();
			while(iter.hasNext())
			{
				String tmp = iter.next();
				if(StringUtil.isNotEmpty(tmp))
					ret += "<span>"+tmp+"</span>";
			}
			ret += "</div></li>";
		}
		if(thickness.size() >1 || (!thickness.contains(0d) && thickness.size() == 1))
		{
			ret += "<li set=0 param=\"thickness\"><input type=\"hidden\" name=\"thickness\" /><h1>厚度<a></a></h1><div class=\"param clearfix\" >";
			final List<Double> list = new ArrayList<Double>();  
	        for(final Double value : thickness){  
	            list.add(value);  
	        }  
	        Collections.sort(list);  
	        for(int i = 0; i<list.size();i++)
	        {
	        	if(list.get(i)!=0)
					ret += "<span>"+list.get(i)+"</span>";
	        }
			ret += "</div></li>";
		}
		if(maxslotdepth.size() >1 || (!maxslotdepth.contains(0d) && maxslotdepth.size() == 1))
		{
			ret += "<li set=0 param=\"maxslotdepth\"><input type=\"hidden\" name=\"maxslotdepth\" /><h1>最大槽深<a></a></h1><div class=\"param clearfix\" >";
			final List<Double> list = new ArrayList<Double>();  
	        for(final Double value : maxslotdepth){  
	            list.add(value);  
	        }  
	        Collections.sort(list);  
	        for(int i = 0; i<list.size();i++)
	        {
	        	if(list.get(i)!=0)
					ret += "<span>"+list.get(i)+"</span>";
	        }
			ret += "</div></li>";
		}
		if(taper.size() >1 || (!taper.contains(0d) && taper.size() == 1))
		{
			ret += "<li set=0 param=\"taper\"><input type=\"hidden\" name=\"taper\" /><h1>锥度<a></a></h1><div class=\"param clearfix\" >";
			final List<Double> list = new ArrayList<Double>();  
	        for(final Double value : taper){  
	            list.add(value);  
	        }  
	        Collections.sort(list);  
	        for(int i = 0; i<list.size();i++)
	        {
	        	if(list.get(i)!=0)
					ret += "<span>"+list.get(i)+"</span>";
	        }
			ret += "</div></li>";
		}
		if(slotwidth.size() >1 || (!slotwidth.contains(0d) && slotwidth.size() == 1))
		{
			ret += "<li set=0 param=\"slotwidth\"><input type=\"hidden\" name=\"slotwidth\" /><h1>槽宽<a></a></h1><div class=\"param clearfix\" >";
			final List<Double> list = new ArrayList<Double>();  
	        for(final Double value : slotwidth){  
	            list.add(value);  
	        }  
	        Collections.sort(list);  
	        for(int i = 0; i<list.size();i++)
	        {
	        	if(list.get(i)!=0)
					ret += "<span>"+list.get(i)+"</span>";
	        }
			ret += "</div></li>";
		}
		if(pointdiameter.size() >1 || (!pointdiameter.contains(0d) && pointdiameter.size() == 1))
		{
			ret += "<li set=0 param=\"pointdiameter\"><input type=\"hidden\" name=\"pointdiameter\" /><h1>刀尖直径<a></a></h1><div class=\"param clearfix\" >";
			final List<Double> list = new ArrayList<Double>();  
	        for(final Double value : pointdiameter){  
	            list.add(value);  
	        }  
	        Collections.sort(list);  
	        for(int i = 0; i<list.size();i++)
	        {
	        	if(list.get(i)!=0)
					ret += "<span>"+list.get(i)+"</span>";
	        }
			ret += "</div></li>";
		}
		if(handledsize.size() >1 || (!handledsize.contains(null) && handledsize.size() == 1))
		{
			ret += "<li set=0 param=\"handledsize\"><input type=\"hidden\" name=\"handledsize\" /><h1>可加持尺寸<a></a></h1><div class=\"param clearfix\" >";
			Iterator<String> iter = handledsize.iterator();
			while(iter.hasNext())
			{
				String tmp = iter.next();
				if(StringUtil.isNotEmpty(tmp))
					ret += "<span>"+tmp+"</span>";
			}
			ret += "</div></li>";
		}
		if(screwsize.size() >1 || (!screwsize.contains(null) && screwsize.size() == 1))
		{
			ret += "<li set=0 param=\"screwsize\"><input type=\"hidden\" name=\"screwsize\" /><h1>螺纹尺寸<a></a></h1><div class=\"param clearfix\" >";
			Iterator<String> iter = screwsize.iterator();
			while(iter.hasNext())
			{
				String tmp = iter.next();
				if(StringUtil.isNotEmpty(tmp))
					ret += "<span>"+tmp+"</span>";
			}
			ret += "</div></li>";
		}
		if(screwdistance.size() >1 || (!screwdistance.contains(null) && screwdistance.size() == 1))
		{
			ret += "<li set=0 param=\"screwdistance\"><input type=\"hidden\" name=\"screwdistance\" /><h1>螺距<a></a></h1><div class=\"param clearfix\" >";
			final List<Double> list = new ArrayList<Double>();  
	        for(final String value : screwdistance){  
	            list.add(Double.valueOf(value));  
	        }  
	        Collections.sort(list);  
	        for(int i = 0; i<list.size();i++)
	        {
	        	if(list.get(i)!=0)
					ret += "<span>"+list.get(i)+"</span>";
	        }
			ret += "</div></li>";
		}
		if(accuracy.size() >1 || (!accuracy.contains(null) && accuracy.size() == 1))
		{
			ret += "<li set=0 param=\"accuracy\"><input type=\"hidden\" name=\"accuracy\" /><h1>精度<a></a></h1><div class=\"param clearfix\" >";
			Iterator<String> iter = accuracy.iterator();
			while(iter.hasNext())
			{
				String tmp = iter.next();
				if(StringUtil.isNotEmpty(tmp))
					ret += "<span>"+tmp+"</span>";
			}
			ret += "</div></li>";
		}
		if(collet.size() >1 || (!collet.contains(null) && collet.size() == 1))
		{
			ret += "<li set=0 param=\"collet\"><input type=\"hidden\" name=\"collet\" /><h1>对应筒夹<a></a></h1><div class=\"param clearfix\" >";
			Iterator<String> iter = collet.iterator();
			while(iter.hasNext())
			{
				String tmp = iter.next();
				if(StringUtil.isNotEmpty(tmp))
					ret += "<span>"+tmp+"</span>";
			}
			ret += "</div></li>";
		}
		if(interfacesize.size() >1 || (!interfacesize.contains(null) && interfacesize.size() == 1))
		{
			ret += "<li set=0 param=\"interfacesize\"><input type=\"hidden\" name=\"interfacesize\" /><h1>接口尺寸<a></a></h1><div class=\"param clearfix\" >";
			Iterator<String> iter = interfacesize.iterator();
			while(iter.hasNext())
			{
				String tmp = iter.next();
				if(StringUtil.isNotEmpty(tmp))
					ret += "<span>"+tmp+"</span>";
			}
			ret += "</div></li>";
		}
		if(necklength.size() >1 || (!necklength.contains(null) && necklength.size() == 1))
		{
			ret += "<li set=0 param=\"necklength\"><input type=\"hidden\" name=\"necklength\" /><h1>颈长<a></a></h1><div class=\"param clearfix\" >";
			List<List<String>> list = CuttingToolsConfiguration.sortSize(necklength);
			final List<String> list1 = list.get(0);
			final List<String> list2 = list.get(1);
			for(int i = 0; i<list1.size();i++)
	        {
				ret += "<span>"+list1.get(i)+"</span>";
	        }
			if(list2.size() > 0 && list1.size() > 0)
			{
				ret += "<em></em>";
			}
			for(int i = 0; i<list2.size();i++)
	        {
				ret += "<span>"+list2.get(i)+"</span>";
	        }
			ret += "</div></li>";
		}
		if(width.size() > 1 || (!width.contains(null) && width.size() == 1))
		{
			ret += "<li set=0 param=\"width\"><input type=\"hidden\" name=\"width\" /><h1>宽度<a></a></h1><div class=\"param clearfix\" >";
			List<List<String>> list = CuttingToolsConfiguration.sortSize(width);
			final List<String> list1 = list.get(0);
			final List<String> list2 = list.get(1);
			final List<String> list3 = list.get(2);
			for(int i = 0; i<list1.size();i++)
	        {
				ret += "<span>"+list1.get(i)+"</span>";
	        }
			if(list2.size() > 0 && list1.size() > 0)
			{
				ret += "<em></em>";
			}
			for(int i = 0; i<list2.size();i++)
	        {
				ret += "<span>"+list2.get(i)+"</span>";
	        }
			if(list3.size() > 0 && (list2.size() > 0 || list1.size() > 0))
			{
				ret += "<em></em>";
			}
			for(int i = 0; i<list3.size();i++)
	        {
				ret += "<span>"+list3.get(i)+"</span>";
	        }
			ret += "</div></li>";
		}
		if(height.size() > 1 || (!height.contains(null) && height.size() == 1))
		{
			ret += "<li set=0 param=\"height\"><input type=\"hidden\" name=\"height\" /><h1>高度<a></a></h1><div class=\"param clearfix\" >";
			List<List<String>> list = CuttingToolsConfiguration.sortSize(height);
			final List<String> list1 = list.get(0);
			final List<String> list2 = list.get(1);
			final List<String> list3 = list.get(2);
			for(int i = 0; i<list1.size();i++)
	        {
				ret += "<span>"+list1.get(i)+"</span>";
	        }
			if(list2.size() > 0 && list1.size() > 0)
			{
				ret += "<em></em>";
			}
			for(int i = 0; i<list2.size();i++)
	        {
				ret += "<span>"+list2.get(i)+"</span>";
	        }
			if(list3.size() > 0 && (list2.size() > 0 || list1.size() > 0))
			{
				ret += "<em></em>";
			}
			for(int i = 0; i<list3.size();i++)
	        {
				ret += "<span>"+list3.get(i)+"</span>";
	        }
			ret += "</div></li>";
		}
		if(screwdirection.size() > 1 || (!screwdirection.contains(null) && screwdirection.size() == 1))
		{
			ret += "<li set=0 param=\"screwdirection\"><input type=\"hidden\" name=\"screwdirection\" /><h1>螺纹方向<a></a></h1><div class=\"param clearfix\" >";
			Iterator<String> iter = screwdirection.iterator();
			while(iter.hasNext())
			{
				String tmp = iter.next();
				if(StringUtil.isNotEmpty(tmp))
					ret += "<span>"+tmp+"</span>";
			}
			ret += "</div></li>";
		}
		if(grooverange.size() > 1 || (!grooverange.contains(null) && grooverange.size() == 1))
		{
			ret += "<li set=0 param=\"grooverange\"><input type=\"hidden\" name=\"grooverange\" /><h1>切槽范围<a></a></h1><div class=\"param clearfix\" >";
			Iterator<String> iter = grooverange.iterator();
			while(iter.hasNext())
			{
				String tmp = iter.next();
				if(StringUtil.isNotEmpty(tmp))
					ret += "<span>"+tmp+"</span>";
			}
			ret += "</div></li>";
		}
		if(drillrange.size() > 1 || (!drillrange.contains(null) && drillrange.size() == 1))
		{
			ret += "<li set=0 param=\"drillrange\"><input type=\"hidden\" name=\"drillrange\" /><h1>钻孔范围<a></a></h1><div class=\"param clearfix\" >";
			Iterator<String> iter = drillrange.iterator();
			while(iter.hasNext())
			{
				String tmp = iter.next();
				if(StringUtil.isNotEmpty(tmp))
					ret += "<span>"+tmp+"</span>";
			}
			ret += "</div></li>";
		}
		if(workingrange.size() > 1 || (!workingrange.contains(null) && workingrange.size() == 1))
		{
			ret += "<li set=0 param=\"workingrange\"><input type=\"hidden\" name=\"workingrange\" /><h1>镗孔范围<a></a></h1><div class=\"param clearfix\" >";
			List<String> list = CuttingToolsConfiguration.sortWorkingrange(workingrange);
			for(int i = 0;i<list.size();i++)
			{
				String tmp = list.get(i);
				if(StringUtil.isNotEmpty(tmp))
					ret += "<span>"+tmp+"</span>";
			}
			ret += "</div></li>";
			
		}
		
		if(handledtype.size() > 1 || (!handledtype.contains(null) && handledtype.size() == 1))
		{
			ret += "<li set=0 param=\"handledtype\"><input type=\"hidden\" name=\"handledtype\" /><h1>夹持方式<a></a></h1><div class=\"param clearfix\" >";
			Iterator<String> iter = handledtype.iterator();
			while(iter.hasNext())
			{
				String tmp = iter.next();
				if(StringUtil.isNotEmpty(tmp))
					ret += "<span>"+tmp+"</span>";
			}
			ret += "</div></li>";
		}
		if(relativescrewct.size() > 1 || (!relativescrewct.contains(null) && relativescrewct.size() == 1))
		{
			ret += "<li set=0 param=\"relativescrewct\"><input type=\"hidden\" name=\"relativescrewct\" /><h1>匹配螺纹刀片<a></a></h1><div class=\"param clearfix\" >";
			Iterator<String> iter = relativescrewct.iterator();
			while(iter.hasNext())
			{
				String tmp = iter.next();
				if(StringUtil.isNotEmpty(tmp))
					ret += "<span>"+tmp+"</span>";
			}
			ret += "</div></li>";
		}
		return CuttingToolsConfiguration.orderParams(ret,code);
	}
	
	public Map<String,List<String>> getSearchParamMap(CuttingToolEntity ct)
	{
		List<CuttingToolEntity> cts = ctDAO.queryCuttingToolByCt(ct);
		HashSet<String> brand= new HashSet<String>();
		HashSet<String> material= new HashSet<String>();
		HashSet<String> usage= new HashSet<String>();
		HashSet<String> shank= new HashSet<String>();
		HashSet<String> shanktype= new HashSet<String>();
		HashSet<String> shape= new HashSet<String>();
		HashSet<String> workingtool= new HashSet<String>();
		HashSet<String> workingtype= new HashSet<String>();
		
		HashSet<String> coatingtype= new HashSet<String>();
		HashSet<String> diameterratio= new HashSet<String>();
		HashSet<String> slotshape= new HashSet<String>();
		HashSet<String> handlenorm= new HashSet<String>();
		HashSet<String> taptype= new HashSet<String>();
		HashSet<String> screwtype= new HashSet<String>();
		HashSet<String> axistype= new HashSet<String>();
		HashSet<String> axisdetail= new HashSet<String>();
		HashSet<String> interfacesize= new HashSet<String>();
		HashSet<String> handledsize= new HashSet<String>();
		HashSet<String> screwsize= new HashSet<String>();
		HashSet<String> screwdistance= new HashSet<String>();
		HashSet<String> accuracy= new HashSet<String>();
		HashSet<String> collet= new HashSet<String>();
		
		HashSet<Integer> ctcount= new HashSet<Integer>();
		HashSet<Double>angle= new HashSet<Double>();
		HashSet<Integer>backangle= new HashSet<Integer>();
		HashSet<Integer>edgeno= new HashSet<Integer>();
		HashSet<Integer>cujing= new HashSet<Integer>();
		HashSet<Integer>direction= new HashSet<Integer>();
		HashSet<Integer>innercooling= new HashSet<Integer>();
		
		HashSet<Double> usefullength= new HashSet<Double>();
		HashSet<Double> pipesize= new HashSet<Double>();
		HashSet<String> diameter= new HashSet<String>();
		HashSet<String> edgelength= new HashSet<String>();
		HashSet<String> totallength= new HashSet<String>();
		HashSet<String> screwangle= new HashSet<String>();
		HashSet<String> rangle= new HashSet<String>();
		HashSet<Double> minworkdiameter= new HashSet<Double>();
		HashSet<Double> thickness= new HashSet<Double>();
		HashSet<Double> maxslotdepth= new HashSet<Double>();
		HashSet<Double> maxbore= new HashSet<Double>();
		HashSet<Double> minbore= new HashSet<Double>();
		HashSet<String> necklength= new HashSet<String>();
		HashSet<Double> taper= new HashSet<Double>();
		HashSet<Double> slotwidth= new HashSet<Double>();
		HashSet<Double> pointdiameter= new HashSet<Double>();
		
		HashSet<String> width= new HashSet<String>();
		HashSet<String> height= new HashSet<String>();
		HashSet<String> grooverange= new HashSet<String>();
		HashSet<String> drillrange= new HashSet<String>();
		HashSet<String> screwdirection= new HashSet<String>();
		HashSet<String> workingrange= new HashSet<String>();
		HashSet<String> handledtype= new HashSet<String>();
		HashSet<String> relativescrewct= new HashSet<String>();
		for(int j = 0; j < cts.size(); j++)
		{
			brand.add(cts.get(j).getBrand());
			material.add(cts.get(j).getMaterial());
			usage.add(cts.get(j).getUsage());
			shank.add(cts.get(j).getShank());
			shanktype.add(cts.get(j).getShanktype());
			shape.add(cts.get(j).getShape());
			workingtool.add(cts.get(j).getWorkingtool());
			workingtype.add(cts.get(j).getWorkingtype());
			coatingtype.add(cts.get(j).getCoatingtype());
			diameterratio.add(cts.get(j).getDiameterratio());
			slotshape.add(cts.get(j).getSlotshape());
			handlenorm.add(cts.get(j).getHandlenorm());
			taptype.add(cts.get(j).getTaptype());
			screwtype.add(cts.get(j).getScrewtype());
			axistype.add(cts.get(j).getAxistype());
			axisdetail.add(cts.get(j).getAxisdetail());
			interfacesize.add(cts.get(j).getInterfacesize());
			handledsize.add(cts.get(j).getHandledsize());
			screwsize.add(cts.get(j).getScrewsize());
			screwdistance.add(cts.get(j).getScrewdistance());
			accuracy.add(cts.get(j).getAccuracy());
			collet.add(cts.get(j).getRelativecollet());
			ctcount.add(cts.get(j).getCtcount());
			angle.add(cts.get(j).getAngle());
			backangle.add(cts.get(j).getBackangle());
			edgeno.add(cts.get(j).getEdgeno());
			cujing.add(cts.get(j).getCujing());
			direction.add(cts.get(j).getDirection());
			innercooling.add(cts.get(j).getInnercooling());
			usefullength.add(cts.get(j).getUsefullength());
			pipesize.add(cts.get(j).getPipesize());
			diameter.add(cts.get(j).getDiameter());
			edgelength.add(cts.get(j).getEdgelength());
			totallength.add(cts.get(j).getTotallength());
			screwangle.add(cts.get(j).getScrewangle());
			rangle.add(cts.get(j).getRangle());
			minworkdiameter.add(cts.get(j).getMinworkdiameter());
			thickness.add(cts.get(j).getThickness());
			maxslotdepth.add(cts.get(j).getMaxslotdepth());
			necklength.add(cts.get(j).getNecklength());
			taper.add(cts.get(j).getTaper());
			slotwidth.add(cts.get(j).getSlotwidth());
			pointdiameter.add(cts.get(j).getPointdiameter());
			width.add(cts.get(j).getWidth());
			height.add(cts.get(j).getHeight());
			grooverange.add(cts.get(j).getGrooverange());
			drillrange.add(cts.get(j).getDrillrange());
			screwdirection.add(cts.get(j).getScrewdirection());
			workingrange.add(cts.get(j).getWorkingrange());
			handledtype.add(cts.get(j).getHandledtype());
			relativescrewct.add(cts.get(j).getRelativescrewct());
		}
		Map<String,List<String>> map = new HashMap<String, List<String>>();
		if(brand.size() > 1 || (!brand.contains(null) && brand.size() == 1))
		{
			map.put("brand", CuttingToolsConfiguration.convertSetToList(brand));
		}
		if(angle.size() > 1 || (!angle.contains(0d) && angle.size() == 1))
		{
			map.put("angle", CuttingToolsConfiguration.sortDoubleList(angle));
		}
		if(ctcount.size() > 1 || (!ctcount.contains(0) && ctcount.size() == 1))
		{
			map.put("ctcount", CuttingToolsConfiguration.sortIntegerList(ctcount));
		}
		if(material.size() > 1 || (!material.contains(null) && material.size() == 1))
		{
			map.put("material", CuttingToolsConfiguration.convertSetToList(material));
		}
		if(diameter.size() > 1 || (!diameter.contains(null) && diameter.size() == 1))
		{
			map.put("diameter", CuttingToolsConfiguration.mergeList(diameter));
		}
		if(usefullength.size() > 1 || (!usefullength.contains(0d) && usefullength.size() == 1))
		{
			map.put("usefullength", CuttingToolsConfiguration.sortDoubleList(usefullength));
		}
		if(usage.size() > 1 || (!usage.contains(null) && usage.size() == 1))
		{
			List<String> list = CuttingToolsConfiguration.splitWorkingtoolOrUsage(usage);
			map.put("usage", list);
		}
		if(cujing.size() > 1 || (!cujing.contains(0) && cujing.size() == 1))
		{
			map.put("cujing", CuttingToolsConfiguration.sortIntegerList(cujing));
		}
		if(pipesize.size() > 1 || (!pipesize.contains(0d) && pipesize.size() == 1))
		{
			map.put("pipesize", CuttingToolsConfiguration.sortDoubleList(pipesize));
		}
		if(shank.size() > 1 || (!shank.contains(null) && shank.size() == 1))
		{
			map.put("shank", CuttingToolsConfiguration.mergeList(shank));
		}
		if(shanktype.size() > 1 || (!shanktype.contains(null) && shanktype.size() == 1))
		{
			map.put("shanktype", CuttingToolsConfiguration.splitWorkingtoolOrUsage(shanktype));
		}
		if(shape.size() > 1 || (!shape.contains(null) && shape.size() == 1))
		{
			map.put("shape", CuttingToolsConfiguration.convertShapeToList(shape));
		}
		if(backangle.size() > 1 || (!backangle.contains(1000) && backangle.size() == 1))
		{
			map.put("backangle", CuttingToolsConfiguration.sortBaList(backangle));
		}
		if(workingtool.size() > 1 || (!workingtool.contains(null) && workingtool.size() == 1))
		{
			List<String> list = CuttingToolsConfiguration.splitWorkingtoolOrUsage(workingtool);
			map.put("workingtool", list);
		}
		if(workingtype.size() > 1 || (!workingtype.contains(null) && workingtype.size() == 1))
		{
			List<String> list = CuttingToolsConfiguration.splitWorkingtoolOrUsage(workingtype);
			map.put("workingtype", list);
		}
		if(edgeno.size() > 1 || (!edgeno.contains(0) && edgeno.size() == 1))
		{
			map.put("edgeno", CuttingToolsConfiguration.sortIntegerList(edgeno));
		}
		if(edgelength.size() > 1 || (!edgelength.contains(null) && edgelength.size() == 1))
		{
			map.put("edgelength", CuttingToolsConfiguration.mergeList(edgelength));
		}
		if(totallength.size() > 1 || (!totallength.contains(null) && totallength.size() == 1))
		{
			map.put("totallength", CuttingToolsConfiguration.mergeList(totallength));
		}
		if(screwangle.size() > 1 || (!screwangle.contains(null) && screwangle.size() == 1))
		{
			map.put("screwangle", CuttingToolsConfiguration.convertSetToList(screwangle));
		}
		if(coatingtype.size() > 1 || (!coatingtype.contains(null) && coatingtype.size() == 1))
		{
			map.put("coatingtype", CuttingToolsConfiguration.convertSetToList(coatingtype));
		}
		if(rangle.size() > 1 || (!rangle.contains(null) && rangle.size() == 1))
		{
			map.put("rangle", CuttingToolsConfiguration.mergeList(rangle));
		}
		if(direction.size() > 1 || (!direction.contains(0) && direction.size() == 1))
		{
			map.put("direction", CuttingToolsConfiguration.sortIntegerList(direction));
		}
		if(minworkdiameter.size() > 1 || (!minworkdiameter.contains(0d) && minworkdiameter.size() == 1))
		{
			map.put("minworkdiameter", CuttingToolsConfiguration.sortDoubleList(minworkdiameter));
		}
		if(innercooling.size() > 1 || (!innercooling.contains(0) && innercooling.size() == 1))
		{
			map.put("innercooling", CuttingToolsConfiguration.sortIntegerList(innercooling));
		}
		if(diameterratio.size() > 1 || (!diameterratio.contains(null) && diameterratio.size() == 1))
		{
			map.put("diameterratio", CuttingToolsConfiguration.convertSetToList(diameterratio));
		}
		if(slotshape.size() > 1 || (!slotshape.contains(null) && slotshape.size() == 1))
		{
			map.put("slotshape", CuttingToolsConfiguration.convertSetToList(slotshape));
		}
		if(handlenorm.size() > 1 || (!handlenorm.contains(null) && handlenorm.size() == 1))
		{
			map.put("handlenorm", CuttingToolsConfiguration.convertSetToList(handlenorm));
		}
		if(taptype.size() > 1 || (!taptype.contains(null) && taptype.size() == 1))
		{
			map.put("taptype", CuttingToolsConfiguration.convertSetToList(taptype));
		}
		if(screwtype.size() > 1 || (!screwtype.contains(null) && screwtype.size() == 1))
		{
			map.put("screwtype", CuttingToolsConfiguration.convertSetToList(screwtype));
		}
		if(axistype.size() > 1 || (!axistype.contains(null) && axistype.size() == 1))
		{
			map.put("axistype", CuttingToolsConfiguration.convertSetToList(axistype));
		}
		if(axisdetail.size() > 1 || (!axisdetail.contains(null) && axisdetail.size() == 1))
		{
			map.put("axisdetail", CuttingToolsConfiguration.convertSetToList(axisdetail));
		}
		if(thickness.size() > 1 || (!thickness.contains(0d) && thickness.size() == 1))
		{
			map.put("thickness", CuttingToolsConfiguration.sortDoubleList(thickness));
		}
		if(maxslotdepth.size() > 1 || (!maxslotdepth.contains(0d) && maxslotdepth.size() == 1))
		{
			map.put("maxslotdepth", CuttingToolsConfiguration.sortDoubleList(maxslotdepth));
		}
		if(taper.size() > 1 || (!taper.contains(0d) && taper.size() == 1))
		{
			map.put("taper", CuttingToolsConfiguration.sortDoubleList(taper));
		}
		if(slotwidth.size() > 1 || (!slotwidth.contains(0d) && slotwidth.size() == 1))
		{
			map.put("slotwidth", CuttingToolsConfiguration.sortDoubleList(slotwidth));
		}
		if(pointdiameter.size() > 1 || (!pointdiameter.contains(0d) && pointdiameter.size() == 1))
		{
			map.put("pointdiameter", CuttingToolsConfiguration.sortDoubleList(pointdiameter));
		}
		if(handledsize.size() > 1 || (!handledsize.contains(null) && handledsize.size() == 1))
		{
			map.put("handledsize", CuttingToolsConfiguration.convertSetToList(handledsize));
		}
		if(screwsize.size() > 1 || (!screwsize.contains(null) && screwsize.size() == 1))
		{
			map.put("screwsize", CuttingToolsConfiguration.convertSetToList(screwsize));
		}
		if(screwdistance.size() > 1 || (!screwdistance.contains(null) && screwdistance.size() == 1))
		{
			map.put("screwdistance", CuttingToolsConfiguration.convertSetToList(screwdistance));
		}
		if(accuracy.size() > 1 || (!accuracy.contains(null) && accuracy.size() == 1))
		{
			map.put("accuracy", CuttingToolsConfiguration.convertSetToList(accuracy));
		}
		if(collet.size() > 1 || (!collet.contains(null) && collet.size() == 1))
		{
			map.put("collet", CuttingToolsConfiguration.convertSetToList(collet));
		}
		if(interfacesize.size() > 1 || (!interfacesize.contains(null) && interfacesize.size() == 1))
		{
			map.put("interfacesize", CuttingToolsConfiguration.convertSetToList(interfacesize));
		}
		if(maxbore.size() > 1 || (!maxbore.contains(0d) && maxbore.size() == 1))
		{
			map.put("maxbore", CuttingToolsConfiguration.sortDoubleList(maxbore));
		}
		if(minbore.size() > 1 || (!minbore.contains(0d) && minbore.size() == 1))
		{
			map.put("minbore", CuttingToolsConfiguration.sortDoubleList(minbore));
		}
		if(necklength.size() > 1 || (!necklength.contains(null) && necklength.size() == 1))
		{
			map.put("necklength", CuttingToolsConfiguration.mergeList(necklength));
		}
		if(width.size() > 1 || (!width.contains(null) && width.size() == 1))
		{
			map.put("width", CuttingToolsConfiguration.mergeList(width));
		}
		if(height.size() > 1 || (!height.contains(null) && height.size() == 1))
		{
			map.put("height", CuttingToolsConfiguration.mergeList(height));
		}
		if(screwdirection.size() > 1 || (!screwdirection.contains(null) && screwdirection.size() == 1))
		{
			map.put("screwdirection", CuttingToolsConfiguration.convertSetToList(screwdirection));
		}
		if(grooverange.size() > 1 || (!grooverange.contains(null) && grooverange.size() == 1))
		{
			map.put("grooverange", CuttingToolsConfiguration.convertSetToList(grooverange));
		}
		if(drillrange.size() > 1 || (!drillrange.contains(null) && drillrange.size() == 1))
		{
			map.put("drillrange", CuttingToolsConfiguration.convertSetToList(drillrange));
		}
		if(workingrange.size() > 1 || (!workingrange.contains(null) && workingrange.size() == 1))
		{
			map.put("workingrange", CuttingToolsConfiguration.convertSetToList(workingrange));
		}
		if(handledtype.size() > 1 || (!handledtype.contains(null) && handledtype.size() == 1))
		{
			map.put("handledtype", CuttingToolsConfiguration.convertSetToList(handledtype));
		}
		if(relativescrewct.size() > 1 || (!relativescrewct.contains(null) && relativescrewct.size() == 1))
		{
			map.put("relativescrewct", CuttingToolsConfiguration.convertSetToList(relativescrewct));
		}
		return map;
	}
	//type:1是系列，2是型号
	public List<CuttingToolEntity> getSeriesByParam(CuttingToolEntity ct, int type)
	{
		return ctDAO.getSeriesByParam(ct,type);
	}
	
	public List<UserEntity> getProviders(String providers)
	{
		List<UserEntity> users = new ArrayList<UserEntity>();
		if(StringUtil.isEmpty(providers))
		{
			return users;
		}
		String[] str = providers.split(",");
		
		for(int i = 0;i<str.length;i++)
		{
			users.add(userDAO.queryUserEntityById(Integer.valueOf(str[i])));
		}
		return users;
	}
	public int getBrandCountByCode(String code)
	{
		return ctDAO.getBrandCountByCode(code);
	}
	public int getSeriesnameCountByCode(String code)
	{
		return ctDAO.getSeriesnameCountByCode(code);
	}
	
	public IndexEntity getIndex()
	{
		return ctDAO.getIndexEntity();
	}
	
	public List<CuttingToolEntity> getIndexItems()
	{
		List<CuttingToolEntity> cts = new ArrayList<CuttingToolEntity>();
		List<Integer> id = new ArrayList<Integer>();
		Map<Integer,String> items = Util.getIndexInfo();
		for (Iterator<Integer> it =  items.keySet().iterator();it.hasNext();) 
		{
			id.add(Integer.valueOf(it.next().toString()));
		}
		cts = ctDAO.getIndexCts(id);
		for(int i = 0;i<cts.size();i++)
		{
			cts.get(i).setOutline(items.get(cts.get(i).getId()));
		}
		return cts;
	}
	
	public int updateSampleDownloadCount(int id)
	{
		return sampleDAO.updateSampleDownloadCount(id);
	}
}