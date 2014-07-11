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
	public int insertItemEntity(ItemEntity item) {
		this.getSqlSession().insert("insertItemEntity", item);
		return item.getId();
	}

	@Override
	public int updateItemById(ItemEntity item) {
		return this.getSqlSession().update("updateItemById", item);
	}

	@Override
	public List<ItemEntity> getAllItemsByType(ItemEntity item)
	{
		return this.getSqlSession().selectList("getAllItemsByType", item);
	}
	
	@Override
	public List<ItemEntity> getMainItems()
	{
		return this.getSqlSession().selectList("getMainItems");
	}
	
	@Override
	public int getItemCount(int userid)
	{
		return this.getSqlSession().selectOne("getItemCount", userid);
	}
	
	@Override
	public int getItemCountByType(ItemEntity item)
	{
		return this.getSqlSession().selectOne("getItemCountByType", item);
	}
	
	@Override
	public List<String> getItemTypeList(int type)
	{
		return this.getSqlSession().selectList("getItemTypeList", type);
	}
	
}
