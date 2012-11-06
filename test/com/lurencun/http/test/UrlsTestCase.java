package com.lurencun.http.test;

import java.io.InputStream;
import java.net.URL;

import junit.framework.Assert;

import com.lurencun.http.ResponseCallback;

/**
 * author : 桥下一粒砂
 * e-mail : chenyoca@gmail.com
 * date   : 2012-10-22
 * desc   : TODO
 */
public class UrlsTestCase extends BaseTeseCase{

	final static String[] AvailableUrls = {
		"http://www.lurencun.com",
		"http://www.163.com",
		"http://www.126.com",
		"http://www.google.com.hk",
		"http://www.qq.com",
		"http://www.ifeng.com",
		"http://www.renren.com",
		"http://www.58.com",
		"http://www.psbc.com",
		"http://www.suning.com",
		"http://www.jumei.com",
		"http://www.xiu.com",
		"http://www.letv.com",
		"http://www.miercn.com",
		"http://www.jrj.com",
		"http://www.suning.com",
		"http://www.9ku.com",
		"http://www.jumei.com",
		"http://www.ctrip.com",
		"http://www.tuniu.com",
		"http://www.icbc.com",
		"http://www.ccb.com",
		"http://www.gexing.com",
		"http://www.nipic.com",
		"http://www.jxedt.com",
		"http://www.iqiyi.com",
		"http://www.skycn.com",
		"http://www.7k7k.com",
	};

	@Override
	protected String[] loadUrls() {
		return AvailableUrls;
	}

	@Override
	protected void sendGetRequest(final String url) {
		http.get(url, null, new ResponseCallback() {
			
			@Override
			public void onResponse(InputStream response,URL url) {
				System.out.println("[test GET] --> response back, url = "+url);
				Assert.assertNotNull(response);
				requestBack();
			}
			
			@Override
			public void onError(Throwable exp) {
				System.err.println("[test GET] --> response error, url = "+url);
				requestBack();
			}

			@Override
			public void onSubmit(URL url) {
			}

			@Override
			public void onResponse(InputStream response, URL url, Object token) {
			}
		});
	}

}
