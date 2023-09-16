package com.ippon.kata.tetris.scoring.secondary.spring;

import com.ippon.kata.tetris.scoring.domain.ScoreInitializedEvent;
import com.ippon.kata.tetris.shared.secondary.spring.EventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class ScoreInitializedPublisher implements EventPublisher<ScoreInitializedEvent> {

  private final ApplicationEventPublisher applicationEventPublisher;

  public ScoreInitializedPublisher(ApplicationEventPublisher applicationEventPublisher) {
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @Override
  public ScoreInitializedEvent publish(ScoreInitializedEvent event) {
    applicationEventPublisher.publishEvent(ScoreInitializedEventDTO.from(this, event));
    return event;
  }
}
