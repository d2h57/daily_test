package com.dy;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class NettyServer {
	static class Message{
		private String info;

		public String getInfo() {
			return info;
		}

		public void setInfo(String info) {
			this.info = info;
		}
	}
	
	static class MessageHandler extends SimpleChannelInboundHandler<Message>{

		@Override
		protected void channelRead0(ChannelHandlerContext ctx, Message msg)
				throws Exception {
			// TODO Auto-generated method stub
			System.out.println(msg.toString());
		}
	}
	
	static class EchoHandler extends ChannelInboundHandlerAdapter{
		@Override
	    public void channelActive(ChannelHandlerContext ctx) throws Exception {
			System.out.println("receive connection from client");
	    }
		
		@Override		
		public void channelRead(ChannelHandlerContext ctx, Object msg)
				throws Exception {
			ByteBuf bb = (ByteBuf)msg;
			byte[] content = new byte[bb.readableBytes()];
			bb.readBytes(content);
			
			String info = new String(content,"UTF-8");
			System.out.println("msg from client:"+info);
			
			bb.clear();
			bb.writeBytes(("hello:"+info).getBytes());
			ctx.writeAndFlush(bb).sync();
		}
		
		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
	            throws Exception {
			System.out.println("exception happend!");
	        ctx.close().syncUninterruptibly();
	    }
	}
	
	static class EchoOutboundHanlder extends ChannelOutboundHandlerAdapter{
		
	}
	
	public static void main(String[] args){
		
		NioEventLoopGroup bossGroup = new NioEventLoopGroup();
		NioEventLoopGroup workGroup = new NioEventLoopGroup();
		try{
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workGroup)
				.channel(NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG,100)
				/*.handler(new LoggingHandler(LogLevel.DEBUG))*/
				.childHandler(new ChannelInitializer<SocketChannel>(){
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						// TODO Auto-generated method stub
						ch.pipeline().addLast(new EchoHandler());
					}
				});
			ChannelFuture channel =  bootstrap.bind(62000).sync();
			System.out.println("bind channel on 62000");
			channel.channel().closeFuture().sync();
			System.out.println("serverbootstrap shutdown!");
		}catch(InterruptedException e){
			e.printStackTrace();
		}finally{
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
		
	}
}
