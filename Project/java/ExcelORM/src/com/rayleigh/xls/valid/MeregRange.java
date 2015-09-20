package com.rayleigh.xls.valid;

public class MeregRange {
	private int topleft_row ;
	private int topleft_column ;
	private int bottomRight_row ;
	private int bottomRight_column;
	private String value ;
	
	public MeregRange() {
		super();
	}

	public MeregRange(int topleft_row, int topleft_column, int bottomRight_row,int bottomRight_column){
		this(topleft_row, topleft_column, bottomRight_row, bottomRight_column, null);
	}
	
	public MeregRange(int topleft_row, int topleft_column, int bottomRight_row,int bottomRight_column,String value) {
		super();
		this.topleft_row = topleft_row;
		this.topleft_column = topleft_column;
		this.bottomRight_row = bottomRight_row;
		this.bottomRight_column = bottomRight_column;
		this.value = value;
	}
	
	public boolean isInRange(int row,int column) {
		if (row >= topleft_row && column >= topleft_column && row <= bottomRight_row && column <= bottomRight_column) {
			return true;
		}
		return false;
	}
	
	public int getTopleft_row() {
		return topleft_row;
	}
	public void setTopleft_row(int topleft_row) {
		this.topleft_row = topleft_row;
	}
	public int getTopleft_column() {
		return topleft_column;
	}
	public void setTopleft_column(int topleft_column) {
		this.topleft_column = topleft_column;
	}
	public int getBottomRight_row() {
		return bottomRight_row;
	}
	public void setBottomRight_row(int bottomRight_row) {
		this.bottomRight_row = bottomRight_row;
	}
	public int getBottomRight_column() {
		return bottomRight_column;
	}
	public void setBottomRight_column(int bottomRight_column) {
		this.bottomRight_column = bottomRight_column;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
