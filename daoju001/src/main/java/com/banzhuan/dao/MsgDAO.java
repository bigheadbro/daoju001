package com.banzhuan.dao;

import com.banzhuan.entity.MessageEntity;;

public interface MsgDAO {
	MessageEntity queryBuyerEntityById(int id);
	
	public int insertMessageEntity(MessageEntity buyer);
	
	public int updateMessageById(MessageEntity buyer);
}
