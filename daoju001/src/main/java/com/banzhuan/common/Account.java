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
	

}
