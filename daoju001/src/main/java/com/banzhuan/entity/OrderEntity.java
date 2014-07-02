package com.banzhuan.entity;

import java.io.Serializable;

public class OrderEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1313893719946146309L;
	//
	private int id;
	private int usertype;
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
	
	private int quantity;
	//
	private double price;
	//1已购买，2已付款，3已发货，4已收货，0已取消
	private int state;
	//
	private String gmtCreate;

	private String gmtSubmitOrder;
	
	private String gmtPay;
	
	private String gmtSell;
	
	private String gmtAssure;
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
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
	 * @param userid
	 *            the userid to set
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
	 * @param addressid
	 *            the addressid to set
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
	 * @param itemid
	 *            the itemid to set
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
	 * @param itemAddr
	 *            the itemAddr to set
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
	 * @param logNumber
	 *            the logNumber to set
	 */
	public void setLogNumber(String logNumber) {
		this.logNumber = logNumber;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
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
	 * @param gmtCreate
	 *            the gmtCreate to set
	 */
	public void setGmtCreate(String gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	/**
	 * @return the usertype
	 */
	public int getUsertype() {
		return usertype;
	}

	/**
	 * @param usertype the usertype to set
	 */
	public void setUsertype(int usertype) {
		this.usertype = usertype;
	}

	/**
	 * @return the gmtSubmitOrder
	 */
	public String getGmtSubmitOrder() {
		return gmtSubmitOrder;
	}

	/**
	 * @param gmtSubmitOrder the gmtSubmitOrder to set
	 */
	public void setGmtSubmitOrder(String gmtSubmitOrder) {
		this.gmtSubmitOrder = gmtSubmitOrder;
	}

	/**
	 * @return the gmtPay
	 */
	public String getGmtPay() {
		return gmtPay;
	}

	/**
	 * @param gmtPay the gmtPay to set
	 */
	public void setGmtPay(String gmtPay) {
		this.gmtPay = gmtPay;
	}

	/**
	 * @return the gmtSell
	 */
	public String getGmtSell() {
		return gmtSell;
	}

	/**
	 * @param gmtSell the gmtSell to set
	 */
	public void setGmtSell(String gmtSell) {
		this.gmtSell = gmtSell;
	}

	/**
	 * @return the gmtAssure
	 */
	public String getGmtAssure() {
		return gmtAssure;
	}

	/**
	 * @param gmtAssure the gmtAssure to set
	 */
	public void setGmtAssure(String gmtAssure) {
		this.gmtAssure = gmtAssure;
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

}