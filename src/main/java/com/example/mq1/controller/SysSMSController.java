package com.example.mq1.controller;

import com.example.mq1.MQProducer.MQProducer;
import com.example.mq1.bean.Response;
import com.example.mq1.bean.SMSPayload;
import com.example.mq1.constant.CacheKey;
import com.example.mq1.en.SMSType;
import com.example.mq1.util.DateUtil;
import com.example.mq1.util.LogSequence;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    /**
     * 使用飞鸽平台发送推广短信
     * <p>使用mq发送 马喜明</p>
     * @author 张永贺
     * @param mobile
     */
    @RequestMapping("/v1/ad/user/pro/send/consumer/message")
    public void customFeiGeSmsSend(String mobile){
        //缓存处理批量发送
        Date datetime = this.getServerTime();
        //设置发送参数
        SMSPayload smsPayload = new SMSPayload();
        smsPayload.setMobile(mobile);
        smsPayload.setCreateTime(datetime);
        String code = LogSequence.get();
        smsPayload.setCode(code);

        smsPayload.setType(SMSType.C2.getCode());
        // 发送到mq
        MQProducer.INSTANCE.sendOneway(code, "zxwy-api-topic", "zxwy-api-sms-tag", this.getJSON(smsPayload));
    }

    Date getServerTime(){
        return DateUtil.getServerTime();
    }


    String getJSON(Object obj){
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            return objectMapper.writeValueAsString(obj);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
