package com.banzhuan.entity;
import java.io.Serializable;

public class NormalctEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -781971153075295027L;
	//
	private int id;
	//
	private String brand;
	//
	private String version;
	//
	private String material;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}
}