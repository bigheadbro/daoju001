package com.banzhuan.entity;

import java.io.Serializable;
import java.util.List;

public class ProfessionalAnswerEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6679115751261653584L;
	//
	private int id;
	// 回答内容
	private String content;
	// 报价
	private String price;
	// 0=已发布，1=草稿
	private int state;
	
	private boolean hasPic;
	// 买家id
	private int buyerId;
	// 问题id
	private int questionId;
	
	private int cntComment;
	// 代理商id
	private int agentId;
	// 代理商名字
	private String agentName;
	private String brandName;
	// 代理商logo
	private String agentLogo;
	
	private String verifiedLink;
	
	private boolean FreeUse;
	
	private List<CommentEntity> comments;
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
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
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
	 * @return the questionId
	 */
	public int getQuestionId() {
		return questionId;
	}

	/**
	 * @param questionId
	 *            the questionId to set
	 */
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	/**
	 * @return the agentId
	 */
	public int getAgentId() {
		return agentId;
	}

	/**
	 * @param agentId
	 *            the agentId to set
	 */
	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}

	/**
	 * @return the agentName
	 */
	public String getAgentName() {
		return agentName;
	}

	/**
	 * @param agentName
	 *            the agentName to set
	 */
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	/**
	 * @return the agentLogo
	 */
	public String getAgentLogo() {
		return agentLogo;
	}

	/**
	 * @param agentLogo
	 *            the agentLogo to set
	 */
	public void setAgentLogo(String agentLogo) {
		this.agentLogo = agentLogo;
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
	 * @return the isFreeUse
	 */
	public boolean isFreeUse() {
		return FreeUse;
	}

	/**
	 * @param isFreeUse the isFreeUse to set
	 */
	public void setFreeUse(boolean isFreeUse) {
		this.FreeUse = isFreeUse;
	}

	/**
	 * @return the hasPic
	 */
	public boolean isHasPic() {
		return hasPic;
	}

	/**
	 * @param hasPic the hasPic to set
	 */
	public void setHasPic(boolean hasPic) {
		this.hasPic = hasPic;
	}

	/**
	 * @return the brandName
	 */
	public String getBrandName() {
		return brandName;
	}

	/**
	 * @param brandName the brandName to set
	 */
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	/**
	 * @return the cntComment
	 */
	public int getCntComment() {
		return cntComment;
	}

	/**
	 * @param cntComment the cntComment to set
	 */
	public void setCntComment(int cntComment) {
		this.cntComment = cntComment;
	}

	/**
	 * @return the comments
	 */
	public List<CommentEntity> getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(List<CommentEntity> comments) {
		this.comments = comments;
	}


	
}