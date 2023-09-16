package com.ippon.kata.tetris.executing.infrastructure.secondary.spring;

import com.ippon.kata.tetris.executing.application.domain.BoardInitializedEvent;
import com.ippon.kata.tetris.shared.secondary.spring.AbstractPublisher;
import com.ippon.kata.tetris.shared.secondary.spring.model.BoardInitializedEventDTO;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class BoardInitializedPublisher extends AbstractPublisher<BoardInitializedEventDTO, BoardInitializedEvent> {

    public BoardInitializedPublisher(ApplicationEventPublisher applicationEventPublisher) {
        super(applicationEventPublisher);
    }

    @Override
    protected BoardInitializedEventDTO from(Object source, BoardInitializedEvent domainEvent) {
        return BoardInitializedEventDTO.from(this, domainEvent);
    }
}
