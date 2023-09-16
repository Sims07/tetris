package com.ippon.kata.tetris.gaming.infrastructure.secondary.spring;

import com.ippon.kata.tetris.gaming.application.domain.NextRoundStartedEvent;
import com.ippon.kata.tetris.shared.secondary.spring.EventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class NextRoundStartedPublisher implements EventPublisher<NextRoundStartedEvent> {

    private final ApplicationEventPublisher applicationEventPublisher;

    public NextRoundStartedPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public NextRoundStartedEvent publish(NextRoundStartedEvent event) {
        applicationEventPublisher.publishEvent(NextRoundStartedEventDTO.from(this, event));
        return event;
    }
}
