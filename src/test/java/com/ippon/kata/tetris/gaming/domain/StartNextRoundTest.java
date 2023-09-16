package com.ippon.kata.tetris.gaming.domain;

import static com.ippon.kata.tetris.gaming.application.domain.RoundStatus.IDLE;
import static com.ippon.kata.tetris.gaming.application.domain.RoundStatus.STARTED;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;

import com.ippon.kata.tetris.gaming.application.domain.Game;
import com.ippon.kata.tetris.gaming.application.domain.Games;
import com.ippon.kata.tetris.gaming.application.domain.NextRoundStartedEvent;
import com.ippon.kata.tetris.gaming.application.domain.Round;
import com.ippon.kata.tetris.gaming.application.domain.Settings;
import com.ippon.kata.tetris.gaming.application.domain.StartNextRound;
import com.ippon.kata.tetris.shared.domain.GameId;
import com.ippon.kata.tetris.shared.domain.Level;
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

  public static final int INITIAL_LEVEL = 1;
  @InjectMocks StartNextRound startNextRound;
  @Mock Games games;

  @Mock EventPublisher<NextRoundStartedEvent> eventPublisher;

  @Test
  void startNextRound_shouldUpdateCurrentRoundAndEmitNextRoundStarted() {
    final GameId gameId = new GameId(UUID.randomUUID());
    final Game game = GameFixture.game(gameId, new Round(IDLE, 0), new Settings(new Level(INITIAL_LEVEL)));
    given(games.get(gameId)).willReturn(game);
    final Game saved = GameFixture.game(gameId, new Round(STARTED, INITIAL_LEVEL), new Settings(new Level(INITIAL_LEVEL)));
    given(games.save(saved)).willReturn(saved);
    final ShapeType shapeType = ShapeType.I;
    final NextRoundStartedEvent roundStartedEvent =
        new NextRoundStartedEvent(gameId, shapeType, INITIAL_LEVEL, new Level(INITIAL_LEVEL));
    given(eventPublisher.publish(roundStartedEvent)).willReturn(roundStartedEvent);

    final NextRoundStartedEvent nextRoundStartedEvent = startNextRound.start(gameId, shapeType);

    then(nextRoundStartedEvent).isNotNull();
    then(nextRoundStartedEvent.roundIndex()).isEqualTo(INITIAL_LEVEL);
  }

  @Test
  void givenIndexRounbd100_startNextRound_shouldIncreaseLevel() {
    final int nextLevel = 2;
    final GameId gameId = new GameId(UUID.randomUUID());
    final Game game = GameFixture.game(gameId, new Round(IDLE, 100), new Settings(new Level(INITIAL_LEVEL)));
    given(games.get(gameId)).willReturn(game);
    final Game saved = GameFixture.game(gameId, new Round(STARTED, 101), new Settings(new Level(nextLevel)));
    given(games.save(saved)).willReturn(saved);
    final ShapeType shapeType = ShapeType.I;
    final NextRoundStartedEvent roundStartedEvent =
        new NextRoundStartedEvent(gameId, shapeType, 101, new Level(nextLevel));
    given(eventPublisher.publish(roundStartedEvent)).willReturn(roundStartedEvent);

    final NextRoundStartedEvent nextRoundStartedEvent = startNextRound.start(gameId, shapeType);

    then(nextRoundStartedEvent).isNotNull();
    then(nextRoundStartedEvent.level()).isEqualTo(new Level(nextLevel));
    then(nextRoundStartedEvent.roundIndex()).isEqualTo(101);
  }
}
