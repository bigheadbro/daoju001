package com.banzhuan.form;

import java.io.Serializable;

public class ProductForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2837306102423066642L;
	
	private int processMethod;
	private int industry;
	private int wpMaterial;
	private int wpHardness;
	private String name;
	private String description;
	private int pid;
	private int userid;
	private String picture;
	private int isEdit;

	public int getProcessMethod() {
		return processMethod;
	}
	public void setProcessMethod(int processMethod) {
		this.processMethod = processMethod;
	}
	public int getIndustry() {
		return industry;
	}
	public void setIndustry(int industry) {
		this.industry = industry;
	}
	public int getWpMaterial() {
		return wpMaterial;
	}
	public void setWpMaterial(int wpMaterial) {
		this.wpMaterial = wpMaterial;
	}
	public int getWpHardness() {
		return wpHardness;
	}
	public void setWpHardness(int wpHardnes) {
		this.wpHardness = wpHardnes;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

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
	 * @return the isEdit
	 */
	public int getIsEdit() {
		return isEdit;
	}
	/**
	 * @param isEdit the isEdit to set
	 */
	public void setIsEdit(int isEdit) {
		this.isEdit = isEdit;
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
	 * @return the pid
	 */
	public int getPid() {
		return pid;
	}
	/**
	 * @param pid the pid to set
	 */
	public void setPid(int pid) {
		this.pid = pid;
	}
}
