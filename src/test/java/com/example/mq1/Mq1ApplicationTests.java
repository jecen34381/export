package com.example.mq1;

import javafx.application.Application;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
class Mq1ApplicationTests {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Test
    void contextLoads() {

        stringRedisTemplate.opsForValue().set("name", "snake");
        Assert.assertEquals("snake", stringRedisTemplate.opsForValue().get("name"));

    }

    @Test
    public void testLottery(){
        Map<Integer, Integer> lotterySet = new HashMap<>();//奖项设置

        String express = "1,2,3";

        System.err.println(express.indexOf(","));
    }

}
