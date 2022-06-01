package com.geekbrain.cloud.cloudapplication;

import com.geekbrain.cloud.cloudapplication.dto.AuthRequest;
import com.geekbrain.cloud.cloudapplication.dto.BasicRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

//public class Client {
//
//    private SocketChannel channel;
//    private static final String HOST = "localhost";
//    private static final int PORT = 8189;
//
//    public Client() {
//        new Thread(() -> {
//            EventLoopGroup workerGroup = new NioEventLoopGroup();
//            try {
//                Bootstrap b = new Bootstrap();
//                b.group(workerGroup);
//                b.channel(NioSocketChannel.class);
//                b.handler(new ChannelInitializer<SocketChannel>() {
//                    @Override
//                    protected void initChannel(SocketChannel socketChannel) throws Exception {
//                        channel = socketChannel;
//                        socketChannel.pipeline().addLast(new StringDecoder(), new StringEncoder());
//                    }
//                });
//                ChannelFuture future = b.connect(HOST, PORT).sync();
//                future.channel().closeFuture().sync();
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                workerGroup.shutdownGracefully();
//            }
//        }).start();
//    }
//
//    public void sendMessage(BasicRequest request) {
//        channel.writeAndFlush(request);
//    }
//}


public class Client {

    private static final int MB_20 = 20 * 1_000_000;
    private SocketChannel channel;
    private static final String HOST = "localhost";
    private static final int PORT = 8189;

    public Client() {
        new Thread(() -> {
            EventLoopGroup workerGroup = new NioEventLoopGroup();
            try {
                Bootstrap bootstrap = new Bootstrap();
                bootstrap.group(workerGroup);
                bootstrap.channel(NioSocketChannel.class);
                bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        channel = socketChannel;
                        socketChannel.pipeline().addLast(
                                new ObjectDecoder(MB_20, ClassResolvers.cacheDisabled(null)),
                                new ObjectEncoder(),
                                new ClientHandler()
                        );
                    }
                });
                ChannelFuture channelFuture = bootstrap.connect(HOST, PORT).sync();
                channelFuture.channel().closeFuture().sync();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                workerGroup.shutdownGracefully();
            }
        }).start();
    }

    public void sendAuthRequestMessage(AuthRequest request) {
        channel.writeAndFlush(request);
    }





}






