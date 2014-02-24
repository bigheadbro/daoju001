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

import com.banzhuan.dao.AgentDAO;
import com.banzhuan.dao.BuyerDAO;
import com.banzhuan.dao.CommentDAO;
import com.banzhuan.dao.ComplainDAO;
import com.banzhuan.dao.GoodcaseDAO;
import com.banzhuan.dao.MsgDAO;
import com.banzhuan.dao.ProfessionalAnswerDAO;
import com.banzhuan.dao.QuestionDAO;
import com.banzhuan.dao.SampleDAO;
import com.banzhuan.entity.AgentEntity;
import com.banzhuan.entity.BuyerEntity;
import com.banzhuan.entity.CommentEntity;
import com.banzhuan.entity.ComplainEntity;
import com.banzhuan.entity.GoodcaseEntity;
import com.banzhuan.entity.ProfessionalAnswerEntity;
import com.banzhuan.entity.QuestionEntity;
import com.banzhuan.entity.SampleEntity;
import com.banzhuan.common.Account;
import com.banzhuan.common.Result;
import com.banzhuan.form.CommentForm;
import com.banzhuan.form.GoodcaseForm;
import com.banzhuan.form.LoginForm;
import com.banzhuan.form.QuestionForm;
import com.banzhuan.util.ChineseSpelling;
import com.banzhuan.util.StringUtil;

/**
 * @author guichaoqun
 *
 */
@Service("commonService")
public class CommonService {
	@Autowired
	@Qualifier("buyerDAO")
	private BuyerDAO buyerDAO;

	@Autowired
	@Qualifier("questionDAO")
	private QuestionDAO questionDAO;
	
	@Autowired
	@Qualifier("agentDAO")
	private AgentDAO agentDAO;
	
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
			AgentEntity agent = agentDAO.queryAgentEntityByMail(form.getMail());
			if(agent == null)
			{
				errors.rejectValue("mail", "MAIL_IS_NOT_EXISTS"); // 邮箱不存在
				return result;
			}
			if(StringUtil.isNotEqual(agent.getPassword(), StringUtil.encrypt(form.getPassword())))
			{
				errors.rejectValue("password", "PASSWORD_ERROR"); // 密码错误
				return result;
			}
			result.add("user", agent);
			result.add("userType", 0);
			return result;
		}
		
		if(StringUtil.isNotEqual(buyer.getPassword(), StringUtil.encrypt(form.getPassword())))
		{
			errors.rejectValue("password", "PASSWORD_ERROR"); // 密码错误
			return result;
		}
		
		result.add("user", buyer);
		result.add("userType", 1);
		return result;
	}
	
	public Result changpwd(String mail, String pwd, Errors errors)
	{
		Result result = new Result();
		BuyerEntity buyer = buyerDAO.queryBuyerEntityByMail(mail);
		if(buyer == null)
		{
			AgentEntity agent = agentDAO.queryAgentEntityByMail(mail);
			if(agent == null)
			{
				errors.rejectValue("mail", "MAIL_IS_NOT_EXISTS"); // 邮箱不存在
				return result;
			}
			agent.setPassword(StringUtil.encrypt(pwd)); 
			agentDAO.updateAgentPwdById(agent);
			return result;
		}
		else
		{
			buyer.setPassword(StringUtil.encrypt(pwd));
			buyerDAO.updateBuyerPwdById(buyer);
		}
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
	
	public Map<Integer,Map<Integer,List<AgentEntity>>> getAllAgents()
	{
		List<AgentEntity> agents = agentDAO.getAllagents(1);
		
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

		
		Map<Integer,Map<Integer,List<AgentEntity>>> agentMap = new HashMap<Integer, Map<Integer,List<AgentEntity>>>();
		
		for(int i = 0;i < agents.size(); i++)
		{
			if(agents.get(i).getBrand() == 0)
			{
				continue;
			}
			String aa = StringUtil.getBrand(((AgentEntity)agents.get(i)).getBrand());
			String bb = ChineseSpelling.getFirstLetter(aa);
			int alpha = ChineseSpelling.letterToNum(bb);
			Map<Integer,List<AgentEntity>> tmpMap = agentMap.get(alpha);
			//是否存在该字母的代理商
			if(tmpMap == null)
			{
				tmpMap = new HashMap<Integer,List<AgentEntity>>();
				List<AgentEntity> tmp = new ArrayList<AgentEntity>();
				tmp.add((AgentEntity)agents.get(i));
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
					    List<AgentEntity> val = tmpMap.get(key); 
					    if(val == null)
						{
					    	val = new ArrayList<AgentEntity>();
						}
					    //如果是同一个品牌
					    if(key == agents.get(i).getBrand())
					    {
						    val.add((AgentEntity)agents.get(i));
						    tmpMap.put(key, val);
						    break;
					    }
					} 
				}
				else
				{
					List<AgentEntity> tmp = new ArrayList<AgentEntity>();
					tmp.add((AgentEntity)agents.get(i));
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
		List<AgentEntity> agents = agentDAO.getMainagents();
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
		
		if(form.getAgentId() > 0)
		{
			comment.setAgentId(form.getAgentId());
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
		
		if(form.getBuyerId() > 0)
		{
			comment.setBuyerId(form.getBuyerId());
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
}