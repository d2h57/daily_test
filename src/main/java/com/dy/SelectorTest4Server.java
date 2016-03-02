package com.dy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class SelectorTest4Server {
	//Nio工作线程处理所有网络事件,包括连接请求,请求处理
	static class NioRunnable implements Runnable{
		private Selector sel;
		
		public NioRunnable(Selector sel){
			this.sel = sel;
		}
		
		@Override
		public void run(){
			while(true){
				try{
					sel.select();
					Iterator<SelectionKey> keys = sel.selectedKeys().iterator();
					while(keys.hasNext()){
						try{
							SelectionKey key = keys.next();
							if(key.isAcceptable()){
								ServerSocketChannel serverSocket = (ServerSocketChannel)key.channel();
								SocketChannel socket = serverSocket.accept();
								socket.configureBlocking(false);
								socket.register(sel, SelectionKey.OP_READ);
							} else if (key.isReadable()) {
								SocketChannel socket = (SocketChannel) key.channel();
								ByteBuffer bb = ByteBuffer.allocate(100);
								int result = socket.read(bb);
								if (-1 == result) {//判断远端socket是否已经关闭
									System.out.println("socket has closed by peer");
									socket.close();
									key.cancel();
								} else {
									System.out.println("receive content:"+ new String(bb.array()).trim()
											+ ",from remote:"+ socket.getRemoteAddress().toString());
									bb.clear();
									bb.put(new String("this is ok\r\n").getBytes());
									// 此处要注意,往ByteBuffer里写入内容后,指针已经移动,将ByteBuffer内容输出到socket中时需要调用flip将指针重新置为0
									bb.flip();
									socket.write(bb);
								}
							}
						}finally{
							keys.remove();
						}
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	
	static class AcceptRunnable implements Runnable{
		private Selector sel;
		
		public AcceptRunnable(Selector sel){
			this.sel = sel;
		}
		
		@Override
		public void run(){
			while(true){
				try{
					sel.select();
					Iterator<SelectionKey> keys = sel.selectedKeys().iterator();
					while(keys.hasNext()){
						try{
							SelectionKey key = keys.next();
							if(key.isAcceptable()){
								ServerSocketChannel serverSocket = (ServerSocketChannel)key.attachment();
								SocketChannel socket = serverSocket.accept();
								socket.configureBlocking(false);
								Selector socketSel = Selector.open();
								socket.register(socketSel, SelectionKey.OP_READ, socket);
								new Thread(new ReadRunnable(socketSel)).start();
							}
						}finally{
							keys.remove();
						}
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	
	//采用
	static class ReadRunnable implements Runnable{
		private Selector sel;
		
		public ReadRunnable(Selector sel){
			this.sel = sel;
		}
		
		@Override
		public void run(){
			while(true){
				try{
					sel.select();
					Iterator<SelectionKey> keys = sel.selectedKeys().iterator();
					while(keys.hasNext()){
						try{
							SelectionKey key = keys.next();
							if(key.isReadable()){
								SocketChannel socket = (SocketChannel)key.attachment();
								ByteBuffer bb = ByteBuffer.allocate(100);
								int result = socket.read(bb);
								if (-1 == result) {//判断远端socket是否已经关闭
									System.out.println("socket has closed by peer");
									socket.close();
									key.cancel();
								} else {
									System.out.println("receive content:"+new String(bb.array()).trim()+",from remote:"+socket.getRemoteAddress().toString());
									
									bb.clear();
									bb.put(new String("this is ok\r\n").getBytes());
									//此处要注意,往ByteBuffer里写入内容后,指针已经移动,将ByteBuffer内容输出到socket中时需要调用flip将指针重新置为0
									bb.flip();
									socket.write(bb);
								}
							}
						}finally{
							keys.remove();
						}
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) throws Exception{
		try{
			ServerSocketChannel server = ServerSocketChannel.open();
			SocketAddress address = new InetSocketAddress(62000);
			server.bind(address);
			server.configureBlocking(false);
			
			Selector selector = Selector.open();
			server.register(selector, SelectionKey.OP_ACCEPT);
			//采用主从线程模型,主线程负责接收连接请求,从线程负责处理数据请求
//			Thread t = new Thread(new AcceptRunnable(selector));
			
			//采用单线程模型处理网络事件
			Thread t = new Thread(new NioRunnable(selector));
			t.start();
			t.join();
			selector.close();
			server.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
