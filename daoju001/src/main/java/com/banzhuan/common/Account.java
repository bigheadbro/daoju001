package com.banzhuan.common;

import java.io.Serializable;

public class Account implements Serializable{

	private boolean isLogin;
	private String userName;
	private String password;
	private String qqConnectId;
	private String accessToken;
	private int userId;
	private int positionId;
	private boolean isBuyer;
	private boolean isAgent;
	private String logo;
	private String wxlogo;
	private String mail;
	private int brandName;
	private int brandName2;
	private String companyName;
	private int authority;
	private String verifiedLink;
	private int unreadMsgCount;
	private int questionCnt;
	private int anwserCnt;
	private int sampleCnt;
	private int gcCnt;
	private int productlimit;
	private String qq;
	private String area;
	private String phone;
	private String mobile;
	private String wxbrand;
	private String position;
	private String wxid;
	
	public boolean isAgent() {
		return isAgent;
	}
	public void setAgent(boolean isAgent) {
		this.isAgent = isAgent;
	}
	public boolean isBuyer() {
		return isBuyer;
	}
	public void setBuyer(boolean isBuyer) {
		this.isBuyer = isBuyer;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public int getPositionId() {
		return positionId;
	}
	public void setPositionId(int positionId) {
		this.positionId = positionId;
	}
	public boolean isLogin() {
		return isLogin;
	}
	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getQqConnectId() {
		return qqConnectId;
	}
	public void setQqConnectId(String qqConnectId) {
		this.qqConnectId = qqConnectId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	/**
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}
	/**
	 * @param mail the mail to set
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}
	/**
	 * @return the brandName
	 */
	public int getBrandName() {
		return brandName;
	}
	/**
	 * @param brandName the brandName to set
	 */
	public void setBrandName(int brandName) {
		this.brandName = brandName;
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
	 * @return the unreadMsgCount
	 */
	public int getUnreadMsgCount() {
		return unreadMsgCount;
	}
	/**
	 * @param unreadMsgCount the unreadMsgCount to set
	 */
	public void setUnreadMsgCount(int unreadMsgCount) {
		this.unreadMsgCount = unreadMsgCount;
	}
	public int getQuestionCnt() {
		return questionCnt;
	}
	public void setQuestionCnt(int questionCnt) {
		this.questionCnt = questionCnt;
	}
	public int getSampleCnt() {
		return sampleCnt;
	}
	public void setSampleCnt(int sampleCnt) {
		this.sampleCnt = sampleCnt;
	}
	public int getGcCnt() {
		return gcCnt;
	}
	public void setGcCnt(int gcCnt) {
		this.gcCnt = gcCnt;
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
	 * @return the anwserCnt
	 */
	public int getAnwserCnt() {
		return anwserCnt;
	}
	/**
	 * @param anwserCnt the anwserCnt to set
	 */
	public void setAnwserCnt(int anwserCnt) {
		this.anwserCnt = anwserCnt;
	}
	/**
	 * @return the brandName2
	 */
	public int getBrandName2() {
		return brandName2;
	}
	/**
	 * @param brandName2 the brandName2 to set
	 */
	public void setBrandName2(int brandName2) {
		this.brandName2 = brandName2;
	}
	/**
	 * @return the qq
	 */
	public String getQq() {
		return qq;
	}
	/**
	 * @param qq the qq to set
	 */
	public void setQq(String qq) {
		this.qq = qq;
	}
	/**
	 * @return the area
	 */
	public String getArea() {
		return area;
	}
	/**
	 * @param area the area to set
	 */
	public void setArea(String area) {
		this.area = area;
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
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
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
