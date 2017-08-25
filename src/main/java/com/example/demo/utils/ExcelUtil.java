package com.example.demo.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.bean.ExcelCell;
import com.example.demo.bean.ExcelField;
import com.example.demo.bean.ExcelFieldBean;
import com.example.demo.bean.ExcelRow;
import com.example.demo.controller.ExcelHeaderData;

public class ExcelUtil {

	private static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

	private static final String DEFAULTVALUE = "--";

	/**
	 * 当前的行数
	 */
	private static ThreadLocal<Integer> localRow = new ThreadLocal<Integer>() {

		@Override
		protected Integer initialValue() {
			return 0;
		}

	};

	/**
	 * 获取当前wb填充到哪一行了
	 * 
	 * @return
	 */
	public static Integer getLocalRow() {
		return localRow.get();
	}

	/**
	 * excel行数往下退一行，行数递增
	 */
	public static void increaseRow() {
		Integer row = localRow.get();
		localRow.set(row + 1);
	}

	/**
	 * 业务场景：多个表格导出到同一个sheet 功能：填充sheet的核心部分
	 * 
	 * @param list
	 *            数据list
	 * @param wb
	 *            excel 工作簿
	 * @param sheet
	 *            excle sheet
	 * @param fields
	 *            javaBean 属性字段
	 * @param titles
	 *            表格标题
	 */
	public static <T> void drawExcel(List<T> list, HSSFWorkbook wb, HSSFSheet sheet, Class<?> clazz) {

		logger.info("===>开启填充 {} sheet....", clazz.getName());

		List<ExcelFieldBean> excleFieldBeanList = analyseExcelField(clazz);

		Row row = null;
		Cell cell = null;
		row = sheet.createRow(getLocalRow());
		HSSFCellStyle titleStyle = titleStyle(wb);
		// 填充表头，不考虑复杂表头的情况
		for (int t = 0; t < excleFieldBeanList.size(); t++) {
			cell = row.createCell(t);
			cell.setCellValue(excleFieldBeanList.get(t).getTitle());
			cell.setCellStyle(titleStyle);
		}
		// 画完标题后行号+1
		increaseRow();

		for (int i = 0; i < list.size(); i++) {
			row = sheet.createRow(getLocalRow());
			increaseRow();

			for (int f = 0; f < excleFieldBeanList.size(); f++) {
				cell = row.createCell(f);
				ExcelFieldBean excelFieldBean = excleFieldBeanList.get(f);
				String field = excelFieldBean.getFiled();
				String datePattern = excelFieldBean.getDatePattern();
				String numberPattern = excelFieldBean.getNumberPattern();
				short textAlign = excelFieldBean.getTextAlign();
				Class<?> filedClazz = excelFieldBean.getFiledClazz();

				Object value = ReflectUtil.getTypeField(list.get(i), field);
				if (null == value) {
					value = ExcelUtil.DEFAULTVALUE;
				} else {
					if (filedClazz == Date.class) {
						value = DateUtil.formatDate(value, datePattern);
					} else if (filedClazz.isAssignableFrom(Number.class) || filedClazz.isAssignableFrom(Double.class)) {
						value = NumberUtil.formatNumber(value, numberPattern);
					}
				}
				cell.setCellValue(value.toString());
				HSSFCellStyle bodyStyle = bodyStyle(wb);
				bodyStyle.setAlignment(textAlign);
				cell.setCellStyle(bodyStyle);
			}
		}

	}

