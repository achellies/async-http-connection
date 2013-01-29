package com.lurencun.http;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author : 桥下一粒砂
 * @email  : chenyoca@gmail.com
 * @date   : 2012-10-22
 * @desc   : An asynchronous multithread http connection framework. 
 */
public class AsyncHttpConnection {

	public final static String VERSION = "1.0.4";
	final static int THREAD_POOL_SIZE = 5;
	final ExecutorService threadPoolMng = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
	
	private AsyncHttpConnection(){}
	private static class SingletonProvider {
		private static AsyncHttpConnection instance = new AsyncHttpConnection();
	}
	public static AsyncHttpConnection getInstance(){
		return SingletonProvider.instance;
	}
	
	/**
	 * Send a 'get' request to url with params, response on callback
	 * @param url
	 * @param params
	 * @param callback
	 * @return request id
	 *
	 */
	public void get(String url,ParamsWrapper params,ResponseCallback callback){
		get(url, params, null,callback);
	}
	
	/**
	 * Send a 'get' request to url with params & <b>TOKEN</b> , response on callback. The <b>TOKEN</b> object 
	 * will be back to callback <i>ResponseCallback.onResponse(InputStream response,URL url,Object token)</i>
	 * as an identify of this request. You can put a object like '12345' to mark your request.
	 * @param url
	 * @param params
	 * @param token
	 * @param callback
	 * @return request id
	 *
	 */
	public void get(String url,ParamsWrapper params,Object token,ResponseCallback callback){
		verifyParams(url,callback);
		sendRequest(RequestInvoker.METHOD_GET,url,params,token,callback);
	}
	
	/**
	 * Send a 'post' request to url with params, response on callback
	 * @param url
	 * @param params
	 * @param callback
	 * @return request id
	 *
	 */
	public void post(String url,ParamsWrapper params,ResponseCallback callback){
		post(url, params, null,callback);
	}
	
	/**
	 * Send a 'post' request to url with params & <b>TOKEN</b> , response on callback. The <b>TOKEN</b> object 
	 * will be back to callback <i>ResponseCallback.onResponse(InputStream response,URL url,Object token)</i>
	 * as an identify of this request. You can put a object like '12345' to mark your request.
	 * @param url
	 * @param params
	 * @param token
	 * @param callback
	 * @return request id
	 *
	 */
	public void post(String url,ParamsWrapper params,Object token,ResponseCallback callback){
		verifyParams(url,callback);
		sendRequest(RequestInvoker.METHOD_POST,url,params,token,callback);
	}
	
	private void verifyParams(String url,ResponseCallback callback){
		if(callback == null) throw new IllegalArgumentException("ResponseCallback cannot be null");
		if(url == null) throw new IllegalArgumentException("Connection url cannot be null");
	}
	
	private void sendRequest(String method,String url,ParamsWrapper params,Object token,ResponseCallback handler){
		if(url == null) return;
		threadPoolMng.submit(InvokerFactory.obtain(method, url, params, token, handler));
	}
	
}
