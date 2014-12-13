package com.banzhuan.dao;

import java.util.List;

import com.banzhuan.entity.StatisticsEntity;

/**
 * 代理商DAO
 * @author guichaoqun
 *
 */
public interface StatisticsDAO {

	public int insertStatisticsEntity(StatisticsEntity st);
	
	public List<StatisticsEntity> getStatistcisByType(int type);

}
