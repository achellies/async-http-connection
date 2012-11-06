package com.lurencun.http.assist;

import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.URL;

import com.lurencun.http.ResponseCallback;

/**
 * @author : 桥下一粒砂
 * @email  : chenyoca@gmail.com
 * @date   : 2012-10-24
 * @desc   : 字节型数据处理类
 */
public abstract class BinaryResponseHandler implements ResponseCallback {

	@Override
	final public void onResponse(InputStream response,URL url) {
		onResponse(response, url, null);
	}

	@Override
	final public void onResponse(InputStream response, URL url, Object token) {
		try {
			byte[] data = ResponseStreamUtil.convertToByteArray(response);
			if(token != null){
				onResponse(data,url,token);
			}else{
				onResponse(data,url);
			}
		} catch (IOException exp) {
			onError(new ConnectException(exp.getMessage()));
			exp.printStackTrace();
		}
	}

	public abstract void onResponse(byte[] data,URL url);
	
	public void onResponse(byte[] data,URL url,Object token){};
}
