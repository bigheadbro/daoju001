package com.banzhuan.form;

import java.io.Serializable;

public class QuestionForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2837306102423066642L;
	
	private int type;
	private int processMethod;
	private int industry;
	private int wpMaterial;
	private int wpHardness;
	private String content;
	private int userid;

	public int getProcessMethod() {
		return processMethod;
	}
	public void setProcessMethod(int processMethod) {
		this.processMethod = processMethod;
	}
	public int getIndustry() {
		return industry;
	}
	public void setIndustry(int industry) {
		this.industry = industry;
	}
	public int getWpMaterial() {
		return wpMaterial;
	}
	public void setWpMaterial(int wpMaterial) {
		this.wpMaterial = wpMaterial;
	}
	public int getWpHardness() {
		return wpHardness;
	}
	public void setWpHardness(int wpHardnes) {
		this.wpHardness = wpHardnes;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	/**
	 * @return the userid
	 */
	public int getUserid() {
		return userid;
	}
	/**
	 * @param userid the userid to set
	 */
	public void setUserid(int userid) {
		this.userid = userid;
	}
}
