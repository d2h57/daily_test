package com.dy;

import java.io.IOException;
import java.net.URI;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;


public class HttpRequestSend {
	public static void main(String[] args){
		try{
			/*get请求测试*/
			/*HttpClient client = new DefaultHttpClient();
			HttpGet operation = new HttpGet("http://localhost:8080/track/api/latestversion.jsp");
			
			HttpResponse reponse = client.execute(operation);
			if (reponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				System.out.println("response:" + EntityUtils.toString(reponse.getEntity()));
			} else {
				System.out.println("request error!");
			}*/
			
			
			//请求https
            SSLContext ctx = SSLContext.getInstance("TLS");  
            //Implementation of a trust manager for X509 certificates  
			X509TrustManager tm = new X509TrustManager() {  
			    public void checkClientTrusted(X509Certificate[] xcs,  
			            String string) throws CertificateException {  
			
			    }  
			
			    public void checkServerTrusted(X509Certificate[] xcs,  
			            String string) throws CertificateException {  
			    }  
			
			    public X509Certificate[] getAcceptedIssuers() {  
			        return null;  
			    }  
			};
			
			X509HostnameVerifier hostnameVerifier = new X509HostnameVerifier() {
	            public boolean verify(String arg0, SSLSession arg1) {
	                return true;
	            }
	            public void verify(String arg0, SSLSocket arg1) throws IOException {}
	            public void verify(String arg0, String[] arg1, String[] arg2) throws SSLException {}
	            public void verify(String arg0, X509Certificate arg1) throws SSLException {}
	        };
	        
			ctx.init(null, new TrustManager[] { tm }, null);  
			SSLSocketFactory ssf = new SSLSocketFactory(ctx);
			ssf.setHostnameVerifier(hostnameVerifier);
			
            HttpClient client = new DefaultHttpClient();
            ClientConnectionManager ccm = client.getConnectionManager();  
            //register https protocol in httpclient's scheme registry  
            SchemeRegistry sr = ccm.getSchemeRegistry();  
            sr.register(new Scheme("https", 8443, ssf));
			
            HttpGet operation = new HttpGet("https://localhost:8443/track/api/latestversion.jsp");
            HttpResponse reponse = client.execute(operation);
			
			if (reponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				System.out.println("response:" + EntityUtils.toString(reponse.getEntity()));
			} else {
				System.out.println("request error!");
			}
            
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
