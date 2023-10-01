package com.ippon.kata.tetris.executing.application.domain;

import com.ippon.kata.tetris.shared.domain.Direction;
import com.ippon.kata.tetris.shared.domain.asserts.Asserts;
import java.util.List;
import java.util.Optional;

public record Board(BoardId id, PositionSlot slots, Optional<Tetromino> fallingTetromino) {

  public static final int NB_COLUMNS = 10;
  public static final int NB_LINES = 22;

  public Board(BoardId boardId) {
    this(boardId, PositionSlot.emptySlots(), Optional.empty());
  }

  public Board {
    Asserts.withContext(getClass())
        .notNull(id, "Id should not be null")
        .notNull(fallingTetromino, "Falling tetromino should not be null")
        .notEmpty(slots, "Slots should not be empty");
  }

  public Board move(Tetromino tetromino, Direction direction) {
    final Tetromino movedTetromino =
        fallingTetromino()
            .map(fallingTetromino -> fallingTetromino.move(direction, slots))
            .orElse(tetromino);
    if (tetrominoPositionsAvailable(movedTetromino)) {
      return new Board(
          id, slots.moveTetrominoFromTo(tetromino, movedTetromino), Optional.of(movedTetromino));
    } else {
      throw new TetrominoFixedException(
          tetromino,
          new Board(id, slots(), Optional.empty()),
          tetromino.positions().stream().map(Position::y).anyMatch(x -> x == 0));
    }
  }

  public Board eraseLines(List<LineIndex> lineToErase) {
    return new Board(id, slots.eraseLines(lineToErase), fallingTetromino);
  }

  public List<LineIndex> lineToErase() {
    return slots.lineToErase();
  }

  public Optional<Tetromino> projectedTetromino() {
    if (fallingTetromino().isEmpty()) {
      return Optional.empty();
    }
    return emulateFalling();
  }

  private boolean tetrominoPositionsAvailable(Tetromino movedTetromino) {
    return movedTetromino.positions().stream()
        .map(position -> positionAvailable(movedTetromino, position))
        .reduce(true, Boolean::logicalAnd);
  }

  private Boolean positionAvailable(Tetromino movedTetromino, Position position) {
    if (inBoard(position)) {
      return isEmptySlot(position) || tetrominoAlreadyAtThisSlot(movedTetromino, position);
    } else {
      return false;
    }
  }

  private static boolean inBoard(Position position) {
    return position.y() < Board.NB_LINES;
  }

  private boolean isEmptySlot(Position position) {
    final Optional<Tetromino> tetromino = slots.get(position);
    return tetromino != null && slots().get(position).isEmpty();
  }

  private boolean tetrominoAlreadyAtThisSlot(Tetromino movedTetromino, Position position) {
    return inRange(position)
        && slots
            .get(position)
            .map(Tetromino::id)
            .filter(id -> id.equals(movedTetromino.id()))
            .isPresent();
  }

  private boolean inRange(Position position) {
    return position.y() < NB_LINES && position.x() < NB_COLUMNS;
  }

  private Optional<Tetromino> emulateFalling() {
    boolean fixed = false;
    Board boardWithFallingTetromino = this;
    Tetromino fixedTetromino = null;
    while (!fixed) {
      try {
        boardWithFallingTetromino =
            boardWithFallingTetromino.move(
                boardWithFallingTetromino.fallingTetromino().orElseThrow(), Direction.DOWN);
      } catch (TetrominoFixedException tfe) {
        fixed = true;
        fixedTetromino = tfe.tetromino();
      }
    }
    return Optional.of(fixedTetromino);
  }
}
