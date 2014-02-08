package com.banzhuan.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.banzhuan.entity.MessageEntity;;

public interface MsgDAO {
	MessageEntity queryBuyerEntityById(int id);
	
	public int insertMessageEntity(MessageEntity buyer);
	
	public int updateMessageById(MessageEntity buyer);
	
	public int getUnreadMsgCount(int userid);
	
	int getMsgCount(int userid);
	
	public List<MessageEntity> getMsgsByUserid(int userid);
	
	public List<MessageEntity> getMsgsByUserid(int userid, RowBounds bound);
	
	int getMsgsCountByUserid(int userid);
	
	int setMsgAsRead(int id);
	
}
