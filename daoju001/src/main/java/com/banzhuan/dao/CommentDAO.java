package com.banzhuan.dao;

import com.banzhuan.entity.CommentEntity;;

public interface CommentDAO {
	CommentEntity queryCommentEntityById(int id);
	
	public int insertCommentEntity(CommentEntity buyer);
	
	public int updateCommentById(CommentEntity buyer);
}
