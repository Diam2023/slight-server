package top.monoliths.slight_server.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import java.net.InetSocketAddress;
import top.monoliths.slight_server.kernel.ConfigData;

/**
 * netty server 2018/11/1.
 */
public class HttpServer {

    /**
     * web server config data
     */
    public static ConfigData configData;

    /**
     * initial web config
     *
     * @param configData to set web config data
     */
    public HttpServer(ConfigData configData) {
        HttpServer.configData = configData;
    }

    public void start() throws Exception {
        ServerBootstrap bootstrap = new ServerBootstrap();

        /**
         * boss
         */
        EventLoopGroup boss = new NioEventLoopGroup();

        /**
         * work
         */
        EventLoopGroup work = new NioEventLoopGroup();

        bootstrap
            .group(boss, work)
            .handler(new LoggingHandler(LogLevel.DEBUG))
            .channel(NioServerSocketChannel.class)
            .childHandler(new HttpServerInitializer());

        ChannelFuture channelFuture = bootstrap
            .bind(new InetSocketAddress(configData.getLocal(), configData.getPort()))
            .sync();

        System.out.println(configData);
        System.out.println("server: http://" + configData.getLocal() + ":" + configData.getPort() + "/" + configData.getHome());

        channelFuture.channel().closeFuture().sync();
    }
}
