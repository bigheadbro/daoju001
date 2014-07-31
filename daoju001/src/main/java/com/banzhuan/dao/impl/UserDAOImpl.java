/**
 * 
 */
package com.banzhuan.dao.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.banzhuan.dao.UserDAO;
import com.banzhuan.entity.UserEntity;

/**
 * @author guichaoqun
 *
 */
@Repository("userDAO")
public class UserDAOImpl extends SqlSessionDaoSupport implements UserDAO {

	@Override
	public UserEntity queryUserEntityById(int id) {
		return this.getSqlSession().selectOne("queryUserEntityById", id);
	}
	
	@Override
	public UserEntity queryUserEntityByName(String name)
	{
		return this.getSqlSession().selectOne("queryUserEntityByName", name);
	}

	@Override
	public UserEntity queryUserEntityByMail(String mail)
	{
		return this.getSqlSession().selectOne("queryUserEntityByMail", mail);
	}
	
	@Override
	public int insertUserEntity(UserEntity agent) {
		this.getSqlSession().insert("insertUserEntity", agent);
		return agent.getId();
	}
	
	@Override
	public int updateUserPwdById(UserEntity agent) {
		return this.getSqlSession().update("updateUserPwdById", agent);
	}
	
	@Override
	public int updateUserEntityById(UserEntity agent) {
		return this.getSqlSession().update("updateUserEntityById", agent);
	}
	
	@Override
	public int updateUserReadCountById(int agentid)
	{
		return this.getSqlSession().update("updateUserReadCountById", agentid);
	}
	
	@Override
	public List<UserEntity> getUsers()
	{
		return this.getSqlSession().selectList("getUsers");
	}
	
	@Override
	public List<UserEntity> getUsersByAuth(int type)
	{
		return this.getSqlSession().selectList("getUsersByAuth", type);
	}
	
	@Override
	public List<UserEntity> getMainagents()
	{
		return this.getSqlSession().selectList("getMainagents");
	}
	
	@Override
	public int getUsersCount(boolean istoday)
	{
		return this.getSqlSession().selectOne("getUsersCount",istoday);
	}

}
