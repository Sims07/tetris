package com.ippon.kata.tetris;

import com.ippon.kata.tetris.shared.primary.javafx.TetrominoGame;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class JavaFxTetrisApplication {
 
  public static void main(String[] args) {
    Application.launch(TetrominoGame.class, args);
  }

}
