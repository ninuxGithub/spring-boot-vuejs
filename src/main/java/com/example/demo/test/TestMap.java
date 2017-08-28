package com.example.demo.test;

public class TestMap {
	public static void main(String[] args) {
		int hash = TestMap.hash("java");
		System.out.println(hash);
	}
	static final int hash(Object key) {
		int h;
		return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
	}

}
