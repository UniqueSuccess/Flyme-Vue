package com.goldencis.flyme.common.config;

import com.goldencis.flyme.common.mq.RedisSubPub;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
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
public class MqConfiguration {

    @Configuration
    @ConditionalOnClass({ JedisPool.class })
    public static class MqRedisConfiguration{
        @Bean(destroyMethod = "close")
        public RedisSubPub redisSubPub() throws IOException {
            RedisSubPub subpub = new RedisSubPub("10.10.16.116", 6379);
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
