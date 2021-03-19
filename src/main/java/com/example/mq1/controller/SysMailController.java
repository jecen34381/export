package com.example.mq1.controller;

import com.example.mq1.MQProducer.OnewayProducer;
import com.example.mq1.MQProducer.Producer;
import com.example.mq1.MQProducer.SyncProducer;
import com.example.mq1.bean.Mail;
import com.example.mq1.bean.Response;
import com.sun.org.apache.xerces.internal.impl.dv.xs.StringDV;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowFocusListener;
import java.rmi.Remote;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

@RequestMapping("/mq")
@RestController
public class SysMailController {

    @Autowired
    SyncProducer syncProducer;

    @Autowired
    OnewayProducer onewayProducer;

    @RequestMapping("/send/mail")
    public Response<String> sendMail(HttpServletResponse response){
        try {
            syncProducer.send();
            return new Response<String>("消息发送成功！");
        } catch (Exception e) {
            return new Response<String>("消息发送时出现异常！" + e.getMessage());
        }
    }

    @RequestMapping("/send/sms")
    public Response<String> sendSMS(HttpServletResponse response){
        try {
            onewayProducer.send();
            return new Response<String>("消息发送成功！");
        } catch (Exception e) {
            return new Response<String>("消息发送时出现异常！" + e.getMessage());
        }
    }

    @Test
    public void testHashSet(){

        try {
            Mail mail1 = new Mail();
            mail1.setMailFrom("123");
            mail1.setBody("1");
            Mail mail2 = new Mail();
            mail2.setMailFrom("123");
            mail2.setBody("2");
            Mail mail3 = new Mail();
            mail3.setMailFrom("123");
            mail3.setBody("3");

            Set<Mail> mailSet = new HashSet<>();
            mailSet.add(mail1);
            mailSet.add(mail2);
            mailSet.add(mail3);

            System.out.println(mailSet.size());
            for (Mail mail : mailSet){
                System.err.println(mail.getBody());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void copyArray() throws AWTException {


        Robot robot = new Robot();// 创建Robot对象

        robot.delay(2000);

        robot.keyPress(KeyEvent.VK_WINDOWS);
        robot.keyPress(KeyEvent.VK_R);

        robot.keyRelease(KeyEvent.VK_WINDOWS);
        robot.keyRelease(KeyEvent.VK_R);
        robot.delay(500);
        robot.mouseMove(80, 885);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);

        robot.keyPress(KeyEvent.VK_C);
        robot.keyPress(KeyEvent.VK_M);
        robot.keyPress(KeyEvent.VK_D);

        robot.keyRelease(KeyEvent.VK_C);
        robot.keyRelease(KeyEvent.VK_M);
        robot.keyRelease(KeyEvent.VK_D);

        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        robot.delay(1000);
        robot.keyPress(KeyEvent.VK_I);
        robot.keyRelease(KeyEvent.VK_I);
        robot.keyPress(KeyEvent.VK_P);
        robot.keyRelease(KeyEvent.VK_P);
        robot.keyPress(KeyEvent.VK_C);
        robot.keyRelease(KeyEvent.VK_C);
        robot.keyPress(KeyEvent.VK_O);
        robot.keyRelease(KeyEvent.VK_O);
        robot.keyPress(KeyEvent.VK_N);
        robot.keyRelease(KeyEvent.VK_N);
        robot.keyPress(KeyEvent.VK_F);
        robot.keyRelease(KeyEvent.VK_F);
        robot.keyPress(KeyEvent.VK_I);
        robot.keyRelease(KeyEvent.VK_I);
        robot.keyPress(KeyEvent.VK_G);
        robot.keyRelease(KeyEvent.VK_G);

        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }


    @Test
    public void contextLoads() {

        RedisTemplate<String,Integer> redisTemplate = new RedisTemplate<String,Integer>();


        redisTemplate.opsForList().leftPush("lottery", 1);
       /* System.out.println(redisTemplate);
        for (int i=0;i < 100;i++){

            redisTemplate.opsForList().leftPush("lottery", (int)(Math.random() * 10));
        }*/

    }


}
