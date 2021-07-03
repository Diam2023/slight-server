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
import top.monoliths.slight_server.kernel.ResponseRulesMap;

/**
 * netty server
 */
public class HttpServer {

    /**
     * web server config data
     */
    public static ConfigData configData;

    /**
     * define response method of every request file use ConcurrentHashMao to keep
     * thread-safe
     */
    public static ResponseRulesMap responseRuls;

    /**
     * initial web config
     *
     * @param configData to set web config data
     */
    public HttpServer(ConfigData configData, ResponseRulesMap responseRuls) {
        HttpServer.configData = configData;
        HttpServer.responseRuls = responseRuls;
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

        bootstrap.group(boss, work).handler(new LoggingHandler(LogLevel.DEBUG)).channel(NioServerSocketChannel.class)
                .childHandler(new HttpServerInitializer());

        ChannelFuture channelFuture = bootstrap.bind(new InetSocketAddress(configData.getLocal(), configData.getPort()))
                .sync();

        channelFuture.channel().closeFuture().sync();
    }
}
