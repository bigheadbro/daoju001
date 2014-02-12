package com.banzhuan.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.banzhuan.dao.MsgDAO;
import com.banzhuan.entity.GoodcaseEntity;
import com.banzhuan.entity.MessageEntity;

@Repository("msgDAO")
public class MsgDAOImple extends SqlSessionDaoSupport implements MsgDAO {

	@Override
	public MessageEntity queryBuyerEntityById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insertMessageEntity(MessageEntity buyer) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateMessageById(MessageEntity buyer) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int getUnreadMsgCount(MessageEntity msg)
	{
		Object obj = this.getSqlSession().selectOne("getUnreadMsgCount", msg);
		if(obj != null)
			return (Integer)obj;
		return 0;
	}
	
	@Override
	public int getMsgCount(MessageEntity msg)
	{
		Object obj = this.getSqlSession().selectOne("getMsgCount", msg);
		if(obj != null)
			return (Integer)obj;
		return 0;
	}
	
	@Override
	public List<MessageEntity> getMsgsByUserid(MessageEntity msg)
	{
		return this.getSqlSession().selectList("getMsgsByUserid", msg);
	}
	
	@Override
	public List<MessageEntity> getMsgsByUserid(MessageEntity msg, RowBounds bound)
	{
		return this.getSqlSession().selectList("getMsgsByUserid", msg, bound);
	}
	
	@Override
	public int getMsgsCountByUserid(MessageEntity msg)
	{
		return this.getSqlSession().selectOne("getMsgsCountByUserid", msg);
	}
	
	@Override
	public int setMsgAsRead(int id)
	{
		return this.getSqlSession().update("setMsgAsRead", id);
	}
	

}
