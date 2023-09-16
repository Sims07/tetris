package com.ippon.kata.tetris.executing.application.domain;

import static java.util.stream.Collectors.*;

import com.ippon.kata.tetris.shared.asserts.Asserts;
import com.ippon.kata.tetris.shared.domain.Direction;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public record Board(
    BoardId boardId,
    // TODO replace by value object slots
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
                    .forEach(j -> emptyBoard.put(new Position(j, i), Optional.empty())));
    return emptyBoard;
  }

  public Board move(Tetromino tetromino, Direction direction) {
    final Tetromino movedTetromino =
        fallingTetromino()
            .map(fallingTetromino -> fallingTetromino.move(direction, slots))
            .orElse(tetromino);
    if (nextTetrominoPositionAvailable(movedTetromino)) {
      return new Board(
          boardId, moveTetrominoFromTo(tetromino, movedTetromino), Optional.of(movedTetromino));
    } else {
      throw new TetrominoFixedException(
          tetromino,
          new Board(boardId, slots(), Optional.empty()),
          tetromino.positions().stream().map(Position::y).anyMatch(x -> x == 0));
    }
  }

  private boolean nextTetrominoPositionAvailable(Tetromino movedTetromino) {
    return movedTetromino.positions().stream()
        .map(
            position -> {
              if (position.y() < Board.NB_LINES) {
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
    return position.y() < NB_LINES && position.x() < NB_COLUMNS;
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

  public Board eraseLines(List<LineIndex> lineToErase) {
    final List<Integer> lineIndexValues = lineToErase.stream().map(LineIndex::value).toList();
    Map<Position, Optional<Tetromino>> updatedBoard = new HashMap<>(NB_COLUMNS * NB_LINES);
    IntStream.range(0, NB_LINES)
        .forEach(
            i ->
                IntStream.range(0, NB_COLUMNS)
                    .forEach(
                        j -> {
                          final Position position = new Position(j, i);
                          if (lineIndexValues.contains(i)) {
                            updatedBoard.put(position, Optional.empty());
                          } else {
                            updatedBoard.put(position, slots.get(position));
                          }
                        }));
    Map<Position, Optional<Tetromino>> updatedBoard2 = new HashMap<>(NB_COLUMNS * NB_LINES);
    final int translateOffset = lineToErase.size();
    final int from = lineToErase.stream().mapToInt(LineIndex::value).min().getAsInt();
    revRange(0, NB_LINES)
        .forEach(
            i ->
                revRange(0, NB_COLUMNS)
                    .forEach(
                        j -> {
                          final Position position = new Position(j, i);
                          if(i<from){
                            updatedBoard2.put(new Position(j, i+translateOffset), slots.get(position));
                            updatedBoard2.put(new Position(j, i), Optional.empty());
                          }else {
                            updatedBoard2.put(position, slots.get(position));
                          }
                        }));
    return new Board(boardId, updatedBoard2, fallingTetromino);
  }
  static IntStream revRange(int from, int to) {
    return IntStream.range(from, to)
        .map(i -> to - i + from - 1);
  }
  public List<LineIndex> lineToErase() {
    return slots.entrySet().stream()
        .collect(
            groupingBy(
                e -> e.getKey().y(), summingInt((pos1) -> pos1.getValue().isPresent() ? 1 : 0)))
        .entrySet()
        .stream()
        .filter(entry -> entry.getValue() == NB_COLUMNS)
        .map(Entry::getKey)
        .map(LineIndex::new)
        .sorted(Comparator.comparingInt(LineIndex::value).reversed())
        .toList();
  }
}
