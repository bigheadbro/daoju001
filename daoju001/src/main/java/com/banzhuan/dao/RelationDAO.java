package com.banzhuan.dao;

import java.util.List;

import com.banzhuan.entity.RelationEntity;
/**
 * 代理商DAO
 * @author guichaoqun
 *
 */
public interface RelationDAO {
	/**
	 * @param id
	 * @return
	 */
	RelationEntity queryRelationByRelation(RelationEntity relation);
	
	List<RelationEntity> queryRelationById(int id);

	public int insertRelationEntity(RelationEntity relation);
	
	void delRelation(RelationEntity relation);

}
