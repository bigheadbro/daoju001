package com.banzhuan.dao;

import com.banzhuan.entity.EventEntity;;

public interface EventDAO {
	EventEntity queryEventById(int id);
	
	public int insertEventEntity(EventEntity e);
	
}
