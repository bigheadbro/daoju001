/**
 * 
 */
package com.banzhuan.dao.impl;

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
	public AgentEntity queryUserEntityById(int id) {
		return this.getSqlSession().selectOne("queryAgentEntityById", id);
	}

	@Override
	public int insertAgentEntity(AgentEntity agent) {
		this.getSqlSession().insert("insertAgentEntity", agent);
		return agent.getId();
	}
}
