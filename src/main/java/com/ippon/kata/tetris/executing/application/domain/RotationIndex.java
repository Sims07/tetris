package com.ippon.kata.tetris.executing.application.domain;

public record RotationIndex(int value) {

  public RotationIndex next() {
    return new RotationIndex((value + 1) % 4);
  }
}
