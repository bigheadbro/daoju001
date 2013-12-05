package com.banzhuan.dao;

import com.banzhuan.entity.SampleEntity;;

public interface SampleDAO {
	SampleEntity queryBuyerEntityById(int id);
	
	public int insertSampleEntity(SampleEntity buyer);
	
	public int updateSampleById(SampleEntity buyer);
}
