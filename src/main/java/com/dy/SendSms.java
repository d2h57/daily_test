package com.dy;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class SendSms {
	public static void main(String[] args){
		HttpClient client = new DefaultHttpClient();  
        HttpPost post = new HttpPost("http://api.sms.cn/mtutf8/");
        
        try {
        	StringBuilder content = new StringBuilder();
        	content.append("您的验证码为：").append((int)(Math.random()*900000+100000)).append("，5分钟内有效，谢谢！【纽曼科技】");
        	List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        	pairs.add(new BasicNameValuePair("uid", "newsmy"));
        	pairs.add(new BasicNameValuePair("pwd", "661ebf9e378d9b287b7d95186ca0293c"));
        	pairs.add(new BasicNameValuePair("mobile","18908464499"));
        	pairs.add(new BasicNameValuePair("content",content.toString()));

        	
        	StringEntity entity = new StringEntity(URLEncodedUtils.format(pairs, "UTF-8"));
        	entity.setContentType("application/x-www-form-urlencoded");
            post.setEntity(entity);  
            
        	/*UrlEncodedFormEntity requestEntity = new UrlEncodedFormEntity(pairs);
        	requestEntity.setContentEncoding("UTF-8");  
        	requestEntity.setContentType("application/x-www-form-urlencoded");
            post.setEntity(requestEntity); */ 
              
            HttpResponse res = client.execute(post);  
            if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){  
               System.out.println("response:"+EntityUtils.toString(res.getEntity())); 
            }else{
            	System.out.println("post error!"); 
            }
        } catch (Exception e) {  
        	e.printStackTrace();
        }finally{
        	client.getConnectionManager().shutdown();
        }
        
	}
}
