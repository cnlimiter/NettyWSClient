package cn.evolvefield.sdk.fastws.client.core;

import cn.evolvefield.sdk.fastws.client.WebSocketClient;

/**
 * Project: NettyWSClient
 * Author: cnlimiter
 * Date: 2023/4/13 0:42
 * Description:上下文转换器
 */
public interface ContextConverter {
    /**
     * 连接触发的回调
     * @param websocket 对 Netty 封装的对象
     */
    public void __onOpen__(WebSocketClient websocket);

    /**
     * 关闭触发的回调
     * @param websocket 对 Netty 封装的对象
     */
    public void __onClose__(WebSocketClient websocket);

    /**
     * 错误触发的回调
     * @param websocket 对 Netty 封装的对象
     */
    public void __onError__(WebSocketClient websocket, Throwable throwable);

    /**
     * 文本消息触发的回调
     * @param websocket 对 Netty 封装的对象
     */
    public void __onMessageText__(WebSocketClient websocket, String message);

    /**
     * 二进制流消息触发的回调
     * @param websocket 对 Netty 封装的对象
     */
    public void __onMessageBinary__(WebSocketClient websocket, byte[] message);

    /**
     * Pong 消息触发的回调
     * @param websocket 对 Netty 封装的对象
     */
    public void __onMessagePong__(WebSocketClient websocket, byte[] message);

    /**
     * Ping 消息连接触发的回调
     * @param websocket 对 Netty 封装的对象
     */
    public void __onMessagePing__(WebSocketClient websocket, byte[] message);

    /**
     * Continuation 消息触发的回调
     * @param websocket 对 Netty 封装的对象
     */
    public void __onMessageContinuation__(WebSocketClient websocket, byte[] message);
}
