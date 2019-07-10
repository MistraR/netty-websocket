package com.mistra.nw.websocketserver;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @ Author: WangRui
 * @ Version: 1.0
 * @ Time: 2019/7/10 21:07
 * @ Description:
 */
public class WSServerInitialzer extends ChannelInitializer<SocketChannel> {

    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline channelPipeline = socketChannel.pipeline();
        //websocket基于htpp协议，所以需要有http编解码器
        channelPipeline.addLast(new HttpServerCodec());
        //对写大数据流支持
        channelPipeline.addLast(new ChunkedWriteHandler());
        //对httpMessage进行聚合，聚合成FullHttpRequest或FullHttpResponse,在netty编程中，都会用到此handler
        channelPipeline.addLast(new HttpObjectAggregator(1024 * 64));


        //websocket服务器处理协议 ，用于指定客户端连接访问的路由/ws
        //此handler 处理握手动作 handshaking （close ping  pong）ping+pong=心跳
        //对websocket来说，都是以frames来传输的，不同的数据类型对应的frames也不同
        channelPipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        //自定义的handler助手类，读取用户的消息并处理
        channelPipeline.addLast(new ChatHandler());
    }
}
