package com.ippon.kata.tetris.executing.application.domain;

import com.ippon.kata.tetris.shared.domain.asserts.Asserts;

public record LineIndex(int value) {

  public LineIndex {
    Asserts.withContext(getClass()).strictlyGreaterThan(value, -1, "Should be positive");
  }
}
