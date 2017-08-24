package com.example.demo.utils;

import java.util.List;

import com.google.gson.Gson;

public class JsonUtil {

	public static <T> String toJson(List<T> list) {
		if (null == list || list.size() == 0) {
			return "{}";
		}
		Gson gson = new Gson();

		String json = gson.toJson(list);

		return json;
	}

}
