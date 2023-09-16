package com.ippon.kata.tetris.shared.secondary.spring.model;

import com.ippon.kata.tetris.executing.application.domain.LineIndex;
import com.ippon.kata.tetris.executing.application.domain.LinesErasedEvent;
import java.util.List;
import java.util.UUID;
import org.springframework.context.ApplicationEvent;

public class LinesErasedEventDTO extends ApplicationEvent {
  private final List<Integer> erasedLines;
  private final UUID gameId;

  public LinesErasedEventDTO(Object source, List<Integer> erasedLines, UUID gameId) {
    super(source);
    this.erasedLines = erasedLines;
    this.gameId = gameId;
  }

  public static LinesErasedEventDTO from(Object source, LinesErasedEvent domainEvent) {
    return new LinesErasedEventDTO(source,
        domainEvent.erasedLines().stream().map(LineIndex::value).toList(),
        domainEvent.gameId().value());
  }

  public List<Integer> erasedLines() {
    return erasedLines;
  }

  public UUID gameId() {
    return gameId;
  }
}
