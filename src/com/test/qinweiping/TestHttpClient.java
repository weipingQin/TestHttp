package com.test.qinweiping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Base64.Decoder;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.RestoreAction;

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

	public static final String BASE_URL = "http://grs.zju.edu.cn/allogene/page/home.htm";
	//public static final String LOGIN_URL = "https://grs.zju.edu.cn/cas/login?service=http%3A%2F%2Fyjsds.zju.edu.cn%2Flogincheck.htm";
	public static final String LOGIN_URL = "https://grs.zju.edu.cn/cas/login?service=http%3A%2F%2Fyjsds.zju.edu.cn%2Flogincheck.htm";


	private static HttpClient httpClient = new DefaultHttpClient();

	public static void main(String[] args) {
		String result = "";
		String urldecodeString = java.net.URLDecoder.decode(LOGIN_URL);
		System.out.println("经过解码后的URL地址:"+urldecodeString);
	//	HttpPost httpPost = new HttpPost(urldecodeString);
		HttpGet httpGet = new HttpGet(urldecodeString);

		//先拼接头请求 

//		httpPost.setHeader("Referer","https://grs.zju.edu.cn/cas/login?service=http%3A%2F%2Fgrs.zju.edu.cn%2Fallogene%2Fpage%2Fhome.htm");
//		httpPost.setHeader("Connection","keep-alive");
//		httpPost.setHeader("Cache-Control","max-age=0");
//		httpPost.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
//		httpPost.setHeader("Upgrade-Insecure-Requests","1");
//		httpPost.setHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.157 Safari/537.36");
//		httpPost.setHeader("Content-Type","application/x-www-form-urlencoded");
//		httpPost.setHeader("Accept-Encoding","gzip, deflate");
//		httpPost.setHeader("Accept-Language","zh-CN,zh;q=0.8");
//
//		List<NameValuePair> params = new ArrayList<NameValuePair>();
//		params.add(new BasicNameValuePair("username","21531133"));
//		params.add(new BasicNameValuePair("password","love1993,."));
//		params.add(new BasicNameValuePair("lt","LT-1932102-10lNq3fZPbccSbO4dRyd7WXg9XrryU"));
//		params.add(new BasicNameValuePair("execution","e3s1"));
//		params.add(new BasicNameValuePair("_eventId","submit"));
//		params.add(new BasicNameValuePair("submit", ""));

		try {
			//httpGet.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
			HttpResponse response = httpClient.execute(httpGet);
			Header[] headArray = response.getAllHeaders();
			//for(int i = 0 ; i < headArray.length ; i++){
			//				String sessionString = headArray[i].toString();
			//				System.out.println("返回的Session：："+sessionString);
			String serverTime =  headArray[1].getValue();
			//System.out.println("serverTime is"+serverTime);
			String year = serverTime.substring(12,16);
			String month = serverTime.substring(8,11);
			String date = serverTime.substring(5,7);
			String time = serverTime.substring(17,25);
			if(month.equals("Dec")){
				month = "12";
			}
		//	System.out.println(year);
		//	System.out.println(month);
		//	System.out.println(date);
		//	System.out.println(time);
			String serverTimeTemp = year+"-"+month+"-"+date;
		//	System.out.println(serverTimeTemp);

			try {
				SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd");
				Date date2 = simpleDateFormat.parse(serverTimeTemp);
				long timeStemp = date2.getTime();
				//System.out.println("timeStemp"+timeStemp);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			//			 SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd");
			//			try {
			//			//	Date date = simpleDateFormat.parse("2010-06-25");
			//				  long timeStemp =date.getTime();
			//				     System.out.println(timeStemp);
			//			} catch (ParseException e) {
			//				// TODO Auto-generated catch block
			//				e.printStackTrace();
			//			}


			//			}
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, "utf-8");
			int flag = result.indexOf("<");
			int flag2 = result.indexOf("!");
			int flag3 = result.indexOf("\"lt\"");
			int flag4 = result.indexOf("\"execution\"");
			System.out.println("lt index :"+flag3); //对了
			String testLTString = "LT-1942722-qfH7Eyv4jwKQeG5l9dRPms4qpcxbf7";
			String testExecuteString = "e1s1";
			int LtStringstartFlag = flag3 + 12;
			int executeStringStartFlag = flag4 + 19;
			System.out.println(result.substring(LtStringstartFlag,LtStringstartFlag+testLTString.length()));
			//System.out.println(result);
			System.out.println(result.substring(executeStringStartFlag,executeStringStartFlag+testExecuteString.length()));
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
		httpGet.releaseConnection();
	}
}


