package com.banzhuan.dao;

import java.util.List;

import com.banzhuan.entity.EventEntity;

public interface EventDAO {
	EventEntity queryEventById(int id);
	
	public int insertEventEntity(EventEntity e);

	List<EventEntity> getAllevents();
	
}
