package com.example.demo.utils;

import java.lang.reflect.Field;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.reflect.FieldUtils;

public class ReflectUtil {

	/**
	 * Java 反射获取对应字段的值
	 * 
	 * @param <V>
	 * 
	 * @param fund
	 * @param field
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T, V> V getTypeField(T t, String field) {
		if (StringUtils.isNotBlank(field)) {
			Class<?> clazz = t.getClass();
			try {
				Field declaredField = FieldUtils.getDeclaredField(clazz, field, true);
				declaredField.setAccessible(true);
				return (V) declaredField.get(t);
			} catch (Exception e) {
				throw new RuntimeException("反射获取字段值异常");
			}
		}
		return null;
	}

}
