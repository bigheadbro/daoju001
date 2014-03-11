package com.banzhuan.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import com.banzhuan.dao.AgentDAO;
import com.banzhuan.dao.BuyerDAO;
import com.banzhuan.dao.MsgDAO;
import com.banzhuan.dao.QuestionDAO;
import com.banzhuan.entity.BuyerEntity;
import com.banzhuan.entity.MessageEntity;
import com.banzhuan.entity.QuestionEntity;
import com.banzhuan.common.Account;
import com.banzhuan.common.Constant;
import com.banzhuan.common.Result;
import com.banzhuan.form.BuyerProfileForm;
import com.banzhuan.form.RegForm;
import com.banzhuan.form.LoginForm;
import com.banzhuan.form.QuestionForm;
import com.banzhuan.util.StringUtil;

/**
 * @author guichaoqun
 *
 */
@Service("buyerService")
public class BuyerService {
	@Autowired
	@Qualifier("buyerDAO")
	private BuyerDAO buyerDAO;

	@Autowired
	@Qualifier("agentDAO")
	private AgentDAO agentDAO;
	
	@Autowired
	@Qualifier("questionDAO")
	private QuestionDAO questionDAO;
	
	@Autowired
	@Qualifier("msgDAO")
	private MsgDAO msgDAO;
	/**
	 * 模拟QQ回调处理逻辑
	 * @param uuid: 唯一ID
	 * @param nick: qq 昵称
	 * @return
	 */
	public BuyerEntity doQqConnectEnter(String qqConnectId, String nick)
	{
		
		BuyerEntity user = buyerDAO.buyerLoginByQQConnect(qqConnectId);
		if(user == null)
		{
			user  = new BuyerEntity();
			user.setQqConnectId(qqConnectId);
			user.setNick(nick);
			buyerDAO.insertBuyerEntity(user); // 初始化用户信息
		}
		return user;
	}

	
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
		else if(buyerDAO.queryBuyerEntityByName(form.getName().trim()) != null) 
		{
			errors.rejectValue("name", "USER_NAME_IS_EXISTS");
			return result;
		}
		else if(buyerDAO.queryBuyerEntityByMail(form.getMail()) != null || agentDAO.queryAgentEntityByMail(form.getMail()) != null) // 第二步，判断注册用户名是否存在
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
		
