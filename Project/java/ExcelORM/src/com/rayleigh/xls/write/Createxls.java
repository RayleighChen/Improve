package com.rayleigh.xls.write;

import java.io.File;
import java.io.IOException;
import java.sql.Date;

import jxl.Workbook;
import jxl.format.PageOrientation;
import jxl.format.PaperSize;
import jxl.format.UnderlineStyle;
import jxl.write.Border;
import jxl.write.BorderLineStyle;
import jxl.write.DateFormat;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class Createxls {
	public Createxls() {
	}

	public static void main(String[] args) {
		WritableWorkbook myexcel = null;
		try {
			myexcel = Workbook.createWorkbook(new File("我的EXCEL.xls "));
		} catch (IOException ex) {
		}

		WritableSheet mysheet = myexcel.createSheet("第一页 ", 0);

		// 合并单元格
		try {
			mysheet.mergeCells(10, 10, 13, 13);
		} catch (WriteException ex4) {
		}

		// 设置打印属性
		// 第一个参数为方向，第二个为纸张大小，第三个为设置页眉的高度，第四个设置页脚的高度
		mysheet.setPageSetup(PageOrientation.LANDSCAPE, PaperSize.A5, 1.5, 2.0);

		Label mylabel = new Label(0, 0, "wuwenjun ");
		WritableFont font = new WritableFont(WritableFont.TIMES, 18,
				WritableFont.BOLD, true, UnderlineStyle.NO_UNDERLINE,
				jxl.format.Colour.RED);
		WritableCellFormat wcf = new WritableCellFormat(font);
		Label label = new Label(3, 0, "彩色字 ", wcf);

		// 设置日期格式
		jxl.write.DateFormat df = new DateFormat("yyyy-mm-dd ");
		jxl.write.WritableCellFormat wcfD = new jxl.write.WritableCellFormat(df);
		Date dd = new Date(System.currentTimeMillis());
		DateTime mydate = new DateTime(5, 5, dd, wcfD);

		// //定义样式
		// WritableCellFeatures ewcf=new WritableCellFeatures();
		// ewcf.setComment( "wuwenjun ");
		// WritableCellFormat ecf=new WritableCellFormat();
		// try {
		// ecf.setBackground(Colour.RED);
		// } catch (WriteException ex3) {
		// }

		// //blank
		// jxl.write.Blank blank=new Blank(6,6);
		// blank.setCellFeatures(ewcf);
		// blank.setCellFormat(ecf);
		//
		// //emptycell
		// EmptyCell emptycell=new EmptyCell(7,7);
		// emptycell.setCellFeatures(ewcf);
		// emptycell.setCellFormat(ecf);

		// 设置数字格式
		jxl.write.NumberFormat nf = new jxl.write.NumberFormat(
				"###,###,###,###,###.## ");
		jxl.write.WritableCellFormat wcfN = new jxl.write.WritableCellFormat(nf);
		// 设置单元格的边框样式
		try {
			wcfN.setBorder(Border.ALL, BorderLineStyle.THIN);
		} catch (WriteException ex1) {
		}

		Number mynumber = new Number(3, 3, 56566454.1212121, wcfN);
		try {
			mysheet.addCell(mylabel);
			mysheet.addCell(mynumber);
			mysheet.addCell(label);
			mysheet.addCell(mydate);

			myexcel.write();
			if (myexcel != null)
				myexcel.close();
		} catch (WriteException ex2) {
		} catch (IOException ex2) {
		}
	}
}
