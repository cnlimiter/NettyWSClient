package cn.evolvefield.sdk.fastws.client.core.model;

import cn.evolvefield.sdk.fastws.client.WebSocketClient;

import java.util.HashMap;
import java.util.Map;

/**
 * Project: NettyWSClient
 * Author: cnlimiter
 * Date: 2023/4/13 0:54
 * Description:
 * 每一个模板对应一个缓存对象，用来缓存连接的客户端信息
 */
public class ModelCache {
    private Map<String, WebSocketClient> mouldClientMap = new HashMap<>();

    public void put(String id, WebSocketClient client) {
        if (id != null) mouldClientMap.put(id, client);
    }

    public void del(String id) {
        if (id != null) mouldClientMap.remove(id);
    }

    public WebSocketClient get(String id) {
        if (id == null) return null;
        else return mouldClientMap.get(id);
    }

    public Map<String, WebSocketClient> get() {
        return mouldClientMap;
    }

}
