package cn.evolvefield.sdk.fastws.client;

import cn.evolvefield.sdk.fastws.client.config.WSConfig;
import cn.evolvefield.sdk.fastws.client.core.model.Model;
import cn.evolvefield.sdk.fastws.client.core.model.ModelCache;
import cn.evolvefield.sdk.fastws.client.core.model.NioModel;
import cn.evolvefield.sdk.fastws.client.core.process.ActuatorAbstractAsync;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
/**
 * Project: NettyWSClient
 * Author: cnlimiter
 * Date: 2023/4/13 0:52
 * Description:
 */
public class FastWSClient {
    private Model model;
    private ThreadPoolExecutor threadPool;

    public static FastWSClient run() {
        return new FastWSClient(NioModel.class, new WSConfig[] {});
    }

    public static FastWSClient run(Model model) {
        return new FastWSClient(model, new WSConfig[] {});
    }

    public static FastWSClient run(Class<? extends Model> model) {
        return new FastWSClient(model, new WSConfig[] {});
    }

    public static FastWSClient run(Model model, WSConfig... products) {
        return new FastWSClient(model, products);
    }

    public static FastWSClient run(Class<? extends Model> model, WSConfig... products) {
        return new FastWSClient(model, products);
    }

    public static FastWSClient run(Model model, ThreadPoolExecutor threadPool) {
        return new FastWSClient(model, threadPool, new WSConfig[]{});
    }

    public static FastWSClient run(Class<? extends Model> model, ThreadPoolExecutor threadPool) {
        return new FastWSClient(model, threadPool, new WSConfig[]{});
    }

    public static FastWSClient run(Model model, ThreadPoolExecutor threadPool, WSConfig... products) {
        return new FastWSClient(model, threadPool, products);
    }

    public static FastWSClient run(Class<? extends Model> model, ThreadPoolExecutor threadPool, WSConfig... products) {
        return new FastWSClient(model, threadPool, products);
    }

    public FastWSClient(Class<? extends Model> model, WSConfig[] products) {
        try {
            this.threadPool = new ThreadPoolExecutor(0, 30, 60L, TimeUnit.SECONDS, new SynchronousQueue<>());
            this.model = model.newInstance().run().connect(products);
            ActuatorAbstractAsync.init(threadPool);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public FastWSClient(Model model, WSConfig[] products) {
        this.threadPool = new ThreadPoolExecutor(0, 30, 60L, TimeUnit.SECONDS, new SynchronousQueue<>());
        this.model = model.connect(products);
        ActuatorAbstractAsync.init(threadPool);
    }

    public FastWSClient(Class<? extends Model> model, ThreadPoolExecutor threadPool, WSConfig[] products) {
        try {
            this.threadPool = threadPool;
            this.model = model.newInstance().run().connect(products);
            ActuatorAbstractAsync.init(threadPool);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public FastWSClient(Model model, ThreadPoolExecutor threadPool, WSConfig[] products) {
        this.threadPool = threadPool;
        this.model = model.connect(products);
        ActuatorAbstractAsync.init(threadPool);
    }

    public FastWSClient connect(WSConfig product) {
        this.model.connect(product);
        return this;
    }

    public FastWSClient connect(WSConfig[] products) {
        this.model.connect(products);
        return this;
    }

    public FastWSClient close() {
        this.model.close();
        return this;
    }

    public FastWSClient close(String id) {
        this.model.close(id);
        return this;
    }

    public FastWSClient close(String[] ids) {
        this.model.close(ids);
        return this;
    }

    public void destroy() {
        this.model.close();
        if (threadPool != null && !threadPool.isShutdown()) threadPool.shutdown();
    }

    public ModelCache getModelCache() {
        return this.model.getModelCache();
    }

    public ThreadPoolExecutor getThreadPool() {
        return threadPool;
    }
}
