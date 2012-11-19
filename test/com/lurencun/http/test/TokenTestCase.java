package com.lurencun.http.test;

import java.net.URL;

import org.junit.Assert;

import com.lurencun.http.StringResponseHandler;

/**
 * @author : 桥下一粒砂
 * @email  : chenyoca@gmail.com
 * @date   : 2012-11-2
 * @desc   : TODO
 */
public class TokenTestCase extends BaseTeseCase {

	@Override
	protected String[] loadUrls() {
		return new String[]{"http://www.baidu.com"};
	}

	@Override
	protected void sendGetRequest(String url) {
		http.get(url, null,"123456", new StringResponseHandler() {
			@Override
			public void onSubmit(URL url) {
			}
			
			@Override
			public void onError(Throwable exp) {
			}
			
			@Override
			public void onResponse(String content, URL url) {
			}

			@Override
			public void onResponseWithToken(String content, URL url, Object token) {
				System.out.println("token -> "+String.valueOf(token));
				Assert.assertEquals("123456", String.valueOf(token));
				requestBack();
			}
		});
	}

	

}
