package com.example.mq1.controller;

import com.example.mq1.bean.Response;
import com.example.mq1.constant.CacheKey;
import com.example.mq1.service.SysUserCheckinService;
import org.apache.tomcat.util.buf.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Controller
@Scope("prototype")
public class SysUserCheckinController {

    @Autowired
    SysUserCheckinService sysUserCheckinService;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    RedisTemplate redisTemplate;

    @ResponseBody
    @RequestMapping("/sys/yser/checkin/export")
    public  Response<?> export() {

        return new Response<String>("请求失败！");
    }
    @ResponseBody
    @RequestMapping("/sys/yser/checkin/message/push")
    public Response<?> pushMessage(@RequestParam("ids") String ids){
        try {
            String[] isArray = ids.split(",");
            Map<String, Integer> result = new HashMap<>();
            Arrays.asList(isArray).stream().forEach(id -> {
                int randomNum = (int) (Math.random() * 10);
                System.err.println("info:消息"+ randomNum +"压入队列。。。。。。");
                redisTemplate.opsForList().leftPush(CacheKey.CHECKIN_EXPORT_MQ, String.valueOf(randomNum));
                long pushedSize = redisTemplate.opsForList().size(CacheKey.CHECKIN_EXPORT_MQ);
                System.err.println("info:消息压入后队列长度 " + pushedSize);
                result.put(id, randomNum);
            });
            return new Response<Map>(result);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response<Object>("error", "Failure");
        }


    }

    @ResponseBody
    @RequestMapping("/sys/yser/checkin/message/get")
    public Response<?> getMessage(){
        try {
            String result = (String) redisTemplate.opsForList().leftPop(CacheKey.CHECKIN_EXPORT_MQ_RESULT);
            System.err.println("从消息队列拿出消息：" + result);
            return new Response<Object>(result);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response<Object>("error", "Failure");
        }


    }


    @Test
    public void enumTest(){
        long startTime = System.currentTimeMillis();
        String nums = "";
        for (int i = 0;i < 10000;i++)nums += String.valueOf(i);
        String[] numArray = nums.split("");
        List<String> result = new ArrayList<>();
        Arrays.asList(numArray).parallelStream().forEach(num -> {
            result.add(Character.isDigit(num.toCharArray()[0]) ? num : num);
        });
        String reustlStr = StringUtils.join(result).replace(",", "");
        System.err.println(reustlStr);
        long endTime = System.currentTimeMillis();
        System.err.println("花费时间：" + (endTime - startTime));
    }

}
