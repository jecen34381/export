package com.example.mq1.MQConsumer;

import com.example.mq1.config.SpringContext;
import com.example.mq1.listener.SMSListener;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum MQSMSConsumer {
	INSTANCE;
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private DefaultMQPushConsumer consumer = null;
	private String groupName = "zxwy-api-producer-group";
	private String nameSrvAddr = "localhost:9876";
	private String instanceName = "zxwy-api-producer-instance";
	private MQSMSConsumer(){
		consumer = new DefaultMQPushConsumer();
		consumer.setConsumerGroup(groupName);
		consumer.setNamesrvAddr(nameSrvAddr);
		consumer.setInstanceName(instanceName);
		consumer.setMessageModel(MessageModel.CLUSTERING);
		try {
			consumer.subscribe("zxwy-api-topic","zxwy-api-sms-tag");
			consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
			consumer.setConsumeThreadMin(1);
			consumer.setConsumeThreadMax(3);
			consumer.setConsumeMessageBatchMaxSize(50);
			consumer.registerMessageListener(SpringContext.getBean(SMSListener.class));
			consumer.start();
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new RuntimeException(e);
		}
	}
	public void start(){
		logger.info("zxwy data sms consumer start");
	}
}