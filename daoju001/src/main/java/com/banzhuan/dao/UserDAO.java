package com.banzhuan.dao;

import java.util.List;

import com.banzhuan.entity.UserEntity;
/**
 * 代理商DAO
 * @author guichaoqun
 *
 */
public interface UserDAO {
	/**
	 * @param id
	 * @return
	 */
	UserEntity queryUserEntityById(int id);
	
	UserEntity queryUserEntityByWxid(String wxid);
	
	UserEntity queryUserEntityByMail(String mail);
	
	UserEntity queryUserEntityByName(String name);
	
	/**
	 * 插入代理商信息
	 * @param companyUser
	 * @return
	 */
	public int insertUserEntity(UserEntity agent);
	

	/**
	 * 更新代理商密码
	 * @param agent
	 * @return
	 */
	public int updateUserPwdById(UserEntity agent);
	
	public int updateUserEntityById(UserEntity agent);
	
	int updateUserReadCountById(int agentid);
	
	List<UserEntity> getUsers();
	
	List<UserEntity> getUsersByAuth(int type);
	
	List<UserEntity> getMainagents();
	
	int getUsersCount(boolean istoday);
	
	List<UserEntity> searchUser(UserEntity user);

	int queryUserEntityOrderByScore(int id);
}
