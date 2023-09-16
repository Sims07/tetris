package com.ippon.kata.tetris;

import com.ippon.kata.tetris.shared.primary.javafx.TetrominoGame;
import java.util.concurrent.Executor;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@EnableAsync
public class JavaFxTetrisApplication implements AsyncConfigurer {
  @Override
  public Executor getAsyncExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(20);
    executor.setMaxPoolSize(100);
    executor.setQueueCapacity(500);
    executor.setThreadNamePrefix("tetris-");
    executor.initialize();
    return executor;
  }

  public static void main(String[] args) {
    Application.launch(TetrominoGame.class, args);
  }
}
