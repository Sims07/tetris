package com.ippon.kata.tetris.shared.infrastructure.secondary.spring.model;

import com.ippon.kata.tetris.executing.application.domain.TetrominoPickedEvent;
import java.util.UUID;
import org.springframework.context.ApplicationEvent;

public class TetrominoPickedEventDTO extends ApplicationEvent {

  private final UUID gameId;

  public TetrominoPickedEventDTO(Object source, UUID gameId) {
    super(source);
    this.gameId = gameId;
  }

  public static TetrominoPickedEventDTO from(Object source, TetrominoPickedEvent event) {
    return new TetrominoPickedEventDTO(source, event.gameId().value());
  }

  public UUID getGameId() {
    return gameId;
  }
}
