package com.banzhuan.dao.impl;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.banzhuan.dao.ProductDAO;
import com.banzhuan.entity.ProductEntity;

@Repository("productDAO")
public class ProductDAOImpl extends SqlSessionDaoSupport implements ProductDAO {

	@Override
	public ProductEntity queryProductEntityById(int id)
	{
		return this.getSqlSession().selectOne("queryProductEntityById", id);
	}
	
	@Override
	public List<ProductEntity> queryProductEntityByUserid(int id, RowBounds bound) {
		return this.getSqlSession().selectList("queryProductEntityByUserid", id, bound);
	}
	

	@Override
	public List<ProductEntity> queryProductEntityByAgentid(int id) {
		return this.getSqlSession().selectList("queryProductEntityByAgentid", id);
	}
	
	@Override
	public List<ProductEntity> queryProductEntityByBuyerid(int id) {
		return this.getSqlSession().selectList("queryProductEntityByBuyerid", id);
	}
	
	@Override
	public int insertProductEntity(ProductEntity gc) {
		this.getSqlSession().insert("insertProductEntity", gc);
		return gc.getId();
	}

	@Override
	public int updateProductById(ProductEntity gc) {
		return this.getSqlSession().update("updateProductById", gc);
	}

	@Override
	public List<ProductEntity> getAllProductsByType(ProductEntity gc)
	{
		return this.getSqlSession().selectList("getAllProductsByType", gc);
	}
	
	@Override
	public List<ProductEntity> getAllProductsByType(ProductEntity gc, RowBounds bound)
	{
		return this.getSqlSession().selectList("getAllProductsByType", gc, bound);
	}
	
	@Override
	public List<ProductEntity> getMainProductsByType()
	{
		return this.getSqlSession().selectList("getMainProductsByType");
	}
	
	@Override
	public int getProductCount(ProductEntity Product)
	{
		return this.getSqlSession().selectOne("getProductCount", Product);
	}
	
	@Override
	public int getProductCountByType(ProductEntity gc)
	{
		return this.getSqlSession().selectOne("getProductCountByType", gc);
	}
	
	@Override
	public void delProduct(int id)
	{
		this.getSqlSession().delete("delProduct", id);
	}
	
	
	
}
