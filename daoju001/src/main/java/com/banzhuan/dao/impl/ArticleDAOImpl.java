package com.banzhuan.dao.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.banzhuan.dao.ArticleDAO;
import com.banzhuan.entity.ArticleEntity;

@Repository("articleDAO")
public class ArticleDAOImpl extends SqlSessionDaoSupport implements ArticleDAO {

	@Override
	public List<ArticleEntity> getAllarticles() {
		return this.getSqlSession().selectList("getAllarticles");
	}

	@Override
	public int insertArticleEntity(ArticleEntity article) {
		this.getSqlSession().insert("insertArticleEntity", article);
		return article.getId();
	}

	@Override
	public int updateArticleById(ArticleEntity addr) {
		return this.getSqlSession().update("updateArticleById", addr);
	}
	
	@Override
	public void delArticle(int id)
	{
		this.getSqlSession().delete("delArticle",id);
	}

}
