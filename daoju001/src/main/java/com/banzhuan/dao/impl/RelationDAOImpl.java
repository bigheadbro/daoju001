package com.banzhuan.dao.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.banzhuan.dao.RelationDAO;
import com.banzhuan.entity.RelationEntity;

@Repository("relationDAO")
public class RelationDAOImpl extends SqlSessionDaoSupport implements RelationDAO {

	@Override
	public RelationEntity queryRelationByRelation(RelationEntity relation) {
		return this.getSqlSession().selectOne("queryRelationByRelation", relation);
	}

	@Override
	public List<RelationEntity> queryRelationById(int id) {
		return this.getSqlSession().selectList("queryRelationById",id);
	}

	@Override
	public int insertRelationEntity(RelationEntity relation) {
		this.getSqlSession().insert("insertRelationEntity", relation);
		return relation.getId();
	}
	
	@Override
	public void delRelation(RelationEntity relation)
	{
		this.getSqlSession().delete("delRelation",relation);
	}

}
