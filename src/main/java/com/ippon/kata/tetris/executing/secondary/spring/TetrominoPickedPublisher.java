package com.ippon.kata.tetris.executing.secondary.spring;

import com.ippon.kata.tetris.executing.domain.TetrominoPickedEvent;
import com.ippon.kata.tetris.shared.TetrominoPickedEventDTO;
import com.ippon.kata.tetris.shared.secondary.spring.AbstractPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class TetrominoPickedPublisher extends AbstractPublisher<TetrominoPickedEventDTO, TetrominoPickedEvent> {

    public TetrominoPickedPublisher(ApplicationEventPublisher applicationEventPublisher) {
        super(applicationEventPublisher);
    }

    @Override
    protected TetrominoPickedEventDTO from(Object source, TetrominoPickedEvent domainEvent) {
        return TetrominoPickedEventDTO.from(source, domainEvent);
    }
}
