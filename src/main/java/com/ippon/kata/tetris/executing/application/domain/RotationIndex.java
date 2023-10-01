package com.ippon.kata.tetris.executing.application.domain;

import com.ippon.kata.tetris.shared.domain.asserts.Asserts;

public record RotationIndex(int value) {

  public static final int NUMBER_OF_ROTATIONS = 4;
  public static final int NEXT_OFFSET = 1;

  public RotationIndex {
    Asserts.withContext(getClass())
        .strictlyGreaterThan(value, -NEXT_OFFSET, "Rotation start from 0")
        .strictlyLessThan(value, NUMBER_OF_ROTATIONS, "There is only 4 rotations");
  }

  public RotationIndex next() {
    return new RotationIndex((value + NEXT_OFFSET) % NUMBER_OF_ROTATIONS);
  }
}
