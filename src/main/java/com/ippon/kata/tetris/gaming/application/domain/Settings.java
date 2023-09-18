package com.ippon.kata.tetris.gaming.application.domain;

import static com.ippon.kata.tetris.shared.domain.asserts.Asserts.withContext;

import com.ippon.kata.tetris.shared.domain.Level;

public record Settings(Level level) {

  public Settings {
    withContext(getClass()).notNull(level, "Level should not be null");
  }

  public Settings increaseLevel() {
    return new Settings(level.next());
  }
}
