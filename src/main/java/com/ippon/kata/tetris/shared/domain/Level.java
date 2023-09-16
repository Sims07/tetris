package com.ippon.kata.tetris.shared.domain;

import static com.ippon.kata.tetris.shared.asserts.Asserts.withContext;

public record Level(int value) {

  public static final int OFFSET = 1;

  public Level {
    withContext(getClass()).strictlyGreaterThan(value, 0.0, "Level value should not be negative");
  }

  public Level next() {
     return new Level(value + OFFSET);
  }
}
