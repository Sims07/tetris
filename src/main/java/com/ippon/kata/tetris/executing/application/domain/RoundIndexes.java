package com.ippon.kata.tetris.executing.application.domain;

import com.ippon.kata.tetris.shared.domain.GameId;

public interface RoundIndexes {
    RoundIndex get(GameId gameId);

    RoundIndex add(RoundIndex roundIndex);
}
