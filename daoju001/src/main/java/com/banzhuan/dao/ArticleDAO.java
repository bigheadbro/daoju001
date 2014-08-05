package com.banzhuan.dao;

import java.util.List;

import com.banzhuan.entity.ArticleEntity;
/**
 * 代理商DAO
 * @author guichaoqun
 *
 */
public interface ArticleDAO {
	
	List<ArticleEntity> getAllarticles();

	public int insertArticleEntity(ArticleEntity article);

	public int updateArticleById(ArticleEntity article);
	
	void delArticle(int id);

}
