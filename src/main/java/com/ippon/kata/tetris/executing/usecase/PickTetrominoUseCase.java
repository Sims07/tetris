package com.ippon.kata.tetris.executing.usecase;

import com.ippon.kata.tetris.executing.domain.TetrominoPickedEvent;
import com.ippon.kata.tetris.shared.GameId;
import com.ippon.kata.tetris.shared.ShapeType;

public interface PickTetrominoUseCase {

    TetrominoPickedEvent pickTetromino(GameId gameId, ShapeType shapeType);
}
