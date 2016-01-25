package com.dy;

import java.util.concurrent.Executors;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
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
		Disruptor<LocalEvent> disruptor = new Disruptor<LocalEvent>(new LocalEventFactory(),16,Executors.newFixedThreadPool(10));
		disruptor.handleEventsWith(new LocalEventHandler());
		disruptor.start();
		
		Thread evnentPublish1 = new Thread(new EventPublishRunnable(disruptor));
		Thread evnentPublish2 = new Thread(new EventPublishRunnable(disruptor));
		evnentPublish1.start();
		evnentPublish2.start();
		try{
			evnentPublish1.join();
			evnentPublish2.join();
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
}
