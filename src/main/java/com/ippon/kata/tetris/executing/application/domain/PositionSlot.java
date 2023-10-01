package com.ippon.kata.tetris.executing.application.domain;

import static com.ippon.kata.tetris.executing.application.domain.Board.NB_COLUMNS;
import static com.ippon.kata.tetris.executing.application.domain.Board.NB_LINES;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.IntStream;

public record PositionSlot(Map<Position, Optional<Tetromino>> slots) {

  public static PositionSlot emptySlots() {
    Map<Position, Optional<Tetromino>> emptyBoard = empty();
    IntStream.range(0, NB_LINES)
        .forEach(
            i ->
                IntStream.range(0, NB_COLUMNS)
                    .forEach(j -> emptyBoard.put(new Position(j, i), Optional.empty())));
    return new PositionSlot(emptyBoard);
  }

  private static HashMap<Position, Optional<Tetromino>> empty() {
    return HashMap.newHashMap(NB_COLUMNS * NB_LINES);
  }

  public PositionSlot eraseLines(List<LineIndex> lineToErase) {
    final List<Integer> lineIndexValues = lineToErase.stream().map(LineIndex::value).toList();
    Map<Position, Optional<Tetromino>> updatedBoard = empty();
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
    Map<Position, Optional<Tetromino>> updatedBoard2 = empty();
    final int translateOffset = lineToErase.size();
    final int from = lineToErase.stream().mapToInt(LineIndex::value).min().getAsInt();
    revRange(0, NB_LINES)
        .forEach(
            i ->
                revRange(0, NB_COLUMNS)
                    .forEach(
                        j -> {
                          final Position position = new Position(j, i);
                          if (i < from) {
                            updatedBoard2.put(
                                new Position(j, i + translateOffset), slots.get(position));
                            updatedBoard2.put(new Position(j, i), Optional.empty());
                          } else {
                            updatedBoard2.put(position, slots.get(position));
                          }
                        }));
    return new PositionSlot(updatedBoard2);
  }

  static IntStream revRange(int from, int to) {
    return IntStream.range(from, to).map(i -> to - i + from - 1);
  }

  public Optional<Tetromino> get(Position nextPosition) {
    return slots.get(nextPosition);
  }

  public List<LineIndex> lineToErase() {
    return slots.entrySet().stream()
        .collect(groupingBy(PositionSlot::groupingByLine, reduceByTetrominoPresent()))
        .entrySet()
        .stream()
        .filter(entry -> entry.getValue() == NB_COLUMNS)
        .map(Entry::getKey)
        .map(LineIndex::new)
        .sorted(Comparator.comparingInt(LineIndex::value).reversed())
        .toList();
  }

  private static Collector<Entry<Position, Optional<Tetromino>>, ?, Integer>
      reduceByTetrominoPresent() {
    return summingInt(pos -> pos.getValue().isPresent() ? 1 : 0);
  }

  private static int groupingByLine(Entry<Position, Optional<Tetromino>> e) {
    return e.getKey().y();
  }

  public PositionSlot moveTetrominoFromTo(Tetromino tetromino, Tetromino movedTetromino) {
    final Map<Position, Optional<Tetromino>> updatedSlots = clearTetrominoFromSlots(tetromino);
    movedTetromino
        .positions()
        .forEach(position -> updatedSlots.put(position, Optional.of(movedTetromino)));
    return new PositionSlot(updatedSlots);
  }

  private Map<Position, Optional<Tetromino>> clearTetrominoFromSlots(Tetromino tetromino) {
    final Map<Position, Optional<Tetromino>> updatedSlots = new HashMap<>(slots);
    tetromino.positions().forEach(position -> updatedSlots.put(position, Optional.empty()));
    return updatedSlots;
  }
}
