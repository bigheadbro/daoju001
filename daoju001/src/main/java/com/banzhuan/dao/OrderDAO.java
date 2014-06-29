package com.banzhuan.dao;

import java.util.List;

import com.banzhuan.entity.OrderEntity;;;

public interface OrderDAO {
	OrderEntity queryOrderEntityById(int id);

	public int insertOrderEntity(OrderEntity Order);
	
	List<String> getOrderTypeList(int type);
}
