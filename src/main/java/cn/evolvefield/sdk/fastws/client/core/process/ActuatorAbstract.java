package cn.evolvefield.sdk.fastws.client.core.process;

import cn.evolvefield.sdk.fastws.client.WebSocketClient;

/**
 * Project: NettyWSClient
 * Author: cnlimiter
 * Date: 2023/4/13 0:48
 * Description:
 * <p>
 * Actuator 处理类
 * ContextConverter 转换类
 * 接收服务端的消息转换为处理类处理（同步的实现）
 */
public abstract class ActuatorAbstract extends BaseActuator {

    @Override
    public void __onOpen__(WebSocketClient websocket) {
        this.onOpen(websocket);
        System.gc(); // 回收掉生成的一次性的对象，避免内存泄漏
    }

    @Override
    public void __onClose__(WebSocketClient websocket) {
        this.onClose(websocket);
        System.gc(); // 回收掉生成的一次性的对象，避免内存泄漏
    }

    @Override
    public void __onError__(WebSocketClient websocket, Throwable throwable) {
        this.onError(websocket, throwable);
        System.gc(); // 回收掉生成的一次性的对象，避免内存泄漏
    }

    @Override
    public void __onMessageText__(WebSocketClient websocket, String message) {
        this.onMessageText(websocket, message);
        System.gc(); // 回收掉生成的一次性的对象，避免内存泄漏
    }

    @Override
    public void __onMessageBinary__(WebSocketClient websocket, byte[] message) {
        this.onMessageBinary(websocket, message);
        System.gc(); // 回收掉生成的一次性的对象，避免内存泄漏
    }

    @Override
    public void __onMessagePong__(WebSocketClient websocket, byte[] message) {
        this.onMessagePong(websocket, message);
        System.gc(); // 回收掉生成的一次性的对象，避免内存泄漏
    }

    @Override
    public void __onMessagePing__(WebSocketClient websocket, byte[] message) {
        this.onMessagePing(websocket, message);
        System.gc(); // 回收掉生成的一次性的对象，避免内存泄漏
    }

    @Override
    public void __onMessageContinuation__(WebSocketClient websocket, byte[] message) {
        this.onMessageContinuation(websocket, message);
        System.gc(); // 回收掉生成的一次性的对象，避免内存泄漏
    }

    @Override
    public String toType() {
        return "SYNCHRONIZATION_TYPE";
    }
}
