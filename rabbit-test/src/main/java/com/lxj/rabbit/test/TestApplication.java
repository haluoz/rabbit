package com.lxj.rabbit.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Xingjing.Li
 * @since 2021/8/18
 */
@SpringBootApplication
@ComponentScan({"com.lxj.rabbit.*"})
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}
