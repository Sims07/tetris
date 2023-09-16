package com.ippon.kata.tetris.executing.application.domain;

import com.ippon.kata.tetris.shared.domain.GameId;

public record BoardInitializedEvent(GameId gameId) {

}
