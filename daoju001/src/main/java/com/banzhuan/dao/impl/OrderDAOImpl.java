package com.banzhuan.dao.impl;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.banzhuan.dao.OrderDAO;
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
	public List<String> getOrderTypeList(int type)
	{
		return this.getSqlSession().selectList("getOrderTypeList", type);
	}
	
}
