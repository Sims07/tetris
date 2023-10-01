package com.ippon.kata.tetris.scoring.infrastructure.primary.spring;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;

import com.ippon.kata.tetris.scoring.application.domain.ScoreComputedEvent;
import com.ippon.kata.tetris.scoring.application.usecase.ComputeScoreUseCase;
import com.ippon.kata.tetris.shared.domain.EventPublisher;
import com.ippon.kata.tetris.shared.domain.GameId;
import com.ippon.kata.tetris.shared.infrastructure.secondary.spring.model.LinesErasedEventDTO;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PreparingGameStartedListenerTest {
  @InjectMocks PreparingGameStartedListener preparingGameStartedListener;
  @Mock ComputeScoreUseCase computeScoreUseCase;
  @Mock EventPublisher<ScoreComputedEvent> eventPublisher;

  @Test
  void givenErasedLineReceived_onErasedLines_shouldUpdateTheScore() {
    final UUID gameId = UUID.randomUUID();

    preparingGameStartedListener.onApplicationEvent(
        new LinesErasedEventDTO(this, List.of(21), gameId, 1));

    then(computeScoreUseCase).should().compute(new GameId(gameId), 1, 1);
    then(eventPublisher).should().publish(any());
  }
}
