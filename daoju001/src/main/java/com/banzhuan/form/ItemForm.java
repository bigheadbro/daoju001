package com.banzhuan.form;

import java.io.Serializable;

public class ItemForm implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8173699714632893504L;
	
	private String type;
	
	private String detailtype;
	
	private String brand;
	
	private String material;
	
	private String workmaterial;
	
	private int order;//1是价格升序，2是时间降序

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
	
}
