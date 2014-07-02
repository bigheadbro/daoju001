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
	
	List<AddressEntity> queryAddressByUserid(int uid, int type);

	public int insertAddressEntity(AddressEntity address);

	public int updateAddressById(AddressEntity addr);
	
	public int updateOtherAddressById(AddressEntity addr);


}
