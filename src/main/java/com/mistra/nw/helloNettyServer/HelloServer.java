package com.mistra.nw.helloNettyServer;

import io.netty.bootstrap.ServerBootstrap;
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

    public static void main(String[] args) {
        //主线程组，用于接收客户端连接，但不做任何处理
        EventLoopGroup mainEventLoopGroup = new NioEventLoopGroup();

        //从线程组，负责处理
        EventLoopGroup workEventLoopGroup = new NioEventLoopGroup();

        //启动类
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(mainEventLoopGroup, workEventLoopGroup)
                .channel(NioServerSocketChannel.class)
        .childHandler();
    }
}
