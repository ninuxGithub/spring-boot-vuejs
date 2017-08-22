package com.example.demo.bean;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
}
