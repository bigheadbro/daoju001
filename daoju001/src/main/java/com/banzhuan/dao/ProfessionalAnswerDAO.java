package com.banzhuan.dao;

import com.banzhuan.entity.ProfessionalAnswerEntity;;

public interface ProfessionalAnswerDAO {
	ProfessionalAnswerEntity queryProfessionalAnswerEntityById(int id);
	
	public int insertProfessionalAnswerEntity(ProfessionalAnswerEntity pa);
	
	public int updateProfessionalAnswerById(ProfessionalAnswerEntity pa);
}
