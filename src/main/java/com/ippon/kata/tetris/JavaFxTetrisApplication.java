package com.ippon.kata.tetris;

import com.ippon.kata.tetris.shared.primary.javafx.TetrominoGame;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.task.support.TaskExecutorAdapter;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class JavaFxTetrisApplication implements AsyncConfigurer {

  @Override
  public Executor getAsyncExecutor() {
    return new TaskExecutorAdapter(Executors.newVirtualThreadPerTaskExecutor());
  }

  public static void main(String[] args) {
    Application.launch(TetrominoGame.class, args);
    System.exit(0);
  }
}
