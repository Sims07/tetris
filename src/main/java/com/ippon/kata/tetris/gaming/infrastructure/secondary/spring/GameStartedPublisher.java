package com.ippon.kata.tetris.gaming.infrastructure.secondary.spring;

import com.ippon.kata.tetris.gaming.application.domain.GameStartedEvent;
import com.ippon.kata.tetris.shared.secondary.spring.EventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class GameStartedPublisher implements EventPublisher<GameStartedEvent> {

    private final ApplicationEventPublisher applicationEventPublisher;

    public GameStartedPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public GameStartedEvent publish(GameStartedEvent event) {
        applicationEventPublisher.publishEvent(GameStartedEventDTO.from(this, event));
        return event;
    }
}
