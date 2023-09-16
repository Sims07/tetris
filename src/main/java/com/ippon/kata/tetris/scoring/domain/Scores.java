package com.ippon.kata.tetris.scoring.domain;

import com.ippon.kata.tetris.shared.GameId;

public interface Scores {

    Score save(Score score);

    Score get(GameId gameId);
}
