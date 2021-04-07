package com.example.mq1.MQProducer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 枚举生成producer实例
 */
public enum MQProducer {
    INSTANCE;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private String groupName = "";
    private String nameSrvAddr = "";
    private String instanceName = "";
    private org.apache.rocketmq.client.producer.MQProducer mqProducer = null;

    private MQProducer(){

        mqProducer = new DefaultMQProducer();

    }
}
