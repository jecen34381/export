package com.example.mq1.MQConsumer;

import com.example.mq1.listener.MessageConcurrentlyListener;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

//@Component
public class ApiLogConsumer implements Consumer {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {

        logger.info(this.getClass().getName() + " 日志消费者启动......！");

        DefaultMQPushConsumer pushConsumer = new DefaultMQPushConsumer("zxwy1-api-producer-group");

        pushConsumer.setNamesrvAddr("localhost:9876");

        pushConsumer.subscribe("zxwy-api-topic", "*");

        pushConsumer.registerMessageListener(new MessageConcurrentlyListener());

        //pushConsumer.start();
    }
}
