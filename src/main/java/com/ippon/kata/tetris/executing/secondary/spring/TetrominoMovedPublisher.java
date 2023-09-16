package com.ippon.kata.tetris.executing.secondary.spring;

import com.ippon.kata.tetris.executing.domain.TetrominoMovedEvent;
import com.ippon.kata.tetris.shared.TetrominoMovedEventDTO;
import com.ippon.kata.tetris.shared.secondary.spring.AbstractPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class TetrominoMovedPublisher extends AbstractPublisher<TetrominoMovedEventDTO, TetrominoMovedEvent> {

    public TetrominoMovedPublisher(ApplicationEventPublisher applicationEventPublisher) {
        super(applicationEventPublisher);
    }

    @Override
    protected TetrominoMovedEventDTO from(Object source, TetrominoMovedEvent domainEvent) {
        return TetrominoMovedEventDTO.from(source, domainEvent);
    }

}
