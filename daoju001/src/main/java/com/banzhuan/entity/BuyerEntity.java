package com.banzhuan.entity;

import java.io.Serializable;

public class BuyerEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -344076978051360475L;
	//
	private int id;
	// QQ connect 昵称
	private String nick;
	// QQ connect 唯一ID
	private String qqConnectId;
	// 用户名字
	private String username;
	// 登录密码
	private String password;
	// 电子邮箱
	private String email;
	// logo
	private String logo;

	private String companyName;
	private String companyAddress;
	private String companyPhone;
	private String contactName;
	private String contactPhone;
	private String contactQq;
	
	private int productlimit;
	
	private String pca;
	// 注册时间
	private String regTime;
	// 记录创建时间
	private String gmtCreate;
	// 记录修改时间(最近登录时间)
	private String gmtModified;
		
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the nick
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * @param nick
	 *            the nick to set
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * @return the qqConnectId
	 */
	public String getQqConnectId() {
		return qqConnectId;
	}

	/**
	 * @param qqConnectId
	 *            the qqConnectId to set
	 */
	public void setQqConnectId(String qqConnectId) {
		this.qqConnectId = qqConnectId;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the logo
	 */
	public String getLogo() {
		return logo;
	}

	/**
	 * @param logo the logo to set
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}

	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * @return the companyAddress
	 */
	public String getCompanyAddress() {
		return companyAddress;
	}

	/**
	 * @param companyAddress the companyAddress to set
	 */
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	/**
	 * @return the companyPhone
	 */
	public String getCompanyPhone() {
		return companyPhone;
	}

	/**
	 * @param companyPhone the companyPhone to set
	 */
	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}

	/**
	 * @return the contactName
	 */
	public String getContactName() {
		return contactName;
	}

	/**
	 * @param contactName the contactName to set
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	/**
	 * @return the contactPhone
	 */
	public String getContactPhone() {
		return contactPhone;
	}

	/**
	 * @param contactPhone the contactPhone to set
	 */
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	/**
	 * @return the contactQq
	 */
	public String getContactQq() {
		return contactQq;
	}

	/**
	 * @param contactQq the contactQq to set
	 */
	public void setContactQq(String contactQq) {
		this.contactQq = contactQq;
	}
	
	/**
	 * @return the regTime
	 */
	public String getRegTime() {
		return regTime;
	}

	/**
	 * @param regTime
	 *            the regTime to set
	 */
	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}

	/**
	 * @return the gmtCreate
	 */
	public String getGmtCreate() {
		return gmtCreate;
	}

	/**
	 * @param gmtCreate
	 *            the gmtCreate to set
	 */
	public void setGmtCreate(String gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	/**
	 * @return the gmtModified
	 */
	public String getGmtModified() {
		return gmtModified;
	}

	/**
	 * @param gmtModified
	 *            the gmtModified to set
	 */
	public void setGmtModified(String gmtModified) {
		this.gmtModified = gmtModified;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the productlimit
	 */
	public int getProductlimit() {
		return productlimit;
	}

	/**
	 * @param productlimit the productlimit to set
	 */
	public void setProductlimit(int productlimit) {
		this.productlimit = productlimit;
	}

	/**
	 * @return the pca
	 */
	public String getPca() {
		return pca;
	}

	/**
	 * @param pca the pca to set
	 */
	public void setPca(String pca) {
		this.pca = pca;
	}

	
}