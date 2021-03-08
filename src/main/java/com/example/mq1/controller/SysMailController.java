package com.example.mq1.controller;

import com.example.mq1.MQProducer.Producer;
import com.example.mq1.MQProducer.SyncProducer;
import com.example.mq1.bean.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/mail")
@RestController
public class SysMailController {

    @Autowired
    SyncProducer syncProducer;

    @RequestMapping("/send")
    public Response<String> sendMail(){
        try {
            syncProducer.send();
            return new Response<String>("消息发送成功！");
        } catch (Exception e) {
            return new Response<String>("消息发送时出现异常！" + e.getMessage());
        }
    }
}
