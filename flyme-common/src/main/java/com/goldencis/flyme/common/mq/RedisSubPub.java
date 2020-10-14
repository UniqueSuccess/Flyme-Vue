package com.goldencis.flyme.common.mq;

import com.goldencis.flyme.common.domain.Message;
import com.goldencis.flyme.common.domain.MessageHandlerMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @program: Flyme-Vue
 * @description:
 * @Author: chengl
 * @create: 2020-10-12 15:28
 **/
@Slf4j
public class RedisSubPub implements PubAndSub {
    private JedisPool jedisPool;
    private String ip;
    private int port;
    private int failureTime = 0;
    @Autowired
    private RedisTemplate redisTemplate;
    public RedisSubPub(String ip, int port) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(1024);
        config.setMaxIdle(5);
        config.setMaxWaitMillis(1000 * 100L);
        config.setTestOnBorrow(true);
        jedisPool = new JedisPool(config, ip, port);
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void close() throws IOException {

        JedisPool jedisPool = new JedisPool();
        jedisPool.close();
    }

    @Override
    public void doPublish(Message message) {
        Jedis jedis = jedisPool.getResource();
        jedis.publish(message.getChannel(), message.getContent());
        jedis.close();
        log.info("doPublish message to 【{}@{}:{}】==> {}", message.getChannel(), ip, port, message.getContent());
    }

    @Override
    public void doSubscribe(Map<String, MessageHandlerMethod> subscribeMapping, String... channels) {
        JedisPubSub jedisPubSub = new JedisPubSub(){
            public void onMessage(String receiveChannel, String messageAndContent) {
                try {
                    MessageHandlerMethod method = subscribeMapping.get(receiveChannel);
                    if (method == null) {
                        log.warn("Cannot find handler mapping of channel [{}]", receiveChannel);
                        return;
                    }
                    Class<?>[] parameterTypes = method.getMethod().getParameterTypes();
                    try {
                        if (parameterTypes.length == 0) {
                            method.getMethod().invoke(method.getTarget());
                        } else {
                            Message message = Message.builder().build();
                            message.setChannel(receiveChannel);
                            message.setContent(messageAndContent);
                            method.getMethod().invoke(method.getTarget(), message);
                        }
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                        log.error("Fail to handle message", e);
                    }




                    System.out.println("11111111111111111111111111----------------------111111111");
                    log.info("receive message from 【{}@{}:{}】 ==>{}", receiveChannel, ip, port, messageAndContent);

                } catch (Exception ex) {
                    log.error("【this onMessage method exit】", ex);
                }
            }
        };

        //new String[]{“aaa”,”bbb”} ”aaa“
        jedisPool.getResource().subscribe(jedisPubSub, channels);
    }
}
