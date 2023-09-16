package com.ippon.kata.tetris.shared.domain;

public interface EventPublisher<T> {

  T publish(T event);
}
