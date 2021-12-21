package com.cl.smyblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.cl.smyblog.dao")
public class SmyblogApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmyblogApplication.class, args);
    }

}
