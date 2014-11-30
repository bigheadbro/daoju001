package com.banzhuan.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.banzhuan.entity.QuestionEntity;;

public interface QuestionDAO {
	QuestionEntity queryQuestionEntityById(int id);
	
	public int insertQuestionEntity(QuestionEntity question);
	
	public int updateQuestionById(QuestionEntity question);
	
	public List<QuestionEntity> queryQuestionsByUserid(int userid);
	
	public List<QuestionEntity> queryDraftsByUserid(int userid);
	
	public List<QuestionEntity> queryQuestionsByUserid(int userid, RowBounds bound);
	
	public List<QuestionEntity> queryDraftsByUserid(int userid, RowBounds bound);
	
	int queryDraftsCountByUserid(int userid);
	
	public List<QuestionEntity> getAllquestions(QuestionEntity question);
	
	public List<QuestionEntity> getAllquestions(QuestionEntity question, RowBounds bound);
	
	public List<QuestionEntity> getMainquestions();
	
	int getUserQuestionCount(int userid);
	
	int getUserTodayQuestionCount(int userid);
	
	int getAllQuestionCount(QuestionEntity question);
	
	void delQuestion(int qid);
	
	int getQuestionCount(boolean istoday);
	
	QuestionEntity getLatestQuestion();
}
