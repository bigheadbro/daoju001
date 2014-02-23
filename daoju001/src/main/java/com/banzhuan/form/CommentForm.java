package com.banzhuan.form;

import java.io.Serializable;

public class CommentForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2041329388239332401L;
	
	private int type;
	private int replyOther;
	private String content;
	private int commentId;
	private int agentId;
	private int buyerId;
	private int parentId;
	private String userName;
	private String userLogo;
	private int brandName;
	private String verifiedLink;
	
	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}
	/**
	 * @param type the type to set
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
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
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
	 * @return the parentId
	 */
	public int getParentId() {
		return parentId;
	}
	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the userLogo
	 */
	public String getUserLogo() {
		return userLogo;
	}
	/**
	 * @param userLogo the userLogo to set
	 */
	public void setUserLogo(String userLogo) {
		this.userLogo = userLogo;
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
	 * @return the verifiedLink
	 */
	public String getVerifiedLink() {
		return verifiedLink;
	}
	/**
	 * @param verifiedLink the verifiedLink to set
	 */
	public void setVerifiedLink(String verifiedLink) {
		this.verifiedLink = verifiedLink;
	}
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/**
	 * @return the replyOther
	 */
	public int getReplyOther() {
		return replyOther;
	}
	/**
	 * @param replyOther the replyOther to set
	 */
	public void setReplyOther(int replyOther) {
		this.replyOther = replyOther;
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
