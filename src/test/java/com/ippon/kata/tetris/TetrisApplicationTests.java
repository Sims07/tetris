package com.ippon.kata.tetris;

import com.ippon.kata.tetris.gaming.usecase.TetrisGameStartUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TetrisApplicationTests {

    @Autowired
    TetrisGameStartUseCase tetrisGameStartUseCase;

    @Test
    void startGame() throws InterruptedException {
        tetrisGameStartUseCase.start();
        Thread.sleep(10000);
    }

}
