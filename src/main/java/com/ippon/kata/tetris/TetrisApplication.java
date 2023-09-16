package com.ippon.kata.tetris;

import com.ippon.kata.tetris.executing.usecase.MoveTetrominoUseCase;
import com.ippon.kata.tetris.gaming.domain.GameStartedEvent;
import com.ippon.kata.tetris.gaming.usecase.TetrisGameStartUseCase;
import com.ippon.kata.tetris.shared.Direction;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class TetrisApplication {
  @Autowired TetrisGameStartUseCase tetrisGameStartUseCase;
  @Autowired MoveTetrominoUseCase moveTetrominoUseCase;

  public static void main(String[] args) {
    SpringApplication.run(TetrisApplication.class, args);
  }

  @EventListener(ApplicationReadyEvent.class)
  public void doSomethingAfterStartup() {
    final GameStartedEvent gameStartedEvent = tetrisGameStartUseCase.start();
    Scanner scanner = new Scanner(System.in);
    while (scanner.hasNext()) {
      final String line = scanner.nextLine();
      switch (line) {
        case "l" -> moveTetrominoUseCase.move(gameStartedEvent.gameId(), Direction.LEFT);
        case "r" -> moveTetrominoUseCase.move(gameStartedEvent.gameId(), Direction.RIGHT);
        default -> moveTetrominoUseCase.move(gameStartedEvent.gameId(), Direction.DOWN);
      }
    }
  }
}
