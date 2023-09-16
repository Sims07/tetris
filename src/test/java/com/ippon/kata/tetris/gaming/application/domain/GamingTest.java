package com.ippon.kata.tetris.gaming.application.domain;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.spy;

import com.ippon.kata.tetris.executing.application.domain.PickTetromino;
import com.ippon.kata.tetris.executing.application.domain.RotationIndex;
import com.ippon.kata.tetris.executing.application.domain.TetraminoStatus;
import com.ippon.kata.tetris.executing.application.domain.Tetromino;
import com.ippon.kata.tetris.executing.application.domain.TetrominoId;
import com.ippon.kata.tetris.executing.application.domain.TetrominoPickedEvent;
import com.ippon.kata.tetris.executing.application.usecase.PickTetrominoUseCase;
import com.ippon.kata.tetris.gaming.application.usecase.TetrisGameStartUseCase;
import com.ippon.kata.tetris.shared.domain.EventPublisher;
import com.ippon.kata.tetris.shared.domain.GameId;
import com.ippon.kata.tetris.shared.domain.Level;
import com.ippon.kata.tetris.shared.domain.Shape;
import com.ippon.kata.tetris.shared.domain.ShapeType;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GamingTest {

  @Mock EventPublisher<TetrominoPickedEvent> tetrominoPickedEventEventPublisher;
  @Mock EventPublisher<GameStartedEvent> gameStartedEventEventPublisher;
  @Mock Games games;

  @ParameterizedTest
  @EnumSource(ShapeType.class)
  void
      givenTetrominoGeneratedAndGameNotStarted_onTetrominoGenerated_shoudlPutTetrominoOnInitPosition(
          ShapeType shapeType) {
    PickTetrominoUseCase pickTetrominoUseCase =
        new PickTetromino(tetrominoPickedEventEventPublisher);
    final GameId gameId = new GameId(UUID.randomUUID());
    final Shape shape = new Shape(shapeType);
    final TetrominoPickedEvent event =
        new TetrominoPickedEvent(
            gameId,
            new Tetromino(
                new TetrominoId(UUID.randomUUID()),
                shape,
                TetraminoStatus.IDLE,
                shape.initPositions(),
                new RotationIndex(0)));
    given(tetrominoPickedEventEventPublisher.publish(any())).willReturn(event);

    final TetrominoPickedEvent tetrominoPickedEvent =
        pickTetrominoUseCase.pickTetromino(gameId, shapeType);

    then(tetrominoPickedEvent.tetromino()).isNotNull();
    then(tetrominoPickedEvent.tetromino().positions())
        .isEqualTo(new Shape(shapeType).initPositions());
  }

  @Test
  void givenSartedGame_startGame_shouldEndPreviousAndStartNewGameByEmittingGameStarted() {
    TetrisGameStartUseCase tetrisGameStartUseCase =
        new TetrisGameStart(gameStartedEventEventPublisher, games);
    final Game game = GameFixture.game(true,true,false);
    final Game currentRunningGame = spy(GameFixture.game(true,true,false));
    given(games.list()).willReturn(List.of(currentRunningGame));
    given(games.add(any())).willReturn(game);
    given(gameStartedEventEventPublisher.publish(any()))
        .willReturn(new GameStartedEvent(game.id(), new Level(1)));

    final GameStartedEvent gameStartedEvent = tetrisGameStartUseCase.start();

    then(gameStartedEvent).isNotNull();
    then(gameStartedEvent.gameId()).isEqualTo(game.id());
    then(gameStartedEvent.level().value()).isEqualTo(1);
    BDDMockito.then(currentRunningGame).should().end();
    BDDMockito.then(gameStartedEventEventPublisher).should().publish(gameStartedEvent);
  }
}
