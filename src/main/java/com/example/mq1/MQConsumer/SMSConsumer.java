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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;
import sun.net.www.http.HttpClient;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * sms消费者
 * @author zhangyonghe
 */
@Component
public class SMSConsumer implements Consumer{

    @Autowired
    ObjectAndByte objectAndByte;

    @Value("${rocket.mq.sms.group}")
    String groupName;

    @Value("${rocket.mq.sms.send.student.topic}")
    String topic;

    @Value("${rocket.mq.sms.send.student.tag}")
    String tag;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {

        logger.info(this.getClass().getName() + " 消费者启动......！");

        DefaultMQPushConsumer pushConsumer = new DefaultMQPushConsumer(groupName);

        pushConsumer.setNamesrvAddr("localhost:9876");

        pushConsumer.subscribe(topic, tag);

        pushConsumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                    for (MessageExt messageExt : list){
                        System.err.println(objectAndByte.toObject(messageExt.getBody()));
                    }
                return null;
            }
        });

        pushConsumer.start();
    }
}
