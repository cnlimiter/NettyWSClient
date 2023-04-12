package cn.evolvefield.sdk.fastws.client.core.process;

import cn.evolvefield.sdk.fastws.client.WebSocketClient;

/**
 * Project: NettyWSClient
 * Author: cnlimiter
 * Date: 2023/4/13 0:42
 * Description:Web Socket Client 执行器
 */
public interface Actuator {
    /**
     * 连接成功的触发的事件
     * @param webSocket WebSocket 对象的封装
     */
    public void onOpen(WebSocketClient webSocket);

    /**
     * 连接关闭的触发的事件
     * @param webSocket WebSocket 对象的封装
     */
    public void onClose(WebSocketClient webSocket);

    /**
     * 连接错误的触发的事件
     * @param webSocket WebSocket 对象的封装
     * @param throwable 错误事件对象
     */
    public void onError(WebSocketClient webSocket, Throwable throwable);

    /**
     * 触发文本消息的事件
     * @param webSocket WebSocket 对象的封装
     */
    public void onMessageText(WebSocketClient webSocket, String message);

    /**
     * 触发二进制消息的事件
     * @param webSocket WebSocket 对象的封装
     * @param message 消息内容
     */
    public void onMessageBinary(WebSocketClient webSocket, byte[] message);

    /**
     * 触发 Pong 消息的事件
     * @param webSocket WebSocket 对象的封装
     * @param message 消息内容
     */
    public void onMessagePong(WebSocketClient webSocket, byte[] message);

    /**
     * 触发 Ping 消息的事件
     * @param webSocket WebSocket 对象的封装
     * @param message 消息内容
     */
    public void onMessagePing(WebSocketClient webSocket, byte[] message);

    /**
     * 触发 Continuation 消息的事件
     * @param webSocket WebSocket 对象的封装
     * @param message 消息内容
     */
    public void onMessageContinuation(WebSocketClient webSocket, byte[] message);
}
