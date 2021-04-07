package com.example.mq1.task;

import com.example.mq1.constant.CacheKey;
import com.example.mq1.util.ConvertALaBoToChinese;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

//@Component
public class CheckinExportListenerTask {

   private final Logger logger = Logger.getLogger("task");

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    ConvertALaBoToChinese convertALaBoToChinese;

    @Scheduled(initialDelay = 5 * 1000, fixedRate = 10 * 1000)
    public void doListener(){
        while(true) {
            String id = (String) redisTemplate.opsForList().leftPop(CacheKey.CHECKIN_EXPORT_MQ);
            if(id == null) break;
            redisTemplate.opsForList().leftPush(CacheKey.CHECKIN_EXPORT_MQ_RESULT, convertALaBoToChinese.doConvert(id));
        }
    }
}
