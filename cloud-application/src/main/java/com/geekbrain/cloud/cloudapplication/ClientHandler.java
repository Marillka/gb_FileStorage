package com.geekbrain.cloud.cloudapplication;

import com.geekbrain.cloud.cloudapplication.dto.BasicResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        BasicResponse response = (BasicResponse) msg;
        String responseText = response.getResponse();

        if (responseText.equals("auth ok")) {
            System.out.println("Соединение установлено");
        } else {
            System.out.println("Неверный логин или пароль");
        }

    }
}
