package com.banzhuan.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.banzhuan.entity.QuickrequestEntity;
/**
 * 代理商DAO
 * @author guichaoqun
 *
 */
public interface QuickrequestDAO {
	
	List<QuickrequestEntity> queryQuickrequests(int type, RowBounds bound);
	
	QuickrequestEntity queryQuickrequestById(int id);
	
	List<QuickrequestEntity> queryMainQuickrequests();

	int getAllRequestsCount(int type);
	
	public int insertQuickrequestEntity(QuickrequestEntity Quickrequest);

	public int updateQuickrequestCountById(int id);
	
	void delQuickrequest(int id);
	
	List<QuickrequestEntity> queryQuickrequestsForwx();

}
