package com.ippon.kata.tetris.gaming.infrastructure.secondary.spring;

import com.ippon.kata.tetris.gaming.application.domain.GameStartedEvent;
import java.util.UUID;
import org.springframework.context.ApplicationEvent;

public class GameStartedEventDTO extends ApplicationEvent {

  private final UUID gameId;
  private final int level;

  public GameStartedEventDTO(Object source, UUID gameId, int level) {
    super(source);
    this.gameId = gameId;
    this.level = level;
  }

  public static GameStartedEventDTO from(Object source, GameStartedEvent event) {
    return new GameStartedEventDTO(source, event.gameId().value(),event.level().value());
  }

  public UUID getGameId() {
    return gameId;
  }

  public int getLevel() {
    return level;
  }
}
