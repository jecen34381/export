package com.example.mq1.util;

import org.apache.http.HttpRequest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class SecurityCodeUtils {
    // 图片的宽度。
    private int width = 160;
    // 图片的高度。
    private int height = 40;
    // 验证码字符个数
    private int codeCount = 5;
    // 验证码干扰线数
    private int lineCount = 50;
    // 验证码
    private String code = null;
    // 验证码图片Buffer
    private BufferedImage buffImg=null;

    private char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N',  'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z',  '1', '2', '3', '4', '5', '6', '7', '8', '9' };

    public BufferedImage codeCreate(HttpServletRequest request) throws IOException {

        RedisTemplate redisTemplate = new RedisTemplate();

        int x = 0,fontHeight=0,codeY=0;
        int red = 0, green = 0, blue = 0;

        x = width / (codeCount +2);//每个字符的宽度  
        fontHeight = height - 2;//字体的高度  
        codeY = height - 8;

        // 图像buffer  
        BufferedImage buffImg = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buffImg.createGraphics();
        // 生成随机数  
        Random random = new Random();
        // 将图像填充为白色  
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        g.setFont(new Font("宋体", Font.BOLD, 15));

        for (int i = 0; i < lineCount; i++) {
            int xs = random.nextInt(width);
            int ys = random.nextInt(height);
            int xe = xs+random.nextInt(width/8);
            int ye = ys+random.nextInt(height/8);
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            g.setColor(new Color(red, green, blue));
            g.drawLine(xs, ys, xe, ye);
        }

        // randomCode记录随机产生的验证码  
        StringBuffer randomCode = new StringBuffer();
        // 随机产生codeCount个字符的验证码。  
        for (int i = 0; i < codeCount; i++) {
            String strRand = String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);
            // 产生随机的颜色值，让输出的每个字符的颜色值都将不同。  
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            g.setColor(new Color(red, green, blue));
            g.drawString(strRand, (i + 1) * x, codeY);
            // 将产生的四个随机数组合在一起。  
            randomCode.append(strRand);
        }
        request.setAttribute("code", randomCode.toString());
        return buffImg;
    }
}
