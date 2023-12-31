package com.ippon.kata.tetris.executing.application.domain;

import com.ippon.kata.tetris.executing.application.usecase.PickTetrominoUseCase;
import com.ippon.kata.tetris.shared.domain.GameId;
import com.ippon.kata.tetris.shared.domain.Shape;
import com.ippon.kata.tetris.shared.domain.ShapeType;
import com.ippon.kata.tetris.shared.domain.EventPublisher;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PickTetromino implements PickTetrominoUseCase {

  private static final Logger LOGGER = LoggerFactory.getLogger(PickTetromino.class);
  private final EventPublisher<TetrominoPickedEvent> eventPublisher;

  public PickTetromino(EventPublisher<TetrominoPickedEvent> eventPublisher) {
    this.eventPublisher = eventPublisher;
  }

  @Override
  public TetrominoPickedEvent pickTetromino(GameId gameId, ShapeType shapeType) {
    final Shape shape = new Shape(shapeType);
    final Tetromino tetromino =
        new Tetromino(
            new TetrominoId(UUID.randomUUID()),
            shape,
            TetraminoStatus.IDLE,
            shape.initPositions(),
            new RotationIndex(0));
    LOGGER.info("EXECUTING : Command pick tetromino {}", tetromino);
    return eventPublisher.publish(new TetrominoPickedEvent(gameId, tetromino));
  }
}
