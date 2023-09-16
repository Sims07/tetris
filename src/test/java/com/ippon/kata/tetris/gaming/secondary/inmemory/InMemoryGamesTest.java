package com.ippon.kata.tetris.gaming.secondary.inmemory;

import static com.ippon.kata.tetris.gaming.application.domain.RoundStatus.IDLE;
import static org.assertj.core.api.BDDAssertions.then;

import com.ippon.kata.tetris.gaming.application.domain.Game;
import com.ippon.kata.tetris.gaming.application.domain.Games;
import com.ippon.kata.tetris.gaming.application.domain.Round;
import com.ippon.kata.tetris.gaming.infrastructure.secondary.inmemory.InMemoryGames;
import com.ippon.kata.tetris.shared.domain.GameId;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class InMemoryGamesTest {

    @Test
    void add_shouldSaveItInMemory() {
        Games games = new InMemoryGames();

        final GameId gameId = new GameId(UUID.randomUUID());
        final Game toAdd = new Game(gameId, false, false, new Round(IDLE, 0), false, null);
        final Game added = games.save(toAdd);
        final Game game = games.get(gameId);

        then(added).isNotNull();
        then(game).isNotNull();
        then(game).isEqualTo(toAdd);
    }
}
