package com.banzhuan.dao;

import com.banzhuan.entity.BuyerEntity;;

public interface BuyerDAO {
	/**
	 * @param id
	 * @return
	 */
	BuyerEntity queryBuyerEntityById(int id);
	
	/**
	 * @param name
	 * @return
	 */
	BuyerEntity queryBuyerEntityByMail(String mail);
	
	/**
	 * 插入买家信息
	 * @param companyUser
	 * @return
	 */
	public int insertBuyerEntity(BuyerEntity buyer);
	

	/**
	 * 更新买家密码
	 * @param agent
	 * @return
	 */
	public int updateBuyerPwdById(BuyerEntity buyer);
	
	public int updateBuyerEntityById(BuyerEntity buyer);
}