	/**
	 * 分析javaBean 中的属性是否采用了@ExcelField注解，如果含有该注解，提取注解信息
	 * 
	 * @param clazz
	 * @return
	 */
	public static List<ExcelFieldBean> analyseExcelField(Class<?> clazz) {
		List<ExcelFieldBean> excleFieldBeanList = new ArrayList<>();
		for (Class<?> c = clazz; c != Object.class; c = c.getSuperclass()) {
			Field[] declaredFields = c.getDeclaredFields();
			for (Field field : declaredFields) {
				if (field.isAnnotationPresent(ExcelField.class)) {
					// 获取字段的注解对象
					ExcelField excelField = field.getAnnotation(ExcelField.class);
					ExcelFieldBean excelFieldBean = new ExcelFieldBean(excelField.title(), excelField.order(),
							field.getName(), field.getType());
					// 数字格式化
					excelFieldBean.setNumberPattern(excelField.numberPattern());
					// 日期格式化
					excelFieldBean.setDatePattern(excelField.datePattern());
					// 文字居中
					excelFieldBean.setTextAlign(excelField.textAlign());
					excleFieldBeanList.add(excelFieldBean);
				}
			}
		}

		// 排序
		Collections.sort(excleFieldBeanList);
		return excleFieldBeanList;
	}

	public static ServletOutputStream getExcleOutputStream(HttpServletResponse response, String excleName)
			throws UnsupportedEncodingException, IOException {
		response.setContentType("application/vnd.ms-excel");
		String codedFileName = java.net.URLEncoder.encode(excleName, "UTF-8");
		response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xls");
		ServletOutputStream outputStream = response.getOutputStream();
		return outputStream;
	}

	/***
	 * 表格体字体样式
	 * 
	 * @param wb
	 * @return
	 */
	public static HSSFCellStyle bodyStyle(HSSFWorkbook wb) {
		HSSFCellStyle style = wb.createCellStyle();
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		return style;
	}

