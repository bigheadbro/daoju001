package com.banzhuan.entity;

import java.io.Serializable;

public class StatisticsEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5054274202911059356L;
	//
	private int id;
	//
	private int type;
	//
	private String info;
	//
	private String gmtCreate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(String gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
}