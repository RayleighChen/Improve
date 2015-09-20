package com.rayleigh.hibernate;

import java.util.Map;
import java.util.HashMap;

public class OracleDBMap implements DBTypeMap {

	private Map map;

	public OracleDBMap() {
		this.initialize();
	}

	private void initialize() {
		map = new HashMap();
		map.put(String.class, "varchar2");
		map.put(Long.class, "number");
		map.put(Integer.class, "number");
		map.put(Short.class, "number(3)");
		map.put(Boolean.class, "number(1)");
		map.put(java.util.Date.class, "date");
	}

	/* 根据java类型返回对应的数据库类型 */
	public String getDBType(Class cls) {
		return (String) map.get(cls);
	}
}
