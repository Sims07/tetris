package com.ippon.kata.tetris.scoring.infrastructure.secondary.spring;

import com.ippon.kata.tetris.scoring.application.domain.ScoreComputedEvent;
import com.ippon.kata.tetris.shared.domain.EventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class ScoreUpdatedPublisher implements EventPublisher<ScoreComputedEvent> {

  private final ApplicationEventPublisher applicationEventPublisher;

  public ScoreUpdatedPublisher(ApplicationEventPublisher applicationEventPublisher) {
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @Override
  public ScoreComputedEvent publish(ScoreComputedEvent event) {
    applicationEventPublisher.publishEvent(ScoreComputedEventDTO.from(this, event));
    return event;
  }
}
