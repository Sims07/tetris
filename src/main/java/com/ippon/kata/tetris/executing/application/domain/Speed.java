package com.ippon.kata.tetris.executing.application.domain;

import com.ippon.kata.tetris.shared.domain.Level;

public record Speed(Level level) {

  public static final int INITIAL_SPEED = 1000;
  public static final int SPEED_COEF = 100;
  public static final int DELTA = 1;

  public int value() {
    return INITIAL_SPEED - levelSpeedOffset();
  }

  private int levelSpeedOffset() {
    return (level().value() - DELTA) * SPEED_COEF;
  }
}
