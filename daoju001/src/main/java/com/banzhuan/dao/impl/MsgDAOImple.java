package com.banzhuan.dao.impl;

import java.util.List;

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
	public int getUnreadMsgCount(int userid)
	{
		Object obj = this.getSqlSession().selectOne("getUnreadMsgCount", userid);
		if(obj != null)
			return (Integer)obj;
		return 0;
	}
	
	@Override
	public List<MessageEntity> getMsgsByUserid(int userid)
	{
		return this.getSqlSession().selectList("getMsgsByUserid", userid);
	}

}
