package com.example.mq1.util;

import com.alibaba.fastjson.JSON;
import com.example.mq1.bean.App;
import com.example.mq1.bean.FeiGeSendResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.data.redis.core.RedisTemplate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * 飞鸽平台短信发送工具
 */
public class FeigeSmsClient {

	private String account = App.FEIGE_ACCOUNT;
	private String pwd = App.FEIGE_PWD;
	private String signId = App.FEIGE_SIGNID;
	private String url = App.FEIGE_CUSTOM_URL;
	private String templateUrl = App.FEIGE_TEMPLATE_URL;
	Logger logger = Logger.getLogger("sms");
	@Autowired
	private RedisTemplate<String, String> redisTemplate;


	public void SendSms() {
		FeigeSmsRequest feigeSmsRequest = new FeigeSmsRequest();
		feigeSmsRequest.setMobile("13839453763");
		feigeSmsRequest.setContent("同学您好，欢迎来到众学！我们将于8个小时内与您取得联系！请保持手机畅通！2021法考最新课程已开课，专属定制学习计划，一对一精准辅导助您无忧通关！限时咨询16264403382(同微)  退订回T。");



		try {
			CloseableHttpClient client = null;
			CloseableHttpResponse response = null;
			try {


				ArrayList<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
					requestParamSet(feigeSmsRequest, formparams);

					HttpPost httpPost = new HttpPost(this.url);
					httpPost.setEntity(new UrlEncodedFormEntity(formparams, "UTF-8"));
					client = HttpClients.createDefault();
					response = client.execute(httpPost);
					//响应
					HttpEntity entity = response.getEntity();
					String result = EntityUtils.toString(entity);
					System.err.println(result);

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
	 * 指定模板的短信发送
	 * @param feigeSmsRequest
	 */

	public void templateSmsSend(FeigeSmsRequest feigeSmsRequest){
		try {
			CloseableHttpClient client = null;
			CloseableHttpResponse response = null;
			try {

				//请求参数设置
				ArrayList<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
				formparams.add(new BasicNameValuePair("TemplateId", feigeSmsRequest.getTemplateId())); //账号
				requestParamSet(feigeSmsRequest, formparams);
				//请求执行
				HttpPost httpPost = new HttpPost(this.templateUrl);
				httpPost.setEntity(new UrlEncodedFormEntity(formparams, "UTF-8"));
				client = HttpClients.createDefault();
				response = client.execute(httpPost);
				//响应
				HttpEntity entity = response.getEntity();
				String result = EntityUtils.toString(entity);
				logger.info(result);
				FeiGeSendResponse feiGeSendResponse = (FeiGeSendResponse)getObject(result, FeiGeSendResponse.class);
				System.out.println(feiGeSendResponse.getBlackCount() + ":" + feiGeSendResponse.getCode() + ":" + feiGeSendResponse.getSuccessCount());
				HashMap<String, Object> resultMap = this.stringToMap(result);
				//状态码为0，则表示发送成功
				if (0 == (int)resultMap.get("Code")){
					logger.info("发送成功！");
				}else{
					logger.info("发送失败！");
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
			logger.info("发送失败！");
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

	/**
	 * 字符串转map
	 * @param str
	 * @return
	 */
	public HashMap<String, Object> stringToMap(String str){
		HashMap<String, Object> returnMap = JSON.parseObject(str, HashMap.class);
		return returnMap;
	}

	Object getObject(String str,Class<?> clazz){
		ObjectMapper objectMapper = new ObjectMapper();
		try{
			return objectMapper.readValue(str, clazz);
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 自定义内容的短信发送
	 * @author 张永贺
	 * @param feigeSmsRequest
	 */
	public boolean customSendSms(FeigeSmsRequest feigeSmsRequest) {

		try {
			CloseableHttpClient client = null;
			CloseableHttpResponse response = null;
			try {
				//logger.info("FeiGe sms response : {} , {}", new ObjectMapper().writeValueAsString(feigeSmsRequest));
				//请求参数设置
				ArrayList<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
				requestParamSet(feigeSmsRequest, formparams);
				//请求执行
				HttpPost httpPost = new HttpPost(this.url);
				httpPost.setEntity(new UrlEncodedFormEntity(formparams, "UTF-8"));
				client = HttpClients.createDefault();
				response = client.execute(httpPost);
				//响应
				HttpEntity entity = response.getEntity();
				String result = EntityUtils.toString(entity);
				//logger.info(result);
				//数据转换
				FeiGeSendResponse feiGeSendResponse = this.transferSendResponseToFeiGeSendResponse(result);
				//logger.info("yuntongxun sms response : {} , {}", feiGeSendResponse.getCode(), new ObjectMapper().writeValueAsString(feiGeSendResponse));
				//Map<String, Object> resultMap = this.stringToMap(result);
				//状态码为0，则表示发送成功
				if (0 == feiGeSendResponse.getCode()){
					return true;
				}else{
					return false;
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
			logger.info("Sms error message:" + e.getMessage());
			return false;
		}
	}
	/**
	 * 飞鸽平台返回的json字符串转换
	 * @param sendResponse
	 * @return
	 */
	public FeiGeSendResponse transferSendResponseToFeiGeSendResponse(String sendResponse){
		return (FeiGeSendResponse)getObject(sendResponse, FeiGeSendResponse.class);
	}
}
