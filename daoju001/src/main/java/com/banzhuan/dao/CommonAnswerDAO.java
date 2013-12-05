package com.banzhuan.dao;

import com.banzhuan.entity.CommonAnswerEntity;;

public interface CommonAnswerDAO {
	CommonAnswerEntity queryBuyerEntityById(int id);
	
	public int insertCommonAnswerEntity(CommonAnswerEntity buyer);
	
	public int updateCommonAnswerById(CommonAnswerEntity buyer);
}
