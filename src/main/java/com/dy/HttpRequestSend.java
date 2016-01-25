package com.dy;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;


public class HttpRequestSend {
	public static void main(String[] args){
		try{
			/*get请求测试*/
			/*HttpClient client = new DefaultHttpClient();
			
			 
			HttpGet operation = new HttpGet("http://localhost:8080/track/api/device.jsp?account_id=13637488987&command=query_device_usage&iccid=8986061501000337913");
			
			HttpResponse reponse = client.execute(operation);
			if (reponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				System.out.println("response:" + EntityUtils.toString(reponse.getEntity()));
			} else {
				System.out.println("request error!");
			}*/
			
			
			/*url参数格式化*/
			/*JSONObject info = new JSONObject();
			info.put("col", "1111");
			List params = new ArrayList();
			params.add(new BasicNameValuePair("param1", info.toJSONString()));
			String param = URLEncodedUtils.format(params, "UTF-8");
			System.out.println(param);*/
			 
		}catch(Exception e){
			System.out.println(e);
		}
		
	}
}	
