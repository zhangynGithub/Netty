package com.itmayiedu;

import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

class ClientHandler extends SimpleChannelHandler {

	/**
	 * 通道关闭的时候触发
	 */
	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		System.out.println("channelClosed");
	}

	/**
	 * 必须是连接已经建立,关闭通道的时候才会触发.
	 */
	@Override
	public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		super.channelDisconnected(ctx, e);
		System.out.println("channelDisconnected");
	}

	/**
	 * 捕获异常
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		super.exceptionCaught(ctx, e);
		System.out.println("exceptionCaught");

	}

	/**
	 * 接受消息
	 */
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		super.messageReceived(ctx, e);
		// System.out.println("messageReceived");
		System.out.println("服务器端向客户端回复内容:" + e.getMessage());
		// 回复内容
		// ctx.getChannel().write("好的");
	}

}

public class NettyClient {

	public static void main(String[] args) {
		try {
			System.out.println("netty client启动...");
			// 创建客户端类
			ClientBootstrap clientBootstrap = new ClientBootstrap();
			// 线程池
			ExecutorService boos = Executors.newCachedThreadPool();
			ExecutorService worker = Executors.newCachedThreadPool();
			clientBootstrap.setFactory(new NioClientSocketChannelFactory(boos, worker));
			clientBootstrap.setPipelineFactory(new ChannelPipelineFactory() {

				public ChannelPipeline getPipeline() throws Exception {
					ChannelPipeline pipeline = Channels.pipeline();
					// 将数据转换为string类型.
					pipeline.addLast("decoder", new StringDecoder());
					pipeline.addLast("encoder", new StringEncoder());
					pipeline.addLast("clientHandler", new ClientHandler());
					return pipeline;

				}
			});
			// 连接服务端
			ChannelFuture connect = clientBootstrap.connect(new InetSocketAddress("127.0.0.1", 9090));
			Channel channel = connect.getChannel();
			System.out.println("client start");
			channel.write("itma");
//
//			channel.write("itma");
//
//			channel.write("itma");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
