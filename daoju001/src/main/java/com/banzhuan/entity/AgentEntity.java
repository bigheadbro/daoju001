package com.banzhuan.entity;

import java.io.Serializable;

/**
 * @author guichaoqun
 * 
 */
public class AgentEntity implements Serializable {

	private int id;

	private String gmtCreated;

	private String gmtModified;

	private String name;

	private String password;

	private String companyName;

	private String cellphone;

	private String landline;

	private String fax;

	private String mail;

	private String qq;

	private String area;

	private String address;

	// 行业分类：0：机械行业。。。。。
	private int industry;
	
	//代理品牌
	private int brand;
	
	private int answerCount;

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getLandline() {
		return landline;
	}

	public void setLandline(String landline) {
		this.landline = landline;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getIndustry() {
		return industry;
	}

	public void setIndustry(int industry) {
		this.industry = industry;
	}

	public int getAnswerCount() {
		return answerCount;
	}

	public void setAnswerCount(int answerCount) {
		this.answerCount = answerCount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(String gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public String getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(String gmtModified) {
		this.gmtModified = gmtModified;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}