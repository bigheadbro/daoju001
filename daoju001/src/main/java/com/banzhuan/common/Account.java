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
	private String mail;
	private int brandName;
	private String companyName;
	private boolean isVerified;
	private String verifiedLink;
	private int unreadMsgCount;
	private int questionCnt;
	private int sampleCnt;
	private int gcCnt;
	
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
	

}
