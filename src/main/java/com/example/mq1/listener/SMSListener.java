package com.example.mq1.listener;

import com.example.mq1.bean.App;
import com.example.mq1.bean.SMSPayload;
import com.example.mq1.constant.CacheKey;
import com.example.mq1.util.DateUtil;
import com.example.mq1.util.FeigeSmsClient;
import com.example.mq1.util.FeigeSmsRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Service
public class SMSListener implements MessageListenerConcurrently {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	/*@Autowired
	UserMessageDao userMessageDao;
	@Autowired
	UserDao userDao;*/
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
		try {
			Date datetime = DateUtil.getServerTime();
			for (MessageExt msg : msgs) {
				String body = new String(msg.getBody(),StandardCharsets.UTF_8);
				logger.info("zxwy data consume sms message : {}", body);
				SMSPayload smsPayload = (SMSPayload)getObject(body, SMSPayload.class);
				//类型 0:验证码 1:通知  2:营销
				switch (smsPayload.getType()){
					case "2":
						String mobiles = batchCacheMessage(smsPayload.getMobile(), App.FEIGE_PROMOTION_CACHE_UPPER_LIMIT);
						if (mobiles != null){
							customFeiGeSmsSend(mobiles);
						}
						break;
					case "1":break;
					case "0":break;
				}

			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}

	/**
	 * 使用飞鸽平台发送推广短信
	 * @author 张永贺
	 * @param payload
	 */
	public void customFeiGeSmsSend(String mobiles){
		Date datetime = DateUtil.getServerTime();
		//设置发送参数
		FeigeSmsRequest request = new FeigeSmsRequest();
		//设置接收方手机号
		request.setMobile(mobiles);
		//设置发送内容
		//request.setContent(payload.getContent());
		//获取本地时间，小时
		Integer localHour = Integer.valueOf(DateUtil.format(new Date(), "HH"));
		//7--20点发送2小时   20--7点发送8小时
		if (6 < localHour && localHour < 20){
			request.setContent(App.FEIGE_PROMOTION_TWO_HOURS_SMS_CONTENT);
		}else{
			request.setContent(App.FEIGE_PROMOTION_EIGHT_HOURS_SMS_CONTENT);
		}

		//初始化发送端
		FeigeSmsClient client = new FeigeSmsClient();
		//返回结果是否发送成功
		boolean success = client.customSendSms(request);
		//获取所有手机号的用户
		//List<User> users = userDao.selectByUsernames(Arrays.asList(mobiles.split(",")));
		//根据手机号查询用户id
		//User user = userDao.selectByUsername(payload.getMobile());
		try {
			if (success) {
				/*for (User user : users) {
					UserMessage userMessage = new UserMessage();
					userMessage.setUserId(user.getId());
					userMessage.setStatus(DataStatus.Y.getCode());
					userMessage.setType(UserMessageType.SMS.getCode());
					userMessage.setContent(request.getContent());
					userMessage.setCreateTime(datetime);
					userMessage.setModifyTime(datetime);
					userMessageDao.insert(userMessage);
				}*/
			}
		}catch (Exception e){
			logger.error("Promotion sms error message {},{}",e.getMessage(),e);
		}
	}

	/**
	 * 每一个进来的请求都压入缓存，每二十条发送一次
	 * @param mobile
	 * @param lengthOfBatch 批次长度
	 * @return
	 */
	public String batchCacheMessage(String mobile, long lengthOfBatch){

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

	Object getObject(String str,Class<?> clazz){
		ObjectMapper objectMapper = new ObjectMapper();
		try{
			return objectMapper.readValue(str, clazz);
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
