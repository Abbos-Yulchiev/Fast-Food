package com.demo.demo;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Demo {

    @Scheduled(fixedDelay = 1000, initialDelay = 1000)
    public void test() {
        System.out.println("-------------------");
    }
}
