package com.test.qinweiping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.message.BufferedHeader;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class TestHttpClient {
	//采用的是get的方式进行请求

	public static final String BASE_URL = "http://grs.zju.edu.cn/allogene/page/home.htm";
	public static final String LOGIN_URL = "https://grs.zju.edu.cn/cas/login?service=http%3A%2F%2Fgrs.zju.edu.cn%2Fallogene%2Fpage%2Fhome.htm";
	public static final String TEST_URL = "http://grs.zju.edu.cn/allogene/page/home.htm";
	public static final String LOGIN_URL2 = "https://grs.zju.edu.cn/cas/login";
	private static HttpClient httpClient = new DefaultHttpClient();

	public static void main(String[] args) {
		String result = "";
		String urldecodeString = java.net.URLDecoder.decode(LOGIN_URL);
		System.out.println("经过解码后的URL地址:"+urldecodeString);
		//HttpGet httpget = new HttpGet(urldecodeString);
		HttpPost httpPost = new HttpPost(urldecodeString);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("username","21531133"));
		params.add(new BasicNameValuePair("password","love1993,."));
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
			HttpResponse response = httpClient.execute(httpPost);
			Header[] headArray = response.getHeaders("Set-Cookie");
			for(int i = 0 ; i < headArray.length ; i++){
				String sessionString = headArray[i].toString();
				System.out.println("返回的Session：："+sessionString);
			}
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, "utf-8");
			System.out.println(result);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		httpPost.releaseConnection();
	}
}
