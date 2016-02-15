package com.dy;

import java.util.concurrent.Executors;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.EventTranslatorVararg;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

public class DisruptorPractice {
	static class LocalEvent{
		private String info;

		public String getInfo() {
			return info;
		}

		public void setInfo(String info) {
			this.info = info;
		}
	}
	
	static class LocalEventFactory implements EventFactory<LocalEvent>{
		@Override
		public LocalEvent newInstance() {
			return new LocalEvent();
		}
		
	}
	
	static class LocalWorkHandler implements WorkHandler<LocalEvent>{

		@Override
		public void onEvent(LocalEvent event) throws Exception {
			System.out.println(Thread.currentThread().getId()+" processing event:"+event.getInfo());
		}
		
	}
	
	static class LocalEventTranslator implements EventTranslator<LocalEvent>{

		@Override
		public void translateTo(LocalEvent event, long sequence) {
			System.out.println(Thread.currentThread().getId()+" current sequence:"+sequence);
			event.setInfo(String.valueOf(Math.random()*10000));
		}
	}
	/**
	 * EventHandler用于链式处理事件,对于使用线程池来处理事件时就不能用EventHandler,因为每个EventHandler都会收到事件,
	 * 而使用WorkHandler则会保证只有一个Hanlder能接收到事件
	 *
	 */
	static class LocalEventHandler implements EventHandler<LocalEvent>{

		@Override
		public void onEvent(LocalEvent event, long sequence, boolean endOfBatch)
				throws Exception {
			System.out.println(Thread.currentThread().getId()+" processing event:"+event.getInfo()+",sequence is:"+sequence);
		}
		
	}
	
	
	static class EventPublishRunnable implements Runnable{
		private RingBuffer<LocalEvent> rb;
		
		public EventPublishRunnable(RingBuffer<LocalEvent> rb){
			this.rb = rb;
		}
		
		@Override
		public void run() {
			while(true){
				try{
					//直接从ringbuffer中获取元素设置好再发布
					/*
					long sequence = rb.next();
					System.out.println(Thread.currentThread().getId()+" current sequence:"+sequence);
					 try {
						 LocalEvent event = (LocalEvent)rb.get(sequence);
					     event.setInfo(String.valueOf(Math.random()*10000));
					 } finally {
					     rb.publish(sequence);
					 }*/
					
					//调用RingBuffer的pushlishEvent方法来发布
					rb.publishEvent(new LocalEventTranslator());
				}catch(Exception e){
					e.printStackTrace();
					break;
				}
			}
		}
		
	}
	
	public static void main(String[] args){
		/*Disruptor<LocalEvent> disruptor = new Disruptor<LocalEvent>(new LocalEventFactory(),16,Executors.newCachedThreadPool(),
				ProducerType.MULTI,new YieldingWaitStrategy());*/
		
		Disruptor<LocalEvent> disruptor = new Disruptor<LocalEvent>(new LocalEventFactory(),16,Executors.newFixedThreadPool(1),
				ProducerType.MULTI,new YieldingWaitStrategy());
		
		//采用EventHandler的话,一个任务会被多个消费者处理
		disruptor.handleEventsWith(new LocalEventHandler(),new LocalEventHandler()/*,new LocalEventHandler()*/);
		
		/*LocalWorkHandler handler = new LocalWorkHandler();
		disruptor.handleEventsWithWorkerPool(handler,handler,handler);*/
		
		RingBuffer<LocalEvent> rb = disruptor.start();
		
		EventPublishRunnable eventPublish = new EventPublishRunnable(rb);
		Thread evnentPublish1 = new Thread(eventPublish);
		/*Thread evnentPublish2 = new Thread(eventPublish);*/
		/*Thread evnentPublish3 = new Thread(eventPublish);*/
		evnentPublish1.start();
		/*evnentPublish2.start();*/
		/*evnentPublish3.start();*/
		
		try{
			evnentPublish1.join();
/*			evnentPublish2.join();*/
			/*evnentPublish3.join();*/
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		
		disruptor.shutdown();
	}
}
