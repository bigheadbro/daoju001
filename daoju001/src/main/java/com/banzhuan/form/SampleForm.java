package com.banzhuan.form;

import java.io.Serializable;

public class SampleForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8524360521615949881L;
	
	private String name;
	private String link;
	private String type;
	private int size;
	private int isEdit;
	private int sid;
	private int brandid;
	
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
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}
	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}
	/**
	 * @return the brandid
	 */
	public int getBrandid() {
		return brandid;
	}
	/**
	 * @param brandid the brandid to set
	 */
	public void setBrandid(int brandid) {
		this.brandid = brandid;
	}
	

}
