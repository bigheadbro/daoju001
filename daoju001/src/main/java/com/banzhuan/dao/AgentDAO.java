package com.banzhuan.dao;

import java.util.List;

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
	AgentEntity queryAgentEntityById(int id);
	
	AgentEntity queryAgentEntityByMail(String mail);
	
	/**
	 * 插入代理商信息
	 * @param companyUser
	 * @return
	 */
	public int insertAgentEntity(AgentEntity agent);
	

	/**
	 * 更新代理商密码
	 * @param agent
	 * @return
	 */
	public int updateAgentPwdById(AgentEntity agent);
	
	public int updateAgentEntityById(AgentEntity agent);
	
	List<AgentEntity> getAllagents();
	
	List<AgentEntity> getMainagents();

}
