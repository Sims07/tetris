package com.ippon.kata.tetris.gaming.application.domain;

import com.ippon.kata.tetris.shared.domain.GameId;
import com.ippon.kata.tetris.shared.domain.Level;
import java.util.UUID;

public class GameFixture {
  public static Game game(boolean boardInitialized, boolean boardInitialized1, boolean ended) {
    return new Game(
        new GameId(UUID.randomUUID()),
        boardInitialized,
        boardInitialized1,
        new Round(RoundStatus.STARTED, 0),
        boardInitialized1,
        null,
        new Settings(new Level(1)),
        ended);
  }
  public static Game game(GameId gameId, Round currentRound, Settings settings) {
    return new Game(gameId, true, true, currentRound, false, null, settings, false);
  }
}
