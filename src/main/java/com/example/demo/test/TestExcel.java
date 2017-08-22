package com.example.demo.test;


import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class TestExcel {
	public static void main(String[] args) {

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("title");
		HSSFCellStyle style=wb.createCellStyle();
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		Row row = null;
		Cell cell = null;
		for (int i = 0; i < 25; i++) {
			row = sheet.createRow(i);
			for(int j=0; j<30;j++){
				if(i==0){
					cell = row.createCell(j);
					cell.setCellStyle(style);
					cell.setCellValue("第"+(j)+"列");
				}else{
					cell = row.createCell(j);
					cell.setCellStyle(style);
					cell.setCellValue(j);
				}
				
			}
		}
		//锁住第一列
		sheet.createFreezePane(1,0,1,0);
		//锁住第一行
		//sheet.createFreezePane(0,1,0,1);
		writeExcelToDisk("c:/demo.xls", wb);

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
