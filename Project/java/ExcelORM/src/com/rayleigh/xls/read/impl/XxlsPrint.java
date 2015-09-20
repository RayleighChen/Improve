package com.rayleigh.xls.read.impl;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import com.rayleigh.xls.util.XxlsAbstract;

public class XxlsPrint extends XxlsAbstract {

	@Override
	public void optRows(int sheetIndex, int curRow, List<String> rowlist)
			throws SQLException {
		for (int i = 0; i < rowlist.size(); i++) {
			System.out.print(rowlist.get(i) + ",");
		}
		System.out.println();
	}

	public static void main(String[] args) throws Exception {
		XxlsPrint howto = new XxlsPrint();
		howto.setTitleRow(1);
		howto.processOneSheet("F:/test.xlsx", 1);
		File file = new File("F:/new.xlsx");
		file.delete();
		// Scanner in = new Scanner(System.in);
		// System.out.println(in.next());
		// howto.processAllSheets("F:/new.xlsx");
	}
}
