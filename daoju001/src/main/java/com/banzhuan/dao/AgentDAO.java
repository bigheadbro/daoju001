package com.banzhuan.dao;

import com.banzhuan.entity.AgentEntity;

/**
 * 代理商DAO
 * @author guichaoqun
 *
 */
public interface AgentDAO {
	/**
	 * @param id
	 * @return
	 */
	AgentEntity queryUserEntityById(int id);
	
	/**
	 * 插入代理商信息
	 * @param companyUser
	 * @return
	 */
	public int insertAgentEntity(AgentEntity agent);
	
	

}
