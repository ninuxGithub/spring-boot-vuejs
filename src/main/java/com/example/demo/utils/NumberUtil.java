package com.example.demo.utils;

import java.text.DecimalFormat;

public class NumberUtil {

	public static String formatNumber(Object value, String numberPattern) {
		DecimalFormat decimalFormat = new DecimalFormat(numberPattern);
		return decimalFormat.format(value);
	}
	
	

}
