package com.rayleigh.xls.read.impl;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rayleigh.db.util.DBUtils;

public class WriteCsv {
	private static Connection conn = null;

	public static void main(String[] args) {
		WriteCsv wx = new WriteCsv();
		System.out.println(new Date());
		wx.exportCsv(
				"select * from score_temp tmp where not exists (select 1 from pe_student where reg_no=tmp.userid)",
				"E:/无学号表.csv");
		System.out.println(new Date());
		DBUtils.close();
	}

	// 导出csv
	public void exportCsv(String sql, String filename) {
		BufferedWriter fileOut = null;
		Statement stat = null;
		ResultSet rs = null;
		System.out.println(new Date());
		try {
			fileOut = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(filename)));
			stat = conn.createStatement();
			rs = stat.executeQuery(sql);
			List list = getTableMetaData(rs);
			setTitleCvs(list, fileOut);
			int i = 1;
			while (rs.next()) {
				StringBuffer sb = new StringBuffer();
				for (int j = 1; j <= list.size(); j++) {
					String str = rs.getString(j);
					str = str == null ? " " : str;
					sb.append(str + ",");
				}
				sb.deleteCharAt(sb.length() - 1);
				fileOut.write(sb.toString() + "\r\n");
				i++;
				if (i % 1000 == 0) {
					fileOut.flush();
				}
			}
			System.out.println(new Date());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				stat.close();
				fileOut.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public List getTableMetaData(ResultSet resultSet) throws SQLException {
		List list = new ArrayList();
		ResultSetMetaData rsmd = resultSet.getMetaData();
		int cols = rsmd.getColumnCount();
		for (int i = 1; i <= cols; i++) {
			list.add(rsmd.getColumnName(i));
		}
		return list;
	}

	public void setTitleCvs(List list, BufferedWriter bw) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i) + ",");
		}
		sb.deleteCharAt(sb.length() - 1);
		try {
			bw.write(sb.toString() + "\r\n");
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
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
				System.exit(1);
			}
		}
	}

	static {
		conn = DBUtils.getConnection();
	}

}
