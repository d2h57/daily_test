package com.dy;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.AccessController;
import java.security.MessageDigest;
import java.security.PrivilegedExceptionAction;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicInteger;

import sun.misc.Unsafe;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;

public class Test {
	private final static int threadNum = 0;
	
	private final class TestHandle extends SimpleChannelInboundHandler{

		@Override
		protected void channelRead0(ChannelHandlerContext ctx, Object msg)
				throws Exception {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private static final Unsafe THE_UNSAFE;
    static
    {
        try
        {
            final PrivilegedExceptionAction<Unsafe> action = new PrivilegedExceptionAction<Unsafe>()
            {
                public Unsafe run() throws Exception
                {
                    Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
                    theUnsafe.setAccessible(true);
                    return (Unsafe) theUnsafe.get(null);
                }
            };

            THE_UNSAFE = AccessController.doPrivileged(action);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Unable to load unsafe", e);
        }
    }
    
    static Unsafe getUnsafe(){
    	return THE_UNSAFE;
    }
    
	static class T{
		private String time;
		private byte event;

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}

		public byte getEvent() {
			return event;
		}

		public void setEvent(byte event) {
			this.event = event;
		}
	}
	
	static class LocalRunnable implements Runnable{

		@Override
		public void run() {
			System.out.println("LocalRunnable running");
			System.out.println("LocalRunnable finish");
		}
	}
	
	static class LocalRunOnce implements Runnable{

		@Override
		public void run() {
			System.out.println("LocalRunOnce running");
			System.out.println("LocalRunOnce finish");
		}
	}
	
	static class Item{
		private String info;

		public String getInfo() {
			return info;
		}

		public void setInfo(String info) {
			this.info = info;
		}
		
		public String toString(){
			return info;
		}
	}
	
	static class Message<T>{
		private T item;
		private int result;
		public T getItem() {
			return item;
		}
		public void setItem(T item) {
			this.item = item;
		}
		public int getResult() {
			return result;
		}
		public void setResult(int result) {
			this.result = result;
		}
		
		public String toString(){
			return item.toString() + ":" + result;
		}
	}
	
	public static void main(String[] args) throws Exception {
		/*md5测试*/
		/*String password = "mygod123456";
        MessageDigest md5Digest = MessageDigest.getInstance("MD5");
        md5Digest.update(password.getBytes(), 0, password.length());
        String md5Pass = (new BigInteger(1, md5Digest.digest())).toString(16);
        System.out.println(md5Pass);*/
        
		/*JSONObject测试*/
		/*T t = new T();
		t.setEvent((byte)101);
		t.setTime("1234567890");
		JSONObject content = (JSONObject)JSONObject.toJSON(t);
		System.out.println(content.toString());*/
		
		/*测试Thread能否更换Runnable*/
		/*Thread t = new Thread(new LocalRunnable());
		t.start();*/
	
		/*String info = "{\"item\":{\"info\":\"here\"},\"result\":0}";
		Message<Item> message = (Message<Item>)JSON.parseObject(info,Message.class,null);
		System.out.println(message);*/
		
		/*System.out.println(System.currentTimeMillis());*/
		
		/*Date date = new Date();
		date.setTime(1453683936*1000L);
		System.out.println(date.toString());*/
		
		/*Item[] items = new Item[1];
		items[0] = new Item();
		items[0].setInfo("12232");
		
		for(int i = 0;i<items.length;++i){
			System.out.println(items[i].getInfo());
		}*/
	
		/*int bufferSize = 15;
		System.out.println(32&bufferSize);*/
	    
		//Unsafe测试
		/*Unsafe unsafe = getUnsafe();
		int scale = unsafe.arrayIndexScale(int[].class);
		int base = unsafe.arrayBaseOffset(int[].class);
		
		int[] nums = new int[10];
		for(int i = 0;i<nums.length;++i){
			unsafe.putOrderedInt(nums, i*scale + base, i);
		}
		
		for(int i=0;i<nums.length;++i){
			System.out.println(nums[i]);
		}
		
		
		System.out.println(unsafe.arrayIndexScale(Item[].class));
		AtomicInteger ai = new AtomicInteger();*/
		
		/*String retePlanInfo = "产品体验包-TrialKit-50MB-30SMS-3mo";
		String totalTraffic = "";
		int index = 0;
		int fromIndex = 0;
		while((index = retePlanInfo.indexOf('-',fromIndex)) != -1){
			totalTraffic = retePlanInfo.substring(fromIndex,index);
			if(totalTraffic.endsWith("MB") || totalTraffic.endsWith("GB")){
				break;
			}
			
			fromIndex = index+1;
		}
		
		System.out.println("totalTraffic:"+totalTraffic);
		
		String trafficRemaining = totalTraffic;
		double trafficUsed = 0;
		
		String remaining = "";
		if(null != remaining && remaining.length() > 0){
			//dataRemaining单位为MB
			trafficRemaining = remaining + "MB";
			
			System.out.println("trafficRemaining:"+trafficRemaining);
			double dataRemaining = Double.valueOf(remaining).doubleValue();
			if(totalTraffic.endsWith("MB")){
				trafficUsed = Integer.valueOf(totalTraffic.substring(0,totalTraffic.length()-2)).intValue() - dataRemaining;
			}else{
				trafficUsed = Integer.valueOf(totalTraffic.substring(0,totalTraffic.length()-2)).intValue()*1024 - dataRemaining;
			}
			
			if(trafficUsed > 1024){
				DecimalFormat dcmFmt = new DecimalFormat("0.00");
				System.out.println("trafficUsed:"+dcmFmt.format(trafficUsed / 1024)+"GB");
			}else{
				System.out.println("trafficUsed:"+trafficUsed+"MB");
			}
		}else{
			System.out.println("trafficRemaining:"+trafficRemaining);
			System.out.println("trafficUsed:"+trafficUsed+"MB");
		}
		
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		System.out.println(df.format(new Date()));
		//df.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date date = df.parse(df.format(new Date()));
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(format.format(date));
		*/
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.setTime(format.parse("2016-01-31 11:08:17"));
		cal.add(Calendar.MONTH, 1);
		
		System.out.println(format.format(cal.getTime()));
	}
}
