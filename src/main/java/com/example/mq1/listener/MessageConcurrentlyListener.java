package com.example.mq1.listener;

import com.example.mq1.util.ObjectAndByte;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MessageConcurrentlyListener implements MessageListenerConcurrently {

    @Autowired
    ObjectAndByte objectAndByte;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        for (MessageExt messageExt : list){
            System.out.println("解析后的byte：" + objectAndByte.toObject(messageExt.getBody()));

            /*logger.info("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);*/
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
