package com.ippon.kata.tetris.gaming.domain;

import com.ippon.kata.tetris.gaming.application.domain.Game;
import com.ippon.kata.tetris.gaming.application.domain.Round;
import com.ippon.kata.tetris.gaming.application.domain.Settings;
import com.ippon.kata.tetris.shared.domain.GameId;

public class GameFixture {

    public static Game game(GameId gameId, Round currentRound, Settings settings) {
        return new Game(
            gameId,
            true,
            true,
            currentRound,
            false, null, settings);
    }
}
