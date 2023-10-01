package com.ippon.kata.tetris.shared.infrastructure.secondary.spring.model;

import com.ippon.kata.tetris.executing.application.domain.TetraminoStatus;
import com.ippon.kata.tetris.executing.application.domain.TetrominoMovedEvent;
import com.ippon.kata.tetris.shared.domain.Direction;
import com.ippon.kata.tetris.shared.domain.ShapeType;
import java.util.List;
import java.util.UUID;
import org.springframework.context.ApplicationEvent;

public class TetrominoMovedEventDTO extends ApplicationEvent {

  private final UUID gameId;
  private final ShapeType shapeType;
  private final Direction direction;
  private final boolean tetrominoFixed;
  private final boolean outOfScope;
  private final List<PositionDTO> positions;

  public TetrominoMovedEventDTO(
      Object source,
      UUID gameId,
      ShapeType shapeType,
      Direction direction,
      boolean tetrominoFixed,
      boolean outOfScope,
      List<PositionDTO> positions) {
    super(source);
    this.gameId = gameId;
    this.shapeType = shapeType;
    this.direction = direction;
    this.tetrominoFixed = tetrominoFixed;
    this.outOfScope = outOfScope;
    this.positions = positions;
  }

  public static TetrominoMovedEventDTO from(Object source, TetrominoMovedEvent domainEvent) {
    return new TetrominoMovedEventDTO(
        source,
        domainEvent.gameId().value(),
        domainEvent.tetromino().shape().shapeType(),
        domainEvent.direction(),
        domainEvent.tetromino().status() == TetraminoStatus.FIXED,
        domainEvent.outOfScope(),
        domainEvent.tetromino().positions().stream().map(PositionDTO::from).toList());
  }

  public Direction direction() {
    return direction;
  }

  public ShapeType shapeType() {
    return shapeType;
  }

  public boolean tetrominoFixed() {
    return tetrominoFixed;
  }

  public UUID gameId() {
    return gameId;
  }

  @Override
  public String toString() {
    return "TetrominoMovedEventDTO{"
        + "gameId="
        + gameId
        + ", shapeType="
        + shapeType
        + ", direction="
        + direction
        + ", tetrominoFixed="
        + tetrominoFixed
        + '}';
  }

  public boolean outOfScope() {
    return outOfScope;
  }

  public List<PositionDTO> positions() {
    return positions;
  }
}
