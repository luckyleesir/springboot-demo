package com.lucky.common.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.UUID;

/**
 * 客户端
 */
public class NettyClient {

    private final String host;
    private final int port;
    private Channel channel;

    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }


    public void start() throws Exception {
        final EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap b = new Bootstrap();
        b.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                System.out.println("正在连接中...");
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new RpcEncoder(RpcRequest.class));
                pipeline.addLast(new RpcDecoder(RpcResponse.class));
                pipeline.addLast(new NettyClientHandler());

            }
        });
        //发起异步连接请求，绑定连接端口和host信息
        final ChannelFuture future = b.connect(host, port).sync();

        future.addListener((ChannelFutureListener) arg0 -> {
            if (future.isSuccess()) {
                System.out.println("连接服务器成功");

            } else {
                System.out.println("连接服务器失败");
                future.cause().printStackTrace();
                group.shutdownGracefully(); //关闭线程组
            }
        });
        this.channel = future.channel();
    }

    public Channel getChannel() {
        return channel;
    }

    public static void main(String[] args) throws Exception {
        NettyClient client = new NettyClient("127.0.0.1", 9001);
        client.start();
        Channel channel = client.getChannel();
        //消息体
        RpcRequest request = new RpcRequest();
        request.setId(UUID.randomUUID().toString());
        request.setData("client.message");
        //channel对象可保存在map中，供其它地方发送消息
        channel.writeAndFlush(request);
        System.out.println("1");
    }
}