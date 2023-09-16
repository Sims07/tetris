package com.ippon.kata.tetris.executing.domain;

import com.ippon.kata.tetris.shared.domain.ShapeType;
import java.util.List;

public record Shape(ShapeType shapeType) {

    public List<Position> initPositions() {
        return switch (shapeType) {
            case I -> List.of(
                new Position(0, 5),
                new Position(1, 5),
                new Position(2, 5),
                new Position(3, 5)
            );
            case J -> List.of(
                new Position(0, 5),
                new Position(1, 5),
                new Position(2, 4),
                new Position(2, 5)
            );
            case L -> List.of(
                new Position(0, 5),
                new Position(1, 5),
                new Position(2, 5),
                new Position(2, 6)
            );
            case O -> List.of(
                new Position(0, 5),
                new Position(1, 5),
                new Position(0, 6),
                new Position(1, 6)
            );
            case Z -> List.of(
                new Position(0, 4),
                new Position(0, 5),
                new Position(1, 5),
                new Position(1, 6)
            );
            case T -> List.of(
                new Position(0, 4),
                new Position(0, 5),
                new Position(0, 6),
                new Position(1, 5)
            );
            case S -> List.of(
                new Position(1, 4),
                new Position(0, 5),
                new Position(1, 5),
                new Position(0, 6)
            );
        };
    }
}
