package com.banzhuan.dao.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.banzhuan.dao.CuttingToolDAO;
import com.banzhuan.entity.CuttingToolEntity;

@Repository("ctDAO")
public class CuttingToolDAOImpl extends SqlSessionDaoSupport implements CuttingToolDAO {

	@Override
	public CuttingToolEntity queryCuttingToolById(int id) {
		return this.getSqlSession().selectOne("queryCuttingToolById", id);
	}

	@Override
	public int insertCuttingToolEntity(CuttingToolEntity hotct) {
		this.getSqlSession().insert("insertCuttingToolEntity", hotct);
		return hotct.getId();
	}
	
	@Override
	public void test()
	{
		this.getSqlSession().insert("test");
	}
	
	@Override
	public List<CuttingToolEntity> searchCuttingTool(Map<String, String> map) {
		return this.getSqlSession().selectList("searchCuttingTool", map);
	}
	
	@Override
	public List<CuttingToolEntity> getAllItems()
	{
		return this.getSqlSession().selectList("getAllItems");
	}
}
