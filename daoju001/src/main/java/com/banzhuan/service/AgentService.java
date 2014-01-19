package com.banzhuan.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import com.banzhuan.dao.AgentDAO;
import com.banzhuan.dao.GoodcaseDAO;
import com.banzhuan.dao.ProfessionalAnswerDAO;
import com.banzhuan.dao.QuestionDAO;
import com.banzhuan.dao.SampleDAO;
import com.banzhuan.entity.AgentEntity;
import com.banzhuan.entity.GoodcaseEntity;
import com.banzhuan.entity.ProfessionalAnswerEntity;
import com.banzhuan.entity.QuestionEntity;
import com.banzhuan.entity.SampleEntity;
import com.banzhuan.common.Account;
import com.banzhuan.common.Result;
import com.banzhuan.form.AgentProfileForm;
import com.banzhuan.form.GoodcaseForm;
import com.banzhuan.form.LoginForm;
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
@Service("agentService")
public class AgentService {
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
	@Qualifier("questionDAO")
	private QuestionDAO questionDAO;
	
	@Autowired
	@Qualifier("paDAO")
	private ProfessionalAnswerDAO paDAO;
	
	public Result register(RegForm form, Errors errors)
	{
		Result result = new Result();
		
		if(StringUtil.isEmpty(form.getName()))// 第一步， 判断注册名是否为空
		{
			errors.rejectValue("name", "USER_NAME_IS_NOT_NULL");
			return result;
		}
		else if(agentDAO.queryAgentEntityByMail(form.getEmail()) != null) // 第二步，判断注册用户名是否存在
		{
			errors.rejectValue("email", "MAIL_IS_EXISTS");
			return result;
		}
		if(StringUtil.isEmpty(form.getPwd())) //  第三步，判断密码不能为空
		{
			errors.rejectValue("pwd", "PASSWORD_IS_NOT_NULL");
			return result;
		}
		
		AgentEntity agent = new AgentEntity();
		agent.setCompanyName(form.getName());
		agent.setPassword(StringUtil.encrypt(form.getPwd())); // 对密码加密
		agent.setMail(form.getEmail()); // 设置邮箱地址
		agentDAO.insertAgentEntity(agent);
		result.add("agent", agent);
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
		result.add("agent", agent);
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
		
		AgentEntity agent = agentDAO.queryAgentEntityById(form.getUserid());
		if(StringUtil.isNotEqual(StringUtil.encrypt(form.getPwd()), agent.getPassword()))
		{
			errors.rejectValue("pwd", "PASSWORD_ERROR"); // 旧密码输入不正确
			return;
		}
		agent.setPassword(StringUtil.encrypt(form.getPwd1())); // 新密码
		agentDAO.updateAgentPwdById(agent);
	}

	public AgentEntity getAgentEntity(int userId)
	{
		return agentDAO.queryAgentEntityById(userId);
	}
	
