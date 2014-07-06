package com.banzhuan.entity;

import java.io.Serializable;

public class AddressEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3002565488837051600L;
	//
	private int id;
	//
	private int uid;
	//
	private String pca;
	//
	private String province;
	//
	private String city;
	//
	private String area;
	//
	private String addr;
	//
	private String zip;
	//
	private String name;
	//
	private String phone;
	//
	private Boolean defaulte;
	// 记录创建时间
	private String gmtCreate;
	// 记录修改时间(最近登录时间)
	private String gmtModify;
		
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Boolean getDefaulte() {
		return defaulte;
	}
	public void setDefaulte(Boolean defaulte) {
		this.defaulte = defaulte;
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
	 * @return the gmtModify
	 */
	public String getGmtModify() {
		return gmtModify;
	}
	/**
	 * @param gmtModify the gmtModify to set
	 */
	public void setGmtModify(String gmtModify) {
		this.gmtModify = gmtModify;
	}
}