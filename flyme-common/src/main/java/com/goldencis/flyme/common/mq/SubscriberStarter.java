package com.goldencis.flyme.common.mq;

import com.goldencis.flyme.common.annotation.MessageHandlerAnno;
import com.goldencis.flyme.common.annotation.SubscribeChannels;
import com.goldencis.flyme.common.domain.Message;
import com.goldencis.flyme.common.domain.MessageHandlerMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.SmartLifecycle;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @program: Flyme-Vue
 * @description:
 * @Author: chengl
 * @create: 2020-10-13 14:14
 **/
@Slf4j
public class SubscriberStarter implements SmartLifecycle, ApplicationContextAware {

    private ApplicationContext applicationContext;

    private final RedisSubPub subscriber;

    public SubscriberStarter(RedisSubPub subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public void start() {
        startupSubscriber(subscriber, initializeMessageSubscribeMapping(this.applicationContext));
    }

    private Map<String, MessageHandlerMethod> initializeMessageSubscribeMapping(ApplicationContext context) {
        Map<String, Object> annotationMap = context.getBeansWithAnnotation(MessageHandlerAnno.class);
        Map<String, MessageHandlerMethod> messageSubscribeMapping = new HashMap<>();
        for (Object bean : annotationMap.values()) {
            Method[] methods = bean.getClass().getMethods();
            for (Method method : methods) {
                SubscribeChannels subscribeMapping = AnnotationUtils.findAnnotation(method, SubscribeChannels.class);
                if (subscribeMapping == null) {
                    continue;
                }
                Class<?>[] parameterTypes = method.getParameterTypes();
                //仅支持无参和单参且类型为Message的方法
                if (parameterTypes.length == 0 || (parameterTypes.length == 1 && Message.class.equals(parameterTypes[0]))) {
                    String[] channels = subscribeMapping.value();
                    if (channels.length == 0) {
                        continue;
                    }
                    MessageHandlerMethod messageHandlerMethod = MessageHandlerMethod.builder()
                            .target(bean)
                            .method(method)
                            .build();
                    for (String channel : channels) {
                        //各个注解频道不能重复
                        if (messageSubscribeMapping.containsKey(channel)) {
                            throw new RuntimeException(String.format("Detected 2 or more methods[%s,%s] attached to the same channel[%s],please check it first.", messageHandlerMethod
                                    .getMethod(), messageSubscribeMapping.get(channel).getMethod(), channel));
                        }
                        messageSubscribeMapping.put(channel, messageHandlerMethod);
                    }
                    log.info("Subscribe Mapped [{}] onto {}, durable is {}", Stream.of(channels)
                            .collect(Collectors.joining(",")), method);
                } else {
                    throw new RuntimeException(String.format("Detected method [%s] paremeter invalid,only accept either no-parameter or one message parameter.", method));
                }
            }
        }
        return messageSubscribeMapping;


    }

    private void startupSubscriber(RedisSubPub subscriber, Map<String, MessageHandlerMethod> subscribeMapping) {
        List<String> channels = subscribeMapping.keySet().stream().collect(Collectors.toList());
        new Thread(() -> {
            subscriber.doSubscribe(subscribeMapping, channels.toArray(new String[channels.size()]));
        }, "DefaultSubscribeStarterThread").start();
        log.info("DefaultSubscribeStarterThread is started");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }
}
