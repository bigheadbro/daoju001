package com.banzhuan.form;

import java.io.Serializable;

public class RegForm implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2353178323817894578L;
	private int userid;
	private String name;
	private String pwd;
	private String pwd1;
	private String pwd2;
	private String email;
	private String logo;
	/**
	 * @return the userid
	 */
	public int getUserid() {
		return userid;
	}
	/**
	 * @param userid the userid to set
	 */
	public void setUserid(int userid) {
		this.userid = userid;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the pwd
	 */
	public String getPwd() {
		return pwd;
	}
	/**
	 * @param pwd the pwd to set
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the pwd1
	 */
	public String getPwd1() {
		return pwd1;
	}
	/**
	 * @param pwd1 the pwd1 to set
	 */
	public void setPwd1(String pwd1) {
		this.pwd1 = pwd1;
	}
	/**
	 * @return the pwd2
	 */
	public String getPwd2() {
		return pwd2;
	}
	/**
	 * @param pwd2 the pwd2 to set
	 */
	public void setPwd2(String pwd2) {
		this.pwd2 = pwd2;
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

}
