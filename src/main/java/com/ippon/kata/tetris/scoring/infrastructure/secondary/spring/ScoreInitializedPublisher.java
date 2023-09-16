package com.ippon.kata.tetris.scoring.infrastructure.secondary.spring;

import com.ippon.kata.tetris.scoring.application.domain.ScoreUpdatedEvent;
import com.ippon.kata.tetris.shared.secondary.spring.EventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class ScoreInitializedPublisher implements EventPublisher<ScoreUpdatedEvent> {

  private final ApplicationEventPublisher applicationEventPublisher;

  public ScoreInitializedPublisher(ApplicationEventPublisher applicationEventPublisher) {
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @Override
  public ScoreUpdatedEvent publish(ScoreUpdatedEvent event) {
    applicationEventPublisher.publishEvent(ScoreInitializedEventDTO.from(this, event));
    return event;
  }
}
