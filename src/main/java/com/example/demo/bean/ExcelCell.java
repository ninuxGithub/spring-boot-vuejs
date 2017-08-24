package com.example.demo.bean;

public class ExcelCell {

	/**
	 * cell 标题
	 */
	private String title;

	/**
	 * 跨的行
	 */
	private int rowspan;

	/**
	 * 跨的列
	 */
	private int colspan;

	/**
	 * 出现在第几个单元格（脚本从0开始）， 在构造表格的时候需要规划好细节，比较繁琐，也没有更好的方法了
	 */
	private int colIndex;

	public ExcelCell() {
	}

	public ExcelCell(String title, int rowspan, int colspan) {
		this.title = title;
		this.rowspan = rowspan;
		this.colspan = colspan;
	}

	public ExcelCell(String title, int rowspan, int colspan, int colIndex) {
		this.title = title;
		this.rowspan = rowspan;
		this.colspan = colspan;
		this.colIndex = colIndex;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getRowspan() {
		return rowspan;
	}

	public void setRowspan(int rowspan) {
		this.rowspan = rowspan;
	}

	public int getColspan() {
		return colspan;
	}

	public void setColspan(int colspan) {
		this.colspan = colspan;
	}

	public int getColIndex() {
		return colIndex;
	}

	public void setColIndex(int colIndex) {
		this.colIndex = colIndex;
	}

	@Override
	public String toString() {
		return "ExcelCell [title=" + title + ", rowspan=" + rowspan + ", colspan=" + colspan + "]";
	}

}
