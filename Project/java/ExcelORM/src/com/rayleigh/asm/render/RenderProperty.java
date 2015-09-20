package com.rayleigh.asm.render;

import com.rayleigh.asm.BuildProperty;
/*数据库映射类属性描述*/
public class RenderProperty extends BuildProperty{
	/*是否主键*/
	private boolean primary;
	/*序列*/
	private String sequence;
	/*字段长度*/
	private Integer length;
	/*对应数据库字段名*/	
	private String field;

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public boolean getPrimary() {
		return primary;
	}

	public void setPrimary(boolean primary) {
		this.primary = primary;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
		
		
}
