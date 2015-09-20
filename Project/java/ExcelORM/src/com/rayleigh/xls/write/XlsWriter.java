package com.rayleigh.xls.write;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.rayleigh.db.util.DBUtils;

public class XlsWriter {
	private static Connection conn = null;

	public static void main(String[] args) {
		XlsWriter xlsWriter = new XlsWriter();
		String sql = "select name 姓名 from pe_manager where rownum < 10";
		xlsWriter.export(sql, "E:/aaa.xlsx");
	}

	public void export(String sql, String filename) {
		Workbook wb = new XSSFWorkbook();
		FileOutputStream fileOut = null;
		Statement stat = null;
		// CreationHelper createHelper = wb.getCreationHelper();
		Sheet sheet = wb.createSheet("new sheet");
		ResultSet rs = null;
		try {
			fileOut = new FileOutputStream(filename);
			stat = conn.createStatement();
			rs = stat.executeQuery(sql);
			List list = getTableMetaData(rs);
			setTitle(list, sheet);
			int i = 1;
			while (rs.next()) {
				Row row = sheet.createRow(i);
				for (int j = 1; j <= list.size(); j++) {
					row.createCell(j - 1).setCellValue(rs.getString(j));
				}
				i++;
				if (i % 200 == 0) {
					System.out.println(i);
					// wb.write(fileOut);
					fileOut.flush();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				stat.close();
				wb.write(fileOut);
				fileOut.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List getTableMetaData(ResultSet resultSet) throws SQLException {
		List list = new ArrayList();
		ResultSetMetaData rsmd = resultSet.getMetaData();
		int cols = rsmd.getColumnCount();
		for (int i = 1; i <= cols; i++) {
			list.add(rsmd.getColumnName(i));
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public void setTitle(List list, Sheet sheet) {
		Row row = sheet.createRow(0);
		for (int i = 0; i < list.size(); i++) {
			row.createCell(i).setCellValue((String) list.get(i));
		}
	}

	public int executeSql(String sql) {
		Statement stat = null;
		try {
			stat = conn.createStatement();
			return stat.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} finally {
			try {
				stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	static {
		conn = DBUtils.getConnection();
	}
}
