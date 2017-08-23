package com.example.demo.utils;

import java.text.SimpleDateFormat;

public class DateUtil {

	private static ThreadLocal<SimpleDateFormat> localTimeFormat = new ThreadLocal<SimpleDateFormat>() {

		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		}

	};

	/**
	 * 获取日期格式
	 */
	public static SimpleDateFormat getDateFormate() {
		return localTimeFormat.get();
	}

	public static Object formatDate(Object value, String datePattern) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
		return simpleDateFormat.format(value);
	}

}
