package com.goldencis.flyme.common.mq;

import com.goldencis.flyme.common.domain.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.io.Closeable;

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
    void doSubscribe(MessageListener listener);
}
