package com.ippon.kata.tetris.scoring.application.domain;

import com.ippon.kata.tetris.shared.domain.asserts.Asserts;

public record ScoreComputedEvent(Score score) {

  public ScoreComputedEvent {
    Asserts.withContext(getClass()).notNull(score, "Score should not be null");
  }
}
