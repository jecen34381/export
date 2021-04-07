package com.example.mq1.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;

public class FeigeSmsTask {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Scheduled(initialDelay = 10 * 1000, fixedRate= 1 * 1000)
    public void feigeSendStart(){


    }
}
