package com.banzhuan.entity;

import java.io.Serializable;

public class RelationEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -585306812108327240L;
	//
	private int id;
	// 主动收藏的人
	private String wxid;
	// 被收藏的id
	private String wxid2;
	//
	private String wxname;
	//
	private String wxname2;
	//
	private String wxcompany;
	//
	private String wxcompany2;
	//
	private int relation;

	private int userid;

	private int userid2;

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
	 * @return the wxid
	 */
	public String getWxid() {
		return wxid;
	}

	/**
	 * @param wxid
	 *            the wxid to set
	 */
	public void setWxid(String wxid) {
		this.wxid = wxid;
	}

	/**
	 * @return the wxid2
	 */
	public String getWxid2() {
		return wxid2;
	}

	/**
	 * @param wxid2
	 *            the wxid2 to set
	 */
	public void setWxid2(String wxid2) {
		this.wxid2 = wxid2;
	}

	/**
	 * @return the wxname
	 */
	public String getWxname() {
		return wxname;
	}

	/**
	 * @param wxname
	 *            the wxname to set
	 */
	public void setWxname(String wxname) {
		this.wxname = wxname;
	}

	/**
	 * @return the wxname2
	 */
	public String getWxname2() {
		return wxname2;
	}

	/**
	 * @param wxname2
	 *            the wxname2 to set
	 */
	public void setWxname2(String wxname2) {
		this.wxname2 = wxname2;
	}

	/**
	 * @return the wxcompany
	 */
	public String getWxcompany() {
		return wxcompany;
	}

	/**
	 * @param wxcompany
	 *            the wxcompany to set
	 */
	public void setWxcompany(String wxcompany) {
		this.wxcompany = wxcompany;
	}

	/**
	 * @return the wxcompany2
	 */
	public String getWxcompany2() {
		return wxcompany2;
	}

	/**
	 * @param wxcompany2
	 *            the wxcompany2 to set
	 */
	public void setWxcompany2(String wxcompany2) {
		this.wxcompany2 = wxcompany2;
	}

	/**
	 * @return the relation
	 */
	public int getRelation() {
		return relation;
	}

	/**
	 * @param relation
	 *            the relation to set
	 */
	public void setRelation(int relation) {
		this.relation = relation;
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
	 * @return the userid2
	 */
	public int getUserid2() {
		return userid2;
	}

	/**
	 * @param userid2
	 *            the userid2 to set
	 */
	public void setUserid2(int userid2) {
		this.userid2 = userid2;
	}
}