package com.banzhuan.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.banzhuan.dao.ProfessionalAnswerDAO;
import com.banzhuan.entity.ProfessionalAnswerEntity;

@Repository("paDAO")
public class ProfessionalAnswerDAOImpl extends SqlSessionDaoSupport implements ProfessionalAnswerDAO {

	@Override
	public ProfessionalAnswerEntity queryProfessionalAnswerEntityById(int id) {
		return this.getSqlSession().selectOne("queryProfessionalAnswerEntityById", id);
	}

	@Override
	public int insertProfessionalAnswerEntity(ProfessionalAnswerEntity pa) {
		this.getSqlSession().insert("insertProfessionalAnswerEntity", pa);
		return pa.getId();
	}

	@Override
	public int updateProfessionalAnswerById(ProfessionalAnswerEntity pa) {
		return this.getSqlSession().update("updateProfessionalAnswerById", pa);
	}
	
	@Override
	public List<ProfessionalAnswerEntity> queryAnswersByQid(int qid)
	{
		return this.getSqlSession().selectList("queryAnswersByQid", qid);
	}
	
	@Override
	public List<ProfessionalAnswerEntity> queryDraftsByUserid(int userid)
	{
		return this.getSqlSession().selectList("queryDraftsByUserid", userid);
	}
	
	@Override
	public List<ProfessionalAnswerEntity> queryDraftsByUserid(int userid, RowBounds bound)
	{
		return this.getSqlSession().selectList("queryDraftsByUserid", userid, bound);
	}
	
	@Override
	public List<ProfessionalAnswerEntity> queryAnswersByUserid(int userid)
	{
		return this.getSqlSession().selectList("queryAnswersByUserid", userid);
	}
	
	@Override
	public List<ProfessionalAnswerEntity> queryAnswersByUserid(int userid, RowBounds bound)
	{
		return this.getSqlSession().selectList("queryAnswersByUserid", userid, bound);
	}
	
	@Override
	public int getAnswerCount(int userid)
	{
		return this.getSqlSession().selectOne("getAnswerCount", userid);
	}

}
