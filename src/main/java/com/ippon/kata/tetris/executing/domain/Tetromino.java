package com.ippon.kata.tetris.executing.domain;

import java.util.List;
import java.util.UUID;

public record Tetromino(TetrominoId id,
                        Shape shape,
                        TetraminoStatus status,
                        List<Position> positions) {

    public Tetromino(Shape shape) {
        this(new TetrominoId(UUID.randomUUID()), shape, TetraminoStatus.MOVING, null);
    }

    private static Position down(Position initPosition) {
        return new Position(initPosition.x() + 1, initPosition.y());
    }

    public Tetromino moveDown() {
        return new Tetromino(
            id,
            shape,
            TetraminoStatus.MOVING,
            positions.stream()
                .map(Tetromino::down)
                .toList()
        );
    }

    public Tetromino fixe() {
        return new Tetromino(
            new TetrominoId(UUID.randomUUID()), shape,
            TetraminoStatus.FIXED,
            positions
        );
    }
}
