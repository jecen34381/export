package com.example.mq1.MQProducer;

import com.example.mq1.bean.Mail;
import com.example.mq1.factory.ProducerFactory;
import com.example.mq1.util.ObjectAndByte;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.rmi.Remote;

/**
 * 同步消息队列发送
 * @Author zhangyonghe
 */
@Component
public class SyncProducer implements Producer {

    @Autowired
    ObjectAndByte objectAndByte;

    @Value("${rocket.consumer.group.excel}")
    String producerGroup;

    @Override
    public void send() throws Exception {

        //Instantiate with a producer group name
        DefaultMQProducer producer = (DefaultMQProducer)ProducerFactory.getDefaultMQProduce(producerGroup);


       //Specify name server address
        producer.setNamesrvAddr("localhost:9876");

        //start
        producer.start();

        //test send message
        for (int i = 0;i < 1;i++){
            Mail mail = new Mail("1601765623@qq.com", "1601765623@qq.com", false, "test mail send");
            //create a message instance, specify topic, tag and message body
            Message message = new Message("TopicTest"/* topic */, "TagA"/* tag */, objectAndByte.toByteArray(mail) /* Message body*/);

            //Call send message to deliver message to one of broker
            SendResult sendResult = producer.send(message);

            System.out.printf("%s%n", sendResult);
        }

        producer.shutdown();

    }
}
