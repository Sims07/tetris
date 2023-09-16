package com.ippon.kata.tetris.scoring.application.domain;

import com.ippon.kata.tetris.shared.domain.GameId;

public interface Scores {

    Score save(Score score);

    Score get(GameId gameId);
}
