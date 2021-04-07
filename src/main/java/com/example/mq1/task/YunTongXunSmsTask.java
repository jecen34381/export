package com.example.mq1.task;


import com.example.mq1.bean.App;
import com.example.mq1.constant.CacheKey;
import com.example.mq1.util.FeigeSmsClient;
import com.example.mq1.util.FeigeSmsRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.logging.Logger;

//@Component
public class YunTongXunSmsTask{

	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	Logger logger = Logger.getLogger("this");

	/**
	 * 给成单学员发送短信
	 */
	@Scheduled(initialDelay = 10 * 1000, fixedRate= 1 * 10000)
	public void startV2(){
		System.err.println("task start...");
		try{
			while (true){
				String jsonMsg = redisTemplate.opsForList().rightPop(CacheKey.ORDER_SMS_MESSAGE);
				logger.info("成单短信发送定时任务，启动！" + jsonMsg);
				if (jsonMsg != null){
					this.templateFeiGeSmsOrderSend(jsonMsg);
				}else {
					break;
				}
			}
		}catch (Exception e){
			logger.info(e.getMessage());
		}
	}

	/**
	 * 使用飞鸽平台发送成单短信
	 * @author 张永贺
	 * @param jsonMsg
	 */
	public void templateFeiGeSmsOrderSend(String jsonMsg){

		FeigeSmsRequest request = (FeigeSmsRequest) this.getObject(jsonMsg, FeigeSmsRequest.class);
		request.setTemplateId(App.FEIGE_ORDER_TEMPLATE_ID);
		logger.info(request.getMobile() + ":" + request.getContent());
		FeigeSmsClient feigeSmsClient = new FeigeSmsClient();
		feigeSmsClient.templateSmsSend(request);

	}



	Object getObject(String str,Class<?> clazz){
		ObjectMapper objectMapper = new ObjectMapper();
		try{
			return objectMapper.readValue(str, clazz);
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


}
