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
	public List<AddressEntity> queryAddressByUserid(int uid, int type) {
		if(type == 0)//agent or buyer?
		{
			return this.getSqlSession().selectList("queryAddressByBuyerid",uid);
		}
		else
		{
			return this.getSqlSession().selectList("queryAddressByAgentid",uid);
		}
	}

	@Override
	public int insertAddressEntity(AddressEntity address) {
		this.getSqlSession().insert("insertAddressEntity", address);
		return address.getId();
	}

	@Override
	public int updateAddressById(AddressEntity addr) {
		return this.getSqlSession().update("updateAddressById", addr);
	}

}
