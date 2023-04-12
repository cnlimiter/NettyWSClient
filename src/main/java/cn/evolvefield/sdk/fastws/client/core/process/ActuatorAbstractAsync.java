package cn.evolvefield.sdk.fastws.client.core.process;

import cn.evolvefield.sdk.fastws.client.WebSocketClient;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Project: NettyWSClient
 * Author: cnlimiter
 * Date: 2023/4/13 0:50
 * Description:
 * <p>
 * Actuator 处理类
 * ContextConverter 转换类
 * 接收服务端的消息转换为处理类处理（异步的实现）
 */
public abstract class ActuatorAbstractAsync extends BaseActuator {

    /**
     * 创建一个默认的线程池，大小为 30 个线程
     *
     * 所有的异步线程的共用一个线程池
     */
    protected static ThreadPoolExecutor threadPool = null;

    public static void init(ThreadPoolExecutor tp) {
        threadPool = tp;
    }

    @Override
    public void __onOpen__(WebSocketClient websocket) {
        if (threadPool == null || threadPool.isShutdown()) return;
        threadPool.execute(() -> {
            this.onOpen(websocket);
            System.gc(); // 回收掉生成的一次性的对象，避免内存泄漏
        });
    }

    @Override
    public void __onClose__(WebSocketClient websocket) {
        if (threadPool == null || threadPool.isShutdown()) return;
        threadPool.execute(() -> {
            this.onClose(websocket);
            System.gc(); // 回收掉生成的一次性的对象，避免内存泄漏
        });
    }

    @Override
    public void __onError__(WebSocketClient websocket, Throwable throwable) {
        if (threadPool == null || threadPool.isShutdown()) return;
        threadPool.execute(() -> {
            this.onOpen(websocket);
            System.gc(); // 回收掉生成的一次性的对象，避免内存泄漏
        });
    }

    @Override
    public void __onMessageText__(WebSocketClient websocket, String message) {
        if (threadPool == null || threadPool.isShutdown()) return;
        threadPool.execute(() -> {
            this.onMessageText(websocket, message);
            System.gc(); // 回收掉生成的一次性的对象，避免内存泄漏
        });
    }

    @Override
    public void __onMessageBinary__(WebSocketClient websocket, byte[] message) {
        if (threadPool == null || threadPool.isShutdown()) return;
        threadPool.execute(() -> {
            this.onMessageBinary(websocket, message);
            System.gc(); // 回收掉生成的一次性的对象，避免内存泄漏
        });
    }

    @Override
    public void __onMessagePong__(WebSocketClient websocket, byte[] message) {
        if (threadPool == null || threadPool.isShutdown()) return;
        threadPool.execute(() -> {
            this.onMessagePong(websocket, message);
            System.gc(); // 回收掉生成的一次性的对象，避免内存泄漏
        });
    }

    @Override
    public void __onMessagePing__(WebSocketClient websocket, byte[] message) {
        if (threadPool == null || threadPool.isShutdown()) return;
        threadPool.execute(() -> {
            this.onMessagePing(websocket, message);
            System.gc(); // 回收掉生成的一次性的对象，避免内存泄漏
        });
    }

    @Override
    public void __onMessageContinuation__(WebSocketClient websocket, byte[] message) {
        if (threadPool == null || threadPool.isShutdown()) return;
        threadPool.execute(() -> {
            this.onMessageContinuation(websocket, message);
            System.gc(); // 回收掉生成的一次性的对象，避免内存泄漏
        });
    }

    @Override
    public String toType() {
        return "ASYNCHRONOUS_TYPE";
    }
}
