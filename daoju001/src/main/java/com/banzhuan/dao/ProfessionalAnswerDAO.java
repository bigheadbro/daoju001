package com.banzhuan.dao;

import java.util.List;

import com.banzhuan.entity.ProfessionalAnswerEntity;;

public interface ProfessionalAnswerDAO {
	ProfessionalAnswerEntity queryProfessionalAnswerEntityById(int id);
	
	public int insertProfessionalAnswerEntity(ProfessionalAnswerEntity pa);
	
	public int updateProfessionalAnswerById(ProfessionalAnswerEntity pa);
	
	List<ProfessionalAnswerEntity> queryAnswersByQid(int qid);
	
	int getAnswerCount(int userid);
}
