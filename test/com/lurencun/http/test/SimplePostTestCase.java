package com.lurencun.http.test;

import java.net.URL;

import junit.framework.Assert;

import com.lurencun.http.ParamsWrapper;
import com.lurencun.http.StringResponseHandler;

/**
 * @author : 桥下一粒砂
 * @email : chenyoca@gmail.com
 * @date : 2012-10-24
 * @desc : TODO
 */
public class SimplePostTestCase extends BaseTeseCase {

	private final static String[] URLS = {
		"http://www.directory.utk.edu/examples/servlets/servlet/RequestParamExample",
	};
	
	private final static int KEY_VAL = 25;
	private final static String KEY_STR = "XXXXXXXXXXXXXX";
	
	@Override
	protected String[] loadUrls() {
		return URLS;
	}

	@Override
	protected void sendPostRequest(String url) {
		ParamsWrapper params = new ParamsWrapper();
		params.put("firstname", KEY_STR);
		params.put("lastname", KEY_VAL);
		http.post(url, params, new StringResponseHandler() {
			@Override
			public void onSubmit(URL url,ParamsWrapper params) { 
				System.out.println(">> target: "+url.getHost()+" --> "+url.getPath());
			}
			@Override
			public void onError(Throwable exp) {
				requestBack();
				exp.printStackTrace();
			}
			@Override
			public void onResponse(String content, URL url) {
				Assert.assertNotNull(content);
				System.out.println("Return -> "+content);
				boolean containsKey = content.contains(String.valueOf(KEY_VAL));
				Assert.assertEquals(true, containsKey);
				requestBack();
			}

		});
		
	}

}
