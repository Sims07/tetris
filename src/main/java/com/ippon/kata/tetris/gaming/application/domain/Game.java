package com.ippon.kata.tetris.gaming.application.domain;

import com.ippon.kata.tetris.shared.domain.GameId;

public record Game(GameId id,

                   boolean boardInitialized,
                   boolean tetrominoGenerated,
                   Round currentRound,
                   boolean scoreInitialized,
                   Tetromino waitingTetromino) {

    public GameStatus status() {
        return boardInitialized && tetrominoGenerated && scoreInitialized ? GameStatus.PLAYING : GameStatus.INITIALIZING;
    }

    public Game newRound() {
        return new Game(
            id,
            boardInitialized,
            tetrominoGenerated,
            new Round(RoundStatus.IDLE, currentRound.index() + 1),
            scoreInitialized,
            waitingTetromino
        );
    }

    public Game finishRound() {
        return new Game(
            id,
            boardInitialized,
            tetrominoGenerated,
            new Round(RoundStatus.FINISHED, currentRound.index()),
            scoreInitialized,
            waitingTetromino
        );
    }
}
