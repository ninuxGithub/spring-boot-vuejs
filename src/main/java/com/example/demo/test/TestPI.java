package com.example.demo.test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * ForkJoinPool 和 ThreadPoolExecutor 都是 ExecutorService（线程池），但ForkJoinPool
 * 的独特点在于：
 * 
 * ThreadPoolExecutor 只能执行 Runnable 和 Callable 任务， 而 ForkJoinPool 不仅可以执行
 * Runnable 和 Callable 任务，还可以执行 Fork/Join 型任务 —— ForkJoinTask ——
 * 从而满足并行地实现分治算法的需要； ThreadPoolExecutor
 * 中任务的执行顺序是按照其在共享队列中的顺序来执行的，所以后面的任务需要等待前面任务执行完毕后才能执行，而 ForkJoinPool
 * 每个线程有自己的任务队列，并在此基础上实现了 Work-Stealing 的功能，使得在某些情况下 ForkJoinPool 能更大程度的提高并发效率。
 */
public class TestPI {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ForkJoinPool pool = new ForkJoinPool();
		P p = new P(0, 1_000_000_000, 10_000_000);

		// 1.invoke
		double value = pool.invoke(p);
		System.out.println("π：" + value);

		// 2.submit
		P p6 = new P(0, 1_000_000_000l, 10_000_000);
		ForkJoinTask<Double> submit = pool.submit(p6);
		Double value2 = submit.get();
		System.out.println("π：" + value2.doubleValue());
		System.out.println(Math.PI);
		// π：3.1415926535898--standard
		// π：3.141592652589726
		// π：3.1415941913689265
	}

}

class P extends RecursiveTask<Double> {
	private static final long serialVersionUID = 2976847554787780456L;
	private long start;
	private long end;
	private long threshold;

	public P(long start, long end, long threshold) {
		this.start = start;
		this.end = end;
		this.threshold = threshold;
	}

	@Override
	protected Double compute() {
		if (end - start <= threshold) {
			double result = 0.0d;
			int sign = 1;
			for (long i = start; i < end; i++) {
				result += sign / (i * 2.0d + 1);
				sign = -sign;
			}
			return 4 * result;
		}

		long middle = (start + end) / 2;
		P p1 = new P(start, middle, threshold);
		p1.fork();
		P p2 = new P(middle, end, threshold);
		p2.fork();
		double join1 = p1.join();
		double join2 = p2.join();
		return join1 + join2;
	}

}
