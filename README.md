# Async Http Connection
=====================

 * a multithread callback-based async http connection framework. it can be use on android project or common java project.
 * 一个基于回调机制的多线程异步Http连接框架。它可用于Android项目或者一般Java项目。

## 适用
	
	Async HTTP Connection为简单的Http连接请求而设计。适用于API SDK等小数据传输项目，设计目标为Android项目。
	当前项目属性为Java项目，并使用JUnit4作为测试环境，主要是Android项目调试不方便。
	如果需要完善的功能，推荐一位国外大神的项目：[android-async-http](https://github.com/loopj/android-async-http)
	
## 特点

 * **简单** 提供POST和GET两个接口。通过参数和回调接口完成整个Http连接的交互。
 * **轻量** 纯JDK实现，不依赖第三方Jar包。
 * **快速** 采用Executor多线程并发框架，秉承它的并发处理优势。
 * **可扩展** 框架提供Invoker扩展，通过实现RequestInvoker可方便的把HttpClient等优秀框架整合到项目中。

## 使用

默认使用SimpleHttpInvoker类执行Http连接，基于HttpUrlConnection实现。
提供两个接口实现类：BinaryResponseHandler、StringResponseHandler。分别处理二进制数据和字符数据。

更多例子见源目录的 **test** 目录

### 简单的例子

``` java
//使用Get方法，取得服务端响应流：
AsyncHttpConnection http = AsyncHttpConnection.getInstance();
ParamsWrapper params = ...;
String url = ...
int requestId = http.get(url, params, new ResponseCallback() {
	
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
});

```

``` java
//使用POST方法，取得服务端响应流：
AsyncHttpConnection http = AsyncHttpConnection.getInstance();
ParamsWrapper params = ...;
String url = ...
int requestId = http.post(url, params, new ResponseCallback() {
	
	@Override
	public void onResponse(InputStream response,URL url) {
		System.out.println("[test POST] --> response back, url = "+url);
		Assert.assertNotNull(response);
		requestBack();
	}
	
	@Override
	public void onError(Throwable exp) {
		System.err.println("[test POST] --> response error, url = "+url);
		requestBack();
	}

	@Override
	public void onSubmit(URL url) {
	}
});
```

### 更详细的例子 

``` java
//使用POST方法，取得服务端响应流：
AsyncHttpConnection http = AsyncHttpConnection.getInstance();
final int KEY_VAL = 24;
ParamsWrapper params = new ParamsWrapper();
params.put("firstname", "chen");
params.put("lastname", "yoca");
params.put("foo", KEY_VAL);
params.put("cookiename", KEY_VAL);
params.put("cookievalue", KEY_VAL);
int requestId = http.post(url, params, new StringResponseHandler() {

	@Override
	public void onSubmit(URL url) { 
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
		boolean containsKey = content.contains(String.valueOf(KEY_VAL));
		Assert.assertEquals(true, containsKey);
		requestBack();
	}
});

```

``` java
//在大量并发的异步请求情况下，每个请求的回调可能需要一个标识码来标记这个回调结果。
//有两种方式来解决这个问题：
// 1、使用get和post返回的RequestID来标识，但这需要对RequestID进行管理
// 2、使用get和post的token参数

AsyncHttpConnection http = AsyncHttpConnection.getInstance();
final int KEY_VAL = 24;
ParamsWrapper params = new ParamsWrapper();
params.put("firstname", "chen");
params.put("lastname", "yoca");
params.put("foo", KEY_VAL);
params.put("cookiename", KEY_VAL);
params.put("cookievalue", KEY_VAL);

//  ******** 利用token 接口 ************
Object token = "1234566";

// 如果调用了带token的方法，回调的方法将是onResponse(String content, URL url, Object token)
int requestId = http.post(url, params, token, new StringResponseHandler() {

	@Override
	public void onSubmit(URL url) { 
		System.out.println(">> target: "+url.getHost()+" --> "+url.getPath());
	}

	@Override
	public void onError(Throwable exp) {
		requestBack();
		exp.printStackTrace();
	}

	@Override
	public void onResponse(String content, URL url, Object token) {
		// token == "1234566" Token被传到这里作为标识
		Assert.assertNotNull(content);
		boolean containsKey = content.contains(String.valueOf(KEY_VAL));
		Assert.assertEquals(true, containsKey);
		requestBack();
	}
});

```

### 开源协议

The code of this project is released under the Apache License 2.0, see [LICENSE](https://github.com/chenyoca/async-http-connection-core/blob/master/LICENSE)

