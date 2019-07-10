package com.mistra.nw.websocketserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @ Author: WangRui
 * @ Version: 1.0
 * @ Time: 2019/7/9 23:48
 * @ Description:
 */
public class WebSocketServer {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup mainGroup = new NioEventLoopGroup();
        EventLoopGroup subGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(mainGroup, subGroup)// 设置主从线程组
                    .channel(NioServerSocketChannel.class)//设置Nio的双向通道
                    .childHandler(new WSServerInitialzer());//设置子处理器 用于处理workEventLoopGroup
            ChannelFuture channelFuture = serverBootstrap.bind(8088).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            mainGroup.shutdownGracefully();
            subGroup.shutdownGracefully();
        }

    }
}
