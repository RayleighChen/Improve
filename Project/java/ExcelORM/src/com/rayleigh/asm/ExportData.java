package com.rayleigh.asm;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.rayleigh.db.util.DBUtils;

public class ExportData {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		Connection conn = DBUtils.getConnection();
		String sql = "select id,zzmm,xueli,graduate_school,graduate_year,graduate_no,email,phone,address,workplace from pe_student_info stuinfo where exists (select 1 from temp_student_info temp,pe_student stu where stu.reg_no=temp.id and stuinfo.id=stu.fk_stu_info)";

		String updateModel = "update pe_student_info set (zzmm,xueli,graduate_school,graduate_year,graduate_no,email,phone,address,workplace)=('%s','%s','%s','%s','%s','%s','%s','%s','%s') where id= '%s';\n";
		Statement st = null;
		ResultSet rs = null;
		BufferedWriter fileOut = null;
		try {
			fileOut = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(new File("E:/update.sql"))));
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				for (int i = 1; i <= 10; i++) {

				}
				fileOut.write(String.format(updateModel, rs.getString(2),
						rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8),
						rs.getString(9), rs.getString(10), rs.getString(1)));
			}
			fileOut.flush();
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				fileOut.close();
				rs.close();
				st.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
