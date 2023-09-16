package com.ippon.kata.tetris.scoring.secondary.inmemory;

import static org.assertj.core.api.BDDAssertions.then;

import com.ippon.kata.tetris.scoring.domain.Score;
import com.ippon.kata.tetris.shared.GameId;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class InMemoryScoresTest {

    @Test
    void save() {
        final InMemoryScores inMemoryScores = new InMemoryScores();

        final GameId gameId = new GameId(UUID.randomUUID());
        inMemoryScores.save(new Score(
            gameId,
            0
        ));
        final Score score = inMemoryScores.get(gameId);

        then(score).isNotNull();
    }
}