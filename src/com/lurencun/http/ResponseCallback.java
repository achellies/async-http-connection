package com.lurencun.http;

import java.io.InputStream;
import java.net.URL;

/**
 * @author : 桥下一粒砂
 * @email  : chenyoca@gmail.com
 * @date   : 2012-10-22
 * @desc   : Http请求响应回调接口。
 * 			每个Http请求被发出后，只有两种结果：1、服务端响应请求；2、请求异常。
 */
public interface ResponseCallback {

	/**
	 * 在Http请求被提交时回调
	 * @param url 被提交的URL
	 */
	void onSubmit(URL url);
	
	/**
	 * 返回服务端的响应流
	 * @param response
	 */
	void onResponse(InputStream response,URL url);
	
	/**
	 * 返回服务端的响应流,并附带标识数据
	 * @param response
	 */
	void onResponseWithToken(InputStream response,URL url,Object token);

	/**
	 * 请求异常
	 * @param exp
	 */
	void onError(Throwable exp);

}
