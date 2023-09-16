package com.ippon.kata.tetris.gaming.secondary.spring;

import com.ippon.kata.tetris.gaming.domain.GameStartedEvent;
import com.ippon.kata.tetris.shared.EventPublisher;
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
