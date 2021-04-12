package com.example.mq1.config;

import com.example.mq1.MQConsumer.MQSMSConsumer;
import com.example.mq1.MQProducer.MQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class StartCommandLinnerRunner implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public void run(String... args) throws Exception {

        MQSMSConsumer.INSTANCE.start();
        MQProducer.INSTANCE.start();
        logger.info("MQSMSConsumer启动。。。。。。");
    }
}
