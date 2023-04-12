package cn.evolvefield.sdk.fastws.client.core.model;



import cn.evolvefield.sdk.fastws.client.WebSocketClient;
import cn.evolvefield.sdk.fastws.client.core.ContextConverter;
import cn.evolvefield.sdk.fastws.client.core.process.ActuatorDefault;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * Project: NettyWSClient
 * Author: cnlimiter
 * Date: 2023/4/13 0:55
 * Description:
 * 数据管道处理类
 */
public class ModelHandler implements ChannelInboundHandler {

    /** 注入日志对象 */
    private static final Logger logger = LoggerFactory.getLogger(ModelHandler.class);

    private volatile String id;
    private ModelCache modelCache;
    private ContextConverter contextConverter;
    private WebSocketClientHandshaker socketClientHandshaker;

    public ModelHandler(WebSocketClientHandshaker webSocketClientHandshaker,
                           ModelCache ModelCache, ContextConverter ContextConverter) {
        this.modelCache = ModelCache;
        this.contextConverter = ContextConverter == null ? new ActuatorDefault() : ContextConverter;
        this.socketClientHandshaker = webSocketClientHandshaker;
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) {
        logger.debug("( " + id + " ) [ channelRegistered ] ==> " + ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) {
        logger.debug("( " + id + " ) [ channelUnregistered ] ==> " + ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        try {
            logger.debug("( " + id + " ) [ channelActive ] ==> " + ctx);
            socketClientHandshaker.handshake(ctx.channel());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        logger.debug("( " + id + " ) [ channelInactive ] ==> " + ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        logger.debug("( " + id + " ) [ channelRead ] ==> " + ctx);
        try {
            Channel channel = ctx.channel();
            if (!socketClientHandshaker.isHandshakeComplete()) { // 判断是否完成握手
                try {
                    socketClientHandshaker.finishHandshake(channel, (FullHttpResponse) msg); // 结束握手
                    id = UUID.randomUUID().toString().replaceAll("-", "").toLowerCase(); // 客户端生成唯一ID
                    WebSocketClient WebSocketClient = new WebSocketClient(id, ctx); // 创建 WebSocketClient
                    modelCache.put(id, WebSocketClient);
                    if (id != null) contextConverter.__onOpen__(WebSocketClient); // 初始化
                    else logger.error("[ CLIENT ID ] ==> NULL.");
                } catch (WebSocketHandshakeException e) {
                    e.printStackTrace();
                }
            } else if (msg instanceof FullHttpResponse) { // 判断是否为 HTTP 返回的状态码
                FullHttpResponse response = (FullHttpResponse) msg;
                if (id != null) {
                    WebSocketClient webSocket = modelCache.get(id);
                    modelCache.del(id);
                    contextConverter.__onError__(webSocket,
                            new Exception("Unexpected FullHttpResponse [ "
                                    + response.status() + " ] ==> "
                                    + response.content().toString(CharsetUtil.UTF_8)));
                    contextConverter.__onClose__(webSocket);
                } else logger.error("[ CLIENT ID ] ==> NULL.");
            } else {
                if (id != null) {
                    WebSocketClient webSocket = modelCache.get(id);
                    if (webSocket != null) {
                        WebSocketFrame frame = (WebSocketFrame) msg;
                        ByteBuf byteBuf = frame.content();
                        byte[] bytes = new byte[byteBuf.readableBytes()];
                        byteBuf.readBytes(bytes);
                        byteBuf.release();
                        if (frame instanceof BinaryWebSocketFrame) {
                            contextConverter.__onMessageBinary__(webSocket, bytes);
                        } else if (frame instanceof TextWebSocketFrame) {
                            contextConverter.__onMessageText__(webSocket, new String(bytes));
                        } else if (frame instanceof PongWebSocketFrame) {
                            contextConverter.__onMessagePong__(webSocket, bytes);
                        } else if (frame instanceof PingWebSocketFrame) {
                            contextConverter.__onMessagePing__(webSocket, bytes);
                        } else if (frame instanceof ContinuationWebSocketFrame) {
                            contextConverter.__onMessageContinuation__(webSocket, bytes);
                        }
                    }
                } else logger.error("[ CLIENT ID ] ==> NULL.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        logger.debug("( " + id + " ) [ channelReadComplete ] ==> " + ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        logger.debug("( " + id + " ) [ userEventTriggered ] ==> " + ctx);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) {
        logger.debug("( " + id + " ) [ channelWritabilityChanged ] ==> " + ctx);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        logger.debug("( " + id + " ) [ handlerAdded ] ==> " + ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        logger.debug("( " + id + " ) [ handlerRemoved ] ==> " + ctx);
        if (id != null) {
            modelCache.del(id);
            contextConverter.__onClose__(modelCache.get(id));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.debug("( " + id + " ) [ exceptionCaught ] ==> " + ctx);
        cause.printStackTrace();
        if (id != null) contextConverter.__onError__(modelCache.get(id), cause);
        ctx.close(); // 关闭管道
    }
}
