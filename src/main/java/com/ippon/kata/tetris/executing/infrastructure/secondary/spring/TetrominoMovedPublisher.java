package com.ippon.kata.tetris.executing.infrastructure.secondary.spring;

import com.ippon.kata.tetris.executing.application.domain.TetrominoMovedEvent;
import com.ippon.kata.tetris.shared.infrastructure.secondary.spring.AbstractPublisher;
import com.ippon.kata.tetris.shared.infrastructure.secondary.spring.model.TetrominoMovedEventDTO;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class TetrominoMovedPublisher
    extends AbstractPublisher<TetrominoMovedEventDTO, TetrominoMovedEvent> {

  public TetrominoMovedPublisher(ApplicationEventPublisher applicationEventPublisher) {
    super(applicationEventPublisher);
  }

  @Override
  protected TetrominoMovedEventDTO from(Object source, TetrominoMovedEvent domainEvent) {
    return TetrominoMovedEventDTO.from(source, domainEvent);
  }
}
