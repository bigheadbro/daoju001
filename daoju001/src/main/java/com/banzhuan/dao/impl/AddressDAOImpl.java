package com.banzhuan.dao.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.banzhuan.dao.AddressDAO;
import com.banzhuan.entity.AddressEntity;

@Repository("addressDAO")
public class AddressDAOImpl extends SqlSessionDaoSupport implements AddressDAO {

	@Override
	public AddressEntity queryAddressById(int id) {
		return this.getSqlSession().selectOne("queryAddressById", id);
	}

	@Override
	public List<AddressEntity> queryAddressByUserid(int uid) {
		return this.getSqlSession().selectList("queryAddressByUserid",uid);
	}

	@Override
	public int insertAgentEntity(AddressEntity address) {
		this.getSqlSession().insert("insertAgentEntity", address);
		return address.getId();
	}

	@Override
	public int updateAddressById(AddressEntity addr) {
		return this.getSqlSession().update("updateAddressById", addr);
	}

}
