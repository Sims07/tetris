package com.ippon.kata.tetris.shared.infrastructure.secondary.spring.model;

import com.ippon.kata.tetris.executing.application.domain.LineIndex;
import com.ippon.kata.tetris.executing.application.domain.LinesErasedEvent;
import java.util.List;
import java.util.UUID;
import org.springframework.context.ApplicationEvent;

public class LinesErasedEventDTO extends ApplicationEvent {
  private final List<Integer> erasedLines;
  private final UUID gameId;
  private final int level;

  public LinesErasedEventDTO(Object source, List<Integer> erasedLines, UUID gameId, int level) {
    super(source);
    this.erasedLines = erasedLines;
    this.gameId = gameId;
    this.level = level;
  }

  public static LinesErasedEventDTO from(Object source, LinesErasedEvent domainEvent) {
    return new LinesErasedEventDTO(
        source,
        domainEvent.erasedLines().stream().map(LineIndex::value).toList(),
        domainEvent.gameId().value(),
        domainEvent.level().value());
  }

  public List<Integer> erasedLines() {
    return erasedLines;
  }

  public UUID gameId() {
    return gameId;
  }

  public int level() {
    return level;
  }
}
