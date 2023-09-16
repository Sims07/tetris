package com.ippon.kata.tetris.gaming.domain;

import static com.ippon.kata.tetris.gaming.application.domain.RoundStatus.IDLE;
import static com.ippon.kata.tetris.gaming.application.domain.RoundStatus.STARTED;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;

import com.ippon.kata.tetris.gaming.application.domain.Game;
import com.ippon.kata.tetris.gaming.application.domain.Games;
import com.ippon.kata.tetris.gaming.application.domain.NextRoundStartedEvent;
import com.ippon.kata.tetris.gaming.application.domain.Round;
import com.ippon.kata.tetris.gaming.application.domain.StartNextRound;
import com.ippon.kata.tetris.shared.domain.GameId;
import com.ippon.kata.tetris.shared.domain.ShapeType;
import com.ippon.kata.tetris.shared.secondary.spring.EventPublisher;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StartNextRoundTest {

    @InjectMocks
    StartNextRound startNextRound;
    @Mock
    Games games;

    @Mock
    EventPublisher<NextRoundStartedEvent> eventPublisher;

    @Test
    void startNextRound_shouldUpdateCurrentRoundAndEmitNextRoundStarted() {
        final GameId gameId = new GameId(UUID.randomUUID());
        final Game game = GameFixture.game(gameId, 0, new Round(IDLE, 0));
        given(games.get(gameId)).willReturn(game);
        final Game saved = GameFixture.game(gameId, 1, new Round(STARTED, 1));
        given(games.save(saved)).willReturn(saved);
        final ShapeType shapeType = ShapeType.I;
        final NextRoundStartedEvent roundStartedEvent = new NextRoundStartedEvent(
            gameId, shapeType, 1
        );
        given(eventPublisher.publish(roundStartedEvent)).willReturn(roundStartedEvent);

        final NextRoundStartedEvent nextRoundStartedEvent = startNextRound.start(gameId, shapeType);

        then(nextRoundStartedEvent).isNotNull();
        then(nextRoundStartedEvent.roundIndex()).isEqualTo(1);
    }

}