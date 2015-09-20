<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE hibernate-mapping PUBLIC "-//Hibernate/Hibernate Mapping DTD 3.0//EN"
"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd">
<hibernate-mapping>
    <class name="${entity.className}" table="${entity.tableName}">
    	<#if entity.properties?exists>
			<#list entity.properties as property>
				<#if property.primary>
					<id name="${property.name}" type="${property.type}">
				<#else>
					<property name="${property.name}" type="${property.type}">
				</#if>
				<#if property.type=="java.lang.String">
					<column name="${property.field?upper_case}" <#if property.length?exists>length="${property.length}"</#if>></column>
				<#elseif property.type=="java.util.Date">
						<column name="${property.field?upper_case}" length=7></column>
				<#elseif property.type=="java.lang.Long" || property.type=="java.lang.Integer"
					|| property.type=="java.lang.Short">
						<column name="${property.field?upper_case}" <#if property.length?exists>precision="${property.length}"</#if> scale="0"></column>
				</#if>
				<#if property.primary==true>
					<#if property.sequence?exists>
						<generator class="sequence">
							<param name="sequence">${property.sequence}</param>
						</generator>
					</#if>
					</id>
				<#else>
					</property>
				</#if>
			</#list>
		</#if>
    </class>
</hibernate-mapping>
