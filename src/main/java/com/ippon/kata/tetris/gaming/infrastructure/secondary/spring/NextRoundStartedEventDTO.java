package com.ippon.kata.tetris.gaming.infrastructure.secondary.spring;

import com.ippon.kata.tetris.gaming.application.domain.NextRoundStartedEvent;
import com.ippon.kata.tetris.shared.domain.Level;
import java.util.UUID;
import org.springframework.context.ApplicationEvent;

public class NextRoundStartedEventDTO extends ApplicationEvent {

  private final UUID gameId;
  private final String shapeType;
  private final int roundIndex;
  private final int level;

  public NextRoundStartedEventDTO(
      Object source, UUID gameId, String shapeType, int roundIndex, Level level) {
    super(source);
    this.gameId = gameId;
    this.shapeType = shapeType;
    this.roundIndex = roundIndex;
    this.level = level.value();
  }

  public static NextRoundStartedEventDTO from(Object source, NextRoundStartedEvent event) {
    return new NextRoundStartedEventDTO(
        source,
        event.gameId().value(),
        event.shapeType().name(),
        event.roundIndex(),
        event.level());
  }

  public UUID gameId() {
    return gameId;
  }

  public String shapeType() {
    return shapeType;
  }

  public int roundIndex() {
    return roundIndex;
  }

  public int level() {
    return level;
  }
}
