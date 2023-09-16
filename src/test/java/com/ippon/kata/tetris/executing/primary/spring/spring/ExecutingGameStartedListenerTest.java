package com.ippon.kata.tetris.executing.primary.spring.spring;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.ippon.kata.tetris.executing.application.domain.BoardId;
import com.ippon.kata.tetris.executing.application.domain.RoundIndex;
import com.ippon.kata.tetris.executing.application.domain.RoundIndexes;
import com.ippon.kata.tetris.executing.application.domain.Shape;
import com.ippon.kata.tetris.executing.application.domain.TetraminoStatus;
import com.ippon.kata.tetris.executing.application.domain.Tetromino;
import com.ippon.kata.tetris.executing.application.domain.TetrominoId;
import com.ippon.kata.tetris.executing.application.domain.TetrominoMovedEvent;
import com.ippon.kata.tetris.executing.application.domain.TetrominoPickedEvent;
import com.ippon.kata.tetris.executing.application.usecase.FallTetrominoUseCase;
import com.ippon.kata.tetris.executing.application.usecase.InitializeBoardUseCase;
import com.ippon.kata.tetris.executing.application.usecase.PickTetrominoUseCase;
import com.ippon.kata.tetris.executing.infrastructure.primary.spring.ExecutingGameStartedListener;
import com.ippon.kata.tetris.gaming.infrastructure.secondary.spring.GameStartedEventDTO;
import com.ippon.kata.tetris.gaming.infrastructure.secondary.spring.NextRoundStartedEventDTO;
import com.ippon.kata.tetris.shared.domain.Direction;
import com.ippon.kata.tetris.shared.domain.GameId;
import com.ippon.kata.tetris.shared.domain.ShapeType;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ExecutingGameStartedListenerTest {

  @InjectMocks ExecutingGameStartedListener executingGameStartedListener;

  @Mock InitializeBoardUseCase initializeBoardUseCase;
  @Mock PickTetrominoUseCase pickTetrominoUseCase;
  @Mock FallTetrominoUseCase fallTetrominoUseCase;
  @Mock RoundIndexes roundIndexes;

  @Test
  void givenGameStarted_onApplicationEvent_shouldInitBoard() {
    final UUID gameId = UUID.randomUUID();

    executingGameStartedListener.onApplicationEvent(new GameStartedEventDTO(this, gameId));

    then(initializeBoardUseCase).should().init(new GameId(gameId));
  }

  @Test
  void givenNextRoundStartedStarted_onApplicationEvent_shouldFallTetromino()
      throws InterruptedException {
    final UUID gameIdValue = UUID.randomUUID();
    final GameId gameId = new GameId(gameIdValue);
    final TetrominoPickedEvent tetrominoPickedEvent =
        new TetrominoPickedEvent(
            gameId,
            new Tetromino(
                new TetrominoId(UUID.randomUUID()),
                new Shape(ShapeType.L),
                TetraminoStatus.IDLE,
                null));
    given(pickTetrominoUseCase.pickTetromino(gameId, ShapeType.L)).willReturn(tetrominoPickedEvent);
    given(fallTetrominoUseCase.fall(new BoardId(gameId), tetrominoPickedEvent.tetromino()))
        .willReturn(
            new TetrominoMovedEvent(
                gameId, tetrominoPickedEvent.tetromino().fixe(), Direction.DOWN, false));
    given(roundIndexes.get()).willReturn(new RoundIndex(1));
    given(roundIndexes.add(any())).willAnswer(a -> a.getArguments()[0]);

    executingGameStartedListener.onApplicationEvent(
        new NextRoundStartedEventDTO(this, gameIdValue, "L", 0));
  }
}
