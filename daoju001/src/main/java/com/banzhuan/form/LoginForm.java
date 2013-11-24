package com.banzhuan.form;

import java.io.Serializable;

public class LoginForm implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2189906744211228687L;
	private String username;
	private String password;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
