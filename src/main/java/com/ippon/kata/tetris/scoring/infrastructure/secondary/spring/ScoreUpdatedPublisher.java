package com.ippon.kata.tetris.scoring.infrastructure.secondary.spring;

import com.ippon.kata.tetris.scoring.application.domain.ScoreUpdatedEvent;
import com.ippon.kata.tetris.shared.secondary.spring.EventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class ScoreUpdatedPublisher implements EventPublisher<ScoreUpdatedEvent> {

  private final ApplicationEventPublisher applicationEventPublisher;

  public ScoreUpdatedPublisher(ApplicationEventPublisher applicationEventPublisher) {
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @Override
  public ScoreUpdatedEvent publish(ScoreUpdatedEvent event) {
    applicationEventPublisher.publishEvent(ScoreUpdatedEventDTO.from(this, event));
    return event;
  }
}
