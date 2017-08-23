package com.example.demo.controller;

import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

import com.example.demo.bean.ExcelCell;
import com.example.demo.bean.ExcelRow;
import com.example.demo.utils.ExcelUtil;

public class PluginText {

	public static void main(String[] args) {
		HSSFWorkbook wb = new HSSFWorkbook();

		HSSFSheet sheet = wb.createSheet("风控报表1");

		sheet.setDefaultColumnWidth(15);// 15个字符

		String[] head0 = new String[] { "日期", "组合名称", "组合收益率", "业绩基准", "净值损线" };
		String[] head1 = new String[] { "本期", "今年以来", "本期2", "今年以来2" };

		Row row = null;
		Cell cell = null;
		HSSFCellStyle titleStyle = ExcelUtil.titleStyle(wb);
		CellRangeAddress region = null;
		ExcelUtil.increaseRow();// 换行//每个填充之间加入一个空行
		row = sheet.createRow(ExcelUtil.getLocalRow());

		List<ExcelRow> headerRows = new LinkedList<>();
		ExcelRow excelRow = new ExcelRow();
		LinkedList<ExcelCell> cellList = new LinkedList<>();
		// public ExcelCell(String title, int rowspan, int colspan)
		cellList.add(new ExcelCell("日期", 2, 1));
		cellList.add(new ExcelCell("组合名称", 2, 1));
		cellList.add(new ExcelCell("组合收益率", 1, 2));
		cellList.add(new ExcelCell("业绩基准", 1, 2));
		cellList.add(new ExcelCell("净值损线", 2, 1));
		excelRow.setRows(cellList);
		
		int rownum = 0;
		
		ExcelCell excelCell = null;
		
		row = sheet.createRow(rownum);
		
		
		int totalCols = getTotalCols(cellList);
		int totalRows = getTotalRows(cellList);
		
		for(int c = 0 ; c<totalCols; c++){
			for (int i = 0; i < cellList.size(); i++) {
				excelCell = cellList.get(i);
				int colspan = excelCell.getColspan();
				int rowspan = excelCell.getRowspan();
				String title = excelCell.getTitle();
				
				if(i==c){
					break;
				}
			}
		}
		
//		for (int i = 0; i < cellList.size(); i++) {
//			excelCell = cellList.get(i);
//			int colspan = excelCell.getColspan();
//			int rowspan = excelCell.getRowspan();
//			String title = excelCell.getTitle();
//			
//			if(colspan==1){
//				if(row.getCell(i)==null){
//					cell =row.createCell(i);
//				}
//				cell.setCellStyle(titleStyle);
//				cell.setCellValue(title);
//			}else {
//				for(int k =0; k<colspan; k++){
//					if(row.getCell(i)==null){
//						cell =row.createCell(i);
//					}
//					if(k==0){
//						cell.setCellValue(title);
//					}
//					cell.setCellStyle(titleStyle);
//				}
//				
//				sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, i, i+colspan));
//			}
//			
//
////			if (colspan == 1 && rowspan == 1) {
////				cell = row.createCell(i);
////				cell.setCellValue(title);
////				cell.setCellStyle(titleStyle);
////			} else if (colspan > 1 && rowspan == 1) {
////				for (int k = 0; k < colspan; k++) {
////					if(row == null){
////						row = sheet.createRow(rowNum);
////					}
////					Cell createCell = row.createCell(k+i);
////					if (k == 0) {
////						createCell.setCellValue(title);
////					}
////					createCell.setCellStyle(titleStyle);
////				}
////				
////				sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, i, i+colspan));
////				
////			} else if (colspan == 1 && rowspan > 1) {
////				for (int k = 0; k < rowspan; k++) {
////					HSSFRow createRow=sheet.createRow(k+rowNum);
////					cell = createRow.createCell(i);
////					cell.setCellStyle(titleStyle);
////					if (k == 0) {
////						cell.setCellValue(title);
////					}
////				}
////				
////				sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum+colspan, i, i));
////			}else if (colspan > 1 && rowspan > 1) {
////				for (int k = 0; k < rowspan; k++) {
////					row = sheet.getRow(rowNum+k);
////					cell = row.getCell(i);
////					if (k == 0) {
////						cell.setCellValue(title);
////					}
////					cell.setCellStyle(titleStyle);
////				}
////				
////				sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum+colspan, i, i+colspan));
////			}
//
//		}


		headerRows.add(excelRow);

		System.out.println(excelRow);

		excelRow = new ExcelRow();
		cellList = new LinkedList<>();
		cellList.add(new ExcelCell("本期", 1, 1));
		cellList.add(new ExcelCell("今年以来", 1, 1));
		cellList.add(new ExcelCell("本期", 1, 1));
		cellList.add(new ExcelCell("今年以来", 1, 1));
		excelRow.setRows(cellList);
		headerRows.add(excelRow);
		System.out.println(excelRow);

		writeExcelToDisk("c:/demo.xls", wb);

	}

	private static int getTotalCols(LinkedList<ExcelCell> cellList) {
		int total =0;
		for (ExcelCell excelCell : cellList) {
			total+=excelCell.getColspan();
		}
		return total;
	}
	
	private static int getTotalRows(LinkedList<ExcelCell> cellList) {
		int total =0;
		for (ExcelCell excelCell : cellList) {
			total+=excelCell.getRowspan();
		}
		return total;
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
