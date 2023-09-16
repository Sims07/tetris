package com.ippon.kata.tetris.executing.domain;

import com.ippon.kata.tetris.shared.Direction;
import com.ippon.kata.tetris.shared.GameId;

public record TetrominoMovedEvent(
    GameId gameId,
    Tetromino tetromino,
    Direction direction,
    boolean outOfScope
) {

}
