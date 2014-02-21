package com.banzhuan.entity;

import java.io.Serializable;

public class ComplainEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4670375134675999158L;
	//
	private int id;
	
	private int userType;
	private int userId;
	private String content;
	// 记录创建时间
	private String gmtCreate;
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the userType
	 */
	public int getUserType() {
		return userType;
	}
	/**
	 * @param userType the userType to set
	 */
	public void setUserType(int userType) {
		this.userType = userType;
	}
	/**
	 * @return the userid
	 */
	public int getUserid() {
		return userId;
	}
	/**
	 * @param userid the userid to set
	 */
	public void setUserid(int userid) {
		this.userId = userid;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
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
}