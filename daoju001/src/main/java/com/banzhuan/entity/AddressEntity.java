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
	private int defaulte;
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
	public int getDefaulte() {
		return defaulte;
	}
	public void setDefaulte(int defaulte) {
		this.defaulte = defaulte;
	}
}