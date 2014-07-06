package com.banzhuan.form;

import java.io.Serializable;

import com.banzhuan.util.StringUtil;

public class UserProfileForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8707564076021726273L;
	
	private String email;
	private String nick;
	private String companyName;
	private int brand;
	private String brandName;
	private int brand2;
	private String brandName2;
	private String address;
	private String companyPhone;
	private String contactName;
	private String contactPhone;
	private String contactQQ;
	private String description;
	private String pca;
	
	/**
	 * @return the userName
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		if(StringUtil.isNotEmpty(companyName))
		{
			return companyName.trim();
		}
		return companyName;
		
	}
	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the companyPhone
	 */
	public String getCompanyPhone() {
		return companyPhone;
	}
	/**
	 * @param companyPhone the companyPhone to set
	 */
	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}
	/**
	 * @return the contactName
	 */
	public String getContactName() {
		return contactName;
	}
	/**
	 * @param contactName the contactName to set
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	/**
	 * @return the contactPhone
	 */
	public String getContactPhone() {
		return contactPhone;
	}
	/**
	 * @param contactPhone the contactPhone to set
	 */
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	/**
	 * @return the contactQQ
	 */
	public String getContactQQ() {
		return contactQQ;
	}
	/**
	 * @param contactQQ the contactQQ to set
	 */
	public void setContactQQ(String contactQQ) {
		this.contactQQ = contactQQ;
	}
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the brand
	 */
	public int getBrand() {
		return brand;
	}
	/**
	 * @param brand the brand to set
	 */
	public void setBrand(int brand) {
		this.brand = brand;
	}
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
	 * @return the brand2
	 */
	public int getBrand2() {
		return brand2;
	}
	/**
	 * @param brand2 the brand2 to set
	 */
	public void setBrand2(int brand2) {
		this.brand2 = brand2;
	}
	/**
	 * @return the brandName2
	 */
	public String getBrandName2() {
		return brandName2;
	}
	/**
	 * @param brandName2 the brandName2 to set
	 */
	public void setBrandName2(String brandName2) {
		this.brandName2 = brandName2;
	}
	/**
	 * @return the nick
	 */
	public String getNick() {
		return nick;
	}
	/**
	 * @param nick the nick to set
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

}
