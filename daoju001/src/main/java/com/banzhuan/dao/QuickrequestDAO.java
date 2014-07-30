package com.banzhuan.dao;

import java.util.List;
import com.banzhuan.entity.QuickrequestEntity;
/**
 * 代理商DAO
 * @author guichaoqun
 *
 */
public interface QuickrequestDAO {
	
	List<QuickrequestEntity> queryQuickrequests();
	
	QuickrequestEntity queryQuickrequestById(int id);

	public int insertQuickrequestEntity(QuickrequestEntity Quickrequest);

	public int updateQuickrequestCountById(int id);
	
	void delQuickrequest(int id);

}
