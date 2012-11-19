package com.lurencun.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.URL;

import com.lurencun.http.assist.ResponseStreamUtil;

/**
 * @author : 桥下一粒砂
 * @email  : chenyoca@gmail.com
 * @date   : 2012-10-23
 * @desc   : 字符型响应处理类
 */
public abstract class StringResponseHandler implements ResponseCallback {

	@Override
	final public void onResponse(InputStream response,URL url) {
		onResponseWithToken(response, url, null);
	}

	@Override
	final public void onResponseWithToken(InputStream response, URL url, Object token) { 
		try {
			String data = ResponseStreamUtil.convertToString(response);
			if(token != null){
				onResponseWithToken(data,url,token);
			}else{
				onResponse(data,url);
			}
		} catch (IOException exp) {
			onError(new ConnectException(exp.getMessage()));
			exp.printStackTrace();
		}
	}

	public abstract void onResponse(String content,URL url);
	
	public void onResponseWithToken(String content,URL url,Object token){};
}
