package com.banzhuan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import com.banzhuan.dao.BuyerDAO;
import com.banzhuan.entity.BuyerEntity;
import com.banzhuan.common.Result;
import com.banzhuan.form.BuyerRegForm;
import com.banzhuan.form.LoginForm;
import com.banzhuan.entity.BuyerEntity;
import com.banzhuan.util.StringUtil;

/**
 * @author guichaoqun
 *
 */
@Service("buyerService")
public class BuyerService {
	@Autowired
	@Qualifier("buyerDAO")
	private BuyerDAO buyerDAO;

	public Result register(BuyerRegForm form, Errors errors)
	{
		Result result = new Result();
		
		if(StringUtil.isEmpty(form.getName()))// 第一步， 判断注册名是否为空
		{
			errors.rejectValue("name", "USER_NAME_IS_NOT_NULL");
			return result;
		}
		else if(buyerDAO.queryBuyerEntityByMail(form.getEmail()) != null) // 第二步，判断注册用户名是否存在
		{
			errors.rejectValue("name", "USER_NAME_IS_EXISTS");
			return result;
		}
		if(StringUtil.isEmpty(form.getPwd())) //  第三步，判断密码不能为空
		{
			errors.rejectValue("pwd", "PASSWORD_IS_NOT_NULL");
			return result;
		}
		if(StringUtil.isEmpty(form.getEmail()))//  第五步，判断邮箱是否输入
		{
			errors.rejectValue("email", "EMAIL_IS_NOT_NULL");
			return result;
		}
		
		BuyerEntity buyer = new BuyerEntity();
		buyer.setUsername(form.getName());
		buyer.setPassword(StringUtil.encrypt(form.getPwd())); // 对密码加密
		buyer.setEmail(form.getEmail()); // 设置邮箱地址
		buyerDAO.insertBuyerEntity(buyer);
		result.add("buyer", buyer);
		return result;
	}
	
	/**
	 * 用户是否登录
	 * @param name
	 * @param password
	 * @return
	 */
	public Result checkLogin(LoginForm form, Errors errors)
	{
		Result result = new Result();
		if(StringUtil.isEmpty(form.getMail()))
		{
			errors.rejectValue("mail", "USER_NAME_IS_NOT_NULL"); // 用户名不能为空
			return result;
		}
		BuyerEntity buyer = buyerDAO.queryBuyerEntityByMail(form.getMail());
		if(buyer == null)
		{
			errors.rejectValue("mail", "USER_NAME_IS_NOT_EXISTS"); // 用户不存在
			return result;
		}
		if(StringUtil.isNotEqual(buyer.getPassword(), StringUtil.encrypt(form.getPassword())))
		{
			errors.rejectValue("password", "PASSWORD_ERROR"); // 密码错误
			return result;
		}
		result.add("buyer", buyer);
		return result;
	}
}
