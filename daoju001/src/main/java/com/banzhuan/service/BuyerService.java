package com.banzhuan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import com.banzhuan.dao.BuyerDAO;
import com.banzhuan.entity.BuyerEntity;
import com.banzhuan.common.Account;
import com.banzhuan.common.Result;
import com.banzhuan.form.BuyerProfileForm;
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
			errors.rejectValue("email", "MAIL_IS_EXISTS");
			return result;
		}
		if(StringUtil.isEmpty(form.getPwd())) //  第三步，判断密码不能为空
		{
			errors.rejectValue("pwd", "PASSWORD_IS_NOT_NULL");
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
			errors.rejectValue("mail", "MAIL_IS_NOT_NULL"); // 邮箱不能为空
			return result;
		}
		BuyerEntity buyer = buyerDAO.queryBuyerEntityByMail(form.getMail());
		if(buyer == null)
		{
			errors.rejectValue("mail", "MAIL_IS_NOT_EXISTS"); // 邮箱不存在
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
	
	public void changePwd(BuyerRegForm form, Errors errors)
	{
		if(StringUtil.isEmpty(form.getPwd())) //  第三步，判断密码不能为空
		{
			errors.rejectValue("pwd", "PASSWORD_IS_NOT_NULL");
			return;
		}
		if(StringUtil.isEmpty(form.getPwd1())) //  第三步，判断新密码不能为空
		{
			errors.rejectValue("pwd1", "PASSWORD_IS_NOT_NULL");
			return;
		}
		if(StringUtil.isEmpty(form.getPwd2())) //  第三步，判断重复新密码不能为空
		{
			errors.rejectValue("pwd2", "PASSWORD_IS_NOT_NULL");
			return;
		}
		
		if(!StringUtil.isEqual(form.getPwd1(), form.getPwd2()))
		{
			errors.rejectValue("pwd2", "PASSWORD_IS_NOT_SAME"); // 两次新密码输入不一致
			return;
		}
		
		BuyerEntity buyer = buyerDAO.queryBuyerEntityById(form.getUserid());
		if(StringUtil.isNotEqual(StringUtil.encrypt(form.getPwd()), buyer.getPassword()))
		{
			errors.rejectValue("pwd", "PASSWORD_ERROR"); // 旧密码输入不正确
			return;
		}
		buyer.setPassword(StringUtil.encrypt(form.getPwd1())); // 新密码
		buyerDAO.updateBuyerPwdById(buyer);
	}
	
	public void updateBuyerAccnt(BuyerProfileForm form, BuyerEntity buyer)
	{
		if(form.getUserName() != "")
		{
			buyer.setUsername(form.getUserName());
		}
		if(form.getCompanyName() != "")
		{
			buyer.setCompanyName(form.getCompanyName());
		}
		if(form.getAddress() != "")
		{
			buyer.setCompanyAddress(form.getAddress());
		}
		if(form.getCompanyPhone() != "")
		{
			buyer.setCompanyPhone(form.getCompanyPhone());
		}
		if(form.getContactName() != "")
		{
			buyer.setContactName(form.getContactName());
		}
		if(form.getContactPhone() != "")
		{
			buyer.setContactPhone(form.getContactPhone());
		}
		if(form.getContactQQ() != "")
		{
			buyer.setContactQq(form.getContactQQ());
		}
		buyerDAO.updateBuyerEntityById(buyer);
		return;
	}
	
	public BuyerEntity getBuyerEntity(int userId)
	{
		return buyerDAO.queryBuyerEntityById(userId);
	}
	
	public void setBuyerProfileFormWithBuyerEntity(BuyerProfileForm form, BuyerEntity entity)
	{
		if(entity.getUsername() != "")
		{
			form.setUserName(entity.getUsername());
		}
		if(entity.getCompanyName() != "")
		{
			form.setCompanyName(entity.getCompanyName());
		}
		if(entity.getCompanyAddress() != "")
		{
			form.setAddress(entity.getCompanyAddress());
		}
		if(entity.getCompanyPhone() != "")
		{
			form.setCompanyPhone(entity.getCompanyPhone());
		}
		if(entity.getContactName() != "")
		{
			form.setContactName(entity.getContactName());
		}
		if(entity.getContactPhone() != "")
		{
			form.setContactPhone(entity.getContactPhone());
		}
		if(entity.getContactQq() != "")
		{
			form.setContactQQ(entity.getContactQq());
		}
	}
}
