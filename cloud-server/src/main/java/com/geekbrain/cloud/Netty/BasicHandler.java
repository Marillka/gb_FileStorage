package com.geekbrain.cloud.Netty;

import com.geekbrain.cloud.Netty.requests.AuthRequest;
import com.geekbrain.cloud.Netty.requests.BasicRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class BasicHandler extends ChannelInboundHandlerAdapter {

    private final String LOGIN = "user";
    private final String PASSWORD = "pass";

//    private static final Map<Class<? extends BasicRequest>, Consumer<ChannelHandlerContext>> REQUEST_HANDLERS = new HashMap<>();
//
//    static {
//        REQUEST_HANDLERS.put(AuthRequest.class, channelHandlerContext -> {
//            channelHandlerContext.writeAndFlush("login ok");
//        });
//    }
//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("Клиент подключился: " + ctx);
//        System.out.println(ctx.channel().remoteAddress());
//    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // сюда прилетает уже десириализованный объект. Кастуем его для того чтобы можно было с ним работать.
        BasicRequest request = (BasicRequest) msg;
        System.out.println(request.getType());

        if (request instanceof AuthRequest) {
            if (((AuthRequest) msg).getLogin().equals(LOGIN) && msg.equals(PASSWORD)) {
                ctx.writeAndFlush("auth ok");
            } else {
                ctx.writeAndFlush("auth fail");
            }
        }
    }

//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        BasicRequest request = (BasicRequest) msg;
//
//        System.out.println(request.getType());
//        Consumer<ChannelHandlerContext> channelHandlerContextConsumer = REQUEST_HANDLERS.get(request.getType());
//        channelHandlerContextConsumer.accept(ctx);
//    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
