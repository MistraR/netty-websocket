package com.mistra.nw.helloNettyServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @ Author: WangRui
 * @ Version: 1.0
 * @ Time: 2019/7/8 22:52
 * @ Description:
 */
public class HelloServer {

    public static void main(String[] args) throws InterruptedException {

        //主线程组，用于接收客户端连接，但不做任何处理
        EventLoopGroup mainEventLoopGroup = new NioEventLoopGroup();

        //从线程组，负责处理
        EventLoopGroup workEventLoopGroup = new NioEventLoopGroup();
        try {
            //netty服务器的创建，启动类
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(mainEventLoopGroup, workEventLoopGroup)// 设置主从线程组
                    .channel(NioServerSocketChannel.class)//设置Nio的双向通道
                    .childHandler(new HelloServerInitializer());//设置子处理器 用于处理workEventLoopGroup
            //启动server，绑定端口，启动方式为同步
            ChannelFuture channelFuture = serverBootstrap.bind(8088).sync();
            //监听关闭的channel，同步方式
            channelFuture.channel().closeFuture().sync();
        } finally {
            mainEventLoopGroup.shutdownGracefully();
            workEventLoopGroup.shutdownGracefully();
        }
    }
}
