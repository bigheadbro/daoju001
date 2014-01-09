package com.banzhuan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import com.banzhuan.dao.AgentDAO;
import com.banzhuan.entity.AgentEntity;
import com.banzhuan.entity.BuyerEntity;
import com.banzhuan.common.Result;
import com.banzhuan.form.AgentProfileForm;
import com.banzhuan.form.BuyerProfileForm;
import com.banzhuan.form.LoginForm;
import com.banzhuan.form.RegForm;
import com.banzhuan.util.StringUtil;

/**
 * @author guichaoqun
 *
 */
@Service("agentService")
public class AgentService {
	@Autowired
	@Qualifier("agentDAO")
	private AgentDAO agentDAO;
	
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
	
	public void updateAgentAccnt(AgentProfileForm form, AgentEntity agent)
	{
		if(form != null)
		{
			if(form.getUserName() != "")
			{
				
			}
			if(form.getCompanyName() != "")
			{
				
			}
			if(form.getAddress() != "")
			{
				
			}
			if(form.getCompanyPhone() != "")
			{
				
			}
			if(form.getContactName() != "")
			{
				
			}
			if(form.getContactPhone() != "")
			{
				
			}
			if(form.getContactQQ() != "")
			{
				
			}
		}
		agentDAO.updateAgentEntityById(agent);
		return;
	}
}
