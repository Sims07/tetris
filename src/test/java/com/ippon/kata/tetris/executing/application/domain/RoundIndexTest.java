package com.ippon.kata.tetris.executing.application.domain;

import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import com.ippon.kata.tetris.shared.domain.GameId;
import org.junit.jupiter.api.Test;

class RoundIndexTest {

  @Test
  void givenNegativeRound_constructor_shouldThrowException() {
    thenThrownBy(() -> new RoundIndex(-1, mock(GameId.class)))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void givenNullGameId_constructor_shouldThrowException() {
    thenThrownBy(() -> new RoundIndex(1, null)).isInstanceOf(IllegalArgumentException.class);
  }
}
