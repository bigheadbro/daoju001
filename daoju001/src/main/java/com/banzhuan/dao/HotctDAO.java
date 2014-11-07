package com.banzhuan.dao;

import com.banzhuan.entity.HotctEntity;
/**
 * 代理商DAO
 * @author guichaoqun
 *
 */
public interface HotctDAO {
	/**
	 * @param id
	 * @return
	 */
	HotctEntity queryHotctById(int id);
	
	public int insertHotctEntity(HotctEntity hotct);

}
