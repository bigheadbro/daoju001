package com.banzhuan.form;

import java.io.Serializable;

public class LoginForm implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2189906744211228687L;
	private String mail;
	private String password;
	private Boolean rememberme;
	private int Id;
	/**
	 * @return the id
	 */
	public int getId() {
		return Id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		Id = id;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the rememberme
	 */
	public Boolean getRememberme() {
		return rememberme;
	}
	/**
	 * @param rememberme the rememberme to set
	 */
	public void setRememberme(Boolean rememberme) {
		this.rememberme = rememberme;
	}
}
