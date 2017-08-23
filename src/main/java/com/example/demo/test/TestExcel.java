package com.example.demo.test;


import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

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
		int rowNum =0;
		for (int i = 0; i < 25; i++) {
			row = sheet.createRow(i);
			rowNum=i;
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
		
		CellRangeAddress cra=new CellRangeAddress(rowNum+1, rowNum+3, 3, 9); 
		
        sheet.addMergedRegion(cra);  
          
        row = sheet.createRow(rowNum+1);  
          
        Cell cell_1 = row.createCell(3);  
          
        cell_1.setCellValue("When you're right , no one remembers, when you're wrong ,no one forgets .");  
          
        //cell 位置3-9被合并成一个单元格，不管你怎样创建第4个cell还是第5个cell…然后在写数据。都是无法写入的。  
        Cell cell_2 = row.createCell(10);  
          
        cell_2.setCellValue("what's up ! ");  
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
