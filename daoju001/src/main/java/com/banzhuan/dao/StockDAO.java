package com.banzhuan.dao;

import java.util.List;

import com.banzhuan.entity.StockEntity;
/**
 * 代理商DAO
 * @author guichaoqun
 *
 */
public interface StockDAO {
	List<StockEntity> querywxStock();
	
	List<StockEntity> queryAllStock();

	public int insertStockEntity(StockEntity stock);

}
