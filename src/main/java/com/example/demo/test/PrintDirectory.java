package com.example.demo.test;

import java.io.File;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * 递归文件夹
 *
 */
public class PrintDirectory {
	public static void main(String[] args) {
		File file = new File("D:\\搜狗高速下载\\mysql-5.7.19-winx64");
		long t0 = System.currentTimeMillis();
		PrintFile  print  = new PrintFile(file);
		ForkJoinPool pool = new ForkJoinPool();
		pool.invoke(print);
		long t1 = System.currentTimeMillis();
		
		printFile(file, 0);
		long t2 = System.currentTimeMillis();
		
		System.out.println("ForkJoin:"+ (t1-t0));
		System.out.println("Recurseve:"+ (t2-t1));
		
	}

	private static void printFile(File file, int i) {
		String routePreffix ="|--";
		String dirPreffix ="D--";
		if(file.isDirectory()){
			System.out.println(getPreffix(dirPreffix, i) + file.getName());
			i++;
			for(File subFile: file.listFiles()){
				printFile(subFile, i);
			}
		}else{
			String preffix = getPreffix(routePreffix, i);
			System.out.println(preffix + file.getName());
		}
	}
	
	private static String getPreffix(String preffix, int level){
		String str = "";
		for(int i =0; i<level; i++){
			str += preffix;
		}
		return str;
	}
	
	

}

class PrintFile extends RecursiveAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7466581389534631914L;
	
	private File file;
	
	public PrintFile(File file) {
		this.file = file;
	}

	@Override
	protected void compute() {
		if(!file.isDirectory()){
			System.out.println("\tF:---"+ file.getName());
		}else{
			File[] listFiles = file.listFiles();
			for (File file : listFiles) {
				PrintFile pf = new PrintFile(file);
				if(file.isDirectory()){
					System.out.println("D:---"+ file.getName());
				}
				pf.fork();
				pf.join();
			}
		}
		
		
	}

}
