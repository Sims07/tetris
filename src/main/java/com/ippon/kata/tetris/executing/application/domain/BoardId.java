package com.ippon.kata.tetris.executing.application.domain;

import com.ippon.kata.tetris.shared.domain.GameId;
import com.ippon.kata.tetris.shared.domain.asserts.Asserts;

public record BoardId(GameId gameId) {

  public BoardId {
    Asserts.withContext(getClass()).notNull(gameId, "Game id should not be null");
  }
}
