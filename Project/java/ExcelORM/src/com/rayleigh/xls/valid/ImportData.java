package com.rayleigh.xls.valid;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import com.rayleigh.util.exception.EntityException;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * jxl导入excel类，继承类可实现验证方法
 * 
 * @author gaosheng
 * 
 */
public abstract class ImportData {

	private int rows;
	private int columns;
	private int startRow = 1;
	private int startColumn = 0;
	private int minRows = 1;
	private int minColumns = 1;
	private int maxRows = -1;
	private int maxColumns = -1;
	private Map<Integer, Method> methodMap;
	private Map<Integer, List<String>> holdColumns;
	private Cell curCell;
	private Cell[] curRowCells;

	private String[] columnMethods = null;
	private int[] needHoldColumns = null;
	private File importExcel;
	private File errorExcel;
	private boolean hasError = false;
	private List<String> errors = new ArrayList<String>();

	/**
	 * 启动导入
	 * 
	 * @return boolean
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws EntityException
	 * @throws WriteException 
	 */
	public boolean execute() throws SecurityException, NoSuchMethodException,
			EntityException, WriteException {
		setMethodMap();
		setHoldColumns();
		Workbook work = null;
		try {
			work = Workbook.getWorkbook(importExcel);
		} catch (Exception e) {
			throw new EntityException("Excel表格读取异常！批量导入失败！<br/>");
		}
		Sheet sheet = work.getSheet(0);
		columns = sheet.getColumns();
		rows = sheet.getRows();
		// 全局验证
		if (!this.validGlobal()) {
			throw new EntityException("导入文件格式错误");
		}

		int successcount = 0;
		WritableWorkbook writew = null;
		WritableSheet writes = null;
		try {
			writew = Workbook.createWorkbook(errorExcel);
			writes = writew.createSheet("ErrorReport", 0);
			Label label;
			WritableCellFormat wcf;
			for (int i = 0; i < startRow; i++) {
				for (int j = 0; j < columns; j++) {
					label = new Label(j, i, getCellValue(sheet.getCell(j, i)));
					writes.addCell(label);
				}
			}
			label = new Label(columns, startRow - 1, "错误详细");
			WritableFont wf0 = new WritableFont(WritableFont.TIMES, 12);
			wcf = new WritableCellFormat(wf0);
			label.setCellFormat(wcf);
			writes.addCell(label);

			int wi = startRow;
			// -------------------------
			StringBuffer info_temp = null;
			String result = null;
			Method method = null;
			for (int i = startRow; i < rows; i++) {
				curRowCells = sheet.getRow(i);
				boolean[] wj = new boolean[columns];
				info_temp = new StringBuffer();
				for (int j = startColumn; j < columns; j++) {
					curCell = sheet.getCell(j, i);
					System.out.println(curCell.getContents());
					method = methodMap.get(j);
					if (method == null) {
						continue;
					}
					result = (String) method.invoke(this, null);
					if (result != null) {
						info_temp.append(result);
						info_temp.append(" ");
						wj[j] = true;
					}
					if (holdColumns.get(j) != null) {
						holdColumns.get(j).add(getCellValue(curCell));
					}
					if (info_temp.length() > 0) {
						errors.add("第" + (i + 1) + "行 :" + info_temp.toString());
					}
				}

				if (info_temp.length() > 1) {
					for (int ii = startColumn; ii < columns; ii++) {
						Cell c_temp = sheet.getCell(ii, i);
						label = new Label(ii, wi, c_temp.getContents().trim());
						wcf = new WritableCellFormat();
						if (wj[ii])
							wcf.setBackground(Colour.RED);
						label.setCellFormat(wcf);
						writes.addCell(label);
					}
					label = new Label(columns, wi, info_temp.toString());
					WritableFont wf = new WritableFont(WritableFont.TIMES, 12);
					wf.setColour(Colour.RED);
					wcf = new WritableCellFormat(wf);
					label.setCellFormat(wcf);
					writes.addCell(label);
					wi++;
				} else {
					this.save();
					successcount++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			errors.add("导入失败");
			return false;
		} finally {
			try {
				writew.write();
				writew.close();
				work.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (successcount < rows - 2) {
			this.hasError = true;
		}
		return true;
	}

	/**
	 * 全局验证，验证对行数和列数的要求
	 * 
	 * @return
	 */
	public boolean validGlobal() {
		if (minRows != -1 && rows < minRows) {
			return false;
		} else if (minColumns != -1 && columns < minColumns) {
			return false;
		} else if (maxRows != -1 && rows > maxRows) {
			return false;
		} else if (maxColumns != -1 && columns > maxColumns) {
			return false;
		}
		return true;
	}

	public abstract boolean save();

	/**
	 * 初始化存储验证列方法的Map
	 * 
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	@SuppressWarnings("unchecked")
	private void setMethodMap() throws SecurityException, NoSuchMethodException {
		methodMap = new HashMap<Integer, Method>();
		if (columnMethods == null) {
			Method[] methods = this.getClass().getMethods();
			for (int i = 0; i < methods.length; i++) {
				if (methods[i].getName().startsWith("validColumn_")) {
					String column = methods[i].getName().substring(
							methods[i].getName().indexOf("_") + 1);
					try {
						methodMap.put(Integer.parseInt(column), methods[i]);
					} catch (Exception e) {
						throw new NumberFormatException("默认列明必须为数字");
					}

				}
				;
			}
		} else {
			Class<ImportData> class1 = (Class<ImportData>) this.getClass();
			for (int i = 0; i < columnMethods.length; i++) {
				methodMap.put(i, class1.getMethod(columnMethods[i], null));
			}
		}
	}

	/**
	 * 初始化存储保留列的Map，保留列用于验证某些列值时需引用其他列的情况
	 */
	private void setHoldColumns() {
		holdColumns = new HashMap<Integer, List<String>>();
		if (needHoldColumns == null) {
			return;
		}
		for (int i = 0; i < needHoldColumns.length; i++) {
			holdColumns.put(needHoldColumns[i], new ArrayList<String>());
		}
	}

	/**
	 * 获得给定单元格的实际值，对于时间会返回 'yyyy-MM-dd HH:mm:ss' 格式的字符串
	 * 
	 * @param cell
	 * @return String
	 */
	public static String getCellValue(Cell cell) {
		if (cell.getType().equals(CellType.NUMBER)) {
			return Double.toString(((NumberCell) cell).getValue());
		} else if (cell.getType().equals(CellType.DATE)) {
			TimeZone gmt = TimeZone.getTimeZone("GMT");
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
					Locale.getDefault());
			dateFormat.setTimeZone(gmt);
			return dateFormat.format(((DateCell) cell).getDate());
		} else if (cell.getType().equals(CellType.EMPTY)) {
			return null;
		} else {
			return cell.getContents().trim();
		}
	}

	/**
	 * 防止空指针
	 * 
	 * @param object
	 * @return String
	 */
	public static String fixNull(Object object) {
		return object == null ? "" : object.toString();
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public int getMinRows() {
		return minRows;
	}

	public void setMinRows(int minRows) {
		this.minRows = minRows;
	}

	public int getMinColumns() {
		return minColumns;
	}

	public void setMinColumns(int minColumns) {
		this.minColumns = minColumns;
	}

	public int getMaxRows() {
		return maxRows;
	}

	public void setMaxRows(int maxRows) {
		this.maxRows = maxRows;
	}

	public int getMaxColumns() {
		return maxColumns;
	}

	public void setMaxColumns(int maxColumns) {
		this.maxColumns = maxColumns;
	}

	public String[] getColumnMethods() {
		return columnMethods;
	}

	public void setColumnMethods(String[] columnMethods) {
		this.columnMethods = columnMethods;
	}

	public File getImportExcel() {
		return importExcel;
	}

	public void setImportExcel(File importExcel) {
		this.importExcel = importExcel;
	}

	public File getErrorExcel() {
		return errorExcel;
	}

	public void setErrorExcel(File errorExcel) {
		this.errorExcel = errorExcel;
	}

	public boolean isHasError() {
		return hasError;
	}

	public int[] getNeedHoldColumns() {
		return needHoldColumns;
	}

	public void setNeedHoldColumns(int[] needHoldColumns) {
		this.needHoldColumns = needHoldColumns;
	}

	public Map<Integer, List<String>> getHoldColumns() {
		return holdColumns;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getStartColumn() {
		return startColumn;
	}

	public void setStartColumn(int startColumn) {
		this.startColumn = startColumn;
	}

	public Cell getCurCell() {
		return curCell;
	}

	public List<String> getErrors() {
		return errors;
	}

	public Cell[] getCurRowCells() {
		return curRowCells;
	}

}
