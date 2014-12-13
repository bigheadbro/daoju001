package com.banzhuan.entity;

import java.io.Serializable;
import java.util.Comparator;

public class CategoryEntity implements Serializable, Comparable<CategoryEntity> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2159224656610484591L;
	private String code;
	private String name;
	private String pic;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	
	@Override
    public int compareTo(CategoryEntity o) {
        if (Integer.valueOf(this.getCode())-Integer.valueOf(o.getCode()) > 0) {
            return 1;
        }
        if (Integer.valueOf(this.getCode())-Integer.valueOf(o.getCode()) < 0) {
            return -1;
        }
        return 0;
    }
}