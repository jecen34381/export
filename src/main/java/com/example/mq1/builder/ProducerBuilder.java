package com.example.mq1.builder;

import com.example.mq1.MQProducer.Producer;
import com.example.mq1.Mq1Application;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MQProducer;
import org.springframework.beans.factory.annotation.Value;

/**
 * build a Producer
 * @author zhangyonghe
 */
public class ProducerBuilder {

    @Value("${rocket.broker.name.server}")
    String nameSrvAddr;

    private DefaultMQProducer mqProducer;

    public DefaultMQProducer build(String producerGroup){

        mqProducer = new DefaultMQProducer(producerGroup);
        mqProducer.setNamesrvAddr(nameSrvAddr);
        return mqProducer;
    }

    public ProducerBuilder setNamesrvAddress(){

        this.mqProducer.setNamesrvAddr(nameSrvAddr);

        return this;
    }

    public ProducerBuilder setProducerGroup(String producerGroup){
        this.mqProducer.setProducerGroup(producerGroup);
        return this;
    }
}
