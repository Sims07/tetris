package com.ippon.kata.tetris.scoring.application.domain;

import com.ippon.kata.tetris.shared.domain.GameId;

public record Score(
    GameId gameId,
    int value) {

}
