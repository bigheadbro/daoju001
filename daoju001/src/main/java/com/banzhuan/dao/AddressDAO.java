package com.banzhuan.dao;

import java.util.List;

import com.banzhuan.entity.AddressEntity;
/**
 * 代理商DAO
 * @author guichaoqun
 *
 */
public interface AddressDAO {
	/**
	 * @param id
	 * @return
	 */
	AddressEntity queryAddressById(int id);
	
	List<AddressEntity> queryAddressByUserid(int uid);

	public int insertAgentEntity(AddressEntity address);

	public int updateAddressById(AddressEntity addr);


}
