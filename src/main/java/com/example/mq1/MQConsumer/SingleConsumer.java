package com.example.mq1.MQConsumer;

import com.alibaba.fastjson.JSON;
import com.example.mq1.util.ObjectAndByte;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * RocketMQ 消费者
 * @Author zhangyonghe
 */
//@Component
public class SingleConsumer implements Consumer {

    @Autowired
    ObjectAndByte objectAndByte;

    @Value("${rocket.broker.name.server}")
    String nameSrvAddr;

    @Value("${rocket.consumer.group.excel}")
    String consumerGroup;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.consumer();
    }

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void consumer() throws Exception {

        logger.info(this.getClass().getName() + " 消费者启动......！");

        // Instantiate with specified consumer group name.
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("zxwy-api-producer-group");

        // Specify name server addresses.
        consumer.setNamesrvAddr(nameSrvAddr);

        // Subscribe one more more topics to consume.
        consumer.subscribe("zxwy-api-topic", "*");
        // Register callback to execute on arrival of messages fetched from brokers.
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {

                //System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
                for (MessageExt messageExt : msgs){
                    byte[] bytes = (byte[]) messageExt.getBody();
                    InputStream inputStream = new ByteArrayInputStream(bytes);
                    try {
                        String msg = new String(bytes, "GBK");
                        Map<String, Object> msgMap = stringToMap(msg);
                        System.out.println(msgMap);
                        System.out.println(msg);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

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

    /**
     * json转对象
     * @param str
     * @param clazz
     * @return
     */
    Object getObject(String str,Class<?> clazz){
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            return objectMapper.readValue(str, clazz);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 字符串转map
     * @param str
     * @return
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> stringToMap(String str){
        Map<String, Object> returnMap = JSON.parseObject(str, Map.class);
        return returnMap;
    }

}
