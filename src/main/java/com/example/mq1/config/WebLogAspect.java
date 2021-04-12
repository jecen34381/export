package com.example.mq1.config;

import com.example.mq1.bean.Bean;
import com.example.mq1.bean.Log;
import com.example.mq1.bean.SysLog;
import com.example.mq1.constant.CacheKey;
import com.example.mq1.util.IPUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;


//@Aspect
//@Component
public class WebLogAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RedisTemplate redisTemplate;
    private static final String REQUEST_ID = "requestId";
    static final ThreadLocal<Log> THREAD_LOCAL = new ThreadLocal<Log>();

    @Pointcut("execution(public * com.example.mq1.controller.*.*(..))")
    public void webLog(){ }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws JsonProcessingException {
        Log log = new Log();
        THREAD_LOCAL.set(log);
        log.setStartTime(System.currentTimeMillis());
        //请求参数
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        logger.info("request url : {}",request.getRequestURL().toString());
        logger.info("request method : {}",request.getMethod());
        logger.info("request ip : {}",request.getRemoteAddr());
        logger.info("execure class : {}",joinPoint.getSignature().getDeclaringTypeName());
        logger.info("execure method : {}",joinPoint.getSignature().getName());
        log.setRequestId(MDC.get(REQUEST_ID));
        log.setIp(IPUtil.getClientIP(request));
        log.setUrl(request.getRequestURI());

        //打印请求头部信息
        Enumeration<String> headerNames = request.getHeaderNames();
        if(headerNames != null){
            while(headerNames.hasMoreElements()){
                String headerName = headerNames.nextElement();
                logger.info("{} : {}",headerName,request.getHeader(headerName));
            }
        }
        //打印请求参数
        Enumeration<String> parameterNames = request.getParameterNames();
        if(parameterNames != null){
            while(parameterNames.hasMoreElements()){
                String parameterName = parameterNames.nextElement();
                logger.info("{} : {}",parameterName,request.getParameter(parameterName));
            }
        }
        String requestJSON = null;
        //打印参数信息
        Object[] args = joinPoint.getArgs();
        if(args != null){
            for(Object obj : args){
                if(obj instanceof Bean){
                    requestJSON = this.getJSON(obj);
                    logger.info("request : {}",requestJSON);
                }else{
                    logger.info("request : {}",obj);
                }
            }
        }
        // 日志
        log.setRequest(requestJSON);
        String sysLogJSON = (String) this.redisTemplate.opsForValue().get(CacheKey.SYS_LOG + MDC.get(REQUEST_ID));
        if (!StringUtils.isEmpty(sysLogJSON)) {
            ObjectMapper objectMapper = new ObjectMapper();
            SysLog sysLog = objectMapper.readValue(sysLogJSON, SysLog.class);
            sysLog.setUrl(request.getRequestURI());
            sysLog.setRequest(requestJSON);
            this.redisTemplate.opsForValue().set(CacheKey.SYS_LOG + MDC.get(REQUEST_ID), this.getJSON(sysLog));
        }
    }

    @AfterReturning(returning = "response", pointcut = "webLog()")
    public void doAfterReturning(Object response) throws JsonProcessingException {
        Log log = THREAD_LOCAL.get();
        if(response instanceof Bean){
            String responseJSON = this.getJSON(response);
            logger.info("response : {}",responseJSON);
            // 日志
            log.setResponse(responseJSON);
            String sysLogJSON = (String) this.redisTemplate.opsForValue().get(CacheKey.SYS_LOG + MDC.get(REQUEST_ID));
            if (!StringUtils.isEmpty(sysLogJSON)) {
                ObjectMapper objectMapper = new ObjectMapper();
                SysLog sysLog = objectMapper.readValue(sysLogJSON, SysLog.class);
                sysLog.setResponse(responseJSON);
                this.redisTemplate.opsForValue().set(CacheKey.SYS_LOG + MDC.get(REQUEST_ID), this.getJSON(sysLog));
            }
        }else{
            logger.info("response : {}",response);
        }
        log.setEndTime(System.currentTimeMillis());
        // 发送日志到队列
        try {
            if (MQConfig.ENABLE) {
                //MQProducer.INSTANCE.sendOneway(MDC.get(REQUEST_ID), MQTopic.ZXWY_API, MQTag.ZXWY_API_LOG, this.getJSON(log));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }

    /**
     * 请求参数拼装
     *
     * @param
     * @return
     */
    private String getJSON(Object obj){
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            return objectMapper.writeValueAsString(obj);
        }catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new RuntimeException(e);
        }
    }
}
