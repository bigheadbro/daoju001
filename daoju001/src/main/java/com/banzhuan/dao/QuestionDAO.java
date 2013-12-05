package com.banzhuan.dao;

import com.banzhuan.entity.QuestionEntity;;

public interface QuestionDAO {
	QuestionEntity queryQuestionEntityById(int id);
	
	public int insertQuestionEntity(QuestionEntity buyer);
	
	public int updateQuestionById(QuestionEntity buyer);
}
