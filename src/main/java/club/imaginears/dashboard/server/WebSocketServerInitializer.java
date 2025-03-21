package club.imaginears.dashboard.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * Created by Marc on 6/15/15
 */
public class WebSocketServerInitializer extends ChannelInitializer<SocketChannel> {

    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator((int) Math.pow(2, 20)));
        pipeline.addLast(new WebSocketServerHandler());
    }
}