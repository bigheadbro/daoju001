package com.banzhuan.dao.impl;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.banzhuan.dao.ItemDAO;
import com.banzhuan.entity.ItemEntity;

@Repository("itemDAO")
public class ItemDAOImpl extends SqlSessionDaoSupport implements ItemDAO {

	@Override
	public ItemEntity queryItemEntityById(int id)
	{
		return this.getSqlSession().selectOne("queryItemEntityById", id);
	}
	
	@Override
	public int insertItemEntity(ItemEntity gc) {
		this.getSqlSession().insert("insertItemEntity", gc);
		return gc.getId();
	}

	@Override
	public int updateItemById(ItemEntity gc) {
		return this.getSqlSession().update("updateItemById", gc);
	}

	@Override
	public List<ItemEntity> getAllItemsByType(ItemEntity gc)
	{
		return this.getSqlSession().selectList("getAllItemsByType", gc);
	}
	
	@Override
	public List<ItemEntity> getMainItemsByType()
	{
		return this.getSqlSession().selectList("getMainItemsByType");
	}
	
	@Override
	public int getItemCount(int userid)
	{
		return this.getSqlSession().selectOne("getItemCount", userid);
	}
	
	@Override
	public int getItemCountByType(ItemEntity gc)
	{
		return this.getSqlSession().selectOne("getItemCountByType", gc);
	}
	
	
}
