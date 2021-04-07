package com.example.mq1.controller;

import com.example.mq1.bean.Response;
import com.example.mq1.constant.CacheKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageTranscoder;
import java.util.Date;

@RestController
public class SysSMSController {

    private final long SMSMARKETINGUPPERLIMIT = 4;//假设五条一发，上限为四

    @Autowired private RedisTemplate<String, String> redisTemplate;

    @RequestMapping("/sys/send/message")
    public Response<?> sendMessage(){
        String mobile = String.valueOf((int)(Math.random() * 10));

        StringBuilder mobiles;
        if (redisTemplate.opsForList().size("sms-marketing") == SMSMARKETINGUPPERLIMIT){
            mobiles = new StringBuilder();

            for (int i = 0;i < SMSMARKETINGUPPERLIMIT;i++){
                mobiles.append(redisTemplate.opsForList().rightPop("sms-marketing") + ",");
            }

            mobiles.append(mobile);
            System.out.println(mobiles.toString());
            redisTemplate.opsForHash().put("sms-marketing-log", String.valueOf(new Date().getTime()), mobiles.toString());

            return new Response<>("电话：" + mobiles.toString());
        }else{
            redisTemplate.opsForList().leftPush("sms-marketing", mobile);
        }

        return new Response<>(redisTemplate.opsForList().range("sms-marketing", 0, SMSMARKETINGUPPERLIMIT));
    }

    /**
     * 每一个进来的请求都压入缓存，每二十条发送一次
     * @param mobile
     * @return
     */
    public String batchCacheMessage(@RequestParam("mobile") String mobile, @RequestParam("lengthOfBatch") long lengthOfBatch){

        StringBuilder mobiles;
        if (redisTemplate.opsForList().size(CacheKey.FEIGE_SMS_PROMOTION_MESSAGE_SEND) == lengthOfBatch - 1){
            mobiles = new StringBuilder();

            for (int i = 0;i < lengthOfBatch - 1;i++){
                mobiles.append(redisTemplate.opsForList().rightPop(CacheKey.FEIGE_SMS_PROMOTION_MESSAGE_SEND) + ",");
            }

            mobiles.append(mobile);
            return mobiles.toString();
        }else{
            redisTemplate.opsForList().leftPush(CacheKey.FEIGE_SMS_PROMOTION_MESSAGE_SEND, mobile);
        }

        return null;
    }
}
