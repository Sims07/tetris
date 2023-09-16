package com.ippon.kata.tetris.shared;

public interface EventPublisher<T> {

    T publish(T event);
}
