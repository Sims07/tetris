package com.ippon.kata.tetris.scoring.infrastructure.secondary.spring;

import com.ippon.kata.tetris.scoring.application.domain.ScoreUpdatedEvent;
import java.util.UUID;
import org.springframework.context.ApplicationEvent;

public class ScoreUpdatedEventDTO extends ApplicationEvent {

  private final UUID gameId;

  private final int score;

  public ScoreUpdatedEventDTO(Object source, UUID gameId, int score) {
    super(source);
    this.gameId = gameId;
    this.score = score;
  }

  public static ScoreUpdatedEventDTO from(Object source, ScoreUpdatedEvent event) {
    return new ScoreUpdatedEventDTO(source, event.score().gameId().value(), event.score().value());
  }

  public int score() {
    return score;
  }

  public UUID getGameId() {
    return gameId;
  }
}
