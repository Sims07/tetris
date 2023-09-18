package com.ippon.kata.tetris.scoring.application.domain;

import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.Mockito.mock;

import com.ippon.kata.tetris.shared.domain.GameId;
import org.junit.jupiter.api.Test;

class ScoreTest {
  @Test
  void givenNoGameId_constructor_shouldThrowException() {
    thenThrownBy(() -> new Score(null, 1)).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void givenNegativeValue_constructor_shouldThrowException() {
    thenThrownBy(() -> new Score(mock(GameId.class), -1))
        .isInstanceOf(IllegalArgumentException.class);
  }
}
