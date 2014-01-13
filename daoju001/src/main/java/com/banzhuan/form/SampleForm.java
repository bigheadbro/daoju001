package com.banzhuan.form;

import java.io.Serializable;

public class SampleForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8524360521615949881L;
	
	private String name;
	private String link;
	private int isEdit;
	private int sid;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public int getIsEdit() {
		return isEdit;
	}
	public void setIsEdit(int isEdit) {
		this.isEdit = isEdit;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
