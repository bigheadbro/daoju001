package com.banzhuan.dao.impl;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.banzhuan.dao.GoodcaseDAO;
import com.banzhuan.entity.GoodcaseEntity;

@Repository("gcDAO")
public class GoodcaseDAOImpl extends SqlSessionDaoSupport implements GoodcaseDAO {

	@Override
	public GoodcaseEntity queryGCEntityById(int id)
	{
		return this.getSqlSession().selectOne("queryGCEntityById", id);
	}
	
	@Override
	public List<GoodcaseEntity> queryGCEntityByUserid(int id, RowBounds bound) {
		return this.getSqlSession().selectList("queryGCEntityByUserid", id, bound);
	}

	@Override
	public List<GoodcaseEntity> queryGCEntityByUserid(int id) {
		return this.getSqlSession().selectList("queryGCEntityByUserid", id);
	}
	
	@Override
	public int insertGoodcaseEntity(GoodcaseEntity gc) {
		this.getSqlSession().insert("insertGoodcaseEntity", gc);
		return gc.getId();
	}

	@Override
	public int updateGoodcaseById(GoodcaseEntity gc) {
		return this.getSqlSession().update("updateGoodcaseById", gc);
	}

	@Override
	public List<GoodcaseEntity> getAllGoodcasesByType(GoodcaseEntity gc)
	{
		return this.getSqlSession().selectList("getAllGoodcasesByType", gc);
	}
	
	@Override
	public List<GoodcaseEntity> getAllGoodcasesByType(GoodcaseEntity gc, RowBounds bound)
	{
		return this.getSqlSession().selectList("getAllGoodcasesByType", gc, bound);
	}
	
	@Override
	public List<GoodcaseEntity> getMainGoodcasesByType()
	{
		return this.getSqlSession().selectList("getMainGoodcasesByType");
	}
	
	@Override
	public int getGoodcaseCount(int userid)
	{
		return this.getSqlSession().selectOne("getGoodcaseCount", userid);
	}
	
	@Override
	public int getGoodcaseCountByType(GoodcaseEntity gc)
	{
		return this.getSqlSession().selectOne("getGoodcaseCountByType", gc);
	}
	
	@Override
	public void delGoodcase(int id)
	{
		this.getSqlSession().delete("delGoodcase", id);
	}
	
	
	
}
