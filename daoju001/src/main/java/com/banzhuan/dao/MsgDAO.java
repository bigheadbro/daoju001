package com.banzhuan.dao;

import java.util.List;

import com.banzhuan.entity.MessageEntity;;

public interface MsgDAO {
	MessageEntity queryBuyerEntityById(int id);
	
	public int insertMessageEntity(MessageEntity buyer);
	
	public int updateMessageById(MessageEntity buyer);
	
	public int getUnreadMsgCount(int userid);
	
	public List<MessageEntity> getMsgsByUserid(int userid);
}
