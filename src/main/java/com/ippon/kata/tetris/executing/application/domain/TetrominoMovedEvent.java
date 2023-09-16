package com.ippon.kata.tetris.executing.application.domain;

import com.ippon.kata.tetris.shared.domain.Direction;
import com.ippon.kata.tetris.shared.domain.GameId;

public record TetrominoMovedEvent(
    GameId gameId, Tetromino tetromino, Direction direction, boolean outOfScope) {}
