package com.banzhuan.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.banzhuan.entity.AddressEntity;
import com.banzhuan.entity.ItemEntity;
import com.banzhuan.entity.OrderEntity;;;

public interface OrderDAO {
	OrderEntity queryOrderEntityById(int id);

	public int insertOrderEntity(OrderEntity Order);
	
	int updateOrder(OrderEntity order);
	
	List<OrderEntity> queryOrdersByUserid(int uid, int type, RowBounds bound);
	
	int getOrdersCount(int uid, int type);
	
	List<OrderEntity> getAllOrders(OrderEntity order);
}
