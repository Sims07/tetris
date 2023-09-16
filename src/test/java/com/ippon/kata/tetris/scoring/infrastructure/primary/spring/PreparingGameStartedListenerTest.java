package com.ippon.kata.tetris.scoring.infrastructure.primary.spring;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;

import com.ippon.kata.tetris.scoring.application.domain.ScoreUpdatedEvent;
import com.ippon.kata.tetris.scoring.application.usecase.UpdateScoreUseCase;
import com.ippon.kata.tetris.shared.domain.GameId;
import com.ippon.kata.tetris.shared.secondary.spring.EventPublisher;
import com.ippon.kata.tetris.shared.secondary.spring.model.LinesErasedEventDTO;
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
  @Mock UpdateScoreUseCase updateScoreUseCase;
  @Mock EventPublisher<ScoreUpdatedEvent> eventPublisher;

  @Test
  void givenErasedLineReceived_onErasedLines_shouldUpdateTheScore() {
    final UUID gameId = UUID.randomUUID();

    preparingGameStartedListener.onApplicationEvent(
        new LinesErasedEventDTO(this, List.of(21), gameId, 1));

    then(updateScoreUseCase).should().erasedLines(new GameId(gameId), 1, 1);
    then(eventPublisher).should().publish(any());
  }
}
