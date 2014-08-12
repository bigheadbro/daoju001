package com.banzhuan.dao.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.banzhuan.dao.StockDAO;
import com.banzhuan.entity.StockEntity;

@Repository("stockDAO")
public class StockDAOImpl extends SqlSessionDaoSupport implements StockDAO {

	@Override
	public List<StockEntity> queryAllStock() {
		return this.getSqlSession().selectList("queryAllStock");
	}

	@Override
	public List<StockEntity> querywxStock() {
		return this.getSqlSession().selectList("querywxStock");
	}

	@Override
	public int insertStockEntity(StockEntity stock) {
		this.getSqlSession().insert("insertStockEntity", stock);
		return stock.getId();
	}

}
