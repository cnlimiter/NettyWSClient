package cn.evolvefield.sdk.fastws.client.core.process;

import cn.evolvefield.sdk.fastws.client.WebSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Project: NettyWSClient
 * Author: cnlimiter
 * Date: 2023/4/13 0:51
 * Description:
 * 异步处理器的默认实现
 */
public class ActuatorDefaultAsync extends ActuatorAbstractAsync {

    /** 日志的对象的注入 */
    private static final Logger logger = LoggerFactory.getLogger(ActuatorDefaultAsync.class);

    @Override
    public void onOpen(WebSocketClient webSocket) {
        logger.debug("WebSocket [ " + webSocket + " ] ==> onOpen.");
    }

    @Override
    public void onClose(WebSocketClient webSocket) {
        logger.debug("WebSocket [ " + webSocket + " ] ==> onClose.");
    }

    @Override
    public void onError(WebSocketClient webSocket, Throwable throwable) {
        logger.debug("WebSocket [ " + webSocket + " ] ==> onError. \n throwable => " + throwable.getMessage());
    }

    @Override
    public void onMessageText(WebSocketClient webSocket, String message) {
        logger.debug("WebSocket [ " + webSocket + " ] ==> onMessageText. \n message => " + message);
    }

    @Override
    public void onMessageBinary(WebSocketClient webSocket, byte[] message) {
        logger.debug("WebSocket [ " + webSocket + " ] ==> onMessageBinary. \n message => " + new String(message));
    }

    @Override
    public void onMessagePong(WebSocketClient webSocket, byte[] message) {
        logger.debug("WebSocket [ " + webSocket + " ] ==> onMessagePong. \n message => " + new String(message));
    }

    @Override
    public void onMessagePing(WebSocketClient webSocket, byte[] message) {
        logger.debug("WebSocket [ " + webSocket + " ] ==> onMessagePing. \n message => " + new String(message));
    }

    @Override
    public void onMessageContinuation(WebSocketClient webSocket, byte[] message) {
        logger.debug("WebSocket [ " + webSocket + " ] ==> onMessageContinuation. \n message => " + new String(message));
    }
}
