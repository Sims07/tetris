package com.ippon.kata.tetris.scoring.domain;

import com.ippon.kata.tetris.scoring.usecase.InitializeScoreUseCase;
import com.ippon.kata.tetris.shared.EventPublisher;
import com.ippon.kata.tetris.shared.GameId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitializeScore implements InitializeScoreUseCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(InitializeScore.class);
    private final Scores scores;
    private final EventPublisher<ScoreInitializedEvent> eventPublisher;

    public InitializeScore(Scores scores, EventPublisher<ScoreInitializedEvent> eventPublisher) {
        this.scores = scores;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public ScoreInitializedEvent init(GameId gameId) {
        LOGGER.info("SCORING : Command initialize score");
        return eventPublisher.publish(new ScoreInitializedEvent(
                scores.save(
                    new Score(gameId, 0)
                )
            )
        );
    }
}
