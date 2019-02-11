package com.stylefeng.guns.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.stylefeng.guns"})
public class GunsRestApplication {

    private final static Logger logger = LoggerFactory.getLogger(GunsRestApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(GunsRestApplication.class, args);
        logger.error("GunsRestApplication is success!");
    }
}
