package com.mistra.nw.websocketserver;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @ Author: WangRui
 * @ Version: 1.0
 * @ Time: 2019/7/10 21:19
 * @ Description: 处理消息的handler
 * TextWebSocketFrame 在netty中，用于为websocket专门处理文本的对象，frame是消息的载体
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    //用于管理所有客户端的channel
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        //获取客户端发送的消息
        String content = textWebSocketFrame.text();
        System.out.println("接收到的数据：" + content);
        for (Channel channel : channelGroup) {
            channel.writeAndFlush(new TextWebSocketFrame("服务器接收到消息的时间:" + System.currentTimeMillis() + "消息为：" + content));
        }
        //效果跟上面一样
        //channelGroup.writeAndFlush();
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //当客户端连接服务之后，获取客户端的channel，交给channelGroup来管理
        channelGroup.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        //当触发到handlerRemoved方法的时候，channelGroup会自动移除对应的channel，下面这句代码可不写
        //channelGroup.remove(ctx.channel());
        System.out.println(ctx.channel().id());
    }
}
