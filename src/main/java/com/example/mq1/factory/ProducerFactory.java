package com.example.mq1.factory;

import com.example.mq1.MQProducer.Producer;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MQProducer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * create a MQProducer for pre produce group
 * @author 张永贺
 */
public class ProducerFactory {

    private DefaultMQProducer defaultMQProducer;

    private Map<String, MQProducer> mqProducerPool = new HashMap<>();

    public MQProducer getDefaultMQProduce(String producerGroup){
        if (mqProducerPool.get(producerGroup) == null){
            mqProducerPool.put(producerGroup, new DefaultMQProducer(producerGroup));
        }
        return this.mqProducerPool.get(producerGroup);
    }

}
