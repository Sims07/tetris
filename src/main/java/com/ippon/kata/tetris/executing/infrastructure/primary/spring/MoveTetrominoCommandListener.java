package com.ippon.kata.tetris.executing.infrastructure.primary.spring;

import com.ippon.kata.tetris.executing.application.usecase.MoveTetrominoUseCase;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class MoveTetrominoCommandListener {
  private final MoveTetrominoUseCase moveTetrominoUseCase;

  public MoveTetrominoCommandListener(MoveTetrominoUseCase moveTetrominoUseCase) {
    this.moveTetrominoUseCase = moveTetrominoUseCase;
  }

  @Async
  @EventListener
  public void onApplicationEvent(MoveTetrominoCmd event) {
    moveTetrominoUseCase.move(event.gameId(), event.direction());
  }
}
