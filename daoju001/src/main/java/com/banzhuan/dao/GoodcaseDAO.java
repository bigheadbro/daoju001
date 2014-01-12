package com.banzhuan.dao;

import java.util.List;

import com.banzhuan.entity.GoodcaseEntity;;

public interface GoodcaseDAO {
	GoodcaseEntity queryGCEntityById(int id);
	
	List<GoodcaseEntity> queryGCEntityByUserid(int id);
	
	public int insertGoodcaseEntity(GoodcaseEntity gc);
	
	public int updateGoodcaseById(GoodcaseEntity gc);
	
	List<GoodcaseEntity> getAllGoodcasesByType(GoodcaseEntity gc);
}
