package com.lurencun.http;


/**
 * @author : 桥下一粒砂
 * @email  : chenyoca@gmail.com
 * @date   : 2012-10-23
 * @desc   : Http请求执行者，Http请求由此接口的实现类处理。
 */
public abstract class RequestInvoker implements Runnable{
	
	public final static String METHOD_GET = "GET";
	public final static String METHOD_POST = "POST";
	
	protected String method;
	protected String url;
	protected ParamsWrapper params;
	protected ResponseCallback callback;
	protected Object token;

	public RequestInvoker(){ }
	
	public void init(String method,String url,ParamsWrapper params,ResponseCallback callback){
		init(method, url, params, null, callback);
	}
	
	public void init(String method,String url,ParamsWrapper params,Object extra,ResponseCallback callback){
		this.method = method;
		this.url = url;
		this.params = params;
		this.callback = callback;
		this.token = extra;
	}
	
}
