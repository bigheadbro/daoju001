package com.banzhuan.dao.impl;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.banzhuan.dao.HotctDAO;
import com.banzhuan.entity.HotctEntity;

@Repository("hotctDAO")
public class HotctDAOImpl extends SqlSessionDaoSupport implements HotctDAO {

	@Override
	public HotctEntity queryHotctById(int id) {
		return this.getSqlSession().selectOne("queryHotctById", id);
	}

	@Override
	public int insertHotctEntity(HotctEntity hotct) {
		this.getSqlSession().insert("insertHotctEntity", hotct);
		return hotct.getId();
	}
}
