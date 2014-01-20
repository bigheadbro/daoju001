package com.banzhuan.dao.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.banzhuan.dao.ProfessionalAnswerDAO;
import com.banzhuan.entity.ProfessionalAnswerEntity;

@Repository("paDAO")
public class ProfessionalAnswerDAOImpl extends SqlSessionDaoSupport implements ProfessionalAnswerDAO {

	@Override
	public ProfessionalAnswerEntity queryProfessionalAnswerEntityById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insertProfessionalAnswerEntity(ProfessionalAnswerEntity pa) {
		this.getSqlSession().insert("insertProfessionalAnswerEntity", pa);
		return pa.getId();
	}

	@Override
	public int updateProfessionalAnswerById(ProfessionalAnswerEntity buyer) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public List<ProfessionalAnswerEntity> queryAnswersByQid(int qid)
	{
		return this.getSqlSession().selectList("queryAnswersByQid", qid);
	}

}
