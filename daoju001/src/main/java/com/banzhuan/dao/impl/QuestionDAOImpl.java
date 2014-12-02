package com.banzhuan.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;
import com.banzhuan.dao.QuestionDAO;
import com.banzhuan.entity.QuestionEntity;
import com.banzhuan.entity.SampleEntity;

@Repository("questionDAO")
public class QuestionDAOImpl extends SqlSessionDaoSupport implements QuestionDAO {

	@Override
	public QuestionEntity queryQuestionEntityById(int id) {
		return this.getSqlSession().selectOne("queryQuestionEntityById", id);
	}

	@Override
	public int insertQuestionEntity(QuestionEntity question) {
		this.getSqlSession().insert("insertQuestionEntity", question);
		return question.getId();
	}

	@Override
	public int updateQuestionById(QuestionEntity question) {
		return this.getSqlSession().update("updateQuestionById", question);
	}
	
	@Override
	public List<QuestionEntity> queryQuestionsByUserid(int qid)
	{
		return this.getSqlSession().selectList("queryQuestionsByUserId", qid);
	}
	
	@Override
	public List<QuestionEntity> queryDraftsByUserid(int userid)
	{
		return this.getSqlSession().selectList("queryDraftsByUserId", userid);
	}
	
	@Override
	public List<QuestionEntity> queryQuestionsByUserid(int qid, RowBounds bound) 
	{
		return this.getSqlSession().selectList("queryQuestionsByUserId", qid, bound);
	}
	
	@Override
	public List<QuestionEntity> queryDraftsByUserid(int userid, RowBounds bound) 
	{
		return this.getSqlSession().selectList("queryDraftsByUserId", userid, bound);
	}
	
	
	@Override
	public int queryDraftsCountByUserid(int userid)
	{
		return this.getSqlSession().selectOne("queryDraftsCountByUserid", userid);
	}
	
	@Override
	public List<QuestionEntity> getAllquestions(QuestionEntity question)
	{
		return this.getSqlSession().selectList("getAllquestions", question);
	}
	
	@Override
	public List<QuestionEntity> getMainquestions()
	{
		return this.getSqlSession().selectList("getMainquestions");
	}
	
	@Override
	public int getUserTodayQuestionCount(int userid)
	{
		return this.getSqlSession().selectOne("getUserTodayQuestionCount", userid);
	}
	
	@Override
	public int getUserQuestionCount(int userid)
	{
		return this.getSqlSession().selectOne("getUserQuestionCount", userid);
	}
	
	@Override
	public int getAllQuestionCount(QuestionEntity question)
	{
		return this.getSqlSession().selectOne("getAllQuestionCount",question);
	}

	@Override
	public List<QuestionEntity> getAllquestions(QuestionEntity question, RowBounds bound) {
		return this.getSqlSession().selectList("getAllquestions", question, bound);
	}
	
	@Override
	public void delQuestion(int qid)
	{
		this.getSqlSession().delete("delQuestion", qid);
	}
	
	@Override
	public int getQuestionCount(boolean istoday)
	{
		return this.getSqlSession().selectOne("getQuestionCount",istoday); 
	}

	@Override
	public List<QuestionEntity> getLatestQuestion()
	{
		return this.getSqlSession().selectList("getLatestQuestion"); 
	}
	
}
