package com.ippon.kata.tetris;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class TetrisApplication {

    public static void main(String[] args) {
        SpringApplication.run(TetrisApplication.class, args);
    }

}
