package com.banzhuan.entity;
import java.io.Serializable;

public class ItemEntity implements Serializable {
   	  /**
	 * 
	 */
	private static final long serialVersionUID = 2590439214424199675L;
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
	private String gmtCreate;
	// 
	private String gmtModify;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
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
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	/**
	 * @return the gmtCreate
	 */
	public String getGmtCreate() {
		return gmtCreate;
	}
	/**
	 * @param gmtCreate the gmtCreate to set
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
	 * @param gmtModify the gmtModify to set
	 */
	public void setGmtModify(String gmtModify) {
		this.gmtModify = gmtModify;
	}
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
	 * @return the picture
	 */
	public String getPicture() {
		return picture;
	}
	/**
	 * @param picture the picture to set
	 */
	public void setPicture(String picture) {
		this.picture = picture;
	}
	/**
	 * @return the cover
	 */
	public String getCover() {
		return cover;
	}
	/**
	 * @param cover the cover to set
	 */
	public void setCover(String cover) {
		this.cover = cover;
	}
	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
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
	 * @return the limit
	 */
	public int getLimitq() {
		return limitq;
	}
	/**
	 * @param limit the limit to set
	 */
	public void setLimitq(int limitq) {
		this.limitq = limitq;
	}
	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
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
	/**
	 * @return the provider
	 */
	public String getProvider() {
		return provider;
	}
	/**
	 * @param provider the provider to set
	 */
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getFeature() {
		return feature;
	}
	public void setFeature(String feature) {
		this.feature = feature;
	}
   }