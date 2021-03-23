package com.example.mq1.util;

import com.example.mq1.bean.App;
import com.example.mq1.constant.CacheKey;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * 飞鸽平台短信发送工具
 */
public class FeigeSmsClient {

	private String account = App.FEIGE_ACCOUNT;
	private String pwd = App.FEIGE_PWD;
	private String signId = App.FEIGE_SIGNID;
	private String url = App.FEIGE_CUSTOM_URL;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	public void SendSms(FeigeSmsRequest feigeSmsRequest) {
		Logger logger = Logger.getLogger("sms");

		try {
			CloseableHttpClient client = null;
			CloseableHttpResponse response = null;
			try {

				String jsonMsg = redisTemplate.opsForList().rightPop(CacheKey.ORDER_SMS_MESSAGE);
				if (jsonMsg != null) {
					List<BasicNameValuePair> formparams = new ArrayList<>();
					requestParamSet(feigeSmsRequest, formparams);

					HttpPost httpPost = new HttpPost(this.url);
					httpPost.setEntity(new UrlEncodedFormEntity(formparams, "UTF-8"));
					client = HttpClients.createDefault();
					response = client.execute(httpPost);
					//响应
					HttpEntity entity = response.getEntity();
					String result = EntityUtils.toString(entity);
					System.err.println(result);
				}
			} finally {
				if (response != null) {
					response.close();
				}
				if (client != null) {
					client.close();
				}
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
	}

	/**
	 * 请求参数设置
	 * @param feigeSmsRequest
	 * @param formparams
	 */
	private void requestParamSet(FeigeSmsRequest feigeSmsRequest, List<BasicNameValuePair> formparams){

		formparams.add(new BasicNameValuePair("Account", this.account)); //账号
		formparams.add(new BasicNameValuePair("Pwd", this.pwd)); //密钥
		formparams.add(new BasicNameValuePair("Content", feigeSmsRequest.getContent()));
		formparams.add(new BasicNameValuePair("Mobile", feigeSmsRequest.getMobile()));
		formparams.add(new BasicNameValuePair("SignId", this.signId));

	}
}
