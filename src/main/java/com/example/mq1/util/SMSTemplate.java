package com.example.mq1.util;

import com.example.mq1.annotation.TAnnotation;
import org.junit.Test;

import java.lang.annotation.Annotation;

@TAnnotation
public class SMSTemplate {
    public void run(){

    }

    @Test
    public void parseAnotaion(){

        Class c = SMSTemplate.class;
        Annotation[] annotations = c.getAnnotations();
        for (Annotation annotation : annotations){
            System.out.println(annotation);
        }
    }
}
