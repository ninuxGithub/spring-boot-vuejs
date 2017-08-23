package com.example.demo.bean;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.poi.ss.usermodel.CellStyle;

/**
 * 对加入了该注解的JavaBean进行导出，进行一定规则的格式化：加入日期格式化， 数字格式化，文字居中的自定义的属性
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
public @interface ExcelField {
	/**
	 * 表头的标题
	 */
	String title() default "";

	/**
	 * 在表格中的顺序
	 */
	int order() default 10000;

	/**
	 * 日期格式化的格式
	 */
	String datePattern() default "yyyy-MM-dd";

	/**
	 * excel 内容文字的居中
	 */
	short textAlign() default CellStyle.ALIGN_CENTER;

	/**
	 * 数字类型的格式化
	 */
	String numberPattern() default "#,##0.000";
}
