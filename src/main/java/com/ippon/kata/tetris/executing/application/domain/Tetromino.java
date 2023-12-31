package com.ippon.kata.tetris.executing.application.domain;

import com.ippon.kata.tetris.shared.domain.Direction;
import com.ippon.kata.tetris.shared.domain.Shape;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.ToIntFunction;

public record Tetromino(
    TetrominoId id,
    Shape shape,
    TetraminoStatus status,
    List<Position> positions,
    RotationIndex rotationIndex) {

  public static final int MOVE_OFFSET = 1;
  public static final int LOW_LIMIT = 0;
  public static final int NB_COLUMN = 10;

  public Tetromino(Shape shape) {
    this(
        new TetrominoId(UUID.randomUUID()),
        shape,
        TetraminoStatus.MOVING,
        null,
        new RotationIndex(0));
  }

  private static Position down(Position initPosition) {
    return new Position(initPosition.x(), initPosition.y() + MOVE_OFFSET);
  }

  private static Position left(Position initPosition) {
    return new Position(initPosition.x() - MOVE_OFFSET, initPosition.y());
  }

  private static Position right(Position initPosition) {
    return new Position(initPosition.x() + MOVE_OFFSET, initPosition.y());
  }

  public Tetromino move(Direction direction, PositionSlot slots) {
    return switch (direction) {
      case DOWN -> moveDown();
      case LEFT -> moveLeft(slots);
      case ROTATE -> rotate(slots);
      case RIGHT -> moveRight(slots);
    };
  }

  private Tetromino rotate(PositionSlot slots) {
    final List<Position> roratedPositions = rotatedPositions();
    if (cannotRotate(roratedPositions, slots)) {
      return this;
    } else {
      return new Tetromino(id, shape, status, roratedPositions, rotationIndex.next());
    }
  }

  private boolean cannotRotate(
      List<Position> roratedPositions, PositionSlot slots) {
    return outOfRange(roratedPositions) || tetrominoTouched(slots, Position::x, roratedPositions);
  }

  private static boolean outOfRange(List<Position> positions) {
    return positions.stream()
        .anyMatch(position -> position.x() < 0 || position.y() < 0 || position.x() >= NB_COLUMN);
  }

  private List<Position> rotatedPositions() {
    List<Position> rotatedPositions = new ArrayList<>();
    final List<Position> translatedPosition = shape().translatedRotationPositions(rotationIndex);
    for (int i = 0; i < positions().size(); i++) {
      Position newPosition = positions().get(i).add(translatedPosition.get(i));
      rotatedPositions.add(newPosition);
    }
    return rotatedPositions;
  }

  private Tetromino moveRight(PositionSlot slots) {
    if (cannotMoveRight(slots)) {
      return this;
    } else {
      return moveTo(Tetromino::right);
    }
  }

  private Tetromino moveLeft(PositionSlot slots) {
    if (cannotMoveLeft(slots)) {
      return this;
    } else {
      return moveTo(Tetromino::left);
    }
  }

  private boolean borderLeftTouched() {
    return positions.stream().anyMatch(position -> position.x() - MOVE_OFFSET < LOW_LIMIT);
  }

  private boolean cannotMoveLeft(PositionSlot slots) {
    return borderLeftTouched() || tetrominoLeftTouched(slots);
  }

  private boolean tetrominoLeftTouched(PositionSlot slots) {
    return tetrominoTouched(slots, position -> position.x() - MOVE_OFFSET, positions);
  }

  private boolean cannotMoveRight(PositionSlot slots) {
    return borderRightTouched() || tetrominoRightTouched(slots);
  }

  private boolean tetrominoRightTouched(PositionSlot slots) {
    return tetrominoTouched(slots, position -> position.x() + MOVE_OFFSET, positions);
  }

  private boolean tetrominoTouched(
      PositionSlot slots,
      ToIntFunction<Position> toIntFunction,
      List<Position> positionToCheck) {
    return positionToCheck.stream()
        .anyMatch(
            position -> {
              final Position nextPosition =
                  new Position(toIntFunction.applyAsInt(position), position.y());
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
    return positions.stream().anyMatch(position -> position.x() + MOVE_OFFSET >= Board.NB_COLUMNS);
  }

  private Tetromino moveTo(MoveTo moveDirection) {
    return new Tetromino(
        id,
        shape,
        TetraminoStatus.MOVING,
        positions.stream().map(moveDirection).toList(),
        rotationIndex);
  }

  public Tetromino moveDown() {
    return moveTo(Tetromino::down);
  }

  public Tetromino fixe() {
    return new Tetromino(
        new TetrominoId(UUID.randomUUID()), shape, TetraminoStatus.FIXED, positions, rotationIndex);
  }

  interface MoveTo extends Function<Position, Position> {}
}
