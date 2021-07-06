package top.monoliths.slight.server.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * HttpServerInitializer.
 * 
 * @author monoliths
 */
public class HttpServerInitializer extends ChannelInitializer<Channel> {

    /**
     * max content length.
     */
    private static final int MAX_CONTENT_LENGTH = 512 * 1024;

    @Override
    protected final void initChannel(final Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();

        /**
         * http encode
         */
        pipeline.addLast("cwodec", new HttpServerCodec());

        /**
         * max content length
         */
        pipeline.addLast("aggregator", new HttpObjectAggregator(MAX_CONTENT_LENGTH));

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
