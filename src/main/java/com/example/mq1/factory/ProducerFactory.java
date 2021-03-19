package com.example.mq1.factory;

import com.example.mq1.MQProducer.Producer;
import com.example.mq1.builder.ProducerBuilder;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * create a MQProducer for pre produce group
 * @author 张永贺
 */
public class ProducerFactory {

    private static Map<String, MQProducer> mqProducerPool = new HashMap<>();

    /**
     * return ，if object exists of specify producer group  else create new object
     * @param producerGroup
     * @return
     */
    public static  DefaultMQProducer getDefaultMQProduce(String producerGroup){

        if (mqProducerPool.get(producerGroup) == null){

            mqProducerPool.put(producerGroup, new ProducerBuilder().build(producerGroup));
        }
        return (DefaultMQProducer) mqProducerPool.get(producerGroup);
    }

}
