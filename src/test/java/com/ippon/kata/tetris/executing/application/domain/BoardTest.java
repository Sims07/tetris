package com.ippon.kata.tetris.executing.application.domain;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.Mockito.mock;

import com.ippon.kata.tetris.shared.domain.Direction;
import com.ippon.kata.tetris.shared.domain.Shape;
import com.ippon.kata.tetris.shared.domain.ShapeType;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class BoardTest {

  @Test
  void givenNoBoardId_constructor_shouldThrowException() {
    thenThrownBy(() -> new Board(null, new PositionSlot(new HashMap<>()), Optional.empty()))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void givenNullFallingTetromino_constructor_shouldThrowException() {
    thenThrownBy(() -> new Board(mock(BoardId.class), new PositionSlot(new HashMap<>()), null))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void givenEmptyBoard_moveDown_shouldBeOnInitPosition() {
    final Board board = BoardFixture.givenNewBoard();
    final Shape shape = new Shape(ShapeType.S);

    final Board boardWithFallingTetromino =
        board.move(
            new Tetromino(
                new TetrominoId(UUID.randomUUID()),
                shape,
                TetraminoStatus.IDLE,
                shape.initPositions(),
                new RotationIndex(0)),
            Direction.DOWN);

    then(boardWithFallingTetromino.fallingTetromino()).isNotEmpty();
    then(boardWithFallingTetromino.fallingTetromino().map(Tetromino::positions).orElseThrow())
        .isEqualTo(shape.initPositions());
    boardWithFallingTetromino
        .fallingTetromino()
        .map(Tetromino::positions)
        .orElseThrow()
        .forEach(
            position -> {
              then(boardWithFallingTetromino.slots().get(position).orElseThrow()).isNotNull();
            });
  }

  @Test
  void givenEmptyBoardTetrominoPositionNextInitial_moveDown_shouldSetTetrominoStatusToMoving() {
    final Shape shape = new Shape(ShapeType.S);
    final Tetromino tetromino =
        new Tetromino(
            new TetrominoId(UUID.randomUUID()),
            shape,
            TetraminoStatus.MOVING,
            shape.initPositions(),
            new RotationIndex(0));
    final Board boardWithTetromino = givenBoardWithTetromino(tetromino);

    final Board boardUpdated = boardWithTetromino.move(tetromino, Direction.DOWN);

    then(boardUpdated.fallingTetromino().map(Tetromino::status).orElseThrow())
        .isEqualTo(TetraminoStatus.MOVING);
    then(boardUpdated).isNotNull();
    then(boardUpdated.slots().slots().values().stream().filter(Optional::isPresent).count())
        .isEqualTo(4);
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
    final Board boardWithTetromino = givenBoardWithTetromino(tetromino);

    thenThrownBy(() -> boardWithTetromino.move(tetromino, Direction.DOWN))
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

  @Test
  void
      givenEmptyBoardWithFallingITetromino_projectedPosition_shouldListTetrominoPositionTranslatedToTheBottom() {
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

    List<Position> projectedTetrominoPositions =
        boardWithFallingTetromino.projectedTetromino().map(Tetromino::positions).orElseThrow();

    then(projectedTetrominoPositions).isNotNull();
    then(projectedTetrominoPositions).isNotEmpty();
    projectedTetrominoPositions.forEach(
        projectedTetromino -> {
          then(projectedTetromino.y()).isEqualTo(Board.NB_LINES - 1);
        });
  }

  @Test
  void
      givenNonEmptyBoardWithFallingITetromino_projectedPosition_shouldListTetrominoPositionTranslatedToThePreviousTetromino() {
    final Shape shape = new Shape(ShapeType.I);
    final Tetromino tetromino =
        new Tetromino(
            new TetrominoId(UUID.randomUUID()),
            shape,
            TetraminoStatus.MOVING,
            shape.initPositions().stream().map(p -> new Position(9, p.y())).toList(),
            new RotationIndex(0));
    final Board board = BoardFixture.givenNewBoard();
    final Board boardWithFallingTetromino = fallingTetromino(board, tetromino);

    List<Position> projectedTetrominoPositions =
        boardWithFallingTetromino.projectedTetromino().map(Tetromino::positions).orElseThrow();

    then(projectedTetrominoPositions).isNotNull();
    then(projectedTetrominoPositions).isNotEmpty();
    projectedTetrominoPositions.forEach(
        projectedTetromino -> {
          then(projectedTetromino.y()).isEqualTo(Board.NB_LINES - 1);
        });
  }

  @Test
  void givenEmptyBoard_projectedPosition_shouldReturnEmpty() {
    final Board board = BoardFixture.givenNewBoard();

    final Optional<Tetromino> tetromino = board.projectedTetromino();

    then(tetromino).isEmpty();
  }

  private static Board fallingTetromino(Board board, Tetromino tetromino) {
    Board boardWithFallingTetromino =
        board.move(tetromino, Direction.DOWN).move(tetromino, Direction.DOWN);
    boolean fixed = false;
    while (!fixed) {
      try {
        boardWithFallingTetromino = boardWithFallingTetromino.move(tetromino, Direction.DOWN);
      } catch (TetrominoFixedException tfe) {
        fixed = true;
      }
    }
    return boardWithFallingTetromino;
  }

  private static Board givenBoardWithTetromino(Tetromino tetromino) {
    final Board initialBoard = BoardFixture.givenNewBoard();
    return new Board(
        initialBoard.id(), slotsWithTetromino(initialBoard, tetromino), Optional.of(tetromino));
  }

  private static PositionSlot slotsWithTetromino(Board board1, Tetromino movedTetromino) {
    final HashMap<Position, Optional<Tetromino>> updatedSlots =
        new HashMap<>(board1.slots().slots());
    movedTetromino
        .positions()
        .forEach(position -> updatedSlots.put(position, Optional.of(movedTetromino)));
    return new PositionSlot(updatedSlots);
  }
}
