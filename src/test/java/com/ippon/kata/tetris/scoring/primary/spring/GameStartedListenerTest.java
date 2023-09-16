package com.ippon.kata.tetris.scoring.primary.spring;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.ippon.kata.tetris.gaming.infrastructure.secondary.spring.GameStartedEventDTO;
import com.ippon.kata.tetris.scoring.application.domain.Score;
import com.ippon.kata.tetris.scoring.application.domain.ScoreUpdatedEvent;
import com.ippon.kata.tetris.scoring.application.usecase.InitializeScoreUseCase;
import com.ippon.kata.tetris.scoring.infrastructure.primary.spring.PreparingGameStartedListener;
import com.ippon.kata.tetris.shared.domain.GameId;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GameStartedListenerTest {

    public static final int SPEED_MS = 100;
    @InjectMocks
    PreparingGameStartedListener gameStartedListener;
    @Mock
    InitializeScoreUseCase initializeScore;

    @Test
    void givenGameStarted_onApplicationEvent_initializeScore() {
        final UUID gameId = UUID.randomUUID();
        given(initializeScore.init(new GameId(gameId))).willReturn(new ScoreUpdatedEvent(
            new Score(
                new GameId(gameId),
                0
            )
        ));

        gameStartedListener.onApplicationEvent(new GameStartedEventDTO(this, gameId));

        then(initializeScore).should().init(new GameId(gameId));
    }
}