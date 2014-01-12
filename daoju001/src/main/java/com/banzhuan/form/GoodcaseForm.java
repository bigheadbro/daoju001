package com.banzhuan.form;

import java.io.Serializable;

public class GoodcaseForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4088000822520578139L;
	
	private int processMethod;
	private int industry;
	private int wpMaterial;
	private int wpHardness;
	private String name;
	private String link;
	private int isEdit;
	private int gcid;
	
	/**
	 * @return the processMethod
	 */
	public int getProcessMethod() {
		return processMethod;
	}
	/**
	 * @param processMethod the processMethod to set
	 */
	public void setProcessMethod(int processMethod) {
		this.processMethod = processMethod;
	}
	/**
	 * @return the industry
	 */
	public int getIndustry() {
		return industry;
	}
	/**
	 * @param industry the industry to set
	 */
	public void setIndustry(int industry) {
		this.industry = industry;
	}
	/**
	 * @return the wpMaterial
	 */
	public int getWpMaterial() {
		return wpMaterial;
	}
	/**
	 * @param wpMaterial the wpMaterial to set
	 */
	public void setWpMaterial(int wpMaterial) {
		this.wpMaterial = wpMaterial;
	}
	/**
	 * @return the wpHardness
	 */
	public int getWpHardness() {
		return wpHardness;
	}
	/**
	 * @param wpHardness the wpHardness to set
	 */
	public void setWpHardness(int wpHardness) {
		this.wpHardness = wpHardness;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}
	/**
	 * @param link the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/**
	 * @return the isEdit
	 */
	public int getIsEdit() {
		return isEdit;
	}
	/**
	 * @param isEdit the isEdit to set
	 */
	public void setEdit(int isEdit) {
		this.isEdit = isEdit;
	}
	/**
	 * @return the gcid
	 */
	public int getGcid() {
		return gcid;
	}
	/**
	 * @param gcid the gcid to set
	 */
	public void setGcid(int gcid) {
		this.gcid = gcid;
	}

}
