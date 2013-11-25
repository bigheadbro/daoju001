package com.banzhuan.entity;

import java.io.Serializable;

public class QuestionEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9159033297247345294L;
	//
	private int id;
	// 问题名称
	private String title;
	// 问题内容
	private String content;
	// 买家id
	private int buyerId;
	// 专业回答数量
	private int cntPA;
	// 普通回答数量
	private int cntCA;
	// 0=已发布，1=草稿
	private int state;
	// 记录创建时间
	private String gmtCreate;
	// 记录修改时间
	private String gmtModify;

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
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the buyerId
	 */
	public int getBuyerId() {
		return buyerId;
	}

	/**
	 * @param buyerId
	 *            the buyerId to set
	 */
	public void setBuyerId(int buyerId) {
		this.buyerId = buyerId;
	}

	/**
	 * @return the cntPA
	 */
	public int getCntPA() {
		return cntPA;
	}

	/**
	 * @param cntPA
	 *            the cntPA to set
	 */
	public void setCntPA(int cntPA) {
		this.cntPA = cntPA;
	}

	/**
	 * @return the cntCA
	 */
	public int getCntCA() {
		return cntCA;
	}

	/**
	 * @param cntCA
	 *            the cntCA to set
	 */
	public void setCntCA(int cntCA) {
		this.cntCA = cntCA;
	}

	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(int state) {
		this.state = state;
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

	
}