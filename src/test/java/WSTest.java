import cn.evolvefield.sdk.fastws.client.FastWSClient;
import cn.evolvefield.sdk.fastws.client.WebSocketClient;
import cn.evolvefield.sdk.fastws.client.config.WSConfig;
import cn.evolvefield.sdk.fastws.client.core.model.Model;
import cn.evolvefield.sdk.fastws.client.core.model.NioModel;
import cn.evolvefield.sdk.fastws.client.core.process.ActuatorAbstract;
import cn.evolvefield.sdk.fastws.client.core.process.ActuatorAbstractAsync;
import cn.evolvefield.sdk.fastws.client.core.process.ActuatorDefault;
import cn.evolvefield.sdk.fastws.client.core.process.ActuatorDefaultAsync;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Project: NettyWSClient
 * Author: cnlimiter
 * Date: 2023/4/13 1:01
 * Description:
 * 测试同步、异步
 */
public class WSTest {

    public static void main(String[] args)  {

        // 1. 创建 application 对象
        FastWSClient application = FastWSClient.run(NioModel.class);

        WSConfig p6eConfig = new WSConfig("WS:// OR WSS://", new ActuatorDefault());
        p6eConfig.setSslPath("");
        // 2. 客户端 websocket 连接
        application.connect(p6eConfig); // 同步默认的回调

        // 3. 关闭指定 ID 的客户端
        application.close("");

        // 3. 关闭所有客户端
        application.close();

        // 4. 摧毁的方法
        application.destroy();

    }


    @Test
    public void test1() {
        // 默认的实现
        // FastWSClient application = FastWSClient.run(NioModel.class);

        // 自定义的实现
        FastWSClient application = FastWSClient.run(new Model() {
            @Override
            protected void option(Bootstrap bootstrap) {

            }

            @Override
            protected void channel(Bootstrap bootstrap) {

            }

            @Override
            protected EventLoopGroup group(Bootstrap bootstrap) {
                return null;
            }
        });
    }
    @Test
    public void test2() {
        // 创建 application 对象
        FastWSClient application = FastWSClient.run(NioModel.class);

        // 自定义 请求头 和 cookie
        Map<String, Object> headers = new HashMap<>();
        headers.put("h_name", "h_value");
        // cookies 数据
        List<WSConfig.Cookie> cookies = new ArrayList<>();
        cookies.add(new WSConfig.Cookie("cookie_name", "cookie_value"));

        application.connect(new WSConfig("WSS:// OR WS://", headers, cookies, new ActuatorDefault()));
    }
    @Test
    public void test3() {
        // 创建 application 对象
        FastWSClient application = FastWSClient.run(NioModel.class);
        // 连接服务
        application.connect(new WSConfig("WSS:// OR WS://", new ActuatorDefault()));

        // 连接服务
        application.connect(new WSConfig("WSS:// OR WS://", new ActuatorDefault() {
            // 重写默认同步处理消息的方法
            // 自定义选择重写的回调函数
        }));

        // 连接服务
        application.connect(new WSConfig("WSS:// OR WS://", new ActuatorAbstract() {
            // 实现同步抽象接口的方法
            @Override
            public void onOpen(WebSocketClient webSocket) { }

            @Override
            public void onClose(WebSocketClient webSocket) { }

            @Override
            public void onError(WebSocketClient webSocket, Throwable throwable) { }

            @Override
            public void onMessageText(WebSocketClient webSocket, String message) { }

            @Override
            public void onMessageBinary(WebSocketClient webSocket, byte[] message) { }

            @Override
            public void onMessagePong(WebSocketClient webSocket, byte[] message) { }

            @Override
            public void onMessagePing(WebSocketClient webSocket, byte[] message) { }

            @Override
            public void onMessageContinuation(WebSocketClient webSocket, byte[] message) { }
        }));
    }
    @Test
    public void test4() {

        // 线程池
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(0, 30,
                60L, TimeUnit.SECONDS, new SynchronousQueue<>());

        // 创建 application 对象
        // threadPool 线程池用于当前 application 对象创建的异步消息处理
        FastWSClient application = FastWSClient.run(NioModel.class, threadPool);

        // 连接服务
        application.connect(new WSConfig("WSS:// OR WS://", new ActuatorDefaultAsync()));

        // 连接服务
        application.connect(new WSConfig("WSS:// OR WS://", new ActuatorDefaultAsync() {
            // 重写默认异步处理消息的方法
            // 自定义选择重写的回调函数
        }));

        // 连接服务
        application.connect(new WSConfig("WSS:// OR WS://", new ActuatorAbstractAsync() {
            // 实现异步抽象接口的方法
            @Override
            public void onOpen(WebSocketClient webSocket) { }

            @Override
            public void onClose(WebSocketClient webSocket) { }

            @Override
            public void onError(WebSocketClient webSocket, Throwable throwable) { }

            @Override
            public void onMessageText(WebSocketClient webSocket, String message) { }

            @Override
            public void onMessageBinary(WebSocketClient webSocket, byte[] message) { }

            @Override
            public void onMessagePong(WebSocketClient webSocket, byte[] message) { }

            @Override
            public void onMessagePing(WebSocketClient webSocket, byte[] message) { }

            @Override
            public void onMessageContinuation(WebSocketClient webSocket, byte[] message) { }
        }));

        // 4. 摧毁的方法
        application.destroy();
    }
}
