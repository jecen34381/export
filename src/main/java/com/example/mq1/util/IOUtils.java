package com.example.mq1.util;

import com.sun.org.apache.xerces.internal.impl.io.UTF8Reader;
import io.lettuce.core.StrAlgoArgs;
import org.junit.Test;
import org.springframework.web.util.WebUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.logging.Logger;

public class IOUtils {

    @Test
    public void fileReader() throws IOException {
        String separator = File.separator;
        File file = new File("C:" + separator + "Users" + separator + "Administrator" + separator + "Desktop" + separator + "noFinWork.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        //BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, "gbk"));
        System.out.println(bufferedReader.readLine());
       /* byte[] bytes = new byte[1024];
        int count = fileInputStream.read(bytes);
        while (count > 0){
           String hanzi = new String(bytes,"GBK");
            System.out.print(hanzi);
            count = fileInputStream.read(bytes);
        }*/

    }

    @Test
    public void videoRead() throws IOException {
        Logger logger = Logger.getLogger("this.()");
        String separator = File.separator;
        File video = new File("C:"+ separator + "Users" + separator + "Administrator"+ separator +"Desktop"+ separator +"a.mp4");
        FileInputStream fileInputStream = new FileInputStream(video);
        byte[] segment = new byte[1024];
        List<Byte> videoList = new ArrayList<>();
        int count = fileInputStream.read(segment);
        while (count > 0){
            for (byte b : segment){
                videoList.add(b);
            }
            count = fileInputStream.read(segment);
        }
        Integer videoLength = videoList.size();
        byte[] segment1 = new byte[videoLength / 272 / 2];
        final int[] index = {0};
        videoList.stream().forEach(b -> {
            if (index[0] < videoLength / 272 / 2){
                segment1[index[0]] = b;
                index[0] = index[0] + 1;
            }});
        FileOutputStream fos = new FileOutputStream("C:"+ separator + "Users" + separator + "Administrator"+ separator +"Desktop"+ separator +"segment1.mp4");
        fos.write(segment1);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        fileInputStream.close();
        fos.close();

    }

    @Test
    public void readHtml() throws IOException {
        String separator = File.separator;
        URL url = new URL("https://img0.baidu.com/it/u=1028905801,1902977906&fm=26&fmt=auto&gp=0.jpg");
        InputStream inputStream = url.openStream();
        byte[] bytes = new byte[1024 * 8];
        FileOutputStream fileOutputStream = new FileOutputStream("C:"+ separator + "Users" + separator + "Administrator"+ separator +"Desktop"+ separator +"xi.jpg");
        int count = inputStream.read(bytes);
        while (count > 0){
            fileOutputStream.write(bytes, 0, count);
            count = inputStream.read(bytes);
        }
        fileOutputStream.close();
        inputStream.close();
    }
}
