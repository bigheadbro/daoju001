package com.banzhuan.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.banzhuan.entity.ProductEntity;;;

public interface ProductDAO {
	ProductEntity queryProductEntityById(int id);
	
	List<ProductEntity> queryProductEntityByAgentid(int id);
	
	List<ProductEntity> queryProductEntityByBuyerid(int id);
	
	List<ProductEntity> queryProductEntityByUserid(int id);
	
	List<ProductEntity> queryProductEntityByUserid(int id, RowBounds bound);
	
	public int insertProductEntity(ProductEntity Product);
	
	public int updateProductById(ProductEntity Product);
	
	List<ProductEntity> getAllProductsByType(ProductEntity Product);
	
	List<ProductEntity> getAllProductsByType(ProductEntity Product, RowBounds bound);
	
	List<ProductEntity> getMainProductsByType();
	
	int getProductCountByType(ProductEntity Product);
	
	int getProductCount(ProductEntity Product);
	
	void delProduct(int id);
}
