/**
 * 
 */
package com.banzhuan.dao.impl;


import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.banzhuan.dao.ComplainDAO;
import com.banzhuan.entity.ComplainEntity;

/**
 * @author guichaoqun
 *
 */
@Repository("complainDAO")
public class ComplainDAOImpl extends SqlSessionDaoSupport implements ComplainDAO {

	@Override
	public int insertComplainEntity(ComplainEntity complain){
		this.getSqlSession().insert("insertComplainEntity", complain);
		return complain.getId();
	}
}
