package com.dy;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

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
	static class EchoClientHandler extends ChannelInboundHandlerAdapter{
		@Override
	    public void channelActive(ChannelHandlerContext ctx) throws Exception {
			System.out.println("channelActive");
			
			String info = "this is dengyong!";
			ByteBuf bb = ByteBufAllocator.DEFAULT.buffer();
			bb.writeBytes(info.getBytes());
			ctx.writeAndFlush(bb);
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
	}
	
	public static void main(String[] args) throws Exception {
		//使用Bootstrap编写客户端
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
		channel.closeFuture().sync();
		}catch(InterruptedException e){
			e.printStackTrace();
		}finally{
			workGroup.shutdownGracefully();
		}*/
		
		
		SocketChannel channel = null;
		try{
			InetSocketAddress address = new InetSocketAddress("localhost",62000);
			channel = SocketChannel.open(address);
			String info = "this is dengyong!";
			ByteBuffer bb = ByteBuffer.allocate(1024);
			bb.put(info.getBytes());
			bb.flip();
			channel.write(bb);
			System.out.println("send message finish!");
			
			bb.clear();
			channel.read(bb);
			System.out.println("receive from server:"+new String(bb.array()));
			
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
		}
		
		//使用socket编写客户端,注意使socket.readLine()接收数据时服务端返回时要记得带上\r\n
		/*Socket socket = new Socket();
		BufferedWriter writer = null;
		BufferedReader reader = null;
		
		try{
			SocketAddress address = new InetSocketAddress("localhost",62000);
			socket.connect(address);
			
			String info = "this is dengyong!";
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			writer.write(info);
			writer.flush();
			System.out.println("send message finish!");
			
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			if((info = reader.readLine()) != null){
				System.out.println("receive from server:"+info);
			}
			
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
		}*/
	}
}
