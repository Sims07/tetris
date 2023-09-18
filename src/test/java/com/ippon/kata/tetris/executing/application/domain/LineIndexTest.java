package com.ippon.kata.tetris.executing.application.domain;

import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LineIndexTest {

  @Test
  void givenNegativeValue_constructor_shouldThrowException() {
    thenThrownBy(() -> new LineIndex(-1)).isInstanceOf(IllegalArgumentException.class);
  }
}
