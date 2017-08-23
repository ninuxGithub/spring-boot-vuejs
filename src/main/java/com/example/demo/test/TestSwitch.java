package com.example.demo.test;

public class TestSwitch {

	public static void main(String[] args) {

		String str = "java";

		Class<? extends String> clazz = str.getClass();

		System.out.println("isInstance: " + clazz.isInstance(String.class));
		System.out.println("==" + (clazz == String.class));
		System.out.println("isAssignableFrom：" + clazz.isAssignableFrom(String.class));
		
		Double d = 1.234d;
		
		Class<? extends Double> classes = d.getClass();
		System.out.println("isInstance: " + classes.isInstance(Double.class));
		//System.out.println("==" + (classes == Number.class));
		System.out.println("isAssignableFrom：" + classes.isAssignableFrom(Double.class));
		

		switch (str) {
		case "php":
			System.out.println("php");
			break;
		case "c++":
			System.out.println("c++");
			break;
		case "java":
			System.out.println("java");
			break;
		default:
			System.out.println("not match...");
			break;
		}

	}

}
