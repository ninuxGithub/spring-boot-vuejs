package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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

			ExcelUtil.drawExcel(users, wb, sheet, User.class);

			// 锁住第一列
			ExcelUtil.freezeFirstRow(sheet);

			// 获取输出了，设置excel名称
			ServletOutputStream outputStream = ExcelUtil.getExcleOutputStream(response, "风控报表1");
			
			//写入到响应刘
			wb.write(outputStream);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}

}
