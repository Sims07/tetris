package com.ippon.kata.tetris.gaming;

import com.ippon.kata.tetris.gaming.domain.Game;
import com.ippon.kata.tetris.shared.domain.GameId;

public interface Games {

    Game save(Game game);

    Game get(GameId gameId);
}
