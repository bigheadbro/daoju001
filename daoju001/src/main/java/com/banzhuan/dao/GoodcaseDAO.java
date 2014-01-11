package com.banzhuan.dao;

import java.util.List;

import com.banzhuan.entity.GoodcaseEntity;;

public interface GoodcaseDAO {
	List<GoodcaseEntity> queryGCEntityByUserid(int id);
	
	public int insertGoodcaseEntity(GoodcaseEntity gc);
	
	public int updateGoodcaseById(GoodcaseEntity gc);
}
