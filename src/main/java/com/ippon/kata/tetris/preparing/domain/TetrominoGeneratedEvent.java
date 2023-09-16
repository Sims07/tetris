package com.ippon.kata.tetris.preparing.domain;

import com.ippon.kata.tetris.shared.GameId;

public record TetrominoGeneratedEvent(Tetromino tetromino,
                                      GameId gameId) {

    public Tetromino tetromino() {
        return tetromino;
    }
}
