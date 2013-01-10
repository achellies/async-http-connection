package com.lurencun.http.test;

import java.net.URL;

import com.lurencun.http.StringResponseHandler;

/**
 * @author : 桥下一粒砂
 * @email  : chenyoca@gmail.com
 * @date   : 2012-10-29
 * @desc   : TODO
 */
public class SimpleGetTestCase extends BaseTeseCase{

	@Override
	protected String[] loadUrls() {
		return new String[]{"http://api.douban.com/book/subject/isbn/9787115264725"};
	}

	@Override
	protected void sendGetRequest(String url) {
		http.get(url, null, new StringResponseHandler() {
			
			@Override
			public void onSubmit(URL url) {
			}
			
			@Override
			public void onError(Throwable exp) {
				exp.printStackTrace();
				requestBack();
			}
			
			@Override
			public void onResponse(String content, URL url) {
				System.out.println("content->"+content);
				requestBack();
			}
		});
	}

}
