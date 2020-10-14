package com.goldencis.flyme.common.mq;

import com.goldencis.flyme.common.domain.Message;
import com.goldencis.flyme.common.domain.MessageHandlerMethod;

import java.io.Closeable;
import java.util.Map;

/**
 * @program: Flyme-Vue
 * @description:
 * @Author: chengl
 * @create: 2020-10-12 16:30
 **/
public interface PubAndSub extends Closeable {

    /**
     * 发布
     * @param message
     */
    void doPublish(Message message);

    /**
     * 订阅
     * @param listener
     */
    void doSubscribe(Map<String, MessageHandlerMethod> subscribeMapping, String... channels);
}
