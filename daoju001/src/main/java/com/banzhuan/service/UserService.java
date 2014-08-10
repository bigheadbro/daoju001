package com.banzhuan.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import com.banzhuan.dao.AddressDAO;
import com.banzhuan.dao.UserDAO;
import com.banzhuan.dao.BuyerDAO;
import com.banzhuan.dao.CommentDAO;
import com.banzhuan.dao.GoodcaseDAO;
import com.banzhuan.dao.MsgDAO;
import com.banzhuan.dao.ProductDAO;
import com.banzhuan.dao.ProfessionalAnswerDAO;
import com.banzhuan.dao.QuestionDAO;
import com.banzhuan.dao.SampleDAO;
import com.banzhuan.entity.AddressEntity;
import com.banzhuan.entity.UserEntity;
import com.banzhuan.entity.CommentEntity;
import com.banzhuan.entity.GoodcaseEntity;
import com.banzhuan.entity.MessageEntity;
import com.banzhuan.entity.ProductEntity;
import com.banzhuan.entity.ProfessionalAnswerEntity;
import com.banzhuan.entity.QuestionEntity;
import com.banzhuan.entity.SampleEntity;
import com.banzhuan.common.Account;
import com.banzhuan.common.Constant;
import com.banzhuan.common.Result;
import com.banzhuan.form.UserProfileForm;
import com.banzhuan.form.GoodcaseForm;
import com.banzhuan.form.LoginForm;
import com.banzhuan.form.ProductForm;
import com.banzhuan.form.ProfessionalAnswerForm;
import com.banzhuan.form.QuestionForm;
import com.banzhuan.form.RegForm;
import com.banzhuan.form.SampleForm;
import com.banzhuan.util.StringUtil;
import com.banzhuan.util.Util;

/**
 * @author guichaoqun
 *
 */
@Service("userService")
public class UserService {
	@Autowired
	@Qualifier("userDAO")
	private UserDAO userDAO;
	
	@Autowired
	@Qualifier("buyerDAO")
	private BuyerDAO buyerDAO;

	@Autowired
	@Qualifier("gcDAO")
	private GoodcaseDAO gcDAO;
	
	@Autowired
	@Qualifier("sampleDAO")
	private SampleDAO sampleDAO;
	
	@Autowired
	@Qualifier("questionDAO")
	private QuestionDAO questionDAO;
	
	@Autowired
	@Qualifier("paDAO")
	private ProfessionalAnswerDAO paDAO;
	
	@Autowired
	@Qualifier("msgDAO")
	private MsgDAO msgDAO;
	
	@Autowired
	@Qualifier("commentDAO")
	private CommentDAO commentDAO;
	
	@Autowired
	@Qualifier("productDAO")
	private ProductDAO productDAO;
	
	@Autowired
	@Qualifier("addressDAO")
	private AddressDAO addressDAO;
	
