package com.banzhuan.entity;

import java.io.Serializable;

public class GoodcaseEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6818089788212285523L;
	//
	private int id;
	// 案例名称
	private String name;
	// 工件名称
	private String workName;
	// 行业
	private int industry;
	// 工件材质
	private int workMaterial;
	// 工件硬度
	private int workSolidity;
	// 加工方式
	private int processMethod;
	// 代理商id
	private int agentId;
	// 代理商名字
	private String agentName;
	// 代理商logo
	private String agentLogo;
	// 代理品牌
	private String brandName;
	// 是否认证
	private boolean isVerified;
	//
	private String verifiedLink;
	// 下载链接
	private String link;
	// 下载次数
	private String count;
	// 记录创建时间
	private String gmtCreate;
	// 记录修改时间(最近时间)
	private String gmtModified;

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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the industry
	 */
	public int getIndustry() {
		return industry;
	}

	/**
	 * @param industry
	 *            the industry to set
	 */
	public void setIndustry(int industry) {
		this.industry = industry;
	}

	/**
	 * @return the workMaterial
	 */
	public int getWorkMaterial() {
		return workMaterial;
	}

	/**
	 * @param workMaterial
	 *            the workMaterial to set
	 */
	public void setWorkMaterial(int workMaterial) {
		this.workMaterial = workMaterial;
	}

	/**
	 * @return the workSolidity
	 */
	public int getWorkSolidity() {
		return workSolidity;
	}

	/**
	 * @param workSolidity
	 *            the workSolidity to set
	 */
	public void setWorkSolidity(int workSolidity) {
		this.workSolidity = workSolidity;
	}

	/**
	 * @return the workType
	 */
	public int getProcessMethod() {
		return processMethod;
	}

	/**
	 * @param workType
	 *            the workType to set
	 */
	public void setProcessMethod(int workType) {
		this.processMethod = workType;
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
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @param link
	 *            the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * @return the count
	 */
	public String getCount() {
		return count;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(String count) {
		this.count = count;
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
	 * @return the gmtModified
	 */
	public String getGmtModified() {
		return gmtModified;
	}

	/**
	 * @param gmtModified
	 *            the gmtModified to set
	 */
	public void setGmtModified(String gmtModified) {
		this.gmtModified = gmtModified;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the workName
	 */
	public String getWorkName() {
		return workName;
	}

	/**
	 * @param workName the workName to set
	 */
	public void setWorkName(String workName) {
		this.workName = workName;
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
	 * @return the isVerified
	 */
	public boolean isVerified() {
		return isVerified;
	}

	/**
	 * @param isVerified the isVerified to set
	 */
	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

	public String getVerifiedLink() {
		return verifiedLink;
	}

	public void setVerifiedLink(String verifiedLink) {
		this.verifiedLink = verifiedLink;
	}

	
}