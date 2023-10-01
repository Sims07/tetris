package com.ippon.kata.tetris.executing.application.domain;

import static org.assertj.core.api.BDDAssertions.thenThrownBy;

import org.junit.jupiter.api.Test;

class TetrominoIdTest {

  @Test
  void givenNullId_constructor_shouldThrowException() {
    thenThrownBy(() -> new TetrominoId(null)).isInstanceOf(IllegalArgumentException.class);
  }
}