	/**
	 * excle表格的表头样式
	 * 
	 * @param wb
	 * @return
	 */
	public static HSSFCellStyle titleStyle(HSSFWorkbook wb) {
		HSSFCellStyle style = wb.createCellStyle();
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);

		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style.setFont(font);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		return style;
	}

	/**
	 * 锁定第一列
	 * 
	 * @param sheet
	 */
	public static void freezeFirstColumn(HSSFSheet sheet) {
		sheet.createFreezePane(1, 0, 1, 0);
	}

	/**
	 * 锁定第一行
	 * 
	 * @param sheet
	 */
	public static void freezeFirstRow(HSSFSheet sheet) {
		sheet.createFreezePane(0, 1, 0, 1);
	}

	// ====================如果是不规则的表格的表头====================================

	public static void buildTableHeader(HSSFWorkbook wb, HSSFSheet sheet, ExcelHeaderData excelHeaderData, int rownum) {
		List<ExcelRow> headerRows = excelHeaderData.buildHeaderDataList();
		int totalCols = getTotalCols(headerRows);
		logger.trace("totalColspan :" + totalCols);

		HSSFCellStyle titleStyle = ExcelUtil.titleStyle(wb);
		for (int r = 0; r < headerRows.size(); r++) {
			Row row = null;
			Cell cell = null;
			row = sheet.createRow(rownum + r);
			increaseRow();//增加行
			ExcelRow excelRow = headerRows.get(r);
			LinkedList<ExcelCell> rows = excelRow.getRows();
			for (int c = 0; c < totalCols; c++) {
				ExcelCell excelCell = rows.get(c);
				int colspan = excelCell.getColspan();
				int rowspan = excelCell.getRowspan();
				String title = excelCell.getTitle();
				int colIndex = excelCell.getColIndex();

				cell = row.createCell(c);
				cell.setCellStyle(titleStyle);
				if (colIndex == c) {
					if(null != title){
						cell.setCellValue(title);
					}
					if (rowspan > 1 || colspan > 1) {
						CellRangeAddress region = new CellRangeAddress(rownum + r, rownum + r + rowspan - 1, c, c + colspan - 1);
						sheet.addMergedRegion(region);
					}
				}
			}
		}
	}

	/**
	 * @param headerRows
	 *            不规则表头一共有多少行
	 * @return
	 */
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

	/**
	 * @param wb
	 * @param sheet
	 * @param excelHeaderData
	 * @param rownum
	 * @param marginLeft 将表格整体往右移动 marginLeft 个单元格
	 */
	public static void buildTableHeader(HSSFWorkbook wb, HSSFSheet sheet, ExcelHeaderData excelHeaderData, Integer rownum, int marginLeft) {
		List<ExcelRow> headerRows = excelHeaderData.buildHeaderDataList();
		int totalCols = getTotalCols(headerRows);
		logger.trace("totalColspan :" + totalCols);

		HSSFCellStyle titleStyle = ExcelUtil.titleStyle(wb);
		for (int r = 0; r < headerRows.size(); r++) {
			Row row = null;
			Cell cell = null;
			row = sheet.createRow(rownum + r);
			increaseRow();//增加行
			ExcelRow excelRow = headerRows.get(r);
			LinkedList<ExcelCell> rows = excelRow.getRows();
			for (int c = 0; c < totalCols; c++) {
				ExcelCell excelCell = rows.get(c);
				int colspan = excelCell.getColspan();
				int rowspan = excelCell.getRowspan();
				String title = excelCell.getTitle();
				int colIndex = excelCell.getColIndex();
				
				int updateColumn = c + marginLeft;
				cell = row.createCell(updateColumn);
				cell.setCellStyle(titleStyle);
				if (colIndex == c) {
					if(null != title){
						cell.setCellValue(title);
					}
					if (rowspan > 1 || colspan > 1) {
						CellRangeAddress region = new CellRangeAddress(rownum + r, rownum + r + rowspan - 1, updateColumn, updateColumn + colspan - 1);
						sheet.addMergedRegion(region);
					}
				}
			}
		}
		
	}

	/**
	 * @param list 数据集合
	 * @param wb excel工作簿
	 * @param sheet excel sheet
	 * @param clazz List 数封装的Java 类型
	 * @param marginLeft 将表格整体往右边一定marginLeft个单元格
	 */
	public static <T> void drawExcel(List<T> list, HSSFWorkbook wb, HSSFSheet sheet, Class<?> clazz, int marginLeft) {
		logger.info("===>开启填充 {} sheet....", clazz.getName());

		List<ExcelFieldBean> excleFieldBeanList = analyseExcelField(clazz);

		Row row = null;
		Cell cell = null;
		row = sheet.createRow(getLocalRow());
		HSSFCellStyle titleStyle = titleStyle(wb);
		// 填充表头，不考虑复杂表头的情况
		for (int t = 0; t < excleFieldBeanList.size(); t++) {
			int updateColumn = marginLeft + t;
			cell = row.createCell(updateColumn);
			cell.setCellValue(excleFieldBeanList.get(t).getTitle());
			cell.setCellStyle(titleStyle);
		}
		// 画完标题后行号+1
		increaseRow();

		for (int i = 0; i < list.size(); i++) {
			row = sheet.createRow(getLocalRow());
			increaseRow();

			for (int f = 0; f < excleFieldBeanList.size(); f++) {
				int updateColumn = marginLeft + f;
				cell = row.createCell(updateColumn);
				ExcelFieldBean excelFieldBean = excleFieldBeanList.get(f);
				String field = excelFieldBean.getFiled();
				String datePattern = excelFieldBean.getDatePattern();
				String numberPattern = excelFieldBean.getNumberPattern();
				short textAlign = excelFieldBean.getTextAlign();
				Class<?> filedClazz = excelFieldBean.getFiledClazz();

				Object value = ReflectUtil.getTypeField(list.get(i), field);
				if (null == value) {
					value = ExcelUtil.DEFAULTVALUE;
				} else {
					if (filedClazz == Date.class) {
						value = DateUtil.formatDate(value, datePattern);
					} else if (filedClazz.isAssignableFrom(Number.class) || filedClazz.isAssignableFrom(Double.class)) {
						value = NumberUtil.formatNumber(value, numberPattern);
					}
				}
				cell.setCellValue(value.toString());
				HSSFCellStyle bodyStyle = bodyStyle(wb);
				bodyStyle.setAlignment(textAlign);
				cell.setCellStyle(bodyStyle);
			}
		}
		
	}

}
