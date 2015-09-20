package com.rayleigh.xls.read.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.List;

import com.rayleigh.xls.util.HxlsAbstract;

public class HxlsPrint extends HxlsAbstract {
	private PrintStream output = null;

	public HxlsPrint(String filename) throws IOException,
			FileNotFoundException, SQLException {
		super(filename);
		output = System.out;
	}

	@Override
	public void optRows(int sheetIndex, int curRow, List<String> rowlist)
			throws SQLException {
		for (int i = 0; i < rowlist.size(); i++) {
			output.print(rowlist.get(i) + ",");
		}
		output.println();
	}

	public static void main(String[] args) {
		HxlsPrint xls2csv;
		try {
			xls2csv = new HxlsPrint("F:/test.xls");
			xls2csv.setTitleRow(1);
			xls2csv.process();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
