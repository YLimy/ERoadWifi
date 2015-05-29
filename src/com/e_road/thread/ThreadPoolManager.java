package com.e_road.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolManager {
	
	private ExecutorService service;
	
	private final static ThreadPoolManager manager = new ThreadPoolManager();
	
	private ThreadPoolManager(){
		int num = Runtime.getRuntime().availableProcessors();
		service = Executors.newFixedThreadPool(num*8);
	}

	public static ThreadPoolManager getInstatnce(){
		return manager;
	}
	
	public void addTask(Runnable runnable){
		service.execute(runnable);
	}
}
