package com.example.mq1.thread;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 统计当前在线人员
 */
public class CountLoginPerson implements Runnable{

    private Logger logger = LoggerFactory.getLogger(this.getClass());



    RedisTemplate<String, String> redisTemplate = new RedisTemplate();

    AtomicLong aLong = new AtomicLong();

    public void login(){
        aLong.incrementAndGet();
    }

    @Override
    public void run() {
        while (true) {
            System.out.println(1);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(redisTemplate.opsForList());
            //redisTemplate.opsForList().leftPush("threadLog", String.valueOf(Thread.currentThread()));
            logger.info(String.valueOf(Thread.currentThread()));
        }
    }








    public static void main(String args[]){
        try {
            //CountDownLatch countDownLatch = new CountDownLatch(10);
            CountLoginPerson countLoginPerson = new CountLoginPerson();
            for (int i = 0; i < 10; i++) {
                new Thread(countLoginPerson).start();
                //countDownLatch.countDown();
            }
            //countDownLatch.await();
            Thread.sleep(1000);
            System.out.println(countLoginPerson.aLong.get());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
