package com.banzhuan.dao;

import java.util.List;

import com.banzhuan.entity.CommentEntity;;

public interface CommentDAO {
	CommentEntity queryCommentEntityById(int id);
	
	public int insertCommentEntity(CommentEntity comment);
	
	public int updateCommentById(CommentEntity comment);
	
	public List<CommentEntity> getCommentsInQuesByParentid(int pid);
	
	public List<CommentEntity> getCommentsInAnswerByParentid(int pid);
}
