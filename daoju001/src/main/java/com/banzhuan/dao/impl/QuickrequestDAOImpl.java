package com.banzhuan.dao.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.banzhuan.dao.QuickrequestDAO;
import com.banzhuan.entity.QuickrequestEntity;

@Repository("quickrequestDAO")
public class QuickrequestDAOImpl extends SqlSessionDaoSupport implements QuickrequestDAO {

	@Override
	public QuickrequestEntity queryQuickrequestById(int id) {
		return this.getSqlSession().selectOne("queryQuickrequestById", id);
	}

	@Override
	public List<QuickrequestEntity> queryQuickrequests() {
		return this.getSqlSession().selectList("queryQuickrequests");
	}

	@Override
	public int insertQuickrequestEntity(QuickrequestEntity qr) {
		this.getSqlSession().insert("updateQuickrequestCountById", qr);
		return qr.getId();
	}

	@Override
	public int updateQuickrequestCountById(int id) {
		return this.getSqlSession().update("updateQuickrequestById", id);
	}
	
	@Override
	public void delQuickrequest(int id)
	{
		this.getSqlSession().delete("delQuickrequest",id);
	}

}
