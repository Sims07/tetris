package com.ippon.kata.tetris.scoring.infrastructure.secondary.spring;

import com.ippon.kata.tetris.scoring.application.domain.ScoreComputedEvent;
import java.util.UUID;
import org.springframework.context.ApplicationEvent;

public class ScoreComputedEventDTO extends ApplicationEvent {

  private final UUID gameId;

  private final int score;

  public ScoreComputedEventDTO(Object source, UUID gameId, int score) {
    super(source);
    this.gameId = gameId;
    this.score = score;
  }

  public static ScoreComputedEventDTO from(Object source, ScoreComputedEvent event) {
    return new ScoreComputedEventDTO(source, event.score().gameId().value(), event.score().value());
  }

  public int score() {
    return score;
  }

  public UUID getGameId() {
    return gameId;
  }
}
