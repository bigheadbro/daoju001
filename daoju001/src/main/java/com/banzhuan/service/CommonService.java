package com.banzhuan.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
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
import com.banzhuan.dao.EventDAO;
import com.banzhuan.dao.GoodcaseDAO;
import com.banzhuan.dao.ItemDAO;
import com.banzhuan.dao.MsgDAO;
import com.banzhuan.dao.OrderDAO;
import com.banzhuan.dao.ProductDAO;
import com.banzhuan.dao.ProfessionalAnswerDAO;
import com.banzhuan.dao.QuestionDAO;
import com.banzhuan.dao.QuickrequestDAO;
import com.banzhuan.dao.SampleDAO;
import com.banzhuan.dao.UserDAO;
import com.banzhuan.entity.AddressEntity;
import com.banzhuan.entity.AgentEntity;
import com.banzhuan.entity.ArticleEntity;
import com.banzhuan.entity.BuyerEntity;
import com.banzhuan.entity.CommentEntity;
import com.banzhuan.entity.ComplainEntity;
import com.banzhuan.entity.EventEntity;
import com.banzhuan.entity.GoodcaseEntity;
import com.banzhuan.entity.ItemEntity;
import com.banzhuan.entity.MessageEntity;
import com.banzhuan.entity.OrderEntity;
import com.banzhuan.entity.ProductEntity;
import com.banzhuan.entity.ProfessionalAnswerEntity;
import com.banzhuan.entity.QuestionEntity;
import com.banzhuan.entity.QuickrequestEntity;
import com.banzhuan.entity.SampleEntity;
import com.banzhuan.entity.UserEntity;
import com.banzhuan.common.Account;
import com.banzhuan.common.Result;
import com.banzhuan.form.CommentForm;
import com.banzhuan.form.GoodcaseForm;
import com.banzhuan.form.ItemForm;
import com.banzhuan.form.LoginForm;
import com.banzhuan.form.ProductForm;
import com.banzhuan.form.QuestionForm;
import com.banzhuan.form.RequestForm;
import com.banzhuan.util.ChineseSpelling;
import com.banzhuan.util.StringUtil;

/**
 * @author guichaoqun
 *
 */
@Service("commonService")
public class CommonService {

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
	public Map<Integer,Map<Integer,List<UserEntity>>> getAllAgents()
	{
		List<UserEntity> agents = userDAO.getUsersByAuth(4);
		
		//按代理商首字母
		/*Map<Integer,List<AgentEntity>> agentMap = new HashMap<Integer,List<AgentEntity>>();

		for(int i = 0;i < agents.size(); i++)
		{
			List<AgentEntity> tmp = agentMap.get(ChineseSpelling.letterToNum(ChineseSpelling.getFirstLetter(((AgentEntity)agents.get(i)).getCompanyName())));
			if(tmp == null)
			{
				tmp = new ArrayList<AgentEntity>();
			}
			tmp.add((AgentEntity)agents.get(i));
			agentMap.put(ChineseSpelling.letterToNum(ChineseSpelling.getFirstLetter(((AgentEntity)agents.get(i)).getCompanyName())), tmp);
		}*/

		
		Map<Integer,Map<Integer,List<UserEntity>>> agentMap = new HashMap<Integer, Map<Integer,List<UserEntity>>>();
		
		for(int i = 0;i < agents.size(); i++)
		{
			if(agents.get(i).getBrand() == 0)
			{
				continue;
			}
			String aa = StringUtil.getBrand(((UserEntity)agents.get(i)).getBrand());
			String bb = ChineseSpelling.getFirstLetter(aa);
			int alpha = ChineseSpelling.letterToNum(bb);
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
		}
		return agentMap;
	}
	
	public Map<Integer,Map<Integer,List<SampleEntity>>> getAllSamples()
	{
		List<SampleEntity> samples = sampleDAO.getAllsamples();
		
		Map<Integer,Map<Integer,List<SampleEntity>>> sampleMap = new HashMap<Integer, Map<Integer,List<SampleEntity>>>();
		
		for(int i = 0;i < samples.size(); i++)
		{
			int alpha = ChineseSpelling.letterToNum(ChineseSpelling.getFirstLetter(((SampleEntity)samples.get(i)).getAgentName()));
			Map<Integer,List<SampleEntity>> tmpMap = sampleMap.get(alpha);
			//是否存在该字母的代理商案例
			if(tmpMap == null)
			{
				tmpMap = new HashMap<Integer,List<SampleEntity>>();
				List<SampleEntity> tmp = new ArrayList<SampleEntity>();
				tmp.add((SampleEntity)samples.get(i));
				tmpMap.put(samples.get(i).getAgentId(), tmp);
			}
			else
			{
				//是否包含样本的代理商id
				if(tmpMap.containsKey(samples.get(i).getAgentId()))
				{
					Iterator<Integer> iter = tmpMap.keySet().iterator(); 
					while (iter.hasNext()) 
					{ 
					    int key = Integer.valueOf(iter.next().toString()); 
					    List<SampleEntity> val = tmpMap.get(key); 
					    if(val == null)
						{
					    	val = new ArrayList<SampleEntity>();
						}
					    //如果是同一个代理商的样本
					    if(key == samples.get(i).getAgentId())
					    {
						    val.add((SampleEntity)samples.get(i));
						    tmpMap.put(key, val);
						    break;
					    }
					} 
				}
				else
				{
					List<SampleEntity> tmp = new ArrayList<SampleEntity>();
					tmp.add((SampleEntity)samples.get(i));
					tmpMap.put(samples.get(i).getAgentId(), tmp);
				}
			}
			
			sampleMap.put(alpha, tmpMap);
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
	
	public QuestionEntity getQuestion(int id)
	{
		return questionDAO.queryQuestionEntityById(id);
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
}