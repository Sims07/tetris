package com.ippon.kata.tetris.preparing.application.domain;

import com.ippon.kata.tetris.shared.domain.GameId;
import com.ippon.kata.tetris.shared.domain.ShapeType;
import java.util.Optional;
import java.util.Random;

public record TetrominoGenerator() {
  static Random random = new Random();

  public Optional<Tetromino> current() {
    return Optional.empty();
  }

  public TetrominoGeneratedEvent generate(GameId gameId) {
    return new TetrominoGeneratedEvent(new Tetromino(randomShape()), gameId);
  }

  private ShapeType randomShape() {
    return ShapeType.values()[randomInt()];
  }

  public int randomInt() {
    return random.ints(0, ShapeType.values().length).findFirst().orElse(0);
  }
}
