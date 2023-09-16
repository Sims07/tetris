package com.ippon.kata.tetris.preparing.domain;

import com.ippon.kata.tetris.shared.GameId;

public interface GenerateNextTetrominoUseCase {

    TetrominoGeneratedEvent generateNextTetromino(GameId gameId);
}
