package com.ippon.kata.tetris.executing.application.usecase;

import com.ippon.kata.tetris.executing.application.domain.TetrominoPickedEvent;
import com.ippon.kata.tetris.shared.domain.GameId;
import com.ippon.kata.tetris.shared.domain.ShapeType;

public interface PickTetrominoUseCase {

    TetrominoPickedEvent pickTetromino(GameId gameId, ShapeType shapeType);
}
