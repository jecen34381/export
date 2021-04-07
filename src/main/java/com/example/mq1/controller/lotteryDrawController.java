package com.example.mq1.controller;

import com.example.mq1.bean.Response;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class lotteryDrawController {

    @Autowired  private RedisTemplate<String, String> redisTemplate;

    @RequestMapping("/v1/lottery/draw")
    public Response<?> lotteryDraw(){

        for (int i=0;i < 100;i++){
            redisTemplate.opsForList().rightPush("lottery", String.valueOf((int)(Math.random() * 10)));
        }

        return new Response<>();
    }

    @RequestMapping("/v1/lottery/pre/load")
    public Response<?> lotteryPreload(){

        int emptyLottery = 30;

        List<Integer> lotteryId = new ArrayList<>();
        for (int i = 0;i < 10;i++){
            lotteryId.add(1000 + i);
        }

        for (int i=0;i < 100;i++){
            redisTemplate.opsForList().rightPush("lottery", String.valueOf((int)(Math.random() * 10)));
        }



        return new Response<>();
    }

    @Test
    public void testBranch(){
        System.out.println("这是一个测试功能分支的代码。");
    }
}
