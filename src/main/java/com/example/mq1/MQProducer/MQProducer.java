package com.example.mq1.MQProducer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

/**
 * 枚举生成producer实例
 */
public enum MQProducer {
    INSTANCE;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private String groupName = "zxwy-api-producer-group";
    private String nameSrvAddr = "localhost:9876";
    private String instanceName = "zxwy-api-producer-instance";
    private DefaultMQProducer mqProducer = null;

    private MQProducer(){

        mqProducer = new DefaultMQProducer();
        mqProducer.setNamesrvAddr(nameSrvAddr);
        mqProducer.setInstanceName(instanceName);
        mqProducer.setProducerGroup(groupName);


    }
    public void start(){
        logger.info("zxwy api mq producer start");
        try {
            mqProducer.start();
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new RuntimeException(e);
        }
    }
    public void sendOneway(String key,String topic,String tag,String body){
        try{
            logger.info("zxwy api produce oneway message : {}, {}, {}, {}", key,topic,tag,body);
            Message message = new Message();
            message.setKeys(key);
            message.setTopic(topic);
            message.setTags(tag);
            message.setBody(body.getBytes(StandardCharsets.UTF_8));
            mqProducer.sendOneway(message);
        }catch(Exception e){
            logger.error(e.getMessage(),e);
        }
    }

    public DefaultMQProducer getProducer(){
        return mqProducer;
    }
}
