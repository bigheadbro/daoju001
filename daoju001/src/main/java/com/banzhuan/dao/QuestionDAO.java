package com.banzhuan.dao;

import java.util.List;

import com.banzhuan.entity.QuestionEntity;;

public interface QuestionDAO {
	QuestionEntity queryQuestionEntityById(int id);
	
	public int insertQuestionEntity(QuestionEntity question);
	
	public int updateQuestionById(QuestionEntity question);
	
	public List<QuestionEntity> queryQuestionsByUserid(int userid);
	
	public List<QuestionEntity> queryDraftsByUserid(int userid);
}
