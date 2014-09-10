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
	
	List<RelationEntity> queryRelationByWxid(String wxid);
	
	List<RelationEntity> queryRelationByWxid2(String wxid2);

	public int insertRelationEntity(RelationEntity relation);
	
	void delRelation(RelationEntity relation);
	
	int getRelationCount(int userid);

}
