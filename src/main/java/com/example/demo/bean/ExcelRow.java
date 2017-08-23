package com.example.demo.bean;

import java.util.LinkedList;

/**
 * 
 * Excel的一行记录，里面可以包括对个excel单元格
 */
public class ExcelRow {

	private LinkedList<ExcelCell> rows;

	public LinkedList<ExcelCell> getRows() {
		return rows;
	}

	public void setRows(LinkedList<ExcelCell> rows) {
		this.rows = rows;
	}

	@Override
	public String toString() {
		return "ExcelRow [rows=" + rows + "]";
	}

}