		BuyerEntity buyer = new BuyerEntity();
		buyer.setUsername(form.getName());
		buyer.setPassword(StringUtil.encrypt(form.getPwd())); // 对密码加密
		buyer.setEmail(form.getMail()); // 设置邮箱地址
		buyerDAO.insertBuyerEntity(buyer);
		result.add("buyer", buyer);
		return result;
	}
	
	/**
	 * 用户是否登录
	 * @param name
	 * @param password
	 * @return
	 */
	public Result checkLogin(LoginForm form, Errors errors)
	{
		Result result = new Result();
		if(StringUtil.isEmpty(form.getMail()))
		{
			errors.rejectValue("mail", "MAIL_IS_NOT_NULL"); // 邮箱不能为空
			return result;
		}
		BuyerEntity buyer = buyerDAO.queryBuyerEntityByMail(form.getMail());
		if(buyer == null)
		{
			errors.rejectValue("mail", "MAIL_IS_NOT_EXISTS"); // 邮箱不存在
			return result;
		}
		if(StringUtil.isNotEqual(buyer.getPassword(), StringUtil.encrypt(form.getPassword())))
		{
			errors.rejectValue("password", "PASSWORD_ERROR"); // 密码错误
			return result;
		}
		result.add("buyer", buyer);
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
		
		BuyerEntity buyer = buyerDAO.queryBuyerEntityById(form.getUserid());
		if(StringUtil.isNotEqual(StringUtil.encrypt(form.getPwd()), buyer.getPassword()))
		{
			errors.rejectValue("pwd", "PASSWORD_ERROR"); // 旧密码输入不正确
			return;
		}
		buyer.setPassword(StringUtil.encrypt(form.getPwd1())); // 新密码
		buyerDAO.updateBuyerPwdById(buyer);
	}
	
	public int updateBuyerAccnt(BuyerProfileForm form, BuyerEntity buyer, Errors errors)
	{
		Result result = new Result();
		if(form != null)
		{
			if(form.getUserName() != "")
			{
				if(StringUtil.isNotEqual(form.getUserName(),buyer.getUsername()))
				{
					if((buyerDAO.queryBuyerEntityByName(form.getUserName().trim()) != null) || StringUtil.isIlegal(form.getUserName()))
						return 0;
				}
				buyer.setUsername(form.getUserName());
			}
			if(form.getCompanyName() != "")
			{
				buyer.setCompanyName(form.getCompanyName());
			}
			if(form.getAddress() != "")
			{
				buyer.setCompanyAddress(form.getAddress());
			}
			if(form.getCompanyPhone() != "")
			{
				buyer.setCompanyPhone(form.getCompanyPhone());
			}
			if(form.getContactName() != "")
			{
				buyer.setContactName(form.getContactName());
			}
			if(form.getContactPhone() != "")
			{
				buyer.setContactPhone(form.getContactPhone());
			}
			if(form.getContactQQ() != "")
			{
				buyer.setContactQq(form.getContactQQ());
			}
		}
		buyerDAO.updateBuyerEntityById(buyer);
		return 1;
	}
	
	public BuyerEntity getBuyerEntity(int userId)
	{
		return buyerDAO.queryBuyerEntityById(userId);
	}
	
	public void setBuyerProfileFormWithBuyerEntity(BuyerProfileForm form, BuyerEntity entity)
	{
		if(entity.getUsername() != "")
		{
			form.setUserName(entity.getUsername());
		}
		if(entity.getCompanyName() != "")
		{
			form.setCompanyName(entity.getCompanyName());
		}
		if(entity.getCompanyAddress() != "")
		{
			form.setAddress(entity.getCompanyAddress());
		}
		if(entity.getCompanyPhone() != "")
		{
			form.setCompanyPhone(entity.getCompanyPhone());
		}
		if(entity.getContactName() != "")
		{
			form.setContactName(entity.getContactName());
		}
		if(entity.getContactPhone() != "")
		{
			form.setContactPhone(entity.getContactPhone());
		}
		if(entity.getContactQq() != "")
		{
			form.setContactQQ(entity.getContactQq());
		}
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
	
	public int getUnreadMsgCount(int userid)
	{
		MessageEntity msg = new MessageEntity();
    	msg.setReceiverId(userid);
    	msg.setStart(Constant.MSG_BUYER_START);
    	msg.setEnd(Constant.MSG_BUYER_END);
		return msgDAO.getUnreadMsgCount(msg);
	}
	
	public int getMsgCount(int userid)
	{
		MessageEntity msg = new MessageEntity();
    	msg.setReceiverId(userid);
    	msg.setStart(Constant.MSG_BUYER_START);
    	msg.setEnd(Constant.MSG_BUYER_END);
		return msgDAO.getMsgCount(msg);
	}
	
	public int getUserQuestionCount(int userid)
	{
		return questionDAO.getUserQuestionCount(userid);
	}
	
	public int getUserDraftCount(int userid)
	{
		return questionDAO.queryDraftsCountByUserid(userid);
	}
	public Result getAllMsgs(int userid, RowBounds bound)
	{
		Result result = new Result();
		MessageEntity msg = new MessageEntity();
    	msg.setReceiverId(userid);
    	msg.setStart(Constant.MSG_BUYER_START);
    	msg.setEnd(Constant.MSG_BUYER_END);
		List<MessageEntity> msgs = msgDAO.getMsgsByUserid(msg,bound);
		result.add("msgs", msgs);
		return result;
	}
	
	public int getTodayQuestionCount(int userid)
	{
		int a =  questionDAO.getUserTodayQuestionCount(userid);
		return a;
	}
	

}
