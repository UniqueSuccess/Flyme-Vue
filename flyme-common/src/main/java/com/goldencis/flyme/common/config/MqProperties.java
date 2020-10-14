package com.goldencis.flyme.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @program: Flyme-Vue
 * @description:
 * @Author: chengl
 * @create: 2020-10-13 10:30
 **/
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.redis")
public class MqProperties {
    public static final String MQ_TYPE_REDIS = "redis";
    public static final String MQ_TYPE_ACTIVEMQ = "activemq";
    public static final String MQ_TYPE_RABBITMQ = "rabbitmq";
    public static final String MQ_TYPE_ROCKETMQ = "rocketmq";
    public static final String MQ_TYPE_KAFAKA = "kafaka";
    private String host = "localhost";
    private int port = 6379;
    private String type = MQ_TYPE_ACTIVEMQ;
    private String username = "admin";
    private String password = "admin";

}
