package com.banzhuan.util;

import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

public class GenerateMyBatisMapperUtil {
	public Connection connection = null;

	public void init() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		// 2. 获取数据库的连接
		try {
			connection = java.sql.DriverManager
					.getConnection(
							"jdbc:mysql://114.80.208.103:60306/daoju001?useUnicode=true&amp;characterEncoding=utf-8",
							"root", "123123");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public TableEntityMapping gen(String table) throws SQLException {
		if (connection == null)
			init();
		TableEntityMapping tableEntityMapping = new TableEntityMapping();
		tableEntityMapping.setEntityName(tableName2EntityName(table));
		List<ColumnFieldMapping> columns = new ArrayList<ColumnFieldMapping>();
		tableEntityMapping.setColumns(columns);
		ResultSet ret = connection.createStatement().executeQuery(
				"select * from " + table + " limit 1");
		ResultSetMetaData meta = ret.getMetaData();
		int count = meta.getColumnCount();
		for (int i = 1; i <= count; i++) {
			ColumnFieldMapping columnFieldMapping = new ColumnFieldMapping();
			columnFieldMapping.fieldName = columnName2FieldName(meta
					.getColumnName(i));
			columnFieldMapping.columnName = meta
					.getColumnName(i);
			columnFieldMapping.fieldType = columnType2FieldType(meta
					.getColumnTypeName(i));
			columns.add(columnFieldMapping);
		}
		DatabaseMetaData dm = connection.getMetaData();
		ret = dm.getColumns(null, null, table, null);
		while(ret.next())
		{
			for(ColumnFieldMapping c : columns)
			{
				if(ret.getString("COLUMN_NAME").replace("_", "").equalsIgnoreCase(c.getFieldName()))
				{
					c.setRemark(ret.getString("REMARKS"));
				}
			}
		}
		connection.close();
		return tableEntityMapping;
	}

	public static String columnType2FieldType(String columnType) {
		if (columnType.equals("INT") || columnType.equals("TINYINT")) {
			return "int";
		} else if (columnType.equals("VARCHAR") || columnType.equals("TIMESTAMP") || columnType.equals("TEXT")) {
			return "String";
		} else if (columnType.equals("FLOAT")) {
			return "float";
		}
		return "String";
	}

	public static String tableName2EntityName(String tableName) {
		String[] cls = tableName.split("_");
		String f = cls[1];
		StringBuilder buf = new StringBuilder();
		buf.append(f.substring(0, 1).toUpperCase() + f.substring(1));
		for(int i=2;i<cls.length;i++)
		{
			buf.append(cls[i].substring(0, 1).toUpperCase()
					+ cls[i].substring(1));
		}
		return buf.toString() + "Entity";
	}

	public static String columnName2FieldName(String columnName) {
		if (!columnName.contains("_"))
			return columnName;
		String[] cls = columnName.split("_");
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < cls.length; i++) {
			if (i == 0) {
				buf.append(cls[i]);
			} else {
				buf.append(cls[i].substring(0, 1).toUpperCase()
						+ cls[i].substring(1));
			}
		}
		return buf.toString();
	}
	/**
	 * @param args
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException {
		if(args.length <=0)
			return;
		for(String table:args)
		{
			TableEntityMapping entity = new GenerateMyBatisMapperUtil().gen(table);
			// 获取模板引擎
			VelocityEngine ve = new VelocityEngine();
			// 模板文件所在的路径
			String path = "src/main/java/com/banzhuan/util";
			// 设置参数
			ve.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, path);
			// 处理中文问题
			ve.setProperty(Velocity.INPUT_ENCODING, "GBK");
			ve.setProperty(Velocity.OUTPUT_ENCODING, "GBK");
			Template template = null;
			// 选择要用到的模板
			try {
				// 初始化模板
				ve.init();
				template = ve.getTemplate("mapper.vm");
				// 获取上下文
				VelocityContext root = new VelocityContext();
				// 把数据填入上下文
				root.put("entity", entity);
				// 输出
			    //Writer sw = new PrintWriter(new FileOutputStream(new File("src/main/resources/mapper/banzhuan/entity/"+entity.getEntityName()+".java")));
				Writer sw = new StringWriter();
				template.merge(root, sw);
			    sw.flush();
			    System.out.println(sw);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
