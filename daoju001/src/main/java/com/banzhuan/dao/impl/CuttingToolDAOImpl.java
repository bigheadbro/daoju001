package com.banzhuan.dao.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.banzhuan.dao.CuttingToolDAO;
import com.banzhuan.entity.AddressEntity;
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
	
	@Override
	public List<CuttingToolEntity> querySeriesByCode(String code, boolean isLeaf)
	{
		if(isLeaf)
			return this.getSqlSession().selectList("querySeriesByCode", code+"*");
		else
			return this.getSqlSession().selectList("querySeriesByCode", code);
	}
	
	@Override
	public List<CuttingToolEntity> getVersionsBySeries(String sn)
	{
		return this.getSqlSession().selectList("getVersionsBySeries", sn);
	}
	
	@Override
	public List<CuttingToolEntity> getAllSeries()
	{
		return this.getSqlSession().selectList("getAllSeries");
	}
	
	@Override
	public int updateCuttingToolById(CuttingToolEntity ct) {
		return this.getSqlSession().update("updateCuttingToolById", ct);
	}
	
	@Override
	public int getBrandCountByCode(String code) {
		return this.getSqlSession().selectOne("getBrandCountByCode", code);
	}
	
	@Override
	public int getSeriesnameCountByCode(String code) {
		return this.getSqlSession().selectOne("getSeriesnameCountByCode", code);
	}
	
	@Override
	public List<CuttingToolEntity> queryCuttingToolByCode(String code)
	{
		return this.getSqlSession().selectList("queryCuttingToolByCode", code);
	}
	
	@Override
	public List<CuttingToolEntity> getSeriesByParam(CuttingToolEntity ct)
	{
		return this.getSqlSession().selectList("getSeriesByParam",ct);
	}
	@Override
	public List<CuttingToolEntity> queryCuttingToolByCt(CuttingToolEntity ct)
	{
		return this.getSqlSession().selectList("queryCuttingToolByCt",ct);
	}
	
}
