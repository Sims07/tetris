package com.ippon.kata.tetris.executing.infrastructure.secondary.spring;

import com.ippon.kata.tetris.executing.application.domain.LinesErasedEvent;
import com.ippon.kata.tetris.shared.secondary.spring.AbstractPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class LinesErasedPublisher extends AbstractPublisher<LinesErasedEvent, LinesErasedEvent> {

    public LinesErasedPublisher(ApplicationEventPublisher applicationEventPublisher) {
        super(applicationEventPublisher);
    }

    @Override
    protected LinesErasedEvent from(Object source, LinesErasedEvent domainEvent) {
        return domainEvent;
    }
}
