package top.monoliths.slight_server.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * HttpServerInitializer
 */
public class HttpServerInitializer extends ChannelInitializer<Channel> {

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();

        /**
         * http encode
         */
        pipeline.addLast("cwodec", new HttpServerCodec());

        /**
         * max content length
         */
        pipeline.addLast("aggregator", new HttpObjectAggregator(512 * 1024));

        /**
         * compressor
         */
        pipeline.addLast("compressor", new HttpContentCompressor());

        /**
         * handler message request
         */
        pipeline.addLast(new HttpRequestHandler());
    }
}
