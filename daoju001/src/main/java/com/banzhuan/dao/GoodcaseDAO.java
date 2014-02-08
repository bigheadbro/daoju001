package com.banzhuan.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.banzhuan.entity.GoodcaseEntity;;

public interface GoodcaseDAO {
	GoodcaseEntity queryGCEntityById(int id);
	
	List<GoodcaseEntity> queryGCEntityByUserid(int id);
	
	List<GoodcaseEntity> queryGCEntityByUserid(int id, RowBounds bound);
	
	public int insertGoodcaseEntity(GoodcaseEntity gc);
	
	public int updateGoodcaseById(GoodcaseEntity gc);
	
	List<GoodcaseEntity> getAllGoodcasesByType(GoodcaseEntity gc);
	
	List<GoodcaseEntity> getAllGoodcasesByType(GoodcaseEntity gc, RowBounds bound);
	
	List<GoodcaseEntity> getMainGoodcasesByType();
	
	int getGoodcaseCountByType(GoodcaseEntity gc);
	
	int getGoodcaseCount(int userid);
	
	void delGoodcase(int id);
}
