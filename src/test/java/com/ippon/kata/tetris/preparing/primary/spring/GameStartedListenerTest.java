package com.ippon.kata.tetris.preparing.primary.spring;

import com.ippon.kata.tetris.gaming.infrastructure.secondary.spring.GameStartedEventDTO;
import com.ippon.kata.tetris.preparing.application.domain.GenerateNextTetromino;
import com.ippon.kata.tetris.preparing.infrastructure.primary.spring.GameStartedListener;
import com.ippon.kata.tetris.shared.domain.GameId;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GameStartedListenerTest {

  public static final int LEVEL = 1;
  @InjectMocks GameStartedListener gameStartedListener;

  @Mock GenerateNextTetromino generateNextTetromino;

  @Test
  void givenGameStarted_onApplicationEvent_shouldGenerateTetromino() {
    final UUID gameId = UUID.randomUUID();

    gameStartedListener.onApplicationEvent(new GameStartedEventDTO(this, gameId, LEVEL));

    BDDMockito.then(generateNextTetromino).should().generateNextTetromino(new GameId(gameId));
  }
}
