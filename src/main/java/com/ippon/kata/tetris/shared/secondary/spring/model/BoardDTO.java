package com.ippon.kata.tetris.shared.secondary.spring.model;

import com.ippon.kata.tetris.executing.application.domain.Board;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public record BoardDTO(Map<PositionDTO, Optional<TetrominoDTO>> slots) {

    public static BoardDTO from(Board board) {
    return new BoardDTO(board.slots().entrySet()
        .stream()
        .collect(Collectors.toMap(e-> PositionDTO.from(e.getKey()), e -> e.getValue().map(TetrominoDTO::from))));
    }
}
