package com.example.demo.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.bean.ExcelCell;
import com.example.demo.bean.ExcelRow;
import com.example.demo.bean.Order;
import com.example.demo.bean.User;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.ExcelUtil;
import com.example.demo.utils.JsonUtil;

/**
 * @author shenzm
 *
 */
@Controller
public class ExportExcelController implements ExcelHeaderData {

	private static final Logger logger = LoggerFactory.getLogger(ExportExcelController.class);

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = "/exportExcel", method = RequestMethod.GET)
	public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.info("开始导出Excel....");
		List<Order> orders = orderRepository.findAll();
		List<User> users = userRepository.findAll();
		try {
			HSSFWorkbook wb = new HSSFWorkbook();

			HSSFSheet sheet = wb.createSheet("风控报表1");

			sheet.setDefaultColumnWidth(15);// 15个字符
			
			int marginLeft=1;

			ExcelUtil.drawExcel(orders, wb, sheet, Order.class);

			ExcelUtil.increaseRow();// 换行//每个填充之间加入一个空行
			ExcelUtil.increaseRow();// 换行//每个填充之间加入一个空行

			//创建表头
			ExcelUtil.buildTableHeader(wb, sheet, this, ExcelUtil.getLocalRow());
			
			ExcelUtil.increaseRow();// 换行//每个填充之间加入一个空行
			
			ExcelUtil.buildTableHeader(wb, sheet, this, ExcelUtil.getLocalRow(), marginLeft);

			// #####################################
			// drawExcelHeader(sheet, head0, head1, row, titleStyle);
			// #####################################
			
			
			ExcelUtil.increaseRow();// 换行//每个填充之间加入一个空行

			ExcelUtil.drawExcel(users, wb, sheet, User.class);

			ExcelUtil.increaseRow();// 换行//每个填充之间加入一个空行
			
			ExcelUtil.drawExcel(users, wb, sheet, User.class, marginLeft);
			// 锁住第一列
			ExcelUtil.freezeFirstRow(sheet);

			// 获取输出了，设置excel名称
			ServletOutputStream outputStream = ExcelUtil.getExcleOutputStream(response, "风控报表1");

			// 写入到响应刘
			wb.write(outputStream);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}

	}


	@Override
	public List<ExcelRow> buildHeaderDataList() {
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
		cellList.add(new ExcelCell("净值损线2", 1, 1, 7));
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
		cellList.add(new ExcelCell("今年以来", 1, 1, 7));
		excelRow.setRows(cellList);
		headerRows.add(excelRow);
		String json = JsonUtil.toJson(headerRows);
		System.out.println(json);
		return headerRows;
	}
	
	/**
	 * 
	 * 最原始的构造表头的方法
	 * @param sheet
	 * @param head0
	 * @param head1
	 * @param row
	 * @param titleStyle
	 */
	public void drawExcelHeader(HSSFSheet sheet, String[] head0, String[] head1, Row row, HSSFCellStyle titleStyle) {
		Cell cell;
		CellRangeAddress region;
		cell = row.createCell(0);
		cell.setCellValue(head0[0]);
		cell.setCellStyle(titleStyle);
		
		cell = row.createCell(1);
		cell.setCellValue(head0[1]);
		cell.setCellStyle(titleStyle);
		
		cell = row.createCell(2);
		cell.setCellValue(head0[2]);
		cell.setCellStyle(titleStyle);
		
		cell = row.createCell(3);
		cell.setCellStyle(titleStyle);
		
		cell = row.createCell(4);
		cell.setCellValue(head0[3]);
		cell.setCellStyle(titleStyle);
		
		cell = row.createCell(5);
		cell.setCellStyle(titleStyle);
		
		cell = row.createCell(6);
		cell.setCellValue(head0[4]);
		cell.setCellStyle(titleStyle);
		
		region = new CellRangeAddress(ExcelUtil.getLocalRow(), ExcelUtil.getLocalRow() + 1, (short) 0, (short) 0);
		sheet.addMergedRegion(region);
		
		region = new CellRangeAddress(ExcelUtil.getLocalRow(), ExcelUtil.getLocalRow() + 1, (short) 1, (short) 1);
		sheet.addMergedRegion(region);
		
		region = new CellRangeAddress(ExcelUtil.getLocalRow(), ExcelUtil.getLocalRow(), (short) 2, (short) 3);
		sheet.addMergedRegion(region);
		
		region = new CellRangeAddress(ExcelUtil.getLocalRow(), ExcelUtil.getLocalRow(), (short) 4, (short) 5);
		sheet.addMergedRegion(region);
		
		region = new CellRangeAddress(ExcelUtil.getLocalRow(), ExcelUtil.getLocalRow() + 1, (short) 6, (short) 6);
		sheet.addMergedRegion(region);
		
		ExcelUtil.increaseRow();// 换行//每个填充之间加入一个空行
		row = sheet.createRow(ExcelUtil.getLocalRow());
		
		cell = row.createCell(0);
		cell.setCellStyle(titleStyle);
		
		cell = row.createCell(1);
		cell.setCellStyle(titleStyle);
		
		cell = row.createCell(2);
		cell.setCellValue(head1[0]);
		cell.setCellStyle(titleStyle);
		
		cell = row.createCell(3);
		cell.setCellValue(head1[1]);
		cell.setCellStyle(titleStyle);
		
		cell = row.createCell(4);
		cell.setCellValue(head1[2]);
		cell.setCellStyle(titleStyle);
		
		cell = row.createCell(5);
		cell.setCellValue(head1[3]);
		cell.setCellStyle(titleStyle);
		
		cell = row.createCell(6);
		cell.setCellStyle(titleStyle);
	}


}
