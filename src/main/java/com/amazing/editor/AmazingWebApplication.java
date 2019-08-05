package com.amazing.editor;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.amazing.editor.dao")
public class AmazingWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(AmazingWebApplication.class, args);
    }

}
