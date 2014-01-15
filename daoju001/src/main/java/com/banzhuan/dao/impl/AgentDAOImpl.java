/**
 * 
 */
package com.banzhuan.dao.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.banzhuan.dao.AgentDAO;
import com.banzhuan.entity.AgentEntity;

/**
 * @author guichaoqun
 *
 */
@Repository("agentDAO")
public class AgentDAOImpl extends SqlSessionDaoSupport implements AgentDAO {

	@Override
	public AgentEntity queryAgentEntityById(int id) {
		return this.getSqlSession().selectOne("queryAgentEntityById", id);
	}
	
	@Override
	public AgentEntity queryAgentEntityByMail(String mail)
	{
		return this.getSqlSession().selectOne("queryAgentEntityByMail", mail);
	}

	@Override
	public int insertAgentEntity(AgentEntity agent) {
		this.getSqlSession().insert("insertAgentEntity", agent);
		return agent.getId();
	}
	
	@Override
	public int updateAgentPwdById(AgentEntity agent) {
		return this.getSqlSession().update("updateAgentPwdById", agent);
	}
	
	@Override
	public int updateAgentEntityById(AgentEntity agent) {
		return this.getSqlSession().update("updateAgentEntityById", agent);
	}
	
	@Override
	public List<AgentEntity> getAllagents()
	{
		return this.getSqlSession().selectList("getAllagents");
	}
	
	@Override
	public List<AgentEntity> getMainagents()
	{
		return this.getSqlSession().selectList("getMainagents");
	}

}
