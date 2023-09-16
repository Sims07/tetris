package com.ippon.kata.tetris.preparing.application.usecase;


import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

import com.ippon.kata.tetris.preparing.application.domain.GenerateNextTetromino;
import com.ippon.kata.tetris.preparing.application.domain.TetrominoGeneratedEvent;
import com.ippon.kata.tetris.preparing.infrastructure.secondary.spring.TetrominoGeneratedPublisher;
import com.ippon.kata.tetris.shared.domain.GameId;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GenerateNextTetrominoUseCaseTest {

  @Mock TetrominoGeneratedPublisher eventPublisher;

  @Test
  void generateNextTetromino_shouldPublishEvent() {
    GenerateNextTetrominoUseCase generateNextTetrominoUseCase =
        new GenerateNextTetromino(eventPublisher);
    BDDMockito.given(eventPublisher.publish(any())).willReturn(mock(TetrominoGeneratedEvent.class));

    final TetrominoGeneratedEvent tetrominoGeneratedEvent =
        generateNextTetrominoUseCase.generateNextTetromino(new GameId(UUID.randomUUID()));

    then(tetrominoGeneratedEvent).isNotNull();
  }
}
