package com.ippon.kata.tetris.scoring.infrastructure.secondary.inmemory;

import com.ippon.kata.tetris.scoring.application.domain.Score;
import com.ippon.kata.tetris.scoring.application.domain.Scores;
import com.ippon.kata.tetris.shared.domain.GameId;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class InMemoryScores implements Scores {

    private final Map<GameId, Score> scores = new HashMap<>();

    @Override
    public Score save(Score score) {
        scores.put(score.gameId(), score);
        return score;
    }

    @Override
    public Score get(GameId gameId) {
        return scores.get(gameId);
    }
}
