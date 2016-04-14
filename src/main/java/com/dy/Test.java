package com.dy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.security.AccessController;
import java.security.MessageDigest;
import java.security.PrivilegedExceptionAction;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicInteger;

//import org.json.JSONObject;




import javax.swing.Box.Filler;

import org.apache.commons.codec.binary.Base64;

import com.alibaba.fastjson.JSONObject;
import com.dy.service.IAction;

import sun.misc.Unsafe;

//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;

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
		
		public void finalize(){
			System.out.println("finalize");
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
		/*String password = "Newsmy159357newsmy";
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
		
		//时间换算
		/*Date date = new Date();
		date.setTime(1460401260*1000L);
		System.out.println(date.toString());
		
		//1430413200052
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,2016);
		cal.set(Calendar.MONTH, 3);
		cal.set(Calendar.DATE, 12);
		cal.set(Calendar.HOUR_OF_DAY,3);
		cal.set(Calendar.MINUTE,2);
		cal.set(Calendar.SECOND,0);
		System.out.println(cal.getTimeInMillis());*/
		
		/*Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(1457625600*1000L);
        
        StringBuilder tableName = new StringBuilder();
        
        tableName.append(cal.get(Calendar.YEAR)).append(cal.get(Calendar.MONTH)+1 < 10 ? "0"+(cal.get(Calendar.MONTH)+1) : cal.get(Calendar.MONTH)+1);
		System.out.println(tableName.toString());*/
		
		
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
		
		//ByteBuffer使用测试
		/*ByteBuffer bb = ByteBuffer.allocate(100);
		String info = "test bytebuffer by dengyong";
		bb.put(info.getBytes());
		
		bb.clear();
		bb.put(new String("this is ok\r\n").getBytes());
		System.out.println("offset:"+bb.position());
		byte[] val = new byte[bb.position()];
		bb.flip();
		bb.get(val);
		System.out.println(new String(val));*/
		
		//volatile测试
		
		/*JSONObject json = new JSONObject();
		json.put("k1", "v1");
		json.put("k2", "v2");
		
		json.opt("k3");
		System.out.println(json.toString());
		
		JSONObject other = new JSONObject(json.toString());
		System.out.println(other.toString());*/
		
		//SPI 测试 jdk内置的一种服务提供发现机制
		/*ServiceLoader<IAction> loaders = ServiceLoader.load(IAction.class);
		for(IAction action : loaders){
			action.action();
		}*/
		
		
		//采用Iterator遍历,屏蔽数据结构细节,统一操作
		/*Set<String> ss = new HashSet<String>();
		ss.add("111");
		ss.add("222");
		
		for(String str : ss){
			System.out.println(str);
		}
		
		Iterator<String> it = ss.iterator();
		while(it.hasNext()){
			System.out.println(it.next());
		}
		
		Map<String,String> sm = new HashMap<String,String>();
		sm.put("k1", "v1");
		sm.put("k2", "v2");
		
		for(Entry<String,String> et : sm.entrySet()){
			System.out.println(et.getKey()+":"+et.getValue());
		}*/
		
		/*CaptchaStore4SMS.instance().saveCaptcha("12345", "18908464499");
		Thread.currentThread().sleep(20);
		System.out.println(CaptchaStore4SMS.instance().checkCaptcha4Account("12345", "18908464499"));*/
		
		
//		System.out.println(String.format("您的验证码为：%d，5分钟内有效，谢谢！【纽曼科技】",12));
		
		/*Map<String,String> map = new HashMap<String,String>();
		map.put("k1", "v1");
		map.put("k2", "v2");
		JSONObject obj = (JSONObject)JSONObject.toJSON(map);
		
		
		Item t = new Item();
		t.setInfo("nihao");
		
		JSONObject obj = (JSONObject)JSONObject.toJSON(t);
		JSONObject result = new JSONObject();
		
		result.putAll(obj);
		System.out.println(result.toString());*/

		//测试对图片进行base64编解码
		/*FileInputStream is = new FileInputStream(new File("e:\\11.jpg"));
		int size = is.available();
		byte[] content = new byte[size];
		is.read(content, 0, size);
		is.close();
		
		String encodeStr = Base64.encodeBase64String(content);
		System.out.println("base64 encode result:"+encodeStr);
		
		byte[] newImage = Base64.decodeBase64(encodeStr);
		
		FileOutputStream out = new FileOutputStream(new File("e:\\12.jpg"));
		out.write(newImage);
		out.close();*/
		
		/*List<Integer> values = new LinkedList<Integer>();
		
		values.add(1);
		values.add(2);
		values.add(3);
		values.add(4);
		values.add(5);
		values.add(6);
		values.add(7);
		values.add(8);
		values.add(9);
		
		Iterator<Integer> it = values.iterator();
		while(it.hasNext()){
			Integer value = it.next();
			if(value == 3 || value == 4 || value == 5){
				//values.remove(value);
			}
			
			//it.next();
		}
		
		for(Integer it : values){
			if(it == 3 || it == 4 || it == 5){
				values.remove(it);
			}
		}
		
		System.out.println(values.toString());*/
		
		System.out.println("89860615090031854408".compareTo("89860615090031854412"));
		System.out.println("89860615090031856395".compareTo("89860615090031854412"));
	}
}
