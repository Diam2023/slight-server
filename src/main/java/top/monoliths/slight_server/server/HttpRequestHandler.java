package top.monoliths.slight_server.server;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;

import java.io.FileNotFoundException;

/**
 * top implement to ChannelHandler
 */
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest req)
        throws FileNotFoundException, Exception {
        ctx.writeAndFlush(HttpResponseHandler.response(req)).addListener(ChannelFutureListener.CLOSE);
    }
}
