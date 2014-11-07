package com.banzhuan.dao;

import com.banzhuan.entity.NormalctEntity;
/**
 * 代理商DAO
 * @author guichaoqun
 *
 */
public interface NormalctDAO {
	/**
	 * @param id
	 * @return
	 */
	NormalctEntity queryNormalctById(int id);
	
	public int insertNormalctEntity(NormalctEntity normalct);

}
