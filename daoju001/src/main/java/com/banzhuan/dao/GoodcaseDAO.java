package com.banzhuan.dao;

import com.banzhuan.entity.GoodcaseEntity;;

public interface GoodcaseDAO {
	GoodcaseEntity queryBuyerEntityById(int id);
	
	public int insertGoodcaseEntity(GoodcaseEntity buyer);
	
	public int updateGoodcaseById(GoodcaseEntity buyer);
}
