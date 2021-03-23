package com.example.mq1.controller;

import com.example.mq1.util.SecurityCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Controller
public class SecurityCodeController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @RequestMapping("/get/security/code")
    public void codeCreate(HttpServletRequest request, HttpServletResponse response){
        try {
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/jpeg");
            BufferedImage bfi = new SecurityCodeUtils().codeCreate(request);
            ImageIO.write(bfi,"JPEG",response.getOutputStream());
            System.err.println(request.getRemoteAddr());
            redisTemplate.opsForValue().set(request.getRemoteAddr(), (String)request.getAttribute("code"), 60, TimeUnit.SECONDS);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
