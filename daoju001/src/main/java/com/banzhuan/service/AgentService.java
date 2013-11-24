package com.banzhuan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import com.banzhuan.dao.AgentDAO;
import com.banzhuan.entity.AgentEntity;
import com.banzhuan.common.Result;
import com.banzhuan.form.LoginForm;

/**
 * @author guichaoqun
 *
 */
@Service("agentService")
public class AgentService {
	@Autowired
	@Qualifier("agentDAO")
	private AgentDAO agentDAO;

	public Result test(LoginForm form, Errors errors)
	{
		Result result = new Result();
		AgentEntity agent = agentDAO.queryUserEntityById(1);
		result.add("agent", agent);
		return result;
	}
}
