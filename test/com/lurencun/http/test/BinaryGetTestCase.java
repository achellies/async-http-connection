package com.lurencun.http.test;

import java.net.URL;

import org.junit.Assert;

import com.lurencun.http.BinaryResponseHandler;
import com.lurencun.http.ParamsWrapper;

/**
 * @author : 桥下一粒砂
 * @email  : chenyoca@gmail.com
 * @date   : 2012-11-7
 * @desc   : TODO
 */
public class BinaryGetTestCase extends BaseTeseCase {

	@Override
	protected String[] loadUrls() {
		return new String[]{"http://s1.hao123img.com/index/images/newlogo-186X68.png"};
	}

	@Override
	protected void sendGetRequest(String url) {
		http.get(url, null, new BinaryResponseHandler() {
			
			@Override
			public void onSubmit(URL url, ParamsWrapper params) {
			}
			
			@Override
			public void onError(Throwable exp) {
				exp.printStackTrace();
				requestBack();
			}
			
			@Override
			public void onResponse(byte[] data, URL url) {
				final int size = 4465;
				Assert.assertEquals(size, data.length);
				System.out.println("Data size -> "+String.valueOf(data.length));
				requestBack();
			}
		});
		
	}
	
}
