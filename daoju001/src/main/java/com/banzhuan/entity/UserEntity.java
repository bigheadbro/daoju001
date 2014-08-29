package com.banzhuan.entity;

import java.io.Serializable;

public class UserEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1111949594820636928L;
	//
	private int id;
	
	private String wxid;
	// QQ connect 昵称
	private String nick;
	// 登录密码
	private String password;
	private String mail;
	// 公司名称
	private String companyName;
	//权限
	private int authority;
	//
	private int brand;
	// 代理品牌名称
	private String brandName;
	//
	private int brand2;
	// 代理品牌名称
	private String brandName2;
	// 品牌logo
	private String logo;
	
	private String wxlogo;
	// 公司地址
	private String address;
	// 公司电话
	private String phone;
	// 公司传真
	private String fax;
	// 公司介绍
	private String description;
	// 是否认证
	private boolean isVerified;
	// 证书链接
	private String verifiedLink;
	// 联系人名称
	private String contactName;
	// 联系人手机
	private String contactPhone;
	// 联系人QQ
	private String contactQq;
	// 联系人邮箱
	private String contactEmail;
	
	private String wxbrand;
	
	private String position;
	
	private int productlimit;
	
	private String pca;
	// 注册时间
	private String regTime;
	// 记录创建时间
	private String gmtCreate;
	// 记录修改时间(最近登录时间)
	private String gmtModified;
	
	private String gmtAuth;
	
	private int cntAnswer;
	private int cntSample;
	private int cntGc;
	private int cntRead;
	

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
	 * @param nick the nick to set
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
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
	 * @return the brandName
	 */
	public String getBrandName() {
		return brandName;
	}

	/**
	 * @param brandName the brandName to set
	 */
	public void setBrandName(String brandName) {
		this.brandName = brandName;
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
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * @param fax the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the verifiedLink
	 */
	public String getVerifiedLink() {
		return verifiedLink;
	}

	/**
	 * @param verifiedLink the verifiedLink to set
	 */
	public void setVerifiedLink(String verifiedLink) {
		this.verifiedLink = verifiedLink;
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
	 * @return the contactEmail
	 */
	public String getContactEmail() {
		return contactEmail;
	}

	/**
	 * @param contactEmail the contactEmail to set
	 */
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	/**
	 * @return the regTime
	 */
	public String getRegTime() {
		return regTime;
	}

	/**
	 * @param regTime the regTime to set
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
	 * @param gmtCreate the gmtCreate to set
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
	 * @param gmtModified the gmtModified to set
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

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * @return the isVerified
	 */
	public boolean isVerified() {
		return isVerified;
	}

	/**
	 * @param isVerified the isVerified to set
	 */
	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

	/**
	 * @return the brand
	 */
	public int getBrand() {
		return brand;
	}

	/**
	 * @param brand the brand to set
	 */
	public void setBrand(int brand) {
		this.brand = brand;
	}

	/**
	 * @return the cntAnswer
	 */
	public int getCntAnswer() {
		return cntAnswer;
	}

	/**
	 * @param cntAnswer the cntAnswer to set
	 */
	public void setCntAnswer(int cntAnswer) {
		this.cntAnswer = cntAnswer;
	}

	/**
	 * @return the cntSample
	 */
	public int getCntSample() {
		return cntSample;
	}

	/**
	 * @param cntSample the cntSample to set
	 */
	public void setCntSample(int cntSample) {
		this.cntSample = cntSample;
	}

	/**
	 * @return the cntGc
	 */
	public int getCntGc() {
		return cntGc;
	}

	/**
	 * @param cntGc the cntGc to set
	 */
	public void setCntGc(int cntGc) {
		this.cntGc = cntGc;
	}

	/**
	 * @return the cntRead
	 */
	public int getCntRead() {
		return cntRead;
	}

	/**
	 * @param cntRead the cntRead to set
	 */
	public void setCntRead(int cntRead) {
		this.cntRead = cntRead;
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

	/**
	 * @return the brand2
	 */
	public int getBrand2() {
		return brand2;
	}

	/**
	 * @param brand2 the brand2 to set
	 */
	public void setBrand2(int brand2) {
		this.brand2 = brand2;
	}

	/**
	 * @return the brandName2
	 */
	public String getBrandName2() {
		return brandName2;
	}

	/**
	 * @param brandName2 the brandName2 to set
	 */
	public void setBrandName2(String brandName2) {
		this.brandName2 = brandName2;
	}

	/**
	 * @return the authority
	 */
	public int getAuthority() {
		return authority;
	}

	/**
	 * @param authority the authority to set
	 */
	public void setAuthority(int authority) {
		this.authority = authority;
	}

	/**
	 * @return the gmtAuth
	 */
	public String getGmtAuth() {
		return gmtAuth;
	}

	/**
	 * @param gmtAuth the gmtAuth to set
	 */
	public void setGmtAuth(String gmtAuth) {
		this.gmtAuth = gmtAuth;
	}

	/**
	 * @return the wxid
	 */
	public String getWxid() {
		return wxid;
	}

	/**
	 * @param wxid the wxid to set
	 */
	public void setWxid(String wxid) {
		this.wxid = wxid;
	}

	/**
	 * @return the wxbrand
	 */
	public String getWxbrand() {
		return wxbrand;
	}

	/**
	 * @param wxbrand the wxbrand to set
	 */
	public void setWxbrand(String wxbrand) {
		this.wxbrand = wxbrand;
	}

	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * @return the wxlogo
	 */
	public String getWxlogo() {
		return wxlogo;
	}

	/**
	 * @param wxlogo the wxlogo to set
	 */
	public void setWxlogo(String wxlogo) {
		this.wxlogo = wxlogo;
	}

	
}