package com.example.demo.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.bean.Order;
import com.example.demo.bean.User;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;

@Controller
public class ExportExcelController {

	private static final Logger logger = LoggerFactory.getLogger(ExportExcelController.class);

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = "/exportExcel", method = RequestMethod.GET)
	public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {

		List<Order> orders = orderRepository.findAll();
		
		List<User> users = userRepository.findAll();

		try {

			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("风控报表1");
			sheet.setDefaultColumnWidth(15);// 15个字符
			// 表格体字体样式
			HSSFCellStyle style = wb.createCellStyle();
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			// 表格标题字体样式
			HSSFCellStyle titleStyle = wb.createCellStyle();
			titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
			titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			HSSFFont font = wb.createFont();
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			titleStyle.setFont(font);

			int currentRow = 0;
			String[] fields = new String[] { "orderId", "produceName", "productDate", "qualityGuaranteePeriod" };
			String[] titles = new String[] { "订单ID", "产品名称", "生产日期", "保质期" };
			
			currentRow = drawExcel(orders, sheet, style, titleStyle, currentRow, fields, titles);
			logger.info("==>current row is : {}", currentRow);
			
			
			currentRow+=1;
			String[] fields2 = new String[] { "id", "username", "password", "role" };
			String[] titles2 = new String[] { "用户ID", "用户名称", "密码", "角色" };
			drawExcel(users, sheet, style, titleStyle, currentRow, fields2, titles2);
			
			logger.info("==>current row is : {}", currentRow);
			
			
			// 锁住第一列
			sheet.createFreezePane(1, 0, 1, 0);
			// 锁住第一行
			sheet.createFreezePane(0, 1, 0, 1);
			response.setContentType("application/vnd.ms-excel");
			String codedFileName = java.net.URLEncoder.encode("风控报表1", "UTF-8");
			response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xls");
			ServletOutputStream outputStream = response.getOutputStream();
			wb.write(outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param list
	 * @param sheet
	 * @param style
	 * @param currentRow
	 */
	private<T> int drawExcel(List<T> list, HSSFSheet sheet, HSSFCellStyle style, HSSFCellStyle titleStyle,
			int currentRow, String[] fields, String[] titles) {
		Row row = null;
		Cell cell = null;

		row = sheet.createRow(currentRow);
		for (int f = 0; f < fields.length; f++) {
			cell = row.createCell(f);
			cell.setCellValue(titles[f]);
			cell.setCellStyle(titleStyle);
		}
		currentRow += 1;// 画完标题后行号+1

		for (int i = 0; i < list.size(); i++) {
			row = sheet.createRow(currentRow);
			currentRow +=1;

			for (int f = 0; f < fields.length; f++) {
				cell = row.createCell(f);
				String field = fields[f];
				Object value = ReflectUtil.getTypeField(list.get(i), field);
				if (null == value) {
					value = "--";
				} else if (value instanceof Date) {
					value = DateUtil.getDateFormate().format((Date) value);
				}
				cell.setCellValue(value.toString());
				cell.setCellStyle(style);
			}
		}
		
		return currentRow;

	}

}
