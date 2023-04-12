package cn.evolvefield.sdk.fastws.client.core.model;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Project: NettyWSClient
 * Author: cnlimiter
 * Date: 2023/4/13 0:57
 * Description:
 * Nio 的实现
 */
public class NioModel extends Model {

    @Override
    protected EventLoopGroup group(Bootstrap bootstrap) {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        bootstrap.group(eventLoopGroup);
        return eventLoopGroup;
    }

    @Override
    protected void option(Bootstrap bootstrap) {

    }

    @Override
    public void channel(Bootstrap bootstrap) {
        bootstrap.channel(NioSocketChannel.class);
    }

}
