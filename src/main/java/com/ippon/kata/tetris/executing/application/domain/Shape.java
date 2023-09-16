package com.ippon.kata.tetris.executing.application.domain;

import com.ippon.kata.tetris.shared.domain.ShapeType;
import java.util.List;

public record Shape(ShapeType shapeType) {

  public List<Position> initPositions() {
    return switch (shapeType) {
      case I -> List.of(
          new Position(3, 0), new Position(4, 0), new Position(5, 0), new Position(6, 0));
      case J -> List.of(
          new Position(5, 0), new Position(5, 1), new Position(4, 2), new Position(5, 2));
      case L -> List.of(
          new Position(5, 0), new Position(5, 1), new Position(5, 2), new Position(6, 2));
      case O -> List.of(
          new Position(5, 0), new Position(5, 1), new Position(6, 0), new Position(6, 1));
      case Z -> List.of(
          new Position(4, 0), new Position(5, 0), new Position(5, 1), new Position(6, 1));
      case T -> List.of(
          new Position(4, 0), new Position(5, 0), new Position(6, 0), new Position(5, 1));
      case S -> List.of(
          new Position(4, 1), new Position(5, 0), new Position(5, 1), new Position(6, 0));
    };
  }
  public List<Position> translatedRotationPositions(RotationIndex rotationIndex) {
    return switch (shapeType) {
      case I -> switch (rotationIndex.value()) {
        case 0, 2 -> List.of(new Position(1,1),new Position(0,0),new Position(-1,-1),new Position(-2,-2));
        case 1, 3 -> List.of(new Position(-2,-2),new Position(-1,-1),new Position(0,0),new Position(1,1));
        default -> throw new IllegalStateException("Unexpected value: " + rotationIndex.value());
      };
      case J -> null;
      case L -> null;
      case O -> null;
      case S -> null;
      case T -> null;
      case Z -> null;
    };
  }
}
