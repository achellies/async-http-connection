package com.lurencun.http.test;

import static org.junit.Assert.fail;

import java.io.InputStream;
import java.net.URL;

import com.lurencun.http.ResponseCallback;

/**
 * author : 桥下一粒砂
 * e-mail : chenyoca@gmail.com
 * date   : 2012-10-22
 * desc   : 测试Timeout
 */
public class TimeOutTestCase extends BaseTeseCase{

	final static String[] TimeoutTargetUrls = {
		"http://www.facebook.com",
		"http://www.twitter.com",
	};

	@Override
	protected String[] loadUrls() {
		return TimeoutTargetUrls;
	}

	@Override
	protected void sendGetRequest(final String url) {
		http.get(url, null, new ResponseCallback() {
			
			@Override
			public void onResponse(InputStream response,URL url) {
				fail("Target url is not a timeout url!");
				System.err.println("[test GET] --> response back, url = "+url);
				requestBack();
			}
			
			@Override
			public void onError(Throwable exp) {
				exp.printStackTrace();
				System.out.println("[test GET] --> response time out, url = "+url);
				requestBack();
			}

			@Override
			public void onSubmit(URL url) {
			}

			@Override
			public void onResponseWithToken(InputStream response, URL url, Object token) {
			}
		});
	}

}
