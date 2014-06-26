package com.banzhuan.dao;

import java.util.List;

import com.banzhuan.entity.ItemEntity;;;

public interface ItemDAO {
	ItemEntity queryItemEntityById(int id);

	public int insertItemEntity(ItemEntity Item);
	
	public int updateItemById(ItemEntity Item);
	
	List<ItemEntity> getAllItemsByType(ItemEntity Item);

	List<ItemEntity> getMainItemsByType();
	
	int getItemCountByType(ItemEntity Item);
	
	int getItemCount(int userid);
	
	List<String> getItemTypeList(int type);
}
