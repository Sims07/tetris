package com.ippon.kata.tetris.executing.usecase;

import com.ippon.kata.tetris.executing.domain.BoardId;
import com.ippon.kata.tetris.executing.domain.Tetromino;
import com.ippon.kata.tetris.executing.domain.TetrominoMovedEvent;

public interface FallTetrominoUseCase {

    TetrominoMovedEvent fall(BoardId boardId, Tetromino tetromino);

}
