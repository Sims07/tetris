package com.ippon.kata.tetris.executing.application.domain;

import com.ippon.kata.tetris.shared.domain.Direction;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

public record Tetromino(TetrominoId id,
                        Shape shape,
                        TetraminoStatus status,
                        List<Position> positions) {

    public static final int MOVE_OFFSET = 1;
    public Tetromino(Shape shape) {
        this(new TetrominoId(UUID.randomUUID()), shape, TetraminoStatus.MOVING, null);
    }
    private static Position down(Position initPosition) {
        return new Position(initPosition.x() + MOVE_OFFSET, initPosition.y());
    }

    private static Position left(Position initPosition) {
        return new Position(initPosition.x(), initPosition.y() - MOVE_OFFSET);
    }

    private static Position right(Position initPosition) {
        return new Position(initPosition.x(), initPosition.y() + MOVE_OFFSET);
    }

    public Tetromino move(Direction direction) {
        return switch (direction) {
            case DOWN -> moveDown();
            case LEFT -> moveLeft();
            case RIGHT -> moveRight();
        };
    }

    private Tetromino moveRight() {
        return moveTo(Tetromino::right);
    }

    private Tetromino moveTo(MoveTo moveDirection) {
        return new Tetromino(
            id,
            shape,
            TetraminoStatus.MOVING,
            positions.stream()
                .map(moveDirection)
                .toList()
        );
    }

    private Tetromino moveLeft() {
        return moveTo(Tetromino::left);
    }

    public Tetromino moveDown() {
        return moveTo(Tetromino::down);
    }

    public Tetromino fixe() {
        return new Tetromino(
            new TetrominoId(UUID.randomUUID()), shape,
            TetraminoStatus.FIXED,
            positions
        );
    }

    interface MoveTo extends Function<Position, Position> {

    }
}
