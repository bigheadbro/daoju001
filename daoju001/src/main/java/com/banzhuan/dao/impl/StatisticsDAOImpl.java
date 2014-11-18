/**
 * 
 */
package com.banzhuan.dao.impl;


import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;
import com.banzhuan.dao.StatisticsDAO;
import com.banzhuan.entity.StatisticsEntity;

/**
 * @author guichaoqun
 *
 */
@Repository("stDAO")
public class StatisticsDAOImpl extends SqlSessionDaoSupport implements StatisticsDAO {

	@Override
	public int insertStatisticsEntity(StatisticsEntity st){
		this.getSqlSession().insert("insertStatisticsEntity", st);
		return st.getId();
	}
}
