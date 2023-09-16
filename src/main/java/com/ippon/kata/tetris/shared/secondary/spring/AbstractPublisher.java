package com.ippon.kata.tetris.shared.secondary.spring;

import com.ippon.kata.tetris.shared.EventPublisher;
import org.springframework.context.ApplicationEventPublisher;

public abstract class AbstractPublisher<DTO, D> implements EventPublisher<D> {

    private final ApplicationEventPublisher applicationEventPublisher;

    public AbstractPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    protected abstract DTO from(Object source, D domainEvent);

    @Override
    public D publish(D event) {
        applicationEventPublisher.publishEvent(from(this, event));
        return event;
    }

}
