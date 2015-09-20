package com.rayleigh.xls.valid.impl;

import java.io.File;
import java.util.List;
import java.util.regex.Pattern;

import jxl.Cell;
import jxl.write.WriteException;

import com.rayleigh.util.exception.EntityException;
import com.rayleigh.xls.valid.ImportDataMultiSheet;

public class ImportDatemultiImp extends ImportDataMultiSheet {
	private static Pattern pattern;

	public static void main(String[] args) throws SecurityException,
			NoSuchMethodException, EntityException, WriteException {
		File importFile = new File("F:/南昌08级复转成绩总表全.xls");
		File errorFile = new File("F:/error.xls");
		ImportDatemultiImp importDateImp = new ImportDatemultiImp();
		importDateImp.setImportExcel(importFile);
		importDateImp.setErrorExcel(errorFile);
		importDateImp.setStartRow(4);
		importDateImp.execute();
		importDateImp.getErrorExcel();
		for (String error : importDateImp.getErrors()) {
			System.out.println(error);
		}
	}

	public String everyCell() {
		Cell cell = this.getCurCell();
		List<String> semList = this.getTitiles()[2];
		List<String> courseList = this.getTitiles()[3];
		if (cell.getRow() > 3 && cell.getColumn() > 3) {
			String cellvalue = this.getValue(cell);
			String course_name = courseList.get(cell.getColumn());
			String reg_no = this.getValue(this.getCurRowCells()[1]);
			String stuname = this.getValue(this.getCurRowCells()[2]);
			if (cellvalue != null && !cellvalue.equals("")
					&& course_name != null && !course_name.equals("")
					&& reg_no != null && !reg_no.equals("")) {
				if (!pattern.matcher(cellvalue).matches()) {
					return "无效成绩，成绩必须为数字或合格、不合格、优秀、中等、良好";
				}
			} else {
				return "无效成绩";
			}
		}
		return null;
	}

	public String validColumn_1() {
		return null;
	}

	public String validColumn_2() {
		return null;
	}

	public String validColumn_3() {

		return null;
	}

	public boolean save() {
		return false;
	}

	static {
		pattern = Pattern
				.compile("^((\\d+)(\\.\\d+)?)$|^100$|^合格$|^不合格$|^优秀$|^中等$|^良好$|^优$|^良$|^中$");
	}
}
