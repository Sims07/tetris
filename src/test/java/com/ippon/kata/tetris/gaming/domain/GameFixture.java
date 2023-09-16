package com.ippon.kata.tetris.gaming.domain;

import com.ippon.kata.tetris.shared.GameId;

public class GameFixture {

    public static Game game(GameId gameId, int roundIndex, Round currentRound) {
        return new Game(
            gameId,
            true,
            true,
            currentRound,
            false, null);
    }
}
