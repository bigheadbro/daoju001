package com.banzhuan.util;

import java.io.Serializable;
import java.util.List;

public class TableEntityMapping implements Serializable {
	private static final long serialVersionUID = 3094154202413604833L;
	private String entityName; // 表名称
	private List<ColumnFieldMapping> columns; // 字段列表
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public List<ColumnFieldMapping> getColumns() {
		return columns;
	}
	public void setColumns(List<ColumnFieldMapping> columns) {
		this.columns = columns;
	}
}
