package top.monoliths.slight.server.server;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;

import java.io.FileNotFoundException;

/**
 * top implement to ChannelHandler.
 * 
 * @author monoliths
 */
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    @Override
    public final void channelReadComplete(final ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    protected final void channelRead0(final ChannelHandlerContext ctx, final FullHttpRequest req)
            throws FileNotFoundException, Exception {
        ctx.writeAndFlush(HttpResponseHandler.response(req)).addListener(ChannelFutureListener.CLOSE);
    }
}
