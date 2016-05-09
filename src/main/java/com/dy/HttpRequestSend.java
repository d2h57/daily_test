package com.dy;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.CharsetUtils;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;


public class HttpRequestSend {
	
	public static void main(String[] args){
		try{
			//创建账号
			/*String url = "http://localhost:8080/security/account/create";
	        String requestData = "{\"phoneNumber\":\"18908464499\",\"password\":\"7100584\",\"captcha\":\"584046\"}";*/
	        
	        //第三方账号登录
	       /* String url = "http://localhost:8080/security/account/login";
	        String requestData = "{\"oauthIds\":[{\"oauthId\":\"1034883805\",\"type\":1}]}";*/
	        
			//手机号+密码登录
			/*String url = "http://localhost:8080/security/account/login";
			String requestData = "{\"phoneNumber\":\"18908464499\",\"password\":\"123456\",\"imei\":\"48D041E5-445E-4995-87AE-C110785F5ADC\"}";*/
	        
			//忘记密码
			/*String url = "http://localhost:8080/security/account/forgetPassword";
	        String requestData = "{\"phoneNumber\":18908464499,\"password\":\"123456\",\"captcha\":\"750991\"}";*/
			
			//修改账号信息
			/*String url = "http://localhost:8080/security/account/updateInfo";
			String requestData = "{\"nickname\":\"邓涌\",\"email\":\"1034883805@qq.com\",\"id\":100000011}";*/
			
			//修改密码
			/*String url = "http://localhost:8080/security/account/changePassword";
	        String requestData = "{\"id\":100000011,\"oldPassword\":\"123456\",\"password\":\"654321\"}";*/
			
			//配置告警
			/*String url = "http://localhost:8080/security/alarm/configAlarm";
	        String requestData = "{\"accountId\":100000001,\"deviceId\" : \"PPRT-012858-FELJY\",\"alarmType\":1,"
	        		+ "\"enabled\":1,\"content\":{\"startTime\":\"13:00\",\"endTime\":\"24:00\",\"values\":\"1,2,4\"}}";*/
		
			//绑定设备
			/*String url = "http://localhost:8080/security/device/bindDevice";
	        String requestData = "{\"accountId\":100000011,\"device\":\"NAB-000009-11111\"}";*/
	        
	        //修改设备名称
	        /*String url = "http://localhost:8080/security/device/updateInfo";
	        String requestData = "{\"accountId\":100000011,\"deviceId\":\"PPRT-012858-FELJY\",\"name\":\"测试设备\"}";*/
			
			//取消绑定
			/*String url = "http://localhost:8080/security/device/unBindDevice";
	        String requestData = "{\"accountId\":100000011,\"deviceId\":\"NAB-000009-11111\"}";*/
			
			//推送告警
			/*String url = "http://localhost:8080/security/alarm/upload";
	        String requestData = "{\"timestamp\":\"160422131720\",\"deviceId\":\"NAB-000218-GZWBM\",\"event\":6}";*/
			
			//校验密码
			/*String url = "http://localhost:8080/security/account/verifyPassword";
	        String requestData = "{\"id\":100000011,\"password\":\"123456\"}";*/
	        
			/*CloseableHttpClient client = HttpClients.createDefault();  
	        HttpPost post = new HttpPost(url);
	        
	        for(int i=0;i<2;++i){
	        	try {  
		            StringEntity s = new StringEntity(requestData,"UTF-8");
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
		        }finally{
		        	//client.close();
		        }
	        }*/
	        
	        
	        CloseableHttpClient client = HttpClients.createDefault();  
	        //中国天气网简单的天气,只有温度
//	        HttpGet get = new HttpGet("http://www.weather.com.cn/data/sk/101250101.html");
//	        HttpGet get = new HttpGet("http://www.weather.com.cn/data/cityinfo/101250101.html");
	        
	        //新浪天气只有温度,没有湿度,PM25
//	        HttpGet get = new HttpGet("http://php.weather.sina.com.cn/xml.php?city=%b3%a4%c9%b3&password=DJOYnieT8234jlsK&day=0");
	        
	        //使用和天气,包含温度\湿度\PM25
	        HttpGet get = new HttpGet("https://api.heweather.com/x3/weather?cityid=CN101250101&key=a0743e6c80f447f799cd3b393fb8f9b1");
	        
	        	try {  
		            CloseableHttpResponse res = client.execute(get);  
		            if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){  
		               System.out.println("response:"+EntityUtils.toString(res.getEntity(),Charset.forName("utf-8"))); 
		            }else{
		            	
		            	System.out.println("post error:"+res.getStatusLine().getStatusCode()+"!"); 
		            }
		        } catch (Exception e) {  
		        	e.printStackTrace();
		        }finally{
		        	//client.close();
		        }
	        
	        //https双向验证
	        /*KeyStore trustStore  = KeyStore.getInstance(KeyStore.getDefaultType());
		    //加载信任证书文件
	        FileInputStream instream = new FileInputStream(new File("D:/apache-tomcat-6.0.44/conf/client_cert/client_trust.rsa"));
	        try {
	        	trustStore.load(instream, "7100584".toCharArray());
	        } finally {
	        	instream.close();
	        }
	
	        KeyStore keyStore  = KeyStore.getInstance("PKCS12");//注意
		    //加载证书文件
	        instream = new FileInputStream(new File("D:/apache-tomcat-6.0.44/conf/client_cert/security_client.p12"));
	        try {
	        	keyStore.load(instream, "7100584".toCharArray());
	        } finally {
	        	instream.close();
	        }

	        SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(trustStore,new TrustSelfSignedStrategy())
	        		.loadKeyMaterial(keyStore, "7100584".toCharArray())
	        		.build();
	        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext,
	                 SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

	        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
	        		.register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", sslsf).build();  
	        
	        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
	        HttpHost localhost = new HttpHost("locahost", 8443);  
	        cm.setMaxPerRoute(new HttpRoute(localhost), 2);
	        cm.setMaxTotal(2);
	        
			BasicHttpContext context = new BasicHttpContext();

			for (int i = 0; i < 2; ++i) {
				System.out.println("begin time" + i + ":"+ System.currentTimeMillis());

				CloseableHttpClient httpClient = null;
				try {
					// 使用连接池,连接池里进行https设置
					httpClient = HttpClients.custom().setConnectionManager(cm).build();
					// 直接使用https
//					httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
					HttpGet get = new HttpGet("https://localhost:8443/security/account/getCaptcha?phoneNumber=18909999999&type=0");
					// HttpGet get = new HttpGet("http://localhost:8080/track/api/latestversion.jsp");
					CloseableHttpResponse response = httpClient.execute(get,context);

					try {
						StatusLine statusLine = response.getStatusLine();
						if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
							HttpEntity entity = response.getEntity();
							System.out.println("response:"
									+ EntityUtils.toString(entity));
						} else {
							get.abort();
							System.out.println("post error:"
									+ statusLine.getStatusCode() + "!");
						}
					} finally {
						response.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
				}

				System.out.println("end time" + i + ":"+ System.currentTimeMillis());
			}*/
	        
			//请求https
			//上传图片            
			/*KeyStore trustStore  = KeyStore.getInstance(KeyStore.getDefaultType());
		    //加载信任证书文件
	        FileInputStream instream = new FileInputStream(new File("D:/apache-tomcat-6.0.44/conf/client_cert/client_trust.rsa"));
	        try {
	        	trustStore.load(instream, "7100584".toCharArray());
	        } finally {
	        	instream.close();
	        }
	        
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(trustStore, new TrustSelfSignedStrategy())).build();

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
			}*/
			
			//连接opents
			/*CloseableHttpClient client = HttpClients.createDefault();  
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
            }
            client.close();*/
            
            
			//url参数格式化
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
