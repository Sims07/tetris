package com.ippon.kata.tetris.shared.secondary.spring.model;

import com.ippon.kata.tetris.executing.application.domain.Board;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public record BoardDTO(
    Map<PositionDTO, Optional<TetrominoDTO>> slots, Optional<TetrominoDTO> tetrominoProjection) {

  public static BoardDTO from(Board board) {
    return new BoardDTO(slots(board), board.projectedTetromino().map(TetrominoDTO::from));
  }

  private static Map<PositionDTO, Optional<TetrominoDTO>> slots(Board board) {
    return board.slots().slots().entrySet().stream()
        .collect(
            Collectors.toMap(
                e -> PositionDTO.from(e.getKey()), e -> e.getValue().map(TetrominoDTO::from)));
  }
}
