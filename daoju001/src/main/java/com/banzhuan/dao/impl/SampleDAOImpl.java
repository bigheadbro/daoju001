package com.banzhuan.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.banzhuan.dao.SampleDAO;
import com.banzhuan.entity.SampleEntity;

@Repository("sampleDAO")
public class SampleDAOImpl extends SqlSessionDaoSupport implements SampleDAO {

	@Override
	public SampleEntity querySampleEntityById(int id) {
		return this.getSqlSession().selectOne("querySampleEntityById", id);
	}

	@Override
	public int insertSampleEntity(SampleEntity sample) {
		this.getSqlSession().insert("insertSampleEntity", sample);
		return sample.getId();
	}

	@Override
	public int updateSampleById(SampleEntity sample) {
		return this.getSqlSession().update("updateSampleById", sample);
	}
	
	@Override
	public List<SampleEntity> querySampleEntityByUserid(int id, RowBounds bound) {
		return this.getSqlSession().selectList("querySampleEntityByUserid", id, bound);
	}
	
	@Override
	public List<SampleEntity> getAllsamples() {
		return this.getSqlSession().selectList("getAllsamples");
	}
	
	@Override
	public int getSampleCount(int userid)
	{
		return this.getSqlSession().selectOne("getSampleCount", userid);
	}
	
	@Override
	public void delSample(int sid)
	{
		this.getSqlSession().delete("delSample", sid);
	}

}
