package com.ippon.kata.tetris.gaming.domain;

import static com.ippon.kata.tetris.gaming.application.domain.RoundStatus.STARTED;
import static org.assertj.core.api.BDDAssertions.then;

import com.ippon.kata.tetris.gaming.application.domain.Game;
import com.ippon.kata.tetris.gaming.application.domain.GameStatus;
import com.ippon.kata.tetris.gaming.application.domain.Round;
import com.ippon.kata.tetris.gaming.application.domain.RoundStatus;
import com.ippon.kata.tetris.gaming.application.domain.Settings;
import com.ippon.kata.tetris.shared.domain.GameId;
import com.ippon.kata.tetris.shared.domain.Level;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class GameTest {

    private static Game game(boolean boardInitialized, boolean boardInitialized1) {
        return new Game(new GameId(UUID.randomUUID()), boardInitialized, boardInitialized1, new Round(RoundStatus.STARTED, 0), boardInitialized1, null, new Settings(new Level(1)));
    }

    @Test
    void givenBoardNotInit_status_shouldReturnInitializing() {
        final Game game = game(false, false);

        then(game.status()).isEqualTo(GameStatus.INITIALIZING);
    }

    @Test
    void givenBoardInitTetrominoNotGenerated_status_shouldReturnInitializing() {
        final Game game = game(true, false);

        then(game.status()).isEqualTo(GameStatus.INITIALIZING);
    }

    @Test
    void givenBoardInitTetrominoGenerated_status_shouldReturnPlaying() {
        final Game game = game(true, true);

        then(game.status()).isEqualTo(GameStatus.PLAYING);
    }

    @Test
    void givenGame_newRound_setANewIdleRound() {
        final Game game = game(true, true);

        final Game game1 = game.newRound();

        then(game1.currentRound().status()).isEqualTo(STARTED);
    }
}