package com.example.mq1.MQConsumer;

import com.example.mq1.util.ObjectAndByte;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * RocketMQ 消费者
 * @Author zhangyonghe
 */
@Component
public class Consumer implements ApplicationRunner {

    @Autowired
    ObjectAndByte objectAndByte;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.consumer();
    }

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void consumer() throws Exception {

        logger.info("RocketMQ 消费者启动......！");

        // Instantiate with specified consumer group name.
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("please_rename_unique_group_name");

        // Specify name server addresses.
        consumer.setNamesrvAddr("localhost:9876");

        // Subscribe one more more topics to consume.
        consumer.subscribe("TopicTest", "*");
        // Register callback to execute on arrival of messages fetched from brokers.
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {

                //System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
                for (MessageExt messageExt : msgs){
                    System.out.println("解析后的byte：" + objectAndByte.toObject(messageExt.getBody()));
                }
                /*logger.info("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);*/
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();
        logger.info("Consumer Started.%n");

    }


}
