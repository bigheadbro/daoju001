package com.banzhuan.dao.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.banzhuan.dao.CommentDAO;
import com.banzhuan.entity.CommentEntity;

@Repository("commentDAO")
public class CommentDAOImpl extends SqlSessionDaoSupport implements CommentDAO {

	@Override
	public CommentEntity queryCommentEntityById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insertCommentEntity(CommentEntity comment) {
		this.getSqlSession().insert("insertCommentEntity", comment);
		return comment.getId();
	}

	@Override
	public int updateCommentById(CommentEntity buyer) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<CommentEntity> getCommentsInQuesByParentid(int pid)
	{
		return this.getSqlSession().selectList("getCommentsInQuesByParentid",pid);
	}
	
	@Override
	public List<CommentEntity> getCommentsInAnswerByParentid(int pid)
	{
		return this.getSqlSession().selectList("getCommentsInAnswerByParentid",pid);
	}
	
	@Override
	public List<CommentEntity> getAllComments()
	{
		return this.getSqlSession().selectList("getAllComments");
	}
	
	@Override
	public void updateUseridById(CommentEntity comment)
	{
		this.getSqlSession().update("updateUseridById", comment);
	}
}
