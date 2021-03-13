package com.example.mq1.MQProducer;

import com.example.mq1.factory.ProducerFactory;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 一条道消息队列发送
 * @Author zhangyonghe
 */
@Component
public class OnewayProducer implements Producer{

    @Value("${rocket.mq.sms.group}")
    String groupName;

    @Value("${rocket.mq.sms.send.student.topic}")
    String topic;

    @Value("${rocket.mq.sms.send.student.tag}")
    String tag;

    @Override
    public void send() throws Exception {

        MQProducer mqProducer = ProducerFactory.getDefaultMQProduce("groupName");

        mqProducer.start();

        Message msg = new Message(topic, tag, "hello world!".getBytes(RemotingHelper.DEFAULT_CHARSET));

        mqProducer.send(msg);

        Thread.sleep(3000);

        mqProducer.shutdown();

    }
}
