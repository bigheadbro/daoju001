package com.banzhuan.dao.impl;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.banzhuan.dao.BuyerDAO;
import com.banzhuan.entity.BuyerEntity;

@Repository("buyerDAO")
public class BuyerDAOImpl extends SqlSessionDaoSupport implements BuyerDAO {

	@Override
	public BuyerEntity queryBuyerEntityById(int id) {
		return this.getSqlSession().selectOne("queryBuyerEntityById", id);
	}
	
	@Override
	public BuyerEntity queryBuyerEntityByMail(String mail) {
		return this.getSqlSession().selectOne("queryBuyerEntityByMail", mail);
	}

	@Override
	public int insertBuyerEntity(BuyerEntity buyer) {
		this.getSqlSession().insert("insertBuyerEntity", buyer);
		return buyer.getId();
	}

	@Override
	public int updateBuyerPwdById(BuyerEntity buyer) {
		return this.getSqlSession().update("updateBuyerPwdById", buyer);
	}

}
