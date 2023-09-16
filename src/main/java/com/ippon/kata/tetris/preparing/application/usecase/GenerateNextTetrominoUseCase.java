package com.ippon.kata.tetris.preparing.application.usecase;

import com.ippon.kata.tetris.preparing.application.domain.TetrominoGeneratedEvent;
import com.ippon.kata.tetris.shared.domain.GameId;

public interface GenerateNextTetrominoUseCase {

    TetrominoGeneratedEvent generateNextTetromino(GameId gameId);
}
