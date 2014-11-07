package com.banzhuan.dao.impl;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.banzhuan.dao.NormalctDAO;
import com.banzhuan.entity.NormalctEntity;

@Repository("normalctDAO")
public class NormalctDAOImpl extends SqlSessionDaoSupport implements NormalctDAO {

	@Override
	public NormalctEntity queryNormalctById(int id) {
		return this.getSqlSession().selectOne("queryNormalctById", id);
	}

	@Override
	public int insertNormalctEntity(NormalctEntity normalct) {
		this.getSqlSession().insert("insertNormalctEntity", normalct);
		return normalct.getId();
	}
}
