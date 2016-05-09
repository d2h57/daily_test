package com.dy;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CaptchaStore4SMS {
	private static CaptchaStore4SMS instance = new CaptchaStore4SMS();
	private ConcurrentMap<String,Map<String,Object>> accountCaptcha;
	
	private static final int EXPIRATION_TIME = 5*60*1000;//过期时间单位为毫秒
	private static final int ONE_DAY_IN_MILLIS = 24*60*60*1000;//1天对应的毫秒数
	private static final int ONE_MINIUTE_IN_MILLIS = 1*60*1000;//1分钟对应的毫秒数
	private static final int GETTING_CAPTCHA_TIMES_ONE_DAY = 5;//1天之内最多只能获取5次验证码
	private static final int CAPTCHA_UNCHECKED = 0;//验证码未校验
	private static final int CAPTCHA_CHECKED = 1;//验证码已经校验
	
	private static final String CAPTCHA_FIELD = "captcha";//缓存验证码
	private static final String REQUEST_TIME_FIELD = "requestTime";//缓存获取验证码时间
	private static final String TIMES_FIELD = "times";//缓存1天内获取验证码次数
	private static final String CHECKED_FIELD = "checked";//缓存验证码是否校验
	
	private CaptchaStore4SMS(){
		accountCaptcha = new ConcurrentHashMap<String,Map<String,Object>>();
	}
	
	public static CaptchaStore4SMS instance(){
		return instance;
	}
	
	public synchronized void saveCaptcha(String accountId,String captcha){
		Map<String,Object> info = accountCaptcha.get(accountId);
		if(null != info){
			info.put(CAPTCHA_FIELD,captcha);
			info.put(REQUEST_TIME_FIELD,System.currentTimeMillis());
			info.put(TIMES_FIELD,((Integer)info.get(TIMES_FIELD)).intValue()+1);
			info.put(CHECKED_FIELD,CAPTCHA_UNCHECKED);
		}else{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(CAPTCHA_FIELD,captcha);
			map.put(REQUEST_TIME_FIELD,System.currentTimeMillis());
			map.put(TIMES_FIELD,0);
			map.put(CHECKED_FIELD,CAPTCHA_UNCHECKED);
			accountCaptcha.put(accountId,map);
		}
	}
	
	/**
	 * 判断当前账号是否有获取过验证码
	 * @param accountId
	 * @return
	 */
	public synchronized boolean hasReceiveCaptcha(String accountId){
		boolean result = false;
		Map<String,Object> info = accountCaptcha.get(accountId);
		if(null != info){
			if(CAPTCHA_UNCHECKED == ((Integer)info.get(CHECKED_FIELD)).intValue()){
				return true;
			}
		}
		return result;
	}
	
	/**
	 * 
	 * @param captcha
	 * @param accountId
	 * @return 0:验证码正确,且时间有效
	 * 		   1:验证码正确,但是过期
	 *         2:验证码错误
	 */
	public synchronized int checkCaptcha4Account(String accountId,String captcha){
		int result = 0;
		Map<String,Object> info = accountCaptcha.get(accountId);
		if(null != info){
			if(captcha.equals((String)info.get(CAPTCHA_FIELD))){
				if(System.currentTimeMillis() - ((Long)info.get(REQUEST_TIME_FIELD)).longValue() < EXPIRATION_TIME){//验证码5分钟内有效
					info.put(CHECKED_FIELD,CAPTCHA_CHECKED);
					result = 0;
				}else{
					result = 1;
				}
			}else{
				result = 2;
			}
		}
		
		return result;
	}
	
	/**
	 * 检查账户获取验证码间隔时间
	 * 
	 * @param accountId
	 * @return 0:可以获取验证码
	 * 		   1:1分钟之内不能重复获取
	 * 		   2:一天之内获取验证码不能超过5次
	 */
	public synchronized int checkGettingCaptchaIfInvalid(String accountId){
		int result = 0;
		Map<String,Object> info = accountCaptcha.get(accountId);
		if(null != info){
			long diffMillis = System.currentTimeMillis() - ((Long)info.get(REQUEST_TIME_FIELD)).longValue();
			int times = ((Integer)info.get(TIMES_FIELD)).intValue();
			
			if(times >= GETTING_CAPTCHA_TIMES_ONE_DAY){
				if(diffMillis >= ONE_DAY_IN_MILLIS){//获取验证码间隔时间已经超过1天,可以重新获取
					info.put(TIMES_FIELD,0);
				}else{//超过5次就不能再次获取
					result = 2;
				}
			}else{
				if(diffMillis < ONE_MINIUTE_IN_MILLIS){//1分钟之内不能重复发送
					result = 1;
				}
			}
		}
		
		return result;
	}
}
