package com.rayleigh.hibernate;

public interface  DBTypeMap {
	
	/*根据java类型返回对应的数据库类型*/
	String getDBType(Class cls);

}
