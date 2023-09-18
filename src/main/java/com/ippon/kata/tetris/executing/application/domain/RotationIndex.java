package com.ippon.kata.tetris.executing.application.domain;

import com.ippon.kata.tetris.shared.domain.asserts.Asserts;

public record RotationIndex(int value) {

  public RotationIndex {
    Asserts.withContext(getClass())
        .strictlyGreaterThan(value, -1, "Rotation start from 0")
        .strictlyLessThan(value, 4, "There is only 4 rotations");
  }

  public RotationIndex next() {
    return new RotationIndex((value + 1) % 4);
  }
}
