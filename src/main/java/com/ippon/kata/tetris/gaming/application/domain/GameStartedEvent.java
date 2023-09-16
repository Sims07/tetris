package com.ippon.kata.tetris.gaming.application.domain;

import com.ippon.kata.tetris.shared.domain.GameId;

public record GameStartedEvent(GameId gameId, Level level) {

}
