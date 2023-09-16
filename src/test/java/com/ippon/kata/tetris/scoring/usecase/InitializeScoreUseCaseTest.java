package com.ippon.kata.tetris.scoring.usecase;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;

import com.ippon.kata.tetris.scoring.application.domain.InitializeScore;
import com.ippon.kata.tetris.scoring.application.domain.Score;
import com.ippon.kata.tetris.scoring.application.domain.ScoreUpdatedEvent;
import com.ippon.kata.tetris.scoring.application.domain.Scores;
import com.ippon.kata.tetris.scoring.application.usecase.InitializeScoreUseCase;
import com.ippon.kata.tetris.shared.domain.GameId;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class InitializeScoreUseCaseTest {

    @Mock
    Scores scores;

    @Test
    void givenGame_initializeScore_shouldSaveScoreAndReturnScoreInitialized() {
        InitializeScoreUseCase initializeScoreUseCase = new InitializeScore(scores);
        final GameId gameId = new GameId(UUID.randomUUID());
        final Score score = new Score(gameId, 0);
        given(scores.save(score)).willReturn(score);

        final ScoreUpdatedEvent scoreInitializedEvent = initializeScoreUseCase.init(gameId);

        then(scoreInitializedEvent).isNotNull();
        then(scoreInitializedEvent.score()).isNotNull();
        then(scoreInitializedEvent.score().value()).isEqualTo(0);
    }
}