package com.goldencis.flyme.common.config;

import com.goldencis.flyme.common.mq.RedisSubPub;
import com.goldencis.flyme.common.mq.SubscriberStarter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;

import java.io.IOException;

/**
 * @program: Flyme-Vue
 * @description:
 * @Author: chengl
 * @create: 2020-10-12 15:01
 **/
@Configuration
@EnableConfigurationProperties(MqProperties.class)
public class MqConfiguration {

    @Bean
    public SubscriberStarter starter(RedisSubPub subscriber){
        return new SubscriberStarter(subscriber);
    }


    @Configuration
    @ConditionalOnClass({ JedisPool.class })
    public static class MqRedisConfiguration{
        @Bean(destroyMethod = "close")
        public RedisSubPub redisSubPub(MqProperties mqProperties) throws IOException {
            RedisSubPub subpub = new RedisSubPub(mqProperties.getHost(), mqProperties.getPort());
            try {
                subpub.close();
            } catch (Exception ex) {
                subpub.close();
                throw ex;
            }
            return subpub;
        }
    }

}
