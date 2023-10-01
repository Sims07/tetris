package com.ippon.kata.tetris.executing.infrastructure.secondary.spring;

import com.ippon.kata.tetris.executing.application.domain.TetrominoPickedEvent;
import com.ippon.kata.tetris.shared.infrastructure.secondary.spring.AbstractPublisher;
import com.ippon.kata.tetris.shared.infrastructure.secondary.spring.model.TetrominoPickedEventDTO;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class TetrominoPickedPublisher
    extends AbstractPublisher<TetrominoPickedEventDTO, TetrominoPickedEvent> {

  public TetrominoPickedPublisher(ApplicationEventPublisher applicationEventPublisher) {
    super(applicationEventPublisher);
  }

  @Override
  protected TetrominoPickedEventDTO from(Object source, TetrominoPickedEvent domainEvent) {
    return TetrominoPickedEventDTO.from(source, domainEvent);
  }
}
