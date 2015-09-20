package com.rayleigh.xls.read.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.rayleigh.db.util.DBUtils;
import com.rayleigh.xls.util.HxlsAbstract;

public class HxlsBig extends HxlsAbstract {

	public static void main(String[] args) throws Exception {
		// XLS2CSVmra xls2csv = new XLS2CSVmra(args[0], minColumns);
		HxlsBig xls2csv = new HxlsBig("E:/福建师大学生数据/新增——20120503.xls",
				"student_temp");
		xls2csv.process();
		DBUtils.close();
	}

	public HxlsBig(POIFSFileSystem fs, PrintStream output, String tableName)
			throws SQLException {
		super(fs);
		this.conn = DBUtils.getConnection();
		this.statement = conn.createStatement();
		this.tableName = tableName;
	}

	public HxlsBig(String filename, String tableName) throws IOException,
			FileNotFoundException, SQLException {
		this(new POIFSFileSystem(new FileInputStream(filename)), System.out,
				tableName);
	}

	private Connection conn = null;
	private Statement statement = null;
	private PreparedStatement newStatement = null;

	private String tableName = "temp_table";
	private boolean create = true;

	private int colsnum = 0;

	// private int sheetIndex = 0;

	public void optRows(int sheetIndex, int curRow, List<String> rowlist)
			throws SQLException {
		if (curRow == 0 && sheetIndex == 0) {
			StringBuffer preSql = new StringBuffer("insert into " + tableName
					+ " values(");
			StringBuffer table = new StringBuffer("create table " + tableName
					+ "(");
			int c = colsnum = rowlist.size();
			for (int i = 0; i < c; i++) {
				preSql.append("?,");
				table.append(rowlist.get(i));
				table.append("  varchar2(100) ,");
			}

			table.deleteCharAt(table.length() - 1);
			preSql.deleteCharAt(preSql.length() - 1);
			table.append(")");
			preSql.append(")");
			if (create) {
				statement = conn.createStatement();
				try {
					statement.execute("drop table " + tableName);
				} catch (Exception e) {

				} finally {
					System.out.println("表 " + tableName + " 删除成功");
				}
				if (!statement.execute(table.toString())) {
					System.out.println("创建表 " + tableName + " 成功");
					// return;
				} else {
					System.out.println("创建表 " + tableName + " 失败");
					return;
				}
			}
			conn.setAutoCommit(false);
			newStatement = conn.prepareStatement(preSql.toString());

		} else if (curRow > 0) {
			// 一般行
			int col = rowlist.size();
			for (int i = 0; i < col; i++) {
				newStatement.setString(i + 1, rowlist.get(i));
			}
			for (int i = col; i < colsnum; i++) {
				newStatement.setString(i + 1, "");
			}
			newStatement.addBatch();
			if (curRow % 1000 == 0) {
				newStatement.executeBatch();
				conn.commit();
			}
		}
	}

}
