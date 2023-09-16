package com.ippon.kata.tetris.executing.usecase;

import com.ippon.kata.tetris.executing.domain.TetrominoPickedEvent;
import com.ippon.kata.tetris.shared.domain.GameId;
import com.ippon.kata.tetris.shared.domain.ShapeType;

public interface PickTetrominoUseCase {

    TetrominoPickedEvent pickTetromino(GameId gameId, ShapeType shapeType);
}
