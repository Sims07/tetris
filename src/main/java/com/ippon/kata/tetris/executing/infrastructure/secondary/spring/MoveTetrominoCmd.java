package com.ippon.kata.tetris.executing.infrastructure.secondary.spring;

import com.ippon.kata.tetris.shared.domain.Direction;
import com.ippon.kata.tetris.shared.domain.GameId;
import org.springframework.context.ApplicationEvent;

public class MoveTetrominoCmd extends ApplicationEvent {
  private final GameId gameId;
  private final Direction direction;

  public MoveTetrominoCmd(Object source, GameId gameId, Direction direction) {
    super(source);
    this.gameId = gameId;
    this.direction = direction;
  }

  public Direction direction() {
    return direction;
  }

  public GameId gameId() {
    return gameId;
  }
}
