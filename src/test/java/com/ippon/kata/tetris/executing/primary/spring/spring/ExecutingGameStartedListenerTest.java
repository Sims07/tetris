package com.ippon.kata.tetris.executing.primary.spring.spring;

import static org.mockito.BDDMockito.then;

import com.ippon.kata.tetris.executing.application.usecase.InitializeBoardUseCase;
import com.ippon.kata.tetris.executing.infrastructure.primary.spring.ExecutingGameStartedListener;
import com.ippon.kata.tetris.gaming.infrastructure.secondary.spring.GameStartedEventDTO;
import com.ippon.kata.tetris.shared.domain.GameId;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ExecutingGameStartedListenerTest {

  public static final int LEVEL = 1;
  @InjectMocks ExecutingGameStartedListener executingGameStartedListener;

  @Mock InitializeBoardUseCase initializeBoardUseCase;
  @Test
  void givenGameStarted_onApplicationEvent_shouldInitBoard() {
    final UUID gameId = UUID.randomUUID();

    executingGameStartedListener.onApplicationEvent(new GameStartedEventDTO(this, gameId, LEVEL));

    then(initializeBoardUseCase).should().init(new GameId(gameId));
  }

}
