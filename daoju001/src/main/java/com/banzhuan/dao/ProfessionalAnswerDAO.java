package com.banzhuan.dao;

import com.banzhuan.entity.ProfessionalAnswerEntity;;

public interface ProfessionalAnswerDAO {
	ProfessionalAnswerEntity queryProfessionalAnswerEntityById(int id);
	
	public int insertProfessionalAnswerEntity(ProfessionalAnswerEntity buyer);
	
	public int updateProfessionalAnswerById(ProfessionalAnswerEntity buyer);
}
