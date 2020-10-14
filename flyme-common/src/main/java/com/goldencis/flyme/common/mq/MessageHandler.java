package com.goldencis.flyme.common.mq;

import com.goldencis.flyme.common.domain.Message;

import java.util.Set;

/**
 * @program: Flyme-Vue
 * @description:
 * @Author: chengl
 * @create: 2020-10-13 15:13
 **/
public interface MessageHandler {

    /**
     * 处理消息
     * @param message
     */
    void handle(Message message);
    /**
     * 检查是否支持处理此类消息
     * @param message
     * @return false-不支持，true-支持
     */
    boolean supports(Message message);
    /**
     * 获取chanel集合
     *
     * @return
     */
    Set<String> getChannels();


}
