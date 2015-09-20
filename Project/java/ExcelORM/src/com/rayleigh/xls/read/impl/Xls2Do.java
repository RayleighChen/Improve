package com.rayleigh.xls.read.impl;

import java.util.Date;

import com.rayleigh.db.util.DBUtils;

public class Xls2Do {

	public static void main(String[] args) {
		String filename = "G:/Learning/实习/医保名单1.xlsx";
		String hxlsTable = "this_temp";
		String xlsxTable = "this_temp";
		xls2Database(filename, hxlsTable, xlsxTable);

//		filename = "E:/专业对应.xlsx";
//		xlsxTable = "major_temp";
//		xls2Database(filename, hxlsTable, xlsxTable);

	//	xls2Print(filename);
	}

	// excel->databse
	public static void xls2Database(String filename, String hxlsTable,
			String xlsxTable) {

		String fileType = filename.substring(filename.lastIndexOf(".") + 1)
				.toLowerCase();
		try {
			if (fileType.equals("xls")) {
				HxlsBig xls2csv = new HxlsBig(filename, hxlsTable);
				xls2csv.process();
				DBUtils.close();
			} else if (fileType.equals("xlsx")) {
				XxlsBig howto = new XxlsBig(xlsxTable);
				howto.setCreate(true);
				// howto.processOneSheet("F:/new.xlsx",1);
				howto.process(filename);
				DBUtils.close();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// excel->print
	public static void xls2Print(String filename) {
		String fileType = filename.substring(filename.lastIndexOf(".") + 1)
				.toLowerCase();
		try {
			if (fileType.equals("xls")) {
				HxlsPrint xls2csv;
				xls2csv = new HxlsPrint(filename);
				xls2csv.process();
			} else if (fileType.equals("xlsx")) {
				XxlsPrint howto = new XxlsPrint();
				howto.setTitleRow(0);
				howto.process(filename);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
}
