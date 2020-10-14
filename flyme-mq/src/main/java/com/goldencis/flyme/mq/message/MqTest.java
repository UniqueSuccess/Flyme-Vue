package com.goldencis.flyme.mq.message;

import com.goldencis.flyme.common.annotation.MessageHandlerAnno;
import com.goldencis.flyme.common.annotation.SubscribeChannels;
import com.goldencis.flyme.common.domain.Message;

/**
 * @program: Flyme-Vue
 * @description: 注解式监听演示
 * @Author: chengl
 * @create: 2020-10-14 09:55
 **/
@MessageHandlerAnno
public class MqTest {

    @SubscribeChannels({"zhangsan","zhangsan1111"})
    public void Mq(Message message){
        System.out.println("111111111111111-----------1111111111111111");
        System.out.println(message.toString());

    }

    @SubscribeChannels("zhangsan2222")
    public void Mq1(Message message){
        System.out.println("111111111111111-----------1111111111111111");
        System.out.println(message.toString());

    }


}
