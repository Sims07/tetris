package com.ippon.kata.tetris.preparing.infrastructure.secondary.spring;

import com.ippon.kata.tetris.preparing.application.domain.TetrominoGeneratedEvent;
import com.ippon.kata.tetris.shared.secondary.spring.EventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class TetrominoGeneratedPublisher implements EventPublisher<TetrominoGeneratedEvent> {

    private final ApplicationEventPublisher applicationEventPublisher;

    public TetrominoGeneratedPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public TetrominoGeneratedEvent publish(TetrominoGeneratedEvent event) {
        applicationEventPublisher.publishEvent(TetrominoGeneratedEventDTO.from(this, event));
        return event;
    }
}
