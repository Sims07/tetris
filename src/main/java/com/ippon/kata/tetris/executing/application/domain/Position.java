package com.ippon.kata.tetris.executing.application.domain;

public record Position(int x, int y) {

  public Position add(Position position) {
    return new Position(x + position.x(), y + position.y());
  }
}
