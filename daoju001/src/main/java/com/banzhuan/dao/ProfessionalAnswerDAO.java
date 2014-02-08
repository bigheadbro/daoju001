package com.banzhuan.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.banzhuan.entity.ProfessionalAnswerEntity;;

public interface ProfessionalAnswerDAO {
	ProfessionalAnswerEntity queryProfessionalAnswerEntityById(int id);
	
	public int insertProfessionalAnswerEntity(ProfessionalAnswerEntity pa);
	
	public int updateProfessionalAnswerById(ProfessionalAnswerEntity pa);
	
	List<ProfessionalAnswerEntity> queryAnswersByQid(int qid);
	
	List<ProfessionalAnswerEntity> queryAnswersByUserid(int userid);
	
	List<ProfessionalAnswerEntity> queryAnswersByUserid(int userid, RowBounds bound);
	
	List<ProfessionalAnswerEntity> queryDraftsByUserid(int userid);
	
	List<ProfessionalAnswerEntity> queryDraftsByUserid(int userid, RowBounds bound);
	
	int getAnswerCount(int userid);
}
