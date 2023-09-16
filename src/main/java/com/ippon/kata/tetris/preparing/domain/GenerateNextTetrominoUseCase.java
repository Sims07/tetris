package com.ippon.kata.tetris.preparing.domain;

import com.ippon.kata.tetris.shared.domain.GameId;

public interface GenerateNextTetrominoUseCase {

    TetrominoGeneratedEvent generateNextTetromino(GameId gameId);
}
