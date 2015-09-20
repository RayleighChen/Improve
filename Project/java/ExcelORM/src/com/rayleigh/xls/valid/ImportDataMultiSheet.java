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
import jxl.Range;
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
 * jxl导入excel类，继承类实现验证方法
 * 
 * @author gaosheng
 * 
 */
public abstract class ImportDataMultiSheet {

	private int startRow = 1;
	private int startColumn = 0;
	private int minRows = 1;
	private int minColumns = 1;
	private int maxRows = -1;
	private int maxColumns = -1;
	private Map<Integer, Method> methodMap;
	private Map<Integer, List<String>> holdColumns;
	private List<String>[] titiles;
	private List<MeregRange> meregRangeList;
	private Cell curCell;
	private Cell[] curRowCells;
	private int successcount = 0;
	private List<Integer> viewWidthList;

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

		// 数据总行数
		int totalRows = 0;
		Sheet sheet = null;
		WritableWorkbook writew = null;
		WritableSheet writes = null;

		int sheet_num = work.getNumberOfSheets();
		// 全局验证
		if (!this.validGlobal(work.getSheets())) {
			throw new EntityException("导入文件格式错误");
		}
		try {
			writew = Workbook.createWorkbook(errorExcel);
			for (int sheet_index = 0; sheet_index < sheet_num; sheet_index++) {
				if (writes != null) {
					for (int i = 0; i < viewWidthList.size(); i++) {
						writes.setColumnView(i, viewWidthList.get(i) * 2 + 6);
					}
				}
				viewWidthList = new ArrayList<Integer>();
				sheet = work.getSheet(sheet_index);
				writes = writew.createSheet(sheet.getName(), sheet_index);
				meregRangeList = new ArrayList<MeregRange>();
				int columns = sheet.getColumns();
				int rows = sheet.getRows();
				System.out.println("行数: " + rows + "  列数: " + columns);
				totalRows += rows;

				for (Range range : sheet.getMergedCells()) {
					Cell topleft = range.getTopLeft();
					Cell bottomRight = range.getBottomRight();
					meregRangeList.add(new MeregRange(topleft.getRow(), topleft
							.getColumn(), bottomRight.getRow(), bottomRight
							.getColumn(), getCellValue(topleft)));
				}

				Label label;
				WritableFont wf0 = new WritableFont(WritableFont.TIMES, 12);
				WritableCellFormat wcf = new WritableCellFormat(wf0);
				try {
					wcf.setAlignment(jxl.format.Alignment.CENTRE);
				} catch (WriteException e) {
					e.printStackTrace();
				}

				int wi = startRow;
				titiles = new List[startRow];
				List<String> list = null;
				for (int i = 0; i < startRow; i++) {
					list = new ArrayList<String>();
					for (int j = 0; j < columns; j++) {
						String c_temp = getCellValue(sheet.getCell(j, i));
						if (viewWidthList.size() <= j
								|| viewWidthList.get(j) < c_temp.length()) {
							viewWidthList.add(j, c_temp.length());
						}
						label = new Label(j, i, c_temp);
						label.setCellFormat(wcf);
						writes.addCell(label);
						list.add(getValue(sheet.getCell(j, i)));
					}
					titiles[i] = list;
				}
				label = new Label(columns, startRow - 1, "错误详细");

				label.setCellFormat(wcf);
				writes.addCell(label);

				// -------------------------
				StringBuffer info_temp = null;
				String result = null;
				Method method = null;
				for (int i = startRow; i < rows; i++) {
					curRowCells = sheet.getRow(i);

					// System.out.println("当前行列数: "+curRowCells.length);

					if (curRowCells == null || curRowCells.length < minColumns) {
						continue;
					}
					boolean[] wj = new boolean[columns];
					info_temp = new StringBuffer();
					for (int j = startColumn; j < columns; j++) {
						curCell = sheet.getCell(j, i);

						// System.out.print(String.format("%-30.30s",
						// this.getValue(curCell))+"  ");
						result = everyCell();
						if (result == null) {
							method = methodMap.get(j);
							if (method == null) {
								continue;
							}
							result = (String) method.invoke(this, null);
						}
						if (result != null) {
							info_temp.append(result);
							info_temp.append(" | ");
							wj[j] = true;

						}
						if (holdColumns.get(j) != null) {
							holdColumns.get(j).add(this.getValue(curCell));
						}
					}

					// System.out.println();
					if (info_temp.length() > 1) {
						errors.add("sheet " + sheet.getName() + " 中第 "
								+ (i + 1) + " 行 :" + info_temp.toString());
						for (int ii = 0; ii < columns; ii++) {
							String c_temp = getCellValue(sheet.getCell(ii, i));
							if (viewWidthList.size() <= ii
									|| viewWidthList.get(ii) < c_temp.length()) {
								viewWidthList.add(ii, c_temp.length());
							}
							label = new Label(ii, wi, c_temp);
							wcf = new WritableCellFormat();
							if (wj[ii])
								wcf.setBackground(Colour.RED);
							try {
								wcf.setAlignment(jxl.format.Alignment.CENTRE);
							} catch (WriteException e) {
								e.printStackTrace();
							}
							label.setCellFormat(wcf);
							writes.addCell(label);
						}

						if (viewWidthList.size() <= columns
								|| viewWidthList.get(columns) < info_temp
										.toString().length()) {
							viewWidthList.add(columns, info_temp.toString()
									.length());
						}
						label = new Label(columns, wi, info_temp.toString());
						WritableFont wf = new WritableFont(WritableFont.TIMES,
								12);
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
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.hasError = true;
			errors.add("sheet " + sheet.getName() + " 第"
					+ this.curCell.getRow() + " 行 第 "
					+ this.curCell.getColumn() + " 列 :"
					+ this.getCurCell().getContents() + " 遇到错误");
			return false;
		} finally {
			try {
				if (writes != null) {
					for (int i = 0; i < viewWidthList.size(); i++) {
						writes.setColumnView(i, viewWidthList.get(i) * 2 + 6);
					}
				}
				writew.write();
				writew.close();
				work.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (successcount < totalRows - sheet_num * startRow) {
			System.out.println("导入情况: " + successcount + "  "
					+ (totalRows - sheet_num * startRow));
			this.hasError = true;
		}
		return true;
	}

	/**
	 * 全局验证，验证对行数和列数的要求
	 * 
	 * @return
	 */
	public boolean validGlobal(Sheet[] sheets) {
		for (int i = 0; i < sheets.length; i++) {
			if (minRows != -1 && sheets[i].getRows() < minRows) {
				return false;
			} else if (minColumns != -1 && sheets[i].getColumns() < minColumns) {
				return false;
			} else if (maxRows != -1 && sheets[i].getRows() > maxRows) {
				return false;
			} else if (maxColumns != -1 && sheets[i].getColumns() > maxColumns) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 一行数据验证成功后保存
	 * 
	 * @return boolean
	 */
	public abstract boolean save();

	/**
	 * 对每一个单元格进行的操作
	 * 
	 * @return boolean
	 */
	public abstract String everyCell();

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
			}
		} else {
			Class<ImportDataMultiSheet> class1 = (Class<ImportDataMultiSheet>) this
					.getClass();
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
			return "";
		} else {
			return cell.getContents().trim();
		}
	}

	public String getValue(Cell cell) {
		String value = getCellValue(cell);
		if (value == null || getCellValue(cell).equals("")) {
			for (MeregRange meregRange : meregRangeList) {
				if (meregRange.isInRange(cell.getRow(), cell.getColumn())) {
					return meregRange.getValue();
				}
			}
			return value;
		} else {
			return value;
		}
	}

	/**
	 * 防止空指针
	 * 
	 * @param object
	 * @return String
	 */
	public String fixNull(Object object) {
		return object == null ? "" : object.toString();
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

	public List<String>[] getTitiles() {
		return titiles;
	}

	public int getSuccesscount() {
		return successcount;
	}

}
