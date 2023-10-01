package com.ippon.kata.tetris.shared.infrastructure.secondary.spring.model;

import com.ippon.kata.tetris.executing.application.domain.Position;

public record PositionDTO(int x, int y) {
  public static PositionDTO from(Position position) {
    return new PositionDTO(position.y(), position.x());
  }
}
