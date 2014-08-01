package com.banzhuan.entity;

import java.io.Serializable;

public class ProductEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9159033297243345294L;
	//
	private int id;
	private String name;
	private String description;
	private int userId;
	
	private int brandId;
	
	// 记录创建时间
	private String gmtCreate;
	// 记录修改时间
	private String gmtModify;
	// 用户字
	private String userName;
	// 用户logo
	private String userLogo;
	private int industry;
	private int processMethod;
	private int wpHardness;
	private int wpMaterial;
	private String picture;
	private String cover;
	private int state;
	private int count;

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
	 * @return the buyerId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param buyerId
	 *            the buyerId to set
	 */
	public void setUserId(int buyerId) {
		this.userId = buyerId;
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
	 * @return the gmtModify
	 */
	public String getGmtModify() {
		return gmtModify;
	}

	/**
	 * @param gmtModify
	 *            the gmtModify to set
	 */
	public void setGmtModify(String gmtModify) {
		this.gmtModify = gmtModify;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the industry
	 */
	public int getIndustry() {
		return industry;
	}

	/**
	 * @param industry the industry to set
	 */
	public void setIndustry(int industry) {
		this.industry = industry;
	}

	/**
	 * @return the processMethod
	 */
	public int getProcessMethod() {
		return processMethod;
	}

	/**
	 * @param processMethod the processMethod to set
	 */
	public void setProcessMethod(int processMethod) {
		this.processMethod = processMethod;
	}

	/**
	 * @return the wpHardness
	 */
	public int getWpHardness() {
		return wpHardness;
	}

	/**
	 * @param wpHardness the wpHardness to set
	 */
	public void setWpHardness(int wpHardness) {
		this.wpHardness = wpHardness;
	}

	/**
	 * @return the wpMaterial
	 */
	public int getWpMaterial() {
		return wpMaterial;
	}

	/**
	 * @param wpMaterial the wpMaterial to set
	 */
	public void setWpMaterial(int wpMaterial) {
		this.wpMaterial = wpMaterial;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the userLogo
	 */
	public String getUserLogo() {
		return userLogo;
	}

	/**
	 * @param userLogo the userLogo to set
	 */
	public void setUserLogo(String userLogo) {
		this.userLogo = userLogo;
	}

	/**
	 * @return the brandId
	 */
	public int getBrandId() {
		return brandId;
	}

	/**
	 * @param brandId the brandId to set
	 */
	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}

	/**
	 * @return the picture
	 */
	public String getPicture() {
		return picture;
	}

	/**
	 * @param picture the picture to set
	 */
	public void setPicture(String picture) {
		this.picture = picture;
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
	 * @return the cover
	 */
	public String getCover() {
		return cover;
	}

	/**
	 * @param cover the cover to set
	 */
	public void setCover(String cover) {
		this.cover = cover;
	}

	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(int state) {
		this.state = state;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
}