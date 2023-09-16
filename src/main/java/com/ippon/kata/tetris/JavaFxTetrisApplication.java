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

  public static final int CORE_POOL_SIZE = 20;
  public static final int MAX_POOL_SIZE = 100;
  public static final int QUEUE_CAPACITY = 500;
  public static final String THREAD_NAME_PREFIX = "tetris-";

  @Override
  public Executor getAsyncExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(CORE_POOL_SIZE);
    executor.setMaxPoolSize(MAX_POOL_SIZE);
    executor.setQueueCapacity(QUEUE_CAPACITY);
    executor.setThreadNamePrefix(THREAD_NAME_PREFIX);
    executor.initialize();
    return executor;
  }

  public static void main(String[] args) {
    Application.launch(TetrominoGame.class, args);
    System.exit(0);
  }
}
