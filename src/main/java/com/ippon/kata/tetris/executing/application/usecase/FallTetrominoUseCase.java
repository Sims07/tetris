package com.ippon.kata.tetris.executing.application.usecase;

import com.ippon.kata.tetris.executing.application.domain.BoardId;
import com.ippon.kata.tetris.executing.application.domain.Tetromino;
import com.ippon.kata.tetris.executing.application.domain.TetrominoMovedEvent;

public interface FallTetrominoUseCase {

    TetrominoMovedEvent fall(BoardId boardId, Tetromino tetromino);

}
