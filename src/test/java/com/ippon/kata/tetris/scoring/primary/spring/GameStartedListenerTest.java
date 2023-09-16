package com.ippon.kata.tetris.scoring.primary.spring;

import static org.mockito.BDDMockito.then;

import com.ippon.kata.tetris.gaming.secondary.spring.GameStartedEventDTO;
import com.ippon.kata.tetris.scoring.domain.Score;
import com.ippon.kata.tetris.scoring.domain.ScoreInitializedEvent;
import com.ippon.kata.tetris.scoring.usecase.InitializeScoreUseCase;
import com.ippon.kata.tetris.shared.GameId;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GameStartedListenerTest {

    @InjectMocks
    PreparingGameStartedListener gameStartedListener;
    @Mock
    InitializeScoreUseCase initializeScore;

    @Test
    void givenGameStarted_onApplicationEvent_initializeScore() {
        final UUID gameId = UUID.randomUUID();
        BDDMockito.given(initializeScore.init(new GameId(gameId))).willReturn(new ScoreInitializedEvent(
            new Score(
                new GameId(gameId),
                0
            )
        ));

        gameStartedListener.onApplicationEvent(new GameStartedEventDTO(this, gameId));

        then(initializeScore).should().init(new GameId(gameId));
    }
}