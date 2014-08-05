package com.banzhuan.entity;
import java.io.Serializable;

public class ArticleEntity implements Serializable {
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
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the outline
	 */
	public String getOutline() {
		return outline;
	}
	/**
	 * @param outline the outline to set
	 */
	public void setOutline(String outline) {
		this.outline = outline;
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
	private static final long serialVersionUID = 1205872214869809034L;
	// 
	  private int id;
   	  // 
	  private String title;
   	  // 
	  private String outline;
   	  // 
	  private String link;
   	  // 
	  private String cover;
   	  // 
	  private String gmtCreate;
   }