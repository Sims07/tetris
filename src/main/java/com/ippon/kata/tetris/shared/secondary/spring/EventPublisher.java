package com.ippon.kata.tetris.shared.secondary.spring;

public interface EventPublisher<T> {

    T publish(T event);
}
