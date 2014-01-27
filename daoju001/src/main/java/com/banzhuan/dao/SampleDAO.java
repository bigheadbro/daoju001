package com.banzhuan.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.banzhuan.entity.SampleEntity;;

public interface SampleDAO {
	SampleEntity querySampleEntityById(int id);
	
	public int insertSampleEntity(SampleEntity buyer);
	
	public int updateSampleById(SampleEntity buyer);
	
	public List<SampleEntity> querySampleEntityByUserid(int id, RowBounds bound);
	
	List<SampleEntity> getAllsamples();
	
	int getSampleCount(int userid);
}
