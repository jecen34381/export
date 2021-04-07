package com.example.mq1.MQConsumer;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

/**
 * 消费者的顶级接口
 * @author zhangyonghe
 */
public interface Consumer extends ApplicationRunner {

    @Override
    void run(ApplicationArguments args) throws Exception;


}
