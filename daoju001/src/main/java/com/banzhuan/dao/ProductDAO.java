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
	
	List<ProductEntity> getAllProducts();
	
	List<ProductEntity> getAllProductsByType(ProductEntity Product, RowBounds bound);
	
	List<ProductEntity> getMainProducts();
	
	int getProductCountByType(ProductEntity Product);
	
	int getProductCountByUserid(int userid);
	
	void delProduct(int id);
	
	int getProductCount(boolean istoday);
}
