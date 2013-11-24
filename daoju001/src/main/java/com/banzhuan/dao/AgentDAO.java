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

}
