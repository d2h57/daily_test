package com.dy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NettyClient {
	/*static class EchoClientHandler extends ChannelInboundHandlerAdapter{
		@Override
	    public void channelActive(ChannelHandlerContext ctx) throws Exception {
			//ctx.writeAndFlush("this is dengyong!");
	    }
		
		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg)
				throws Exception {
			// TODO Auto-generated method stub
			ByteBuf bb = (ByteBuf)msg;
			byte[] content = new byte[bb.readableBytes()];
			bb.readBytes(content);
			
			String info = new String(content,"UTF-8");
			System.out.println("receive msg from server:"+info);
		}
		
		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
	            throws Exception {
	        ctx.close();
	    }
	}*/
	
	public static void main(String[] args) throws Exception {
		/*Bootstrap  clientBootstrap = new Bootstrap();
		NioEventLoopGroup workGroup = new NioEventLoopGroup();
		try{
			clientBootstrap.group(workGroup).option(ChannelOption.TCP_NODELAY, true)
			.channel(NioSocketChannel.class)
			.handler(new ChannelInitializer<SocketChannel>(){
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					// TODO Auto-generated method stub
					ch.pipeline().addLast(new EchoClientHandler());
				}
			});
		
		Channel channel= clientBootstrap.connect("localhost", 62000).sync().channel();
		channel.write("this is dengyong!");
		Thread.sleep(1000*60*3);
		channel.closeFuture().sync();
		}catch(InterruptedException e){
			e.printStackTrace();
		}finally{
			workGroup.shutdownGracefully();
		}*/
		
		/*SocketChannel channel = null;
		try{
			InetSocketAddress address = new InetSocketAddress("localhost",62000);
			channel = SocketChannel.open(address);
			String info = "this is dengyong!";
			ByteBuffer bb = ByteBuffer.allocate(1024);
			bb.put(info.getBytes());
			channel.write(bb);
			System.out.println("send message finish!");
			
			bb.clear();
			int num = channel.read(bb);
			System.out.println("receive from server:"+new String(bb.array(),"UTF-8"));
			
			channel.close();
			System.out.println("close client channel!");
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if(null != channel && channel.isOpen()){
				try{
					channel.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}*/
		
		
		Socket socket = new Socket();
		BufferedWriter writer = null;
		BufferedReader reader = null;
		
		try{
			SocketAddress address = new InetSocketAddress("localhost",62000);
			socket.connect(address);
			System.out.println("finish connect to server");
			String info = "this is dengyong!";
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			writer.write(info);
			writer.flush();
			System.out.println("send message finish!");
			
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println("receive from server:"+reader.readLine());
			
			System.out.println("close client channel!");
			
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if(null != writer){
				writer.close();
			}
			if(null != reader){
				reader.close();
			}
			if(!socket.isClosed()){
				socket.close();
			}
		}
	}
}