	public void updateAgentAccnt(HttpServletRequest request, AgentProfileForm form, AgentEntity agent, Errors errors)
	{
		if(form != null)
		{
			if(StringUtil.isNotEmpty(form.getCompanyName()))
			{
				agent.setCompanyName(form.getCompanyName());
			}
			if(StringUtil.isNotEmpty(form.getAddress()))
			{
				agent.setAddress(form.getAddress());
			}
			if(StringUtil.isNotEmpty(form.getBrandName()))
			{
				agent.setBrandName(form.getBrandName());
			}
			if(StringUtil.isNotEmpty(form.getCompanyPhone()))
			{
				agent.setPhone(form.getCompanyName());
			}
			if(StringUtil.isNotEmpty(form.getContactName()))
			{
				agent.setContactName(form.getContactName());
			}
			if(StringUtil.isNotEmpty(form.getContactPhone()))
			{
				agent.setContactPhone(form.getContactPhone());
			}
			if(StringUtil.isNotEmpty(form.getContactQQ()))
			{
				agent.setContactQq(form.getContactQQ());
			}
			if(StringUtil.isNotEmpty(form.getDescription()))
			{
				agent.setDescription(form.getDescription());
			}

			// /////////////////////////////////////////////////////////////获取上传的文件///////////////////////////////////
			if (request instanceof DefaultMultipartHttpServletRequest) 
			{
				DefaultMultipartHttpServletRequest r = (DefaultMultipartHttpServletRequest) request;
				List<MultipartFile> files = r.getMultiFileMap().get("verifiedLink");
				if (files != null && files.size() > 0) {
					MultipartFile f = files.get(0);
					if (StringUtil.isNotEmpty(f.getOriginalFilename()))
					{
						String path = request.getSession().getServletContext().getRealPath("/verified");
						String fileName = Util.genRandomName(f.getContentType().toString().split("/")[1]);
						File file = new File(path + "/" + fileName);
						agent.setVerifiedLink("../verified/" + fileName);
						try 
						{
							FileCopyUtils.copy(f.getBytes(), file);

						} catch (IOException e) {

						}
					}
				}
			}
		}
		
		
		agentDAO.updateAgentEntityById(agent);
		return;
	}
	
	public void setAgentProfileFormWithBuyerEntity(AgentProfileForm form, AgentEntity agent)
	{
		if(agent != null)
		{
			if(StringUtil.isNotEmpty(agent.getMail()))
			{
				form.setEmail(agent.getMail());
			}
			if(StringUtil.isNotEmpty(agent.getCompanyName()))
			{
				form.setCompanyName(agent.getCompanyName());
			}
			if(StringUtil.isNotEmpty(agent.getAddress()))
			{
				form.setAddress(agent.getAddress());
			}
			if(StringUtil.isNotEmpty(agent.getBrandName()))
			{
				form.setBrandName(agent.getBrandName());
			}
			if(StringUtil.isNotEmpty(agent.getPhone()))
			{
				form.setCompanyPhone(agent.getPhone());
			}
			if(StringUtil.isNotEmpty(agent.getContactName()))
			{
				form.setContactName(agent.getContactName());
			}
			if(StringUtil.isNotEmpty(agent.getContactPhone()))
			{
				form.setContactPhone(agent.getContactPhone());
			}
			if(StringUtil.isNotEmpty(agent.getContactQq()))
			{
				form.setContactQQ(agent.getContactQq());
			}
			if(StringUtil.isNotEmpty(agent.getDescription()))
			{
				form.setDescription(agent.getDescription());
			}
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
    	if(StringUtil.isNotEmpty(form.getName()))
    	{
    		gc.setName(form.getName());
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
    public Result updateGoodcaseById(GoodcaseForm form, GoodcaseEntity gc, Errors errors)
    {
    	Result result = new Result();
    	if(StringUtil.isEmpty(form.getName()))
		{
    		errors.rejectValue("name", "GOODCASE_NAME_IS_NOT_NULL");
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

    public Result querySamplesByUserid(int userId)
	{
		Result result = new Result();
		List<SampleEntity> samples = sampleDAO.querySampleEntityByUserid(userId);
		result.add("samples", samples);
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
    	if(StringUtil.isNotEmpty(form.getName()))
    	{
    		sample.setName(form.getName());
    	}
    	sampleDAO.insertSampleEntity(sample);
    	return result;
    	
    }
    
    public Result updateSampleById(SampleForm form, SampleEntity sample, Errors errors)
    {
    	Result result = new Result();
    	if(StringUtil.isEmpty(form.getName()))
		{
    		errors.rejectValue("name", "SAMPLE_NAME_IS_NOT_NULL");
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
    
    public Result getAllquestions()
	{
		Result result = new Result();
		QuestionEntity ques = new QuestionEntity();

		List<QuestionEntity> questions = questionDAO.getAllquestions(ques);
		result.add("questions", questions);
		return result;
	}
    
}
