package com.example.mq1.MQProducer;

import com.example.mq1.factory.ProducerFactory;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
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
    public synchronized void send() throws Exception {

        DefaultMQProducer mqProducer = (DefaultMQProducer) ProducerFactory.getDefaultMQProduce(groupName);

        mqProducer.setNamesrvAddr("localhost:9876");

        mqProducer.start();

        Message msg = new Message("sms"/* topic */, "tag1", "hello world!".getBytes(RemotingHelper.DEFAULT_CHARSET));

        mqProducer.sendOneway(msg);

        Thread.sleep(3000);

        //mqProducer.shutdown();

    }

    @Override
    public void shutDown() {

    }


}
