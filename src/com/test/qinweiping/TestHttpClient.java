package com.test.qinweiping;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class TestHttpClient {
	//采用的是get的方式进行请求

	public static final String BASE_URL = "http://grs.zju.edu.cn/allogene/page/home.htm";
	public static final String LOGIN_URL = "https://grs.zju.edu.cn/cas/login?service=http%3A%2F%2Fgrs.zju.edu.cn%2Fallogene%2Fpage%2Fhome.htm";
	private static HttpClient httpClient = new DefaultHttpClient();

	public static void main(String[] args) {
		String result = "";
		String urldecodeString = java.net.URLDecoder.decode(LOGIN_URL);
		System.out.println("经过解码后的URL地址:"+urldecodeString);
		HttpGet httpget = new HttpGet(urldecodeString);
		httpget.setHeader("Referer",urldecodeString);
		httpget.setHeader("Cookie", getCookies());
		try {
			HttpResponse response = httpClient.execute(httpget);
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, "utf-8");
			System.out.println(result);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		httpget.releaseConnection();
	}

	private static String getCookies() {
		StringBuilder sb = new StringBuilder();
		List<Cookie> cookies = ((AbstractHttpClient)httpClient).getCookieStore().getCookies();
		for(Cookie cookie: cookies)
			sb.append(cookie.getName() + "=" + cookie.getValue() + ";");
		// 除了HttpClient自带的Cookie，自己还可以增加自定义的Cookie
		// 增加代码...
		return sb.toString();
	}
}
