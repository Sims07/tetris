package com.ippon.kata.tetris.gaming;

import static com.ippon.kata.tetris.gaming.domain.RoundStatus.IDLE;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.ippon.kata.tetris.executing.domain.PickTetromino;
import com.ippon.kata.tetris.executing.domain.Shape;
import com.ippon.kata.tetris.executing.domain.TetraminoStatus;
import com.ippon.kata.tetris.executing.domain.Tetromino;
import com.ippon.kata.tetris.executing.domain.TetrominoId;
import com.ippon.kata.tetris.executing.domain.TetrominoPickedEvent;
import com.ippon.kata.tetris.executing.usecase.PickTetrominoUseCase;
import com.ippon.kata.tetris.gaming.domain.Game;
import com.ippon.kata.tetris.gaming.domain.GameStartedEvent;
import com.ippon.kata.tetris.gaming.domain.Round;
import com.ippon.kata.tetris.gaming.usecase.TetrisGameStartUseCase;
import com.ippon.kata.tetris.shared.EventPublisher;
import com.ippon.kata.tetris.shared.GameId;
import com.ippon.kata.tetris.shared.ShapeType;
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

    @Mock
    EventPublisher<TetrominoPickedEvent> tetrominoPickedEventEventPublisher;
    @Mock
    EventPublisher<GameStartedEvent> gameStartedEventEventPublisher;
    @Mock
    Games games;

    @ParameterizedTest
    @EnumSource(ShapeType.class)
    void givenTetrominoGeneratedAndGameNotStarted_onTetrominoGenerated_shoudlPutTetrominoOnInitPosition(ShapeType shapeType) {
        PickTetrominoUseCase pickTetrominoUseCase = new PickTetromino(tetrominoPickedEventEventPublisher);
        final GameId gameId = new GameId(UUID.randomUUID());
        final Shape shape = new Shape(shapeType);
        final TetrominoPickedEvent event = new TetrominoPickedEvent(
            gameId,
            new Tetromino(
                new TetrominoId(UUID.randomUUID()), shape,
                TetraminoStatus.IDLE,
                shape.initPositions()
            )
        );
        given(tetrominoPickedEventEventPublisher.publish(any())).willReturn(event);

        final TetrominoPickedEvent tetrominoPickedEvent = pickTetrominoUseCase.pickTetromino(
            gameId,
            shapeType
        );

        then(tetrominoPickedEvent.tetromino()).isNotNull();
        then(tetrominoPickedEvent.tetromino().positions()).isEqualTo(new Shape(shapeType).initPositions());

    }

    @Test
    void startGame_should_createGameAndEmitGameStarted() {
        TetrisGameStartUseCase tetrisGameStartUseCase = new TetrisGameStart(gameStartedEventEventPublisher, games);
        final Game game = new Game(
            new GameId(UUID.randomUUID()),
            false, false, new Round(IDLE, 0), false, null);
        given(games.save(any())).willReturn(game);
        given(gameStartedEventEventPublisher.publish(any())).willReturn(new GameStartedEvent(game.id()));

        final GameStartedEvent gameStartedEvent = tetrisGameStartUseCase.start();

        then(gameStartedEvent).isNotNull();
        then(gameStartedEvent.gameId()).isEqualTo(game.id());
        BDDMockito.then(gameStartedEventEventPublisher).should().publish(gameStartedEvent);
    }
}
