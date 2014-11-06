package com.banzhuan.dao.impl;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.banzhuan.dao.OrderDAO;
import com.banzhuan.entity.AddressEntity;
import com.banzhuan.entity.OrderEntity;

@Repository("orderDAO")
public class OrderDAOImpl extends SqlSessionDaoSupport implements OrderDAO {

	@Override
	public OrderEntity queryOrderEntityById(int id)
	{
		return this.getSqlSession().selectOne("queryOrderEntityById", id);
	}
	
	@Override
	public int insertOrderEntity(OrderEntity gc) {
		this.getSqlSession().insert("insertOrderEntity", gc);
		return gc.getId();
	}
	
	@Override
	public int updateOrder(OrderEntity order)
	{
		return this.getSqlSession().update("updateOrder", order);
	}
	
	@Override
	public List<OrderEntity> queryOrdersByUserid(int uid, int type, RowBounds bound) {
		return this.getSqlSession().selectList("queryOrdersByUserid",uid, bound);
	}
	
	@Override
	public int getOrdersCount(int uid, int type)
	{
		return this.getSqlSession().selectOne("getOrdersCount",uid);
	}
	
	@Override
	public List<OrderEntity> getAllOrders(OrderEntity order)
	{
		return this.getSqlSession().selectList("getAllOrders", order);
	}
}
