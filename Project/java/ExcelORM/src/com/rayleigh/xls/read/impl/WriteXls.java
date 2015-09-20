package com.rayleigh.xls.read.impl;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.rayleigh.db.util.DBUtils;

public class WriteXls {
	private static Connection conn = null;

	public static void main(String[] args) {
		String seme = "ff808081256d72830125725c535a004b";
		WriteXls wx = new WriteXls();
		// String exitsSql =
		// "select * from score_temp tmp where (userid,coursecode) exists (select userid,coursecode from "+wx.early_data+" where )";
		// String deleteSql =
		// "delete from score_temp where (userid,coursecode) in (select userid,coursecode from "+wx.early_data+")";
		String exitsSql = "select * from score_temp tem where exists (select 1 from PE_TCH_COURSE ptc,PR_STU_COURSE_SCORE pcs where ptc.id=pcs.fk_course_id and ptc.code=tem.coursecode and pcs.fk_stu_id=tem.userid)";
		String deleteSql = "delete from score_temp tem where exists (select 1 from PE_TCH_COURSE ptc,PR_STU_COURSE_SCORE pcs where ptc.id=pcs.fk_course_id and ptc.code=tem.coursecode and pcs.fk_stu_id=tem.userid)";
		wx.export(exitsSql, "E:/已存在的学生课程.xlsx");
		wx.executeSql(deleteSql);

		System.out.println(new Date());
		wx.export(
				"select * from score_temp tmp where not exists (select 1 from pe_student where reg_no=tmp.userid)",
				"E:/无学号表.xlsx");
		System.out.println(new Date());

		// String nocourse =
		// "select distinct coursecode,coursename from score_temp tmp where coursecode not in (select ptc.code from PE_TCH_COURSE ptc) order by coursecode";
		// String delNocourse =
		// "delete from score_temp tmp where coursecode not in (select ptc.code from PE_TCH_COURSE ptc)";
		String nocourse = "select distinct coursecode,coursename from score_temp tmp where coursecode not exists (select 1 from PE_TCH_COURSE ptc where ptc.code=tmp.coursecode) order by coursecode";
		String delNocourse = "delete from score_temp tmp where not exists (select ptc.code from PE_TCH_COURSE ptc where ptc.code=tmp.coursecode)";
		wx.export(nocourse, "E:/无课程号表.xlsx");
		wx.executeSql(delNocourse);
		wx.executeSql("update score_temp set id = id+102000000");

		// 将状态不是"正常"的改为补考
		wx.executeSql("update score_temp set status='补考' where status !='正常'");
		// 重复数据转移至temp_score表中
		StringBuffer bufferStr = new StringBuffer();
		bufferStr.append("  insert into temp_score                      ");
		bufferStr.append("    (ID,                                      ");
		bufferStr.append("     USERID,                                  ");
		bufferStr.append("     USERNAME,                                ");
		bufferStr.append("     COURSECODE,                              ");
		bufferStr.append("     COURSENAME,                              ");
		bufferStr.append("     AVERAGESCORE,                            ");
		bufferStr.append("     ENDSCORE,                                ");
		bufferStr.append("     SCORE,                                   ");
		bufferStr.append("     STATUS,                                  ");
		bufferStr.append("     YEAR,                                    ");
		bufferStr.append("     SEMESTER)                                ");
		bufferStr.append("    select ID,                                ");
		bufferStr.append("     USERID,                                  ");
		bufferStr.append("     USERNAME,                                ");
		bufferStr.append("     COURSECODE,                              ");
		bufferStr.append("     COURSENAME,                              ");
		bufferStr.append("     AVERAGESCORE,                            ");
		bufferStr.append("     ENDSCORE,                                ");
		bufferStr.append("     SCORE,                                   ");
		bufferStr.append("     '补考',                                  ");
		bufferStr.append("     YEAR,                                    ");
		bufferStr.append("     SEMESTER                                 ");
		bufferStr.append("      from score_temp                         ");
		bufferStr.append("     where (userid, coursecode) in            ");
		bufferStr.append("           (select userid, coursecode         ");
		bufferStr.append("              from score_temp                 ");
		bufferStr.append("             group by userid, coursecode      ");
		bufferStr.append("            having count(*) > 1)             ");
		wx.executeSql(bufferStr.toString());

		// 删除重复数据
		StringBuffer delBuffer = new StringBuffer();
		delBuffer.append("  delete from score_temp                 ");
		delBuffer.append("   where (userid, coursecode) in         ");
		delBuffer.append("         (select userid, coursecode      ");
		delBuffer.append("            from score_temp              ");
		delBuffer.append("           group by userid, coursecode   ");
		delBuffer.append("          having count(*) > 1)          ");
		wx.executeSql(delBuffer.toString());

		// 非重复数据插入成绩表
		StringBuffer insertSql = new StringBuffer();
		insertSql
				.append("  insert into PR_STU_COURSE_SCORE                              ");
		insertSql
				.append("    (id,                                                       ");
		insertSql
				.append("     FK_STU_ID,                                                ");
		insertSql
				.append("     FK_COURSE_ID,                                             ");
		insertSql
				.append("     SCORE_USUAL,                                              ");
		insertSql
				.append("     SCORE_EXAM,                                               ");
		insertSql
				.append("     SCORE_TOTAL,                                              ");
		insertSql
				.append("     FK_SEMESTER_ID,                                           ");
		insertSql
				.append("     FLAG_ISRECOGNITION,                                       ");
		insertSql
				.append("     FLAG_SCORETYPE,                                           ");
		insertSql
				.append("     FLAG_ISCHEAT,                                             ");
		insertSql
				.append("     FLAG_ISVALID)                                             ");
		insertSql
				.append("    select tmp.id,                                             ");
		insertSql
				.append("           tmp.userid,                                         ");
		insertSql
				.append("           cou.id,                                             ");
		insertSql
				.append("           decode(tmp.averagescore, ' ', 0, tmp.averagescore), ");
		insertSql
				.append("           decode(tmp.endscore, ' ', 0, tmp.endscore),         ");
		insertSql
				.append("           decode(tmp.score, ' ', 0, tmp.score),               ");
		insertSql.append("'" + seme + "'");
		insertSql
				.append("           ,'4028808c2397ade6012397b18f500001',                 ");
		insertSql
				.append("           enu.id,                                             ");
		insertSql
				.append("           '4028809724c2f24f0124c420db000071',                 ");
		insertSql
				.append("           '4028809522f27ad90122f27df23b0001'                  ");
		insertSql
				.append("      from SCORE_TEMP    tmp,                                  ");
		insertSql
				.append("           PE_STUDENT    stu,                                  ");
		insertSql
				.append("           PE_TCH_COURSE cou,                                  ");
		insertSql
				.append("           ENUM_CONST    enu                                   ");
		insertSql
				.append("     where tmp.userid = stu.reg_no                             ");
		insertSql
				.append("       and tmp.coursecode = cou.code                           ");
		insertSql
				.append("       and enu.namespace = 'FlagScoretype'                     ");
		insertSql
				.append("       and enu.name = tmp.status                              ");
		System.out.println("非重复插入" + wx.executeSql(insertSql.toString()));

		// 重复数据插入成绩表
		Statement stat = null;
		ResultSet rs = null;
		try {
			stat = conn.createStatement();
			String uid = " ";
			String couid = " ";
			rs = stat
					.executeQuery("select id,userid,coursecode from temp_score tmp order by tmp.userid, coursecode, score desc");
			StringBuffer insertTwo = new StringBuffer();
			insertTwo
					.append("     insert into PR_STU_COURSE_SCORE                                    ");
			insertTwo
					.append("        (id,                                                            ");
			insertTwo
					.append("         FK_STU_ID,                                                     ");
			insertTwo
					.append("         FK_COURSE_ID,                                                  ");
			insertTwo
					.append("         SCORE_USUAL,                                                   ");
			insertTwo
					.append("         SCORE_EXAM,                                                    ");
			insertTwo
					.append("         SCORE_TOTAL,                                                   ");
			insertTwo
					.append("         FK_SEMESTER_ID,                                                ");
			insertTwo
					.append("         FLAG_ISRECOGNITION,                                            ");
			insertTwo
					.append("         FLAG_SCORETYPE,                                                ");
			insertTwo
					.append("         FLAG_ISCHEAT,                                                  ");
			insertTwo
					.append("         FLAG_ISVALID)                                                  ");
			insertTwo
					.append("        select tmp.id,                                                  ");
			insertTwo
					.append("               tmp.userid,                                              ");
			insertTwo
					.append("               cou.id,                                                  ");
			insertTwo
					.append("               decode(tmp.averagescore, ' ', 0, tmp.averagescore),      ");
			insertTwo
					.append("               decode(tmp.endscore, ' ', 0, tmp.endscore),              ");
			insertTwo
					.append("               decode(tmp.score, ' ', 0, tmp.score),                    ");
			insertTwo.append("               '" + seme
					+ "',                      ");
			insertTwo
					.append("               '4028808c2397ade6012397b18f500001',                      ");
			insertTwo
					.append("               enu.id,                                                  ");
			insertTwo
					.append("               '4028809724c2f24f0124c420db000071',                      ");
			insertTwo
					.append("               '4028809522f27ad90122f27df23b0001'                       ");
			insertTwo
					.append("          from temp_score    tmp,                                       ");
			insertTwo
					.append("               PE_STUDENT    stu,                                       ");
			insertTwo
					.append("               PE_TCH_COURSE cou,                                       ");
			insertTwo
					.append("               ENUM_CONST    enu                                        ");
			insertTwo
					.append("         where tmp.userid = stu.reg_no                                  ");
			insertTwo
					.append("           and tmp.coursecode = cou.code                                ");
			insertTwo
					.append("           and enu.namespace = 'FlagScoretype'                          ");
			insertTwo
					.append("           and enu.name = tmp.status                                    ");
			insertTwo
					.append("           and tmp.id = ?                          ");
			PreparedStatement pst = conn.prepareStatement(insertTwo.toString());
			while (rs.next()) {
				String userid = rs.getString(2);
				String courseid = rs.getString(3);
				if (!uid.equals(userid) || !couid.equals(courseid)) {
					pst.setString(1, rs.getString(1));
					pst.addBatch();
					uid = userid;
					couid = courseid;
				}
			}
			pst.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("插入完毕，开始导出学号不存在数据" + new Date());
		// try {
		// cs =conn.prepareCall("{Call insert_course(?)}");
		// cs.setString(1,"");
		// cs.execute();
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }

		// wx.executeSql("delete from TEMP_SCORE tmp where userid in (select reg_no from pe_student)");
		// wx.executeSql("delete from SCORE_TEMP tmp where userid in (select reg_no from pe_student)");
		// System.out.println(new Date());
		// wx.export("select * from score_temp", "E:/非重复学号不存在.xlsx");
		// System.out.println(new Date());
		// wx.export("select * from temp_score", "E:/重复学号不存在.xlsx");
		// System.out.println(new Date());
		DBUtils.close();
	}

	// 导出excel
	@SuppressWarnings("unchecked")
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
	public void exportCsv(String sql, String filename) {
		BufferedWriter fileOut = null;
		Statement stat = null;
		// CreationHelper createHelper = wb.getCreationHelper();
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

	@SuppressWarnings("unchecked")
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
