package com.banzhuan.dao.impl;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.banzhuan.dao.EventDAO;
import com.banzhuan.entity.EventEntity;

@Repository("eventDAO")
public class EventDAOImpl extends SqlSessionDaoSupport implements EventDAO {

	@Override
	public EventEntity queryEventById(int id) {
		return this.getSqlSession().selectOne("queryEventEntityById", id);
	}

	@Override
	public int insertEventEntity(EventEntity e) {
		this.getSqlSession().insert("insertEventEntity", e);
		return e.getId();
	}

}
