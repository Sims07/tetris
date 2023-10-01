package com.ippon.kata.tetris.shared.infrastructure.secondary.spring.model;

import com.ippon.kata.tetris.executing.application.domain.TetraminoStatus;
import com.ippon.kata.tetris.executing.application.domain.Tetromino;
import com.ippon.kata.tetris.shared.domain.ShapeType;
import java.util.List;

public record TetrominoDTO(ShapeType shape, TetraminoStatus status, List<PositionDTO> positions) {

  public static TetrominoDTO from(Tetromino tetromino) {
    return new TetrominoDTO(
        tetromino.shape().shapeType(),
        tetromino.status(),
        tetromino.positions().stream().map(PositionDTO::from).toList());
  }
}
