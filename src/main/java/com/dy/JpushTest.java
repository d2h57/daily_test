package com.dy;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import cn.jpush.api.report.MessagesResult;
import cn.jpush.api.report.ReceivedsResult;
import cn.jpush.api.report.ReportClient;


public class JpushTest {

	//钮家
/*	private static final String appKey ="14b60266c85162d1d4293625";
	
	private static final String masterSecret = "f30101569432dc0f58bb4c05";*/
	
	//钮眼
	private static final String appKey ="30d839c2f91a0e083277b938";
	private static final String masterSecret = "b599a2a6fd99d17891e37ff9";
	
	public static boolean sendPush(String type,String alias,String device,String title,String MSG_CONTENT){
		JPushClient jpushClient = new JPushClient(masterSecret, appKey, 3);
	
        // For push, all you need do is to build PushPayload object.
        
        PushPayload payload = null;
        if("notify".equals(type))
        	payload = buildPushObject_all_alias_alert(alias,device,title,MSG_CONTENT);
        else
        	payload = buildPushObject_all_alias_message(alias,device,MSG_CONTENT);
        
        try {
            PushResult result = jpushClient.sendPush(payload);
            System.out.println("Got result -"+result);
            return true;
            
        } catch (APIConnectionException e) {
        	e.printStackTrace();
        	return false;
            
        } catch (APIRequestException e) {
        	e.printStackTrace();
        	return false;
        }
	}
	
	//alias：账号名 ，device：设备名称， MSG_CONTENT：内容
    public static PushPayload buildPushObject_all_alias_message(String alias,String device,String MSG_CONTENT) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
//                .setOptions(Options.newBuilder().setApnsProduction(true).build())
                .setAudience(Audience.alias(alias))
                .setMessage(Message.newBuilder()
                        .setMsgContent(MSG_CONTENT)
                        .addExtra("time", getSystemTime())//增加json字符串对像 //输出
                        .addExtra("device",device)//增加json字符串对像 //输出
                        .build())
                .build();
    }   
    //alias：账号名 ，device：设备名称，title：提示标题， MSG_CONTENT：内容
    public static PushPayload buildPushObject_all_alias_alert(String alias,String device,String title,String MSG_CONTENT) {//通知提醒
        return PushPayload.newBuilder()
        		.setPlatform(Platform.all())
                .setAudience(Audience.alias(alias))
//                .setOptions(Options.newBuilder().setApnsProduction(true).build())
                .setNotification(Notification.newBuilder()
                        .setAlert(MSG_CONTENT)
                		.addPlatformNotification(AndroidNotification.newBuilder()
                				.build())
                		.addPlatformNotification(IosNotification.newBuilder()
                				.incrBadge(1)
                				.build())
                		.build())
                .build();
    } 
    
    public static String getSystemTime(){
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	return df.format(new Date());
    }
    
    public static void reportReceiveds(String msgId) throws APIConnectionException, APIRequestException{
    	JPushClient jpushClient = new JPushClient(masterSecret, appKey, 3);
    	ReceivedsResult result = jpushClient.getReportReceiveds(msgId);
    	System.out.println(result);
    }
    
    
    public static void getMessages(String msgId) throws APIConnectionException, APIRequestException{
    	ReportClient client = new ReportClient(masterSecret, appKey);
    	System.out.println(client.getMessages(msgId));
    }
    
	public static void main(String[] args) throws Exception {        
        JSONObject info = new JSONObject();
	    info.put("deviceId","newsmy_10");
	    info.put("time","160503072812");
	    info.put("streetAddress","测试消息发送");
		info.put("radius","1000");
		info.put("event", "102");
		
		/*JSONObject info = new JSONObject();
	    info.put("deviceId","N800_0042");
	    info.put("time","160427161532");
		info.put("event", 101);
		
		sendPush("message","13637488987","","",info.toString());*/
		
		sendPush("message","13637488987","","",info.toString());
		
//		getMessages("789517812");
	}
}


