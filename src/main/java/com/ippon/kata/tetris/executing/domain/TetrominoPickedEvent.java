package com.ippon.kata.tetris.executing.domain;

import com.ippon.kata.tetris.shared.domain.GameId;

public record TetrominoPickedEvent(GameId gameId,
                                   Tetromino tetromino
) {

}
