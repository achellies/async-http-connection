package com.lurencun.http.test;

import java.net.URL;

import com.lurencun.http.ParamsWrapper;
import com.lurencun.http.assist.StringResponseHandler;


/**
 * @author : 桥下一粒砂
 * @email  : chenyoca@gmail.com
 * @date   : 2012-11-6
 * @desc   : TODO
 */
public class BinaryPostTestCase extends BaseTeseCase {

	@Override
	protected String[] loadUrls() {
		return new String[]{"http://localhost/upload.php"};
	}

	@Override
	protected void sendPostRequest(String url) {
		ParamsWrapper params = new ParamsWrapper();
		params.put("session_id", "55_18EF2B0F66521BCF1D45CECC561LK1F867_99");
		params.put("avatar", "MyAvatar");
		params.put("file", "file", "D:\\14.jpg");
		params.put("file1", "file1", "D:\\13.jpg");
		http.post(url, params, new StringResponseHandler() {
			
			@Override
			public void onSubmit(URL url) {
			}
			
			@Override
			public void onError(Throwable exp) {
				System.out.println(exp.getMessage());
			}
			
			@Override
			public void onResponse(String content, URL url) {
				System.out.println(content);
				requestBack();
			}
		});
	}

	
}
