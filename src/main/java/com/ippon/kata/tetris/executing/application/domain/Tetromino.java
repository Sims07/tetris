package com.ippon.kata.tetris.executing.application.domain;

import com.ippon.kata.tetris.shared.domain.Direction;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.ToIntFunction;

public record Tetromino(
    TetrominoId id, Shape shape, TetraminoStatus status, List<Position> positions) {

  public static final int MOVE_OFFSET = 1;
  public static final int LOW_LIMIT = 0;

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

  public Tetromino move(Direction direction, Map<Position, Optional<Tetromino>> slots) {
    return switch (direction) {
      case DOWN -> moveDown();
      case LEFT -> moveLeft(slots);
      case ROTATE -> rotate(slots);
      case RIGHT -> moveRight(slots);
    };
  }

  private Tetromino rotate(Map<Position, Optional<Tetromino>> slots) {
    return null;
  }

  private Tetromino moveRight(Map<Position, Optional<Tetromino>> slots) {
    if (cannotMoveRight(slots)) {
      return this;
    } else {
      return moveTo(Tetromino::right);
    }
  }

  private Tetromino moveLeft(Map<Position, Optional<Tetromino>> slots) {
    if (cannotMoveLeft(slots)) {
      return this;
    } else {
      return moveTo(Tetromino::left);
    }
  }

  private boolean borderLeftTouched() {
    return positions.stream().anyMatch(position -> position.y() - MOVE_OFFSET < LOW_LIMIT);
  }

  private boolean cannotMoveLeft(Map<Position, Optional<Tetromino>> slots) {
    return borderLeftTouched() || tetrominoLeftTouched(slots);
  }

  private boolean tetrominoLeftTouched(Map<Position, Optional<Tetromino>> slots) {
    return tetrominoTouched(slots, position -> position.y() - MOVE_OFFSET);
  }


  private boolean cannotMoveRight(Map<Position, Optional<Tetromino>> slots) {
    return borderRightTouched() || tetrominoRightTouched(slots);
  }

  private boolean tetrominoRightTouched(Map<Position, Optional<Tetromino>> slots) {
    return tetrominoTouched(slots, position -> position.y() + MOVE_OFFSET);
  }

  private boolean tetrominoTouched(Map<Position, Optional<Tetromino>> slots, ToIntFunction<Position> toIntFunction) {
    return positions.stream()
        .anyMatch(
            position -> {
              final Position nextPosition = new Position(position.x(), toIntFunction.applyAsInt(position));
              final Optional<Tetromino> tetrominoNextPosition = slots.get(nextPosition);
              return tetrominoNextPosition != null
                  && tetrominoNextPosition.isPresent()
                  && differentFrom(tetrominoNextPosition);
            });
  }

  private boolean differentFrom(Optional<Tetromino> tetrominoNextPosition) {
    return tetrominoNextPosition.filter(tetromino -> !tetromino.id().equals(id())).isPresent();
  }

  private boolean borderRightTouched() {
    return positions.stream().anyMatch(position -> position.y() + MOVE_OFFSET >= Board.NB_COLUMNS);
  }

  private Tetromino moveTo(MoveTo moveDirection) {
    return new Tetromino(
        id, shape, TetraminoStatus.MOVING, positions.stream().map(moveDirection).toList());
  }

  public Tetromino moveDown() {
    return moveTo(Tetromino::down);
  }

  public Tetromino fixe() {
    return new Tetromino(
        new TetrominoId(UUID.randomUUID()), shape, TetraminoStatus.FIXED, positions);
  }

  interface MoveTo extends Function<Position, Position> {}
}