	public Result register(RegForm form, Errors errors)
	{
		Result result = new Result();
		
		if(StringUtil.isEmpty(form.getName()))// 第一步， 判断注册名是否为空
		{
			errors.rejectValue("name", "USER_NAME_IS_NOT_NULL");
			return result;
		}
		else if(StringUtil.isIlegal(form.getName()))
		{
			errors.rejectValue("name", "USER_NAME_IS_ILLEGAL");
			return result;
		}
		else if(userDAO.queryUserEntityByName(form.getName().trim()) != null) 
		{
			errors.rejectValue("name", "USER_NAME_IS_EXISTS");
			return result;
		}
		else if(userDAO.queryUserEntityByMail(form.getMail()) != null ) // 第二步，判断注册用户名是否存在
		{
			errors.rejectValue("mail", "MAIL_IS_EXISTS");
			return result;
		}
		if(StringUtil.isEmpty(form.getPwd())) //  第三步，判断密码不能为空
		{
			errors.rejectValue("pwd", "PASSWORD_IS_NOT_NULL");
			return result;
		}
		if(!form.isAgree())
		{
			errors.rejectValue("agree", "MUST_FOLLOW_DAOSHIFU_RULES");
			return result;
		}
		
		UserEntity user = new UserEntity();
		user.setNick(form.getName());
		user.setPassword(StringUtil.encrypt(form.getPwd())); // 对密码加密
		user.setMail(form.getMail()); // 设置邮箱地址
		user.setLogo(Util.GenAvatar());
		user.setProductlimit(2);
		if(StringUtil.isNotEmpty(form.getCompany()))
		{
			user.setCompanyName(form.getCompany());
		}
		if(StringUtil.isNotEmpty(form.getQq()))
		{
			user.setContactQq(form.getQq());
		}
		if(StringUtil.isNotEmpty(form.getPhone()))
		{
			user.setPhone(form.getPhone());
		}
		if(StringUtil.isNotEmpty(form.getMobile()))
		{
			user.setContactPhone(form.getMobile());
		}
		user.setAuthority(form.getUsertype());
		userDAO.insertUserEntity(user);
		result.add("user", user);
		return result;
	}
	
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
		return result;
	}
	
	public void changePwd(RegForm form, Errors errors)
	{
		if(StringUtil.isEmpty(form.getPwd())) //  第三步，判断密码不能为空
		{
			errors.rejectValue("pwd", "PASSWORD_IS_NOT_NULL");
			return;
		}
		if(StringUtil.isEmpty(form.getPwd1())) //  第三步，判断新密码不能为空
		{
			errors.rejectValue("pwd1", "PASSWORD_IS_NOT_NULL");
			return;
		}
		if(StringUtil.isEmpty(form.getPwd2())) //  第三步，判断重复新密码不能为空
		{
			errors.rejectValue("pwd2", "PASSWORD_IS_NOT_NULL");
			return;
		}
		
		if(!StringUtil.isEqual(form.getPwd1(), form.getPwd2()))
		{
			errors.rejectValue("pwd2", "PASSWORD_IS_NOT_SAME"); // 两次新密码输入不一致
			return;
		}
		
		UserEntity user = userDAO.queryUserEntityById(form.getUserid());
		if(StringUtil.isNotEqual(StringUtil.encrypt(form.getPwd()), user.getPassword()))
		{
			errors.rejectValue("pwd", "OLD_PASSWORD_ERROR"); // 旧密码输入不正确
			return;
		}
		user.setPassword(StringUtil.encrypt(form.getPwd1())); // 新密码
		userDAO.updateUserPwdById(user);
	}

	public Result getUserEntity(int userId)
	{
		Result result = new Result();
		UserEntity user = userDAO.queryUserEntityById(userId);
		result.add("user", user);
		return result;
	}
	
	public int updateUserReadCountById(int userid)
	{
		return userDAO.updateUserReadCountById(userid);
	}
	
	public int updateUserAccnt(HttpServletRequest request, UserProfileForm form, UserEntity user, Account account, Errors errors)
	{
		Result result = new Result();
		
		if(form != null)
		{
			if(StringUtil.isNotEmpty(form.getCompanyName()))
			{
				if(StringUtil.isNotEqual(form.getCompanyName(), account.getCompanyName()))
				{
					if(userDAO.queryUserEntityByName(form.getCompanyName().trim()) != null ||  StringUtil.isIlegal(form.getCompanyName()))
						return 0;
				}
				user.setCompanyName(form.getCompanyName());
				account.setCompanyName(form.getCompanyName());
			}
			if(StringUtil.isNotEmpty(form.getNick()))
			{
				user.setNick(form.getNick());
				account.setUserName(form.getNick());
			}
			if(form.getBrand() > 0)
			{
				user.setBrand(form.getBrand());
				account.setBrandName(form.getBrand());
			}
			if(form.getBrand2() > 0)
			{
				user.setBrand2(form.getBrand2());
				account.setBrandName2(form.getBrand2());
			}
			if(StringUtil.isNotEmpty(form.getAddress()))
			{
				user.setAddress(form.getAddress());
			}
			
			if(StringUtil.isNotEmpty(form.getCompanyPhone()))
			{
				user.setPhone(form.getCompanyPhone());
			}
			if(StringUtil.isNotEmpty(form.getContactName()))
			{
				user.setContactName(form.getContactName());
			}
			if(StringUtil.isNotEmpty(form.getContactPhone()))
			{
				user.setContactPhone(form.getContactPhone());
				account.setPhone(user.getContactPhone());
			}
			if(StringUtil.isNotEmpty(form.getContactQQ()))
			{
				user.setContactQq(form.getContactQQ());
				account.setQq(user.getContactQq());
			}
			if(StringUtil.isNotEmpty(form.getDescription()))
			{
				user.setDescription(form.getDescription());
			}
			if(StringUtil.isNotEmpty(form.getPca()))
			{
				user.setPca(form.getPca());
				account.setArea(user.getPca());
			}
		}
		
		
		return userDAO.updateUserEntityById(user);
	}
	
	public void setUserProfileFormWithUserEntity(UserProfileForm form, UserEntity user)
	{
		if(user != null)
		{
			if(StringUtil.isNotEmpty(user.getNick()))
			{
				form.setNick(user.getNick());
			}
			if(StringUtil.isNotEmpty(user.getMail()))
			{
				form.setEmail(user.getMail());
			}
			if(StringUtil.isNotEmpty(user.getCompanyName()))
			{
				form.setCompanyName(user.getCompanyName());
			}
			if(StringUtil.isNotEmpty(user.getAddress()))
			{
				form.setAddress(user.getAddress());
			}
			if(user.getBrand() > 0)
			{
				form.setBrand(user.getBrand());
				form.setBrandName(StringUtil.getBrand(user.getBrand()));
			}
			if(user.getBrand2() > 0)
			{
				form.setBrand2(user.getBrand2());
				form.setBrandName2(StringUtil.getBrand(user.getBrand2()));
			}
			if(StringUtil.isNotEmpty(user.getPhone()))
			{
				form.setCompanyPhone(user.getPhone());
			}
			if(StringUtil.isNotEmpty(user.getContactName()))
			{
				form.setContactName(user.getContactName());
			}
			if(StringUtil.isNotEmpty(user.getContactPhone()))
			{
				form.setContactPhone(user.getContactPhone());
			}
			if(StringUtil.isNotEmpty(user.getContactQq()))
			{
				form.setContactQQ(user.getContactQq());
			}
			if(StringUtil.isNotEmpty(user.getDescription()))
			{
				form.setDescription(user.getDescription());
			}
			if(StringUtil.isNotEmpty(user.getPca()))
			{
				form.setPca(user.getPca());
			}
		}
	}

	public boolean isPersonalInfoGood(UserEntity user)
	{
		if(user != null)
		{
			if(StringUtil.isNotEmpty(user.getMail()) && StringUtil.isNotEmpty(user.getCompanyName()) && StringUtil.isNotEmpty(user.getAddress()) 
					&& StringUtil.isNotEmpty(user.getPhone()) && StringUtil.isNotEmpty(user.getContactName()) && StringUtil.isNotEmpty(user.getContactPhone()) && StringUtil.isNotEmpty(user.getContactQq())
					&& StringUtil.isNotEmpty(user.getPca()))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
    public Result insertGoodcase(GoodcaseForm form, GoodcaseEntity gc, Errors errors)
    {
    	Result result = new Result();
    	if(StringUtil.isEmpty(form.getName()))
		{
    		errors.rejectValue("name", "GOODCASE_NAME_IS_NOT_NULL");
			return result;
		}
    	if(StringUtil.isEmpty(form.getLink()))
		{
    		errors.rejectValue("link", "GOODCASE_LINK_IS_NOT_NULL");
			return result;
		}
    	if(StringUtil.isNotEmpty(form.getName()))
    	{
    		gc.setName(form.getName());
    	}
    	if(StringUtil.isNotEmpty(form.getLink()))
    	{
    		gc.setLink(form.getLink());
    	}
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
    	gcDAO.insertGoodcaseEntity(gc);
    	return result;
    	
    }
    
    public Result insertProduct(ProductForm form, ProductEntity product, Errors errors)
    {
    	Result result = new Result();
    	if(StringUtil.isEmpty(form.getName()))
		{
    		errors.rejectValue("name", "PRODUCT_NAME_IS_NOT_NULL");
			return result;
		}
    	if(StringUtil.isNotEmpty(form.getName()))
    	{
    		product.setName(form.getName());
    	}

    	if(form.getBrandid() != 0)
    	{
    		product.setBrandId((form.getBrandid()));
    	}
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
    	if(StringUtil.isNotEmpty(form.getPicture()))
    	{
    		product.setPicture(form.getPicture());
    	}
    	if(StringUtil.isNotEmpty(form.getCover()))
    	{
    		product.setCover(form.getCover());
    	}
    	else
    	{
    		product.setCover(form.getPicture().split("[|]")[1]);
    	}
    	if(StringUtil.isNotEmpty(form.getDescription()))
    	{
    		String des = form.getDescription().replace("\n", "<br/>");
    		product.setDescription(des);
    	}
    	productDAO.insertProductEntity(product);
    	return result;
    	
    }
    
    public Result updateProductById(ProductForm form, Errors errors)
	{
		Result result = new Result();
		
		if(StringUtil.isEmpty(form.getName()))// 问题类型为空
		{
			errors.rejectValue("name", "PRODUCT_NAME_IS_NOT_NULL");
			return result;
		}

		ProductEntity product = new ProductEntity();
		product.setId(form.getPid());
		product.setBrandId(form.getBrandid());
		product.setName(form.getName());
		product.setIndustry(form.getIndustry());
		product.setProcessMethod(form.getProcessMethod());
		product.setWpHardness(form.getWpHardness());
		product.setWpMaterial(form.getWpMaterial());
		product.setPicture(form.getPicture());
		product.setCover(form.getCover());
		if(StringUtil.isEmpty(product.getCover()))
    	{
    		product.setCover(product.getPicture().split("[|]")[1]);
    	}
		String des = form.getDescription().replace("\n", "<br/>");
		product.setDescription(des);
		productDAO.updateProductById(product);
		
		return result;
	}
    
    public Result insertAnswer(ProfessionalAnswerForm answerForm, Account account)
    {
    	Result result = new Result();
    	ProfessionalAnswerEntity pa = answerForm.getAnswer();
    	pa.setAgentId(account.getUserId());
    	pa.setAgentName(account.getUserName());
    	pa.setAgentLogo(account.getLogo());
    	pa.setBrandName(account.getBrandName());
    	pa.setVerifiedLink(account.getVerifiedLink());
    	pa.setQuestionId(answerForm.getQuestion().getId());
    	pa.setBuyerId(answerForm.getQuestion().getBuyerId());
    	
    	paDAO.insertProfessionalAnswerEntity(pa);
    	return result;
    }
    
    public void updateAnswer(ProfessionalAnswerForm answerForm)
    {
    	ProfessionalAnswerEntity pa = answerForm.getAnswer();
    	paDAO.updateProfessionalAnswerById(pa);
    }
    
    public Result updateGoodcaseById(GoodcaseForm form, GoodcaseEntity gc, Errors errors)
    {
    	Result result = new Result();
    	if(StringUtil.isEmpty(form.getName()))
		{
    		errors.rejectValue("name", "GOODCASE_NAME_IS_NOT_NULL");
			return result;
		}
    	if(StringUtil.isEmpty(form.getLink()))
		{
    		errors.rejectValue("link", "GOODCASE_LINK_IS_NOT_NULL");
			return result;
		}
    	if(form.getGcid() != 0)
    	{
    		gc.setId(form.getGcid());
    	}
    	if(StringUtil.isNotEmpty(form.getName()))
    	{
    		gc.setName(form.getName());
    	}
    	if(StringUtil.isNotEmpty(form.getLink()))
    	{
    		gc.setLink(form.getLink());
    	}
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
    	gcDAO.updateGoodcaseById(gc);
    	return result;
    	
    }
    
    public Result queryGoodcasesByUserid(int userId, RowBounds bound)
	{
		Result result = new Result();
		List<GoodcaseEntity> goodcases = gcDAO.queryGCEntityByUserid(userId,bound);
		result.add("goodcases", goodcases);
		return result;
	}
    
    public Result queryGoodcasesByUserid(int userId)
	{
		Result result = new Result();
		List<GoodcaseEntity> goodcases = gcDAO.queryGCEntityByUserid(userId);
		result.add("goodcases", goodcases);
		return result;
	}
    public void setGoodcaseFormByGcid(GoodcaseForm form, int gcid)
    {
    	GoodcaseEntity gc = gcDAO.queryGCEntityById(gcid);
    	if(StringUtil.isNotEmpty(gc.getName()))
    	{
    		form.setName(gc.getName());
    	}
    	if(gc.getIndustry() >=0)
		{
			form.setIndustry(gc.getIndustry());
		}
		if(gc.getProcessMethod() >=0)
		{
			form.setProcessMethod(gc.getProcessMethod());
		}
		if(gc.getWorkSolidity() >=0)
		{
			form.setWpHardness(gc.getWorkSolidity());
		}
		if(gc.getWorkMaterial() >=0)
		{
			form.setWpMaterial(gc.getWorkMaterial());
		}
		if(StringUtil.isNotEmpty(gc.getLink()))
    	{
    		form.setLink(gc.getLink());
    	}
    }
    
    public Result querySamplesByUserid(int userId, RowBounds bound)
	{
		Result result = new Result();
		List<SampleEntity> samples = sampleDAO.querySampleEntityByUserid(userId, bound);
		result.add("samples", samples);
		result.add("count", samples.size());
		return result;
	}

    public Result insertSample(SampleForm form, SampleEntity sample, Errors errors)
    {
    	Result result = new Result();
    	if(StringUtil.isEmpty(form.getName()))
		{
    		errors.rejectValue("name", "SAMPLE_NAME_IS_NOT_NULL");
			return result;
		}
    	if(StringUtil.isEmpty(form.getLink()))
		{
    		errors.rejectValue("link", "SAMPLE_LINK_IS_NOT_NULL");
			return result;
		}
    	if(StringUtil.isNotEmpty(form.getName()))
    	{
    		sample.setName(form.getName());
    	}
    	if(StringUtil.isNotEmpty(form.getLink()))
    	{
    		sample.setLink(form.getLink());
    	}
    	if(StringUtil.isNotEmpty(form.getType()))
    	{
    		sample.setType(form.getType());
    	}
    	if(form.getSize() != 0)
    	{
    		sample.setSize(form.getSize()/1024.00/1024.00);
    	}
    	sampleDAO.insertSampleEntity(sample);
    	return result;
    	
    }
    
    public void delSample(int id)
    {
    	sampleDAO.delSample(id);
    }
    
    public void delAddr(int id)
    {
    	addressDAO.delAddr(id);
    }
    public void delGoodcase(int id)
    {
    	gcDAO.delGoodcase(id);
    }
    
    public Result updateSampleById(SampleForm form, SampleEntity sample, Errors errors)
    {
    	Result result = new Result();
    	if(StringUtil.isEmpty(form.getName()))
		{
    		errors.rejectValue("name", "SAMPLE_NAME_IS_NOT_NULL");
			return result;
		}
    	if(StringUtil.isEmpty(form.getLink()))
		{
    		errors.rejectValue("link", "SAMPLE_LINK_IS_NOT_NULL");
			return result;
		}
    	if(form.getSid() != 0)
    	{
    		sample.setId(form.getSid());
    	}
    	if(StringUtil.isNotEmpty(form.getName()))
    	{
    		sample.setName(form.getName());
    	}
    	if(StringUtil.isNotEmpty(form.getLink()))
    	{
    		sample.setLink(form.getLink());
    	}
    	if(StringUtil.isNotEmpty(form.getType()))
    	{
    		sample.setType(form.getType());
    	}
    	if(form.getSize() != 0)
    	{
    		sample.setSize(form.getSize()/1024/1024);
    	}
    	sampleDAO.updateSampleById(sample);
    	return result;
    	
    }
    
    public void setSampleFormBySid(SampleForm form, int sid)
    {
    	SampleEntity sample = sampleDAO.querySampleEntityById(sid);
    	if(StringUtil.isNotEmpty(sample.getName()))
    	{
    		form.setName(sample.getName());
    	}
		if(StringUtil.isNotEmpty(sample.getLink()))
    	{
    		form.setLink(sample.getLink());
    	}
    }
    
    public Result queryQuestionById(int id)
	{
		Result result = new Result();
		QuestionEntity question = questionDAO.queryQuestionEntityById(id);
		
		result.add("question", question);
		return result;
	}
    
    public Result queryAnswersByQid(int qid)
	{
		Result result = new Result();
		List<ProfessionalAnswerEntity> answers = paDAO.queryAnswersByQid(qid);
		for(int i=0;i<answers.size();i++)
		{
			List<CommentEntity> comments = commentDAO.getCommentsInAnswerByParentid(answers.get(i).getId());
			answers.get(i).setComments(comments);
			answers.get(i).setCntComment(comments.size());
		}
		result.add("answers", answers);
		
		return result;
	}
    
    public Result queryAnswersByUserid(int userid)
	{
		Result result = new Result();
		List<ProfessionalAnswerEntity> answers = paDAO.queryAnswersByUserid(userid);
		result.add("answers", answers);
		
		return result;
	}
    
    public Result queryAnswersByUserid(int userid, RowBounds bound)
	{
		Result result = new Result();
		List<ProfessionalAnswerEntity> answers = paDAO.queryAnswersByUserid(userid, bound);
		result.add("answers", answers);
		
		return result;
	}
    
    public ProfessionalAnswerEntity queryProfessionalAnswerEntityById(int id)
	{
		ProfessionalAnswerEntity answer = paDAO.queryProfessionalAnswerEntityById(id);
		
		return answer;
	}
    
    
    public Result queryDraftsByUserid(int userid)
	{
		Result result = new Result();
		List<ProfessionalAnswerEntity> answers = paDAO.queryDraftsByUserid(userid);
		result.add("answers", answers);
		
		return result;
	}
    
    public Result queryDraftsByUserid(int userid, RowBounds bound)
	{
		Result result = new Result();
		List<ProfessionalAnswerEntity> answers = paDAO.queryDraftsByUserid(userid, bound);
		result.add("answers", answers);
		
		return result;
	}
    
    public Result getAllquestions()
	{
		Result result = new Result();
		QuestionEntity ques = new QuestionEntity();

		List<QuestionEntity> questions = questionDAO.getAllquestions(ques);
		result.add("questions", questions);
		return result;
	}
    
    public Result getAllquestions(RowBounds bound)
	{
		Result result = new Result();
		QuestionEntity ques = new QuestionEntity();

		List<QuestionEntity> questions = questionDAO.getAllquestions(ques,bound);
		result.add("questions", questions);
		return result;
	}
    public int getGoodcaseCount(int userid)
	{
		return gcDAO.getGoodcaseCount(userid);
	}
    
    public int getSampleCount(int userid)
	{
		return sampleDAO.getSampleCount(userid);
	}
    
    public int getAnswerCount(int userid)
	{
		return paDAO.getAnswerCount(userid);
	}
    
    public int getUnreadMsgCount(int userid)
	{
    	MessageEntity msg = new MessageEntity();
    	msg.setReceiverId(userid);
		return msgDAO.getUnreadMsgCount(msg);
	}
    
    public Result getAllMsgs(int userid, RowBounds bound)
	{
		Result result = new Result();
		MessageEntity msg = new MessageEntity();
    	msg.setReceiverId(userid);
		List<MessageEntity> msgs = msgDAO.getMsgsByUserid(msg,bound);
		result.add("msgs", msgs);
		return result;
	}
    
    public int getMsgCount(int userid)
	{
    	MessageEntity msg = new MessageEntity();
    	msg.setReceiverId(userid);
		return msgDAO.getMsgCount(msg);
	}
    
    public int getAllquestionsCount()
	{
		QuestionEntity ques = new QuestionEntity();
		return questionDAO.getAllQuestionCount(ques);
	}

    public void setProductFormWithPid(ProductForm form, int id)
	{
		ProductEntity product = productDAO.queryProductEntityById(id);

		if(product.getBrandId() >= 0)
		{
			form.setBrandid(product.getBrandId());
		}
		if(product.getIndustry() >=0)
		{
			form.setIndustry(product.getIndustry());
		}
		if(product.getProcessMethod() >=0)
		{
			form.setProcessMethod(product.getProcessMethod());
		}
		if(product.getWpHardness() >=0)
		{
			form.setWpHardness(product.getWpHardness());
		}
		if(product.getWpMaterial() >=0)
		{
			form.setWpMaterial(product.getWpMaterial());
		}
		if(product.getUserId() > 0)
		{
			form.setUserid(product.getUserId());
		}
		if(StringUtil.isNotEmpty(product.getName()))
		{
			form.setName(product.getName());
		}
		if(StringUtil.isNotEmpty(product.getDescription()))
		{
			form.setDescription(product.getDescription());
		}
		if(StringUtil.isNotEmpty(product.getPicture()))
		{
			form.setPicture(product.getPicture());
		}
		if(StringUtil.isNotEmpty(product.getCover()))
		{
			form.setCover(product.getCover());
		}
		if(StringUtil.isNotEmpty(product.getDescription()))
		{
			form.setDescription(product.getDescription());
		}

	}
    
    public void setProductFormWithProduct(ProductForm form, ProductEntity product)
	{

		if(product.getBrandId() >= 0)
		{
			form.setBrandid(product.getBrandId());
		}
		if(product.getIndustry() >=0)
		{
			form.setIndustry(product.getIndustry());
		}
		if(product.getProcessMethod() >=0)
		{
			form.setProcessMethod(product.getProcessMethod());
		}
		if(product.getWpHardness() >=0)
		{
			form.setWpHardness(product.getWpHardness());
		}
		if(product.getWpMaterial() >=0)
		{
			form.setWpMaterial(product.getWpMaterial());
		}
		if(product.getUserId() > 0)
		{
			form.setUserid(product.getUserId());
		}
		if(StringUtil.isNotEmpty(product.getName()))
		{
			form.setName(product.getName());
		}
		if(StringUtil.isNotEmpty(product.getDescription()))
		{
			form.setDescription(product.getDescription());
		}
		if(StringUtil.isNotEmpty(product.getPicture()))
		{
			form.setPicture(product.getPicture());
		}
		if(StringUtil.isNotEmpty(product.getDescription()))
		{
			form.setDescription(product.getDescription());
		}

	}
    
    public Result queryProductByUserid(int userid)
	{
		Result result = new Result();
		List<ProductEntity> products = productDAO.queryProductEntityByUserid(userid);
		result.add("products", products);
		
		return result;
	}
    
    public int getProductCount(int userid)
    {
    	int ss = productDAO.getProductCountByUserid(userid);
    	return ss;
    }
    
    public void delProduct(int id)
    {
    	productDAO.delProduct(id);
    }
    
    public Result queryAdressesByUserid(int userId)
	{
		Result result = new Result();
		List<AddressEntity> addresses = addressDAO.queryAddressByUserid(userId, 1);
		result.add("addresses", addresses);
		return result;
	}
    
    public Result insertQuestion(QuestionForm form, Account account, Errors errors)
	{
		Result result = new Result();
		
		if(form.getType() == 0)// 问题类型为空
		{
			errors.rejectValue("type", "QUESTION_TYPE_IS_NULL");
			return result;
		}

		if(StringUtil.isEmpty(form.getContent())) //  问题内容为空
		{
			errors.rejectValue("content", "QUESTOIN_CONTENT_IS_NULL");
			return result;
		}
		QuestionEntity question = new QuestionEntity();
		question.setUserName(account.getUserName());
		question.setUserLogo(account.getLogo());
		question.setIndustry(form.getIndustry());
		question.setProcessMethod(form.getProcessMethod());
		question.setWpHardness(form.getWpHardness());
		question.setWpMaterial(form.getWpMaterial());
		question.setType(form.getType());
		question.setContent(form.getContent());
		question.setBuyerId(form.getUserid());
		question.setState(form.getState());
		question.setHasPic(form.getHasPic());
		
		questionDAO.insertQuestionEntity(question);
		
		return result;
	}

	public Result updateQuestionById(QuestionForm form, Errors errors)
	{
		Result result = new Result();
		
		if(form.getType() == 0)// 问题类型为空
		{
			errors.rejectValue("type", "QUESTION_TYPE_IS_NULL");
			return result;
		}

		if(StringUtil.isEmpty(form.getContent())) //  问题内容为空
		{
			errors.rejectValue("content", "QUESTOIN_CONTENT_IS_NULL");
			return result;
		}
		QuestionEntity question = new QuestionEntity();
		question.setId(form.getQid());
		question.setIndustry(form.getIndustry());
		question.setProcessMethod(form.getProcessMethod());
		question.setWpHardness(form.getWpHardness());
		question.setWpMaterial(form.getWpMaterial());
		question.setType(form.getType());
		question.setContent(form.getContent());
		question.setBuyerId(form.getUserid());
		question.setState(form.getState());
		question.setHasPic(form.getHasPic());
		
		questionDAO.updateQuestionById(question);
		
		return result;
	}

	public Result queryQuestionsByUserId(int userId, RowBounds bound)
	{
		Result result = new Result();
		List<QuestionEntity> questions = questionDAO.queryQuestionsByUserid(userId,bound);
		result.add("questions", questions);

		return result;
	}
	
	public Result queryDraftsByUserId(int userId, RowBounds bound)
	{
		Result result = new Result();
		List<QuestionEntity> questions = questionDAO.queryDraftsByUserid(userId,bound);
		result.add("questions", questions);

		return result;
	}
	
	public void setQuestionFormWithQid(QuestionForm form, int id)
	{
		QuestionEntity question = questionDAO.queryQuestionEntityById(id);
		if(question.getType() >=0)
		{
			form.setType(question.getType());
		}
		if(question.getIndustry() >=0)
		{
			form.setIndustry(question.getIndustry());
		}
		if(question.getProcessMethod() >=0)
		{
			form.setProcessMethod(question.getProcessMethod());
		}
		if(question.getWpHardness() >=0)
		{
			form.setWpHardness(question.getWpHardness());
		}
		if(question.getWpMaterial() >=0)
		{
			form.setWpMaterial(question.getWpMaterial());
		}
		if(question.getContent() != null)
		{
			form.setContent(question.getContent());
		}
		if(question.getBuyerId() > 0)
		{
			form.setUserid(question.getBuyerId());
		}
		if(question.getState() >= 0)
		{
			form.setState(question.getState());
		}
	}
	
	public int getUserQuestionCount(int userid)
	{
		return questionDAO.getUserQuestionCount(userid);
	}
	
	public int getUserDraftCount(int userid)
	{
		return questionDAO.queryDraftsCountByUserid(userid);
	}
	
	public int getTodayQuestionCount(int userid)
	{
		int a =  questionDAO.getUserTodayQuestionCount(userid);
		return a;
	}
}
