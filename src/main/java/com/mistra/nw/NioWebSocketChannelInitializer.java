package com.mistra.nw;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;


/**
 * @ Author: WangRui
 * @ Version: 1.0
 * @ Time: 2019/7/3 22:27
 * @ Description:
 */
public class NioWebSocketChannelInitializer  extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) {
        //设置log监听器，并且日志级别为debug，方便观察运行流程
        ch.pipeline().addLast("logging",new LoggingHandler("DEBUG"));
        //设置解码器
        ch.pipeline().addLast("http-codec",new HttpServerCodec());
        //聚合器，使用websocket会用到
        ch.pipeline().addLast("aggregator",new HttpObjectAggregator(65536));
        //用于大数据的分区传输
        ch.pipeline().addLast("http-chunked",new ChunkedWriteHandler());
        //自定义的业务handler
        ch.pipeline().addLast("handler",new NioWebSocketHandler());
    }
}
