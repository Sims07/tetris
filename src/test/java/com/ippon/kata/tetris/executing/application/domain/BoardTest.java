package com.ippon.kata.tetris.executing.application.domain;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

import com.ippon.kata.tetris.shared.domain.Direction;
import com.ippon.kata.tetris.shared.domain.Shape;
import com.ippon.kata.tetris.shared.domain.ShapeType;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class BoardTest {

  public static final int SPEED_MS = 100;

  private static HashMap<Position, Optional<Tetromino>> slotsWithTetromino(
      Board board1, Tetromino movedTetromino) {
    final HashMap<Position, Optional<Tetromino>> updatedSlots = new HashMap<>(board1.slots());
    movedTetromino
        .positions()
        .forEach(position -> updatedSlots.put(position, Optional.of(movedTetromino)));
    return updatedSlots;
  }

  @Test
  void givenEmptyBoard_fallTetromino_shouldBeOnInitPosition() {
    final Board board = BoardFixture.givenNewBoard();
    final Shape shape = new Shape(ShapeType.S);

    final Board board1 =
        board.move(
            new Tetromino(
                new TetrominoId(UUID.randomUUID()),
                shape,
                TetraminoStatus.IDLE,
                shape.initPositions(),
                new RotationIndex(0)),
            Direction.DOWN);

    then(board1.fallingTetromino()).isNotEmpty();
    then(board1.fallingTetromino().map(Tetromino::positions).orElseThrow())
        .isEqualTo(shape.initPositions());
    board1
        .fallingTetromino()
        .map(Tetromino::positions)
        .orElseThrow()
        .forEach(
            position -> {
              then(board1.slots().get(position).orElseThrow()).isNotNull();
            });
  }

  @Test
  void
      givenEmptyBoardTetrominoPositionNextInitial_fallTetromino_shouldSetTetrominoStatusToMoving() {
    final Shape shape = new Shape(ShapeType.S);
    final Tetromino tetromino =
        new Tetromino(
            new TetrominoId(UUID.randomUUID()),
            shape,
            TetraminoStatus.MOVING,
            shape.initPositions(),
            new RotationIndex(0));
    final Board board1 = BoardFixture.givenNewBoard();
    final Board board =
        new Board(board1.boardId(), slotsWithTetromino(board1, tetromino), Optional.of(tetromino));

    final Board boardUpdated = board.move(tetromino, Direction.DOWN);

    then(boardUpdated.fallingTetromino().map(Tetromino::status).orElseThrow())
        .isEqualTo(TetraminoStatus.MOVING);
    then(boardUpdated).isNotNull();
    then(boardUpdated.slots().values().stream().filter(Optional::isPresent).count()).isEqualTo(4);
  }

  @Test
  void givenEmptyBoardTetrominoPositionUntilOut_fallTetromino_shouldSetTetrominoStatusToFixed() {
    final Shape shape = new Shape(ShapeType.S);
    final Tetromino tetromino =
        new Tetromino(
            new TetrominoId(UUID.randomUUID()),
            shape,
            TetraminoStatus.MOVING,
            shape.initPositions().stream()
                .map(p -> new Position(p.x(), p.y() + Board.NB_LINES))
                .toList(),
            new RotationIndex(0));
    final Board board1 = BoardFixture.givenNewBoard();
    final Board board =
        new Board(board1.boardId(), slotsWithTetromino(board1, tetromino), Optional.of(tetromino));
    thenThrownBy(() -> board.move(tetromino, Direction.DOWN))
        .isInstanceOf(TetrominoFixedException.class);
  }

  @Test
  void givenTetrominoOnBorderLeft_moveLeft_shouldDoNothing() {
    final Shape shape = new Shape(ShapeType.I);
    final Tetromino tetromino =
        new Tetromino(
            new TetrominoId(UUID.randomUUID()),
            shape,
            TetraminoStatus.MOVING,
            shape.initPositions().stream().map(p -> new Position(0, p.y())).toList(),
            new RotationIndex(0));
    final Board board = BoardFixture.givenNewBoard();

    final Board moved = board.move(tetromino, Direction.LEFT);
    final Board newBoard = moved.move(moved.fallingTetromino().orElseThrow(), Direction.LEFT);

    final boolean samePosition =
        moved.fallingTetromino().get().positions().stream()
            .map(position -> newBoard.slots().get(position))
            .allMatch(Optional::isPresent);
    then(samePosition).isTrue();
  }

  @Test
  void givenTetrominoShapeIOnBorderRight_rotate_shouldDoNothing() {
    final Shape shape = new Shape(ShapeType.I);
    final Tetromino tetromino =
        new Tetromino(
            new TetrominoId(UUID.randomUUID()),
            shape,
            TetraminoStatus.MOVING,
            shape.initPositions().stream().map(p -> new Position(9, p.y())).toList(),
            new RotationIndex(0));
    final Board board = BoardFixture.givenNewBoard();
    final Board boardWithFallingTetromino =
        board.move(tetromino, Direction.DOWN).move(tetromino, Direction.DOWN);
    final List<Position> tetrominoPositionsBeforeRotate =
        boardWithFallingTetromino.fallingTetromino().map(Tetromino::positions).orElseThrow();

    final Board newBoard = boardWithFallingTetromino.move(tetromino, Direction.ROTATE);

    final boolean samePosition =
        tetrominoPositionsBeforeRotate.stream()
            .map(position -> newBoard.slots().get(position))
            .allMatch(Optional::isPresent);
    then(samePosition).isTrue();
  }
}
