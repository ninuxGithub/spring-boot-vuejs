package com.example.demo.bean;

public class ExcelCell {

	private String title;

	private int rowspan;

	private int colspan;

	public ExcelCell() {
	}

	public ExcelCell(String title, int rowspan, int colspan) {
		this.title = title;
		this.rowspan = rowspan;
		this.colspan = colspan;
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

	@Override
	public String toString() {
		return "ExcelCell [title=" + title + ", rowspan=" + rowspan + ", colspan=" + colspan + "]";
	}

}
