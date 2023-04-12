package cn.evolvefield.sdk.fastws.client.core.process;

import cn.evolvefield.sdk.fastws.client.core.ContextConverter;

/**
 * Project: NettyWSClient
 * Author: cnlimiter
 * Date: 2023/4/13 0:43
 * Description:基础执行器
 */
public abstract class BaseActuator implements ContextConverter, Actuator {

    /**
     * 返回当前实现的类型
     * @return 类型数据
     */
    public abstract String toType();
}
