package com.banzhuan.form;

import java.io.Serializable;

public class ItemForm implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8173699714632893504L;
	
	// 
	private int id;
	// 
	private String type;
	// 
	private String detailtype;
	// 
	private String material;
	// 
	private String workmaterial;
	
	private String version;

	// 
	private String brand;
	// 
	private double price;
	private String picture;
	private String cover;
	private String description;
	private String feature;
	
	private int limitq;
	private int quantity;
	
	private String provider;
	
	private int order;

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the detailtype
	 */
	public String getDetailtype() {
		return detailtype;
	}

	/**
	 * @param detailtype the detailtype to set
	 */
	public void setDetailtype(String detailtype) {
		this.detailtype = detailtype;
	}

	/**
	 * @return the brand
	 */
	public String getBrand() {
		return brand;
	}

	/**
	 * @param brand the brand to set
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}

	/**
	 * @return the material
	 */
	public String getMaterial() {
		return material;
	}

	/**
	 * @param material the material to set
	 */
	public void setMaterial(String material) {
		this.material = material;
	}

	/**
	 * @return the workmaterial
	 */
	public String getWorkmaterial() {
		return workmaterial;
	}

	/**
	 * @param workmaterial the workmaterial to set
	 */
	public void setWorkmaterial(String workmaterial) {
		this.workmaterial = workmaterial;
	}

	/**
	 * @return the order
	 */
	public int getOrder() {
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(int order) {
		this.order = order;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public int getLimitq() {
		return limitq;
	}

	public void setLimitq(int limitq) {
		this.limitq = limitq;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}
	
}
