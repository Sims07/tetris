package com.ippon.kata.tetris.gaming.application.domain;

import com.ippon.kata.tetris.shared.domain.GameId;
import com.ippon.kata.tetris.shared.domain.Level;

public record GameStartedEvent(GameId gameId, Level level) {

}
