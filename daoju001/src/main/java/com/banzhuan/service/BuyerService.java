package com.banzhuan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import com.banzhuan.dao.BuyerDAO;
import com.banzhuan.entity.BuyerEntity;
import com.banzhuan.common.Result;
import com.banzhuan.form.LoginForm;

/**
 * @author guichaoqun
 *
 */
@Service("buyerService")
public class BuyerService {
	@Autowired
	@Qualifier("buyerDAO")
	private BuyerDAO buyerDAO;

	public Result test(LoginForm form, Errors errors)
	{
		Result result = new Result();
		BuyerEntity buyer = buyerDAO.queryBuyerEntityById(1);
		result.add("buyer", buyer);
		return result;
	}
}
