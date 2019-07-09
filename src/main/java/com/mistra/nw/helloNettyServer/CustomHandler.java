package com.mistra.nw.helloNettyServer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;



/**
 * @ Author: WangRui
 * @ Version: 1.0
 * @ Time: 2019/7/9 22:34
 * @ Description: 自定义助手类
 * SimpleChannelInboundHandler 对于请求来说，相当于入栈|  请求把数据放到缓冲区，服务器从缓冲区读取数据处理
 */
public class CustomHandler extends SimpleChannelInboundHandler<HttpObject> {

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject httpObject) throws Exception {
        //获取channel
        Channel channel = channelHandlerContext.channel();
        //打印客户端请求地址
        System.out.println(channel.remoteAddress());
        //设置响应内容
        ByteBuf content = Unpooled.copiedBuffer("hello mistra", CharsetUtil.UTF_8);

        //构建一个HttpResponse
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
        //设置响应内容的格式  文字 图片
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
        //设置长度
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

        //把响应内容刷到客户端
        channelHandlerContext.writeAndFlush(response);
    }
}
