package com.banzhuan.form;

import java.io.Serializable;

public class AddressForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1849937900740869800L;
	
	private String pca;
	
	private String zip;
	
	private String detail;
	
	private String username;
	
	private String phone;
	
	private Boolean isdefault;

	/**
	 * @return the pca
	 */
	public String getPca() {
		return pca;
	}

	/**
	 * @param pca the pca to set
	 */
	public void setPca(String pca) {
		this.pca = pca;
	}

	/**
	 * @return the zip
	 */
	public String getZip() {
		return zip;
	}

	/**
	 * @param zip the zip to set
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * @return the detail
	 */
	public String getDetail() {
		return detail;
	}

	/**
	 * @param detail the detail to set
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the isdefault
	 */
	public Boolean getIsdefault() {
		return isdefault;
	}

	/**
	 * @param isdefault the isdefault to set
	 */
	public void setIsdefault(Boolean isdefault) {
		this.isdefault = isdefault;
	}

}
