package com.ippon.kata.tetris.executing.domain;

import com.ippon.kata.tetris.shared.asserts.Asserts;
import com.ippon.kata.tetris.shared.domain.Direction;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public record Board(
    BoardId boardId,
    Map<Position, Optional<Tetromino>> slots,
    Optional<Tetromino> fallingTetromino) {

  public static final int NB_COLUMNS = 10;
  public static final int NB_LINES = 22;
  private static final Logger LOGGER = LoggerFactory.getLogger(Board.class);

  public Board(BoardId boardId) {
    this(boardId, emptySlots(), Optional.empty());
  }

  public Board {
    Asserts.withContext(getClass()).notEmpty(slots, "Slots should not be empty");
  }

  public static Map<Position, Optional<Tetromino>> emptySlots() {
    Map<Position, Optional<Tetromino>> emptyBoard = new HashMap<>(NB_COLUMNS * NB_LINES);
    IntStream.range(0, NB_LINES)
        .forEach(
            i ->
                IntStream.range(0, NB_COLUMNS)
                    .forEach(j -> emptyBoard.put(new Position(i, j), Optional.empty())));
    return emptyBoard;
  }

  public Board move(Tetromino tetromino, Direction direction) {
    final Tetromino movedTetromino =
        fallingTetromino()
            .map(fallingTetromino -> fallingTetromino.move(direction))
            .orElse(tetromino);
    if (nextTetrominoPositionAvailable(movedTetromino)) {
      return new Board(
          boardId, moveTetrominoFromTo(tetromino, movedTetromino), Optional.of(movedTetromino));
    } else {
      throw new TetrominoFixedException(
          tetromino,
          new Board(boardId, slots(), Optional.empty()),
          tetromino.positions().stream().map(Position::x).anyMatch(x -> x == 0));
    }
  }

  private boolean nextTetrominoPositionAvailable(Tetromino movedTetromino) {
    return movedTetromino.positions().stream()
        .map(
            position -> {
              if (position.x() < Board.NB_LINES) {
                final boolean emptySlot = emptySlot(position);
                final boolean tetrominoAlreadyAtThisSlot =
                    tetrominoAlreadyAtThisSlot(movedTetromino, position);
                LOGGER.debug("{},{},{}", position, emptySlot, tetrominoAlreadyAtThisSlot);
                return emptySlot || tetrominoAlreadyAtThisSlot;
              } else {
                return false;
              }
            })
        .reduce(true, Boolean::logicalAnd);
  }

  private boolean emptySlot(Position position) {
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
    return position.x() < NB_LINES && position.y() < NB_COLUMNS;
  }

  private Map<Position, Optional<Tetromino>> moveTetrominoFromTo(
      Tetromino tetromino, Tetromino movedTetromino) {
    final long nbSlotsBefore = slots().values().stream().filter(Optional::isPresent).count();
    LOGGER.info("Move tetromino from {}, to {}", tetromino, movedTetromino);
    final HashMap<Position, Optional<Tetromino>> updatedSlots = new HashMap<>(slots);
    tetromino.positions().forEach(position -> updatedSlots.put(position, Optional.empty()));
    movedTetromino
        .positions()
        .forEach(position -> updatedSlots.put(position, Optional.of(movedTetromino)));
    final long nbSlotsAfter = updatedSlots.values().stream().filter(Optional::isPresent).count();
    LOGGER.info("Before move {} after move {}", nbSlotsBefore, nbSlotsAfter);
    return updatedSlots;
  }

  public List<Tetromino> busy() {
    return slots().values().stream().filter(Optional::isPresent).map(Optional::get).toList();
  }
}
