package com.example.mq1.controller;

import com.example.mq1.MQProducer.Producer;
import com.example.mq1.MQProducer.SyncProducer;
import com.example.mq1.bean.Mail;
import com.example.mq1.bean.Response;
import com.sun.org.apache.xerces.internal.impl.dv.xs.StringDV;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RequestMapping("/mail")
@RestController
public class SysMailController {

    @Autowired
    SyncProducer syncProducer;

    @RequestMapping("/send")
    public Response<String> sendMail(HttpServletResponse response){
        try {
            syncProducer.send();
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

}
