package com.banzhuan.dao.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;
import com.banzhuan.dao.QuestionDAO;
import com.banzhuan.entity.QuestionEntity;

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
	public int updateQuestionById(QuestionEntity buyer) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public List<QuestionEntity> queryQuestionsByUserid(int userid)
	{
		return this.getSqlSession().selectList("queryQuestionsByUserId", userid);
	}

}
