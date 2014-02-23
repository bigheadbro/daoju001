package com.banzhuan.entity;

import java.io.Serializable;

public class CommentEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5477036096071340041L;
	//
	private int id;

	// 0=question的评论，1=answer的评论
	private int type;
	// 评论内容
	private String content;
	private int commentId;
	private int agentId;
	private int buyerId;
	// 上一条评论id
	private int parent;
	// 用户名称
	private String userName;
	
	private int brandName;
	// 用户头像
	private String userAvatar;
	//
	private String link;
	// 记录创建时间
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
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(int type) {
		this.type = type;
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
	 * @return the parent
	 */
	public int getParent() {
		return parent;
	}

	/**
	 * @param parent
	 *            the parent to set
	 */
	public void setParent(int parent) {
		this.parent = parent;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the userAvatar
	 */
	public String getUserAvatar() {
		return userAvatar;
	}

	/**
	 * @param userAvatar
	 *            the userAvatar to set
	 */
	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
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
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * @return the brandName
	 */
	public int getBrandName() {
		return brandName;
	}

	/**
	 * @param brandName the brandName to set
	 */
	public void setBrandName(int brandName) {
		this.brandName = brandName;
	}

	/**
	 * @return the agentId
	 */
	public int getAgentId() {
		return agentId;
	}

	/**
	 * @param agentId the agentId to set
	 */
	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}

	/**
	 * @return the buyerId
	 */
	public int getBuyerId() {
		return buyerId;
	}

	/**
	 * @param buyerId the buyerId to set
	 */
	public void setBuyerId(int buyerId) {
		this.buyerId = buyerId;
	}

	/**
	 * @return the commentId
	 */
	public int getCommentId() {
		return commentId;
	}

	/**
	 * @param commentId the commentId to set
	 */
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	
}