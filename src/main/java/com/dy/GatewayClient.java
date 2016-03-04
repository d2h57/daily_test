package com.dy;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.newsmy.android.car.jtt808.base.JTT808Define;
import com.newsmy.android.car.jtt808.base.JTT808Factory;
import com.newsmy.android.car.jtt808.base.JTT808Message;
import com.newsmy.android.car.jtt808.base.JTT808MessageBuilder;
import com.newsmy.android.car.jtt808.base.JTT808MessageEncoder;
import com.newsmy.android.car.jtt808.model.Authentication;
import com.newsmy.android.car.jtt808.model.ClientEvent;


public class GatewayClient {
	public static void main(String[] args){
		
        Socket socket = new Socket();
        try {
        	InetSocketAddress address = new InetSocketAddress(InetAddress.getByName(/*"device.newsmycloud.cn"*/"localhost"), 9091);
//        	InetSocketAddress address = new InetSocketAddress("192.168.56.1", 9091);
            socket.connect(address);
            OutputStream outputStream = socket.getOutputStream();
                JTT808MessageBuilder builder = new JTT808MessageBuilder();
                builder.setMessageId(JTT808Define.JTT808_CLIENT_AUTHENTICATION_UPLOAD);
                Authentication authentication = new Authentication();
                authentication.setAuthenticationCode(/*"867910025587314"*//*"352584063075821"*/"A000004F250EEC"/*"008631111543011"*/);
//                authentication.setAppVersion("123wiheqtiojqweiotoi");
                builder.setBody(authentication);
                JTT808Message message = builder.build();
                JTT808MessageEncoder encoder = JTT808Factory.createMessageEncoder();
                for (byte[] buffer : encoder.encode(message)) {
                    outputStream.write(buffer);
                }
                
               builder.setMessageId(JTT808Define.JTT808_CLIENT_EVENT);
               ClientEvent event = new ClientEvent();
               event.setEvent(ClientEvent.EVENT_CLIENT_SHOCK);
               event.setTime("160224103112");
               builder.setBody(event);
               message = builder.build();
               for (byte[] buffer : encoder.encode(message)) {
                   outputStream.write(buffer);
               }
               
               try {
                   Thread.sleep(60000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
        	try{
        		if(socket.isConnected()){
            		socket.close();
            	}
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
	}
}
