package com.ippon.kata.tetris.executing.application.domain;

import com.ippon.kata.tetris.shared.domain.asserts.Asserts;
import java.util.UUID;

public record TetrominoId(UUID value) {

  public TetrominoId {
    Asserts.withContext(getClass()).notNull(value, "Should not be null");
  }
}
