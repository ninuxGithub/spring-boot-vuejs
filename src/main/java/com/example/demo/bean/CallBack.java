package com.example.demo.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 分页回调对象
 * 
 * @param <T>
 */
public class CallBack<T> implements Serializable {

	private static final long serialVersionUID = 617483123363977175L;

	/**
	 * 所有的记录总数
	 */
	private Integer records;

	/**
	 * 当前的页数
	 */
	private Integer page;

	/**
	 * totalPage 总页数
	 */
	private Integer total;

	/**
	 * 每页的记录数量
	 */
	private List<T> rows;
	
	public CallBack(){}
	
	public CallBack(Integer records, Integer page, Integer total, List<T> rows) {
		this.records = records;
		this.page = page;
		this.total = total;
		this.rows = rows;
	}

	public Integer getRecords() {
		return records;
	}

	public void setRecords(Integer records) {
		this.records = records;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

}
