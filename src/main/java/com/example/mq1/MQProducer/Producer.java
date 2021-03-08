package com.example.mq1.MQProducer;

import org.apache.rocketmq.client.exception.MQClientException;

public interface Producer {

    public void send() throws MQClientException, Exception;
}
