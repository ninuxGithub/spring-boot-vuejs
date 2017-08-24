package com.example.demo.controller;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.github.plugin.bean.ExcelCell;
import com.github.plugin.bean.ExcelRow;
import com.github.plugin.utils.ExcelUtil;
import com.github.plugin.utils.JsonUtil;


public class PluginText implements com.github.plugin.interfaces.ExcelHeaderData{

	public static void main(String[] args) {
		HSSFWorkbook wb = new HSSFWorkbook();

		HSSFSheet sheet = wb.createSheet("风控报表1");

		sheet.setDefaultColumnWidth(15);// 15个字符

		ExcelUtil.buildTableHeader(wb, sheet, new PluginText() , 2);
		
		
//		ExcelUtil.buildTableHeader(wb, sheet, buildHeaderDataList(), 2);

		writeExcelToDisk("c:/demo3.xls", wb);

	}

//	public static void buildTableHeader(HSSFWorkbook wb, HSSFSheet sheet) {
//		List<ExcelRow> headerRows = buildHeaderDataList();
//		int totalCols = getTotalCols(headerRows);
//		System.out.println("totalColspan :" + totalCols);
//		int rownum = 0;
//
//		HSSFCellStyle titleStyle = ExcelUtil.titleStyle(wb);
//		for (int r = 0; r < headerRows.size(); r++) {
//			Row row = null;
//			Cell cell = null;
//			row = sheet.createRow(rownum + r);
//			ExcelRow excelRow = headerRows.get(r);
//			LinkedList<ExcelCell> rows = excelRow.getRows();
//			for (int c = 0; c < totalCols; c++) {
//				ExcelCell excelCell = rows.get(c);
//				int colspan = excelCell.getColspan();
//				int rowspan = excelCell.getRowspan();
//				String title = excelCell.getTitle();
//				title = title == null ? "" : title;
//				int colIndex = excelCell.getColIndex();
//
//				cell = row.createCell(c);
//				cell.setCellStyle(titleStyle);
//				if (colIndex == c) {
//					cell.setCellValue(title);
//					if (rowspan > 1 || colspan > 1) {
//						CellRangeAddress region = new CellRangeAddress(rownum + r, rownum + r + rowspan - 1, c,
//								c + colspan - 1);
//						sheet.addMergedRegion(region);
//					}
//				}
//			}
//		}
//	}

	@Override
	public  List<ExcelRow> buildHeaderDataList() {
		List<ExcelRow> headerRows = new LinkedList<>();
		ExcelRow excelRow = new ExcelRow();
		LinkedList<ExcelCell> cellList = new LinkedList<>();

		// 第一行
		cellList.add(new ExcelCell("日期", 2, 1, 0));
		cellList.add(new ExcelCell("组合名称", 2, 1, 1));
		cellList.add(new ExcelCell("组合收益率", 1, 2, 2));
		cellList.add(new ExcelCell(null, 0, 0, 3));
		cellList.add(new ExcelCell("业绩基准", 1, 2, 4));
		cellList.add(new ExcelCell(null, 0, 0, 5));
		cellList.add(new ExcelCell("净值损线", 2, 1, 6));
		excelRow.setRows(cellList);
		headerRows.add(excelRow);

		// 第二行
		excelRow = new ExcelRow();
		cellList = new LinkedList<>();
		cellList.add(new ExcelCell(null, 0, 0, 0));
		cellList.add(new ExcelCell(null, 0, 0, 1));
		cellList.add(new ExcelCell("本期", 1, 1, 2));
		cellList.add(new ExcelCell("今年以来", 1, 1, 3));
		cellList.add(new ExcelCell("本期", 1, 1, 4));
		cellList.add(new ExcelCell("今年以来", 1, 1, 5));
		cellList.add(new ExcelCell(null, 0, 0, 6));
		excelRow.setRows(cellList);
		headerRows.add(excelRow);
		String json = JsonUtil.toJson(headerRows);
		System.out.println(json);
		return headerRows;
	}

	public static int getTotalCols(List<ExcelRow> headerRows) {
		List<Integer> list = new ArrayList<>();
		for (ExcelRow excelRow : headerRows) {
			int total = 0;
			for (ExcelCell excelCell : excelRow.getRows()) {
				total += excelCell.getColspan();
			}
			list.add(total);
		}
		return Collections.max(list);
	}

	private static void writeExcelToDisk(String filePath, HSSFWorkbook wb) {
		try {
			FileOutputStream fout = new FileOutputStream(filePath);
			wb.write(fout);
			fout.close();
			System.out.println("excel已经导出到:" + filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
