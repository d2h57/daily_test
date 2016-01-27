package com.dy;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.AccessController;
import java.security.MessageDigest;
import java.security.PrivilegedExceptionAction;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import sun.misc.Unsafe;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;

public class Test {
	private final static int threadNum = 0;
	
	static class TestHandle extends SimpleChannelInboundHandler{

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
		/*String password = "qwerty";
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
	    
		Unsafe unsafe = getUnsafe();
		/*int scale = unsafe.arrayIndexScale(int[].class);
		int base = unsafe.arrayBaseOffset(int[].class);
		
		int[] nums = new int[10];
		for(int i = 0;i<nums.length;++i){
			unsafe.putOrderedInt(nums, i*scale + base, i);
		}
		
		for(int i=0;i<nums.length;++i){
			System.out.println(nums[i]);
		}*/
		
		System.out.println(unsafe.arrayIndexScale(Item[].class));
	}
}
