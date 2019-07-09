package com.mistra.nw.helloNettyServer;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @ Author: WangRui
 * @ Version: 1.0
 * @ Time: 2019/7/9 22:24
 * @ Description: 初始化器，channel注册后，会执行里面响应的初始化方法
 */
public class HelloServerInitializer extends ChannelInitializer<SocketChannel> {

    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //通过socketChannel获取管道
        ChannelPipeline channelPipeline = socketChannel.pipeline();
        //通过管道添加handler，HttpServerCodec是netty自己提供的助手类，相当于拦截器
        //当请求到服务端，响应服务端，需要编码解码
        channelPipeline.addLast("HttpServerCodec", new HttpServerCodec());
        //添加自定义助手类，返回“hello”字符串
        channelPipeline.addLast("mistraHandler", new CustomHandler());
    }
}
