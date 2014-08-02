package com.banzhuan.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
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
	public List<QuickrequestEntity> queryQuickrequests(int type, RowBounds bound) {
		return this.getSqlSession().selectList("queryQuickrequests",type,bound);
	}

	@Override
	public int getAllRequestsCount(int type)
	{
		return this.getSqlSession().selectOne("getAllRequestsCount", type);
	}
	
	@Override
	public List<QuickrequestEntity> queryMainQuickrequests() {
		return this.getSqlSession().selectList("queryMainQuickrequests");
	}
	
	@Override
	public List<QuickrequestEntity> queryQuickrequestsForwx() {
		return this.getSqlSession().selectList("queryQuickrequestsForwx");
	}
	
	@Override
	public int insertQuickrequestEntity(QuickrequestEntity qr) {
		this.getSqlSession().insert("insertQuickrequestEntity", qr);
		return qr.getId();
	}

	@Override
	public int updateQuickrequestCountById(int id) {
		return this.getSqlSession().update("updateQuickrequestCountById", id);
	}
	
	@Override
	public void delQuickrequest(int id)
	{
		this.getSqlSession().delete("delQuickrequest",id);
	}

}
