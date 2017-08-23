package com.example.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.example.demo.bean.Order;
import com.example.demo.bean.User;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.ExcelUtil;

@Controller
public class ExportExcelController {

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

			ExcelUtil.drawExcel(orders, wb, sheet, Order.class);

			ExcelUtil.increaseRow();// 换行//每个填充之间加入一个空行
			
//			for(int i=0; i<7; i++){
//				CellRangeAddress region = new CellRangeAddress(ExcelUtil.getLocalRow(), ExcelUtil.getLocalRow()+1 , (short) i, (short)i ); 
//				region.
//			}
			
			String[] head0 = new String[]{"日期","组合名称","组合收益率","业绩基准","净值损线"};
			String[] head1 = new String[]{"本期","今年以来","本期","今年以来"};
			
			Row row = null;
			Cell cell = null;
			HSSFCellStyle titleStyle = ExcelUtil.titleStyle(wb);
			row = sheet.createRow(ExcelUtil.getLocalRow());
			for(int i=0; i<7; i++){
				cell = row.createCell(i);
				cell.setCellValue(head0[i]);
				cell.setCellStyle(titleStyle);
			}
			
			
			ExcelUtil.increaseRow();// 换行//每个填充之间加入一个空行
			row = sheet.createRow(ExcelUtil.getLocalRow());
			Integer[] merges = new Integer[]{0,1,6};
			List<Integer> mergeList = Arrays.asList(merges);
			for(int i=0; i<7; i++){
				if(mergeList.contains(i)){
					
				}else{
					cell = row.createCell(i);
					cell.setCellValue(head1[i]);
				}
				cell.setCellStyle(titleStyle);
			}
			
			ExcelUtil.increaseRow();// 换行//每个填充之间加入一个空行

			ExcelUtil.drawExcel(users, wb, sheet, User.class);

			// 锁住第一列
			ExcelUtil.freezeFirstRow(sheet);

			// 获取输出了，设置excel名称
			ServletOutputStream outputStream = ExcelUtil.getExcleOutputStream(response, "风控报表1");
			
			//写入到响应刘
			wb.write(outputStream);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}

	}

}
