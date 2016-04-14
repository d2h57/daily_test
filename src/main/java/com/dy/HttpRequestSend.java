package com.dy;

import java.io.File;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.CharsetUtils;
import org.apache.http.util.EntityUtils;


public class HttpRequestSend {
	
	public static void main(String[] args){
		try{
			//创建账号
			/*String url = "http://192.168.2.177:8080/security/account/create";
	        String requestData = "{\"phoneNumber\":\"18908464499\",\"password\":\"7100584\",\"captcha\":\"701108\"}";*/
	        
	        //第三方账号登录
	        /*String url = "http://192.168.2.177:8080/security/account/login";
	        String requestData = "{\"oauthIds\":[{\"oauthId\":\"1034883805\",\"type\":1}]}";*/
	        
			//手机号+密码登录
			/*String url = "http://192.168.2.177:8080/security/account/login";
			String requestData = "{\"phoneNumber\":\"18673209435\",\"password\":\"111111\",\"imei\":\"48D041E5-445E-4995-87AE-C110785F5ADC\"}";*/
	        
			//忘记密码
			/*String url = "http://localhost:8080/security/account/forgetPassword";
	        String requestData = "{\"phoneNumber\":18908464499,\"password\":\"123456\",\"captcha\":\"289392\"}";*/
			
			//修改账号信息
			/*String url = "http://192.168.2.177:8080/security/account/updateInfo";
			String requestData = "{\"nickname\":\"dengyong11\",\"email\":\"1034883805@qq.com\",\"id\":100000000}";*/
			
			//修改密码
			/*String url = "http://localhost:8080/security/account/changePassword";
	        String requestData = "{\"id\":100000000,\"password\":\"123456\"}";
			
			CloseableHttpClient client = HttpClients.createDefault();  
	        HttpPost post = new HttpPost(url);
	        
	        try {  
	            StringEntity s = new StringEntity(requestData);  
	            s.setContentEncoding("UTF-8");  
	            s.setContentType("application/json;charset=UTF-8");  
	            post.setEntity(s);  
	              
	            CloseableHttpResponse res = client.execute(post);  
	            if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){  
	               System.out.println("response:"+EntityUtils.toString(res.getEntity())); 
	            }else{
	            	
	            	System.out.println("post error:"+res.getStatusLine().getStatusCode()+"!"); 
	            }
	        } catch (Exception e) {  
	        	e.printStackTrace();
	        }
	        client.close();*/
	        
			//请求https
			//上传图片            
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    return true;
                }
            }).build();

            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			try {
				// 要上传的文件的路径
				String filePath = new String("E:\\11.jpg");
				String url = "https://localhost:8443/security/device/uploadAlarmPicture";
				// 把一个普通参数和文件上传给下面这个地址 是一个servlet
				HttpPost httpPost = new HttpPost(url);
				// 把文件转换成流对象FileBody
				File file = new File(filePath);
				FileBody bin = new FileBody(file);
				// 以浏览器兼容模式运行，防止文件名乱码。
				HttpEntity reqEntity = MultipartEntityBuilder.create()
						.setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
						.addPart("uploadFile", bin) // uploadFile对应服务端类的同名属性<File类型>
						.setCharset(CharsetUtils.get("UTF-8")).build();

				httpPost.setEntity(reqEntity);

				System.out.println("发起请求的页面地址 " + httpPost.getRequestLine());
				// 发起请求 并返回请求的响应
				CloseableHttpResponse response = httpClient.execute(httpPost);
				try {
					StatusLine statusLine = response.getStatusLine();
					 if(statusLine.getStatusCode() == HttpStatus.SC_OK){  
						 System.out.println("response:"+EntityUtils.toString(response.getEntity())); 
					 }else{
						 System.out.println("post error:"+statusLine.getStatusCode()+"!"); 
					 }
				} finally {
					response.close();
				}
			} finally {
				httpClient.close();
			}
			
			//连接opents
			/*HttpClient client = new DefaultHttpClient();  
	        HttpPost post = new HttpPost("http://localhost:8080/track/api/eventData.jsp");
	        
	        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        	pairs.add(new BasicNameValuePair("imeiNumber", "351565029001994"));
        	pairs.add(new BasicNameValuePair("deviceState", "61714"));
        	pairs.add(new BasicNameValuePair("latitude","30111111"));
        	pairs.add(new BasicNameValuePair("longitude","30111111"));
        	pairs.add(new BasicNameValuePair("speed","111"));
        	pairs.add(new BasicNameValuePair("altitude","111"));
        	pairs.add(new BasicNameValuePair("bearing","111"));
        	pairs.add(new BasicNameValuePair("time","160407120211"));
        	
            UrlEncodedFormEntity requestEntity = new UrlEncodedFormEntity(pairs,"UTF-8");
            requestEntity.setContentType("application/x-www-form-urlencoded");
            post.setEntity(requestEntity);  
              
            HttpResponse res = client.execute(post);  
            if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){  
               System.out.println("response:"+EntityUtils.toString(res.getEntity())); 
            }else{
            	System.out.println("post error!"); 
            }*/
            
			/*url参数格式化*/
			/*JSONObject info = new JSONObject();
			info.put("col", "1111");
			List params = new ArrayList();
			params.add(new BasicNameValuePair("param1", info.toJSONString()));
			String param = URLEncodedUtils.format(params, "UTF-8");
			System.out.println(param);*/
			 
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}	
