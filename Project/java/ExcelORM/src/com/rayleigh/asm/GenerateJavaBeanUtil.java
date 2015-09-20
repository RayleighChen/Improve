package com.rayleigh.asm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.rayleigh.db.util.DBUtils;

public class GenerateJavaBeanUtil {
	private String tableName;// 表名
	private String[] colnames; // 列名数组
	private String[] colTypes; // 列名类型数组
	private int[] colSizes; // 列名大小数组

	private List<BuildProperty> propertiyList;
	private BuildProperty property;

	public Class<?> generateClass() {
		Connection con = DBUtils.getConnection();
		propertiyList = new ArrayList<BuildProperty>();
		String sql = "select * from " + tableName;
		PreparedStatement pStemt = null;
		Class<?> instance = null;
		try {
			pStemt = con.prepareStatement(sql);
			ResultSetMetaData rsmd = pStemt.getMetaData();
			int size = rsmd.getColumnCount(); // 统计列
			colnames = new String[size];
			colTypes = new String[size];
			colSizes = new int[size];
			for (int i = 0; i < size; i++) {
				colnames[i] = rsmd.getColumnName(i + 1);
				colTypes[i] = rsmd.getColumnTypeName(i + 1);
				colSizes[i] = rsmd.getColumnDisplaySize(i + 1);
			}
			processAllAttrs();

			String path = this.getClass().getResource("").getPath();

			String tmp = path.substring(0, path.length() - 6) + "/bean/"
					+ initcap(tableName.toLowerCase()) + ".class";
			instance = new ClassBuilder().build(
					initcap(tableName.toLowerCase()), tmp, propertiyList);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			System.out.println("Generate Success!");
			try {
				con.close();
				pStemt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return instance;
	}

	private void processAllAttrs() {
		for (int i = 0; i < colnames.length; i++) {
			property = new BuildProperty();
			property.setType(sqlType2JavaType(colTypes[i]));
			property.setName(colnames[i].toLowerCase());
			propertiyList.add(property);
		}
	}

	private String initcap(String str) {
		char[] ch = str.toCharArray();
		if (ch[0] >= 'a' && ch[0] <= 'z') {
			ch[0] = (char) (ch[0] - 32);
		}
		return new String(ch);
	}

	private String sqlType2JavaType(String sqlType) {
		if (sqlType.equalsIgnoreCase("bit")) {
			return "java/lang/Boolean";
		} else if (sqlType.equalsIgnoreCase("smallint")) {
			return "java/lang/Short";
		} else if (sqlType.equalsIgnoreCase("int")) {
			return "java/lang/Integer";
		} else if (sqlType.equalsIgnoreCase("bigint")
				|| sqlType.equalsIgnoreCase("timestamp")) {
			return "java/lang/Long";
		} else if (sqlType.equalsIgnoreCase("float")) {
			return "java/lang/Float";
		} else if (sqlType.equalsIgnoreCase("decimal")
				|| sqlType.equalsIgnoreCase("numeric")
				|| sqlType.equalsIgnoreCase("real")
				|| sqlType.equalsIgnoreCase("money")
				|| sqlType.equalsIgnoreCase("smallmoney")) {
			return "java/lang/Double";
		} else if (sqlType.equalsIgnoreCase("varchar")
				|| sqlType.equalsIgnoreCase("char")
				|| sqlType.equalsIgnoreCase("nvarchar")
				|| sqlType.equalsIgnoreCase("nchar")
				|| sqlType.equalsIgnoreCase("text")) {
			return "java/lang/String";
		} else if (sqlType.equalsIgnoreCase("datetime")
				|| sqlType.equalsIgnoreCase("date")) {
			return "java/util/Date";
		} else if (sqlType.equalsIgnoreCase("tinyint")) {
			return "java/lang/Byte";
		}
		return null;

	}

	public static void main(String[] args) {
		GenerateJavaBeanUtil generate = new GenerateJavaBeanUtil();
		generate.setTableName("person");

		Class<?> tmp = generate.generateClass();
		try {
			Object person = tmp.newInstance();
			System.out.println(tmp);

		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}
