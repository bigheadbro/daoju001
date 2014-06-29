package com.banzhuan.entity;
import java.io.Serializable;

public class OrderEntity implements Serializable {
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
	/**
	 * @return the addressid
	 */
	public int getAddressid() {
		return addressid;
	}
	/**
	 * @param addressid the addressid to set
	 */
	public void setAddressid(int addressid) {
		this.addressid = addressid;
	}
	/**
	 * @return the itemid
	 */
	public int getItemid() {
		return itemid;
	}
	/**
	 * @param itemid the itemid to set
	 */
	public void setItemid(int itemid) {
		this.itemid = itemid;
	}
	/**
	 * @return the itemAddr
	 */
	public String getItemAddr() {
		return itemAddr;
	}
	/**
	 * @param itemAddr the itemAddr to set
	 */
	public void setItemAddr(String itemAddr) {
		this.itemAddr = itemAddr;
	}
	/**
	 * @return the logNumber
	 */
	public String getLogNumber() {
		return logNumber;
	}
	/**
	 * @param logNumber the logNumber to set
	 */
	public void setLogNumber(String logNumber) {
		this.logNumber = logNumber;
	}
	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}
	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(int state) {
		this.state = state;
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
	 * 
	 */
	private static final long serialVersionUID = -1313893719946146309L;
	// 
	  private int id;
   	  // 
	  private int userid;
   	  // 
	  private int addressid;
   	  // 
	  private int itemid;
   	  // 
	  private String itemAddr;
   	  // 
	  private String logNumber;
   	  // 
	  private String price;
   	  // 
	  private int state;
   	  // 
	  private String gmtCreate;
   }