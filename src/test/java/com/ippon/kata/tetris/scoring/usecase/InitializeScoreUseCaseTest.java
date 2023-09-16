package com.ippon.kata.tetris.scoring.usecase;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;

import com.ippon.kata.tetris.scoring.domain.InitializeScore;
import com.ippon.kata.tetris.scoring.domain.Score;
import com.ippon.kata.tetris.scoring.domain.ScoreInitializedEvent;
import com.ippon.kata.tetris.scoring.domain.Scores;
import com.ippon.kata.tetris.shared.domain.GameId;
import com.ippon.kata.tetris.shared.secondary.spring.EventPublisher;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class InitializeScoreUseCaseTest {

    @Mock
    Scores scores;
    @Mock
    EventPublisher<ScoreInitializedEvent> eventPublisher;

    @Test
    void givenGame_initializeScore_shouldSaveScoreAndReturnScoreInitialized() {
        InitializeScoreUseCase initializeScoreUseCase = new InitializeScore(scores, eventPublisher);
        final GameId gameId = new GameId(UUID.randomUUID());
        final Score score = new Score(gameId, 0);
        given(scores.save(score)).willReturn(score);
        final ScoreInitializedEvent event = new ScoreInitializedEvent(score);
        given(eventPublisher.publish(event)).willReturn(event);

        final ScoreInitializedEvent scoreInitializedEvent = initializeScoreUseCase.init(gameId);

        then(scoreInitializedEvent).isNotNull();
        then(scoreInitializedEvent.score()).isNotNull();
        then(scoreInitializedEvent.score().value()).isEqualTo(0);
    }
}