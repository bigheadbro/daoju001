package com.banzhuan.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.banzhuan.entity.MessageEntity;;

public interface MsgDAO {
	MessageEntity queryBuyerEntityById(int id);
	
	public int insertMessageEntity(MessageEntity buyer);
	
	public int updateMessageById(MessageEntity msg);
	
	public int getUnreadMsgCount(MessageEntity msg);
	
	int getMsgCount(MessageEntity msg);
	
	public List<MessageEntity> getMsgsByUserid(MessageEntity msg);
	
	public List<MessageEntity> getMsgsByUserid(MessageEntity msg, RowBounds bound);
	
	int getMsgsCountByUserid(MessageEntity msg);
	
	int setMsgAsRead(int id);
	
	List<MessageEntity> getAllmsgs();
	
}
