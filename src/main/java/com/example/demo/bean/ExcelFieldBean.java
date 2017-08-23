package com.example.demo.bean;

/**
 * 功能说明: 用来存储Excel标题的对象，通过该对象可以获取标题和方法的对应关系
 */
public class ExcelFieldBean implements Comparable<ExcelFieldBean> {
	/**
	 * excel的标题名称
	 */
	private String title;
	/**
	 * 每一个标题的顺序
	 */
	private int order;
	/**
	 * 注解域
	 */
	private String filed;
	/**
	 * 属性类型
	 */
	private Class<?> filedClazz;

	/**
	 * 日期格式化
	 */
	private String datePattern;

	/**
	 * 文字居中
	 */
	private short textAlign;

	/**
	 * 数字格式化
	 */
	private String numberPattern;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getFiled() {
		return filed;
	}

	public void setFiled(String filed) {
		this.filed = filed;
	}

	public Class<?> getFiledClazz() {
		return filedClazz;
	}

	public void setFiledClazz(Class<?> filedClazz) {
		this.filedClazz = filedClazz;
	}

	public int compareTo(ExcelFieldBean o) {
		return order - o.order;
	}
	
	

	public String getDatePattern() {
		return datePattern;
	}

	public void setDatePattern(String datePattern) {
		this.datePattern = datePattern;
	}

	public short getTextAlign() {
		return textAlign;
	}

	public void setTextAlign(short textAlign) {
		this.textAlign = textAlign;
	}

	public String getNumberPattern() {
		return numberPattern;
	}

	public void setNumberPattern(String numberPattern) {
		this.numberPattern = numberPattern;
	}

	public ExcelFieldBean(String title, int order, String filed, Class<?> filedClazz) {
		super();
		this.title = title;
		this.order = order;
		this.filed = filed;
		this.filedClazz = filedClazz;
	}
	
	

	public ExcelFieldBean(String title, int order, String filed, Class<?> filedClazz, String datePattern,
			short textAlign, String numberPattern) {
		super();
		this.title = title;
		this.order = order;
		this.filed = filed;
		this.filedClazz = filedClazz;
		this.datePattern = datePattern;
		this.textAlign = textAlign;
		this.numberPattern = numberPattern;
	}

	@Override
	public String toString() {
		return "ExcelFieldBean [title=" + title + ", order=" + order + ", filed=" + filed + ", filedClazz=" + filedClazz
				+ "]";
	}

}
