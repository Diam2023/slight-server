package top.monoliths.slight.server.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import top.monoliths.slight.server.kernel.ResponseRulesMap;
import top.monoliths.slight.server.utils.ConfigData;

import java.net.InetSocketAddress;

/**
 * netty server.
 *
 * @author monoliths
 */
public class HttpServer {

    /**
     * web server config data.
     */
    public static ConfigData configData;

    /**
     * define response method of every request file<br>
     * use ConcurrentHashMao to keep thread-safe.
     */
    public static ResponseRulesMap responseRuls;

    /**
     * initial web config.
     *
     * @param cd to set web config data
     * @param rr response rules
     */
    public HttpServer(final ConfigData cd, final ResponseRulesMap rr) {
        HttpServer.configData = cd;
        HttpServer.responseRuls = rr;
    }

    /**
     * start http server.
     * 
     * @throws Exception Exception
     */
    public final void start() throws Exception {
        /**
         * get ServerBootstrap inctance to bind and start server.
         */
        ServerBootstrap bootstrap = new ServerBootstrap();

        /**
         * create executor boss.
         * adapt to accept new connect.
         */
        EventLoopGroup boss = new NioEventLoopGroup();

        /**
         * create executor work.
         * adapt to read or write data of connect.
         */
        EventLoopGroup work = new NioEventLoopGroup();

        /**
         * group to issue executor modules.
         * channel to define IO module.
         * childHandler business processing logic
         */
        bootstrap.group(boss, work).handler(new LoggingHandler(LogLevel.DEBUG)).channel(NioServerSocketChannel.class)
                .childHandler(new HttpServerInitializer());

        ChannelFuture channelFuture = bootstrap.bind(new InetSocketAddress(configData.getLocal(), configData.getPort()))
                .sync();

        channelFuture.channel().closeFuture().sync();
    }
}
