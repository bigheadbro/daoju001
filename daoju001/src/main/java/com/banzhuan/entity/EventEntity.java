package com.banzhuan.entity;

import java.io.Serializable;

public class EventEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6626386017134491401L;
	//
	private int id;

	private int eventid;
	
	private String company;
	
	private String name;

	private String address;
	
	private String phone;

	private int material;

	private int type;
	
	private int count;

	private String note;
	
	private String gmtCreate;

	
		
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
	 * @return the content
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setCompany(String content) {
		this.company = content;
	}

	/**
	 * @return the questionId
	 */
	public int getMaterial() {
		return material;
	}

	/**
	 * @param questionId
	 *            the questionId to set
	 */
	public void setMaterial(int questionId) {
		this.material = questionId;
	}

	/**
	 * @return the buyerId
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param buyerId
	 *            the buyerId to set
	 */
	public void setType(int buyerId) {
		this.type = buyerId;
	}

	/**
	 * @return the buyerName
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param buyerName
	 *            the buyerName to set
	 */
	public void setName(String buyerName) {
		this.name = buyerName;
	}

	/**
	 * @return the buyerAvatar
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param buyerAvatar
	 *            the buyerAvatar to set
	 */
	public void setAddress(String buyerAvatar) {
		this.address = buyerAvatar;
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
	public String getPhone() {
		return phone;
	}

	/**
	 * @param gmtModify
	 *            the gmtModify to set
	 */
	public void setPhone(String gmtModify) {
		this.phone = gmtModify;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * @return the eventid
	 */
	public int getEventid() {
		return eventid;
	}

	/**
	 * @param eventid the eventid to set
	 */
	public void setEventid(int eventid) {
		this.eventid = eventid;
	}

	
}