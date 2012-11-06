package com.lurencun.http.test;

import java.util.concurrent.CountDownLatch;

import org.junit.Before;
import org.junit.Test;

import com.lurencun.http.AsyncHttpConnection;

public abstract class BaseTeseCase {
	
	private String[] TargetUrls;

	private CountDownLatch downLatch ;
	
	protected final AsyncHttpConnection http = AsyncHttpConnection.getInstance();
	
	@Before
	public void setUp(){
		TargetUrls = loadUrls();
		downLatch = new CountDownLatch(TargetUrls.length * 2);
	}
	
	@Test
	public void test() throws InterruptedException {
		long startTime = System.currentTimeMillis();
		for(String url : TargetUrls){
			sendGetRequest(url);
			sendPostRequest(url);
		}
		downLatch.await();
		long endTime = System.currentTimeMillis();
		System.err.println("[TEST TIME] --> used time(ms) = "+(endTime - startTime));
	}
	
	protected abstract String[] loadUrls();
	
	
	/**
	 * 如果在发出了GET请求，返回true。
	 * @param url
	 */
	protected void sendGetRequest(String url){
		requestBack();
	}
	
	/**
	 * 如果在发出了GET请求，返回true。
	 * @param url
	 */
	protected void sendPostRequest(String url){
		requestBack();
	};
	
	protected void requestBack(){
		downLatch.countDown();
	}
}
