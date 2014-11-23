package com.banzhuan.entity;

import java.io.Serializable;

public class CategoryEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2159224656610484591L;
	private String code;
	private String name;
	private String pic;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
}