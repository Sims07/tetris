package com.ippon.kata.tetris.executing.application.domain;

import com.ippon.kata.tetris.shared.domain.GameId;
import com.ippon.kata.tetris.shared.domain.asserts.Asserts;

public record RoundIndex(int value, GameId gameId) {

  public RoundIndex {
    Asserts.withContext(getClass())
        .notNull(gameId, "Game id should not be null")
        .strictlyGreaterThan(value, -1, "Value should be positive");
  }
}
