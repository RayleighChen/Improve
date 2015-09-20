package com.rayleigh.asm.render;

import java.util.List;
/*数据库映射类描述*/
public class RenderClass {
	
	/*类名*/
	private String className;
	/*对应表名*/
	private String tableName;
	/*属性*/
	private List properties;
	
	public RenderClass()
	{
		properties = null;
	}
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List getProperties() {
		return properties;
	}

	public void setProperties(List properties) {
		this.properties = properties;
	}
	

	
}
