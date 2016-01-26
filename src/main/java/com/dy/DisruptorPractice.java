package com.dy;

import java.util.concurrent.Executors;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.dsl.Disruptor;

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
		private Disruptor<LocalEvent> disruptor;
		
		public EventPublishRunnable(Disruptor<LocalEvent> disruptor){
			this.disruptor = disruptor;
		}
		
		@Override
		public void run() {
			while(true){
				RingBuffer<LocalEvent> rb = disruptor.getRingBuffer();
				long sequence = rb.next();
				System.out.println(Thread.currentThread().getId()+" current sequence:"+sequence);
				 try {
					 LocalEvent event = (LocalEvent)rb.get(sequence);
				     event.setInfo(String.valueOf(Math.random()*10000));
				 } finally {
				     rb.publish(sequence);
				 }
			}
		}
		
	}
	
	public static void main(String[] args){
		Disruptor<LocalEvent> disruptor = new Disruptor<LocalEvent>(new LocalEventFactory(),16,Executors.newFixedThreadPool(2));
		//disruptor.handleEventsWith(new LocalEventHandler(),new LocalEventHandler(),new LocalEventHandler());
		disruptor.handleEventsWithWorkerPool(new LocalWorkHandler(),new LocalWorkHandler(),new LocalWorkHandler());
		disruptor.start();
		
		//disruptor.p(eventTranslator);
		
		EventPublishRunnable eventPublish = new EventPublishRunnable(disruptor);
		Thread evnentPublish1 = new Thread(eventPublish);
		Thread evnentPublish2 = new Thread(eventPublish);
		Thread evnentPublish3 = new Thread(eventPublish);
		evnentPublish1.start();
		evnentPublish2.start();
		evnentPublish3.start();
		try{
			evnentPublish1.join();
			evnentPublish2.join();
			evnentPublish3.join();
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
}
