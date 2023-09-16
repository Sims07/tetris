package com.ippon.kata.tetris.executing.primary.spring.spring;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.ippon.kata.tetris.executing.domain.BoardId;
import com.ippon.kata.tetris.executing.domain.Shape;
import com.ippon.kata.tetris.executing.domain.TetraminoStatus;
import com.ippon.kata.tetris.executing.domain.Tetromino;
import com.ippon.kata.tetris.executing.domain.TetrominoId;
import com.ippon.kata.tetris.executing.domain.TetrominoPickedEvent;
import com.ippon.kata.tetris.executing.primary.spring.ExecutingGameStartedListener;
import com.ippon.kata.tetris.executing.usecase.FallTetrominoUseCase;
import com.ippon.kata.tetris.executing.usecase.InitializeBoardUseCase;
import com.ippon.kata.tetris.executing.usecase.PickTetrominoUseCase;
import com.ippon.kata.tetris.gaming.secondary.spring.GameStartedEventDTO;
import com.ippon.kata.tetris.gaming.secondary.spring.NextRoundStartedEventDTO;
import com.ippon.kata.tetris.shared.GameId;
import com.ippon.kata.tetris.shared.ShapeType;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ExecutingGameStartedListenerTest {

    @InjectMocks
    ExecutingGameStartedListener executingGameStartedListener;

    @Mock
    InitializeBoardUseCase initializeBoardUseCase;
    @Mock
    PickTetrominoUseCase pickTetrominoUseCase;
    @Mock
    FallTetrominoUseCase fallTetrominoUseCase;

    @Test
    void givenGameStarted_onApplicationEvent_shouldInitBoard() {
        final UUID gameId = UUID.randomUUID();

        executingGameStartedListener.onApplicationEvent(new GameStartedEventDTO(
            this, gameId
        ));

        then(initializeBoardUseCase).should().init(new GameId(gameId));
    }

    @Test
    void givenNextRoundStartedStarted_onApplicationEvent_shouldInitBoard() throws InterruptedException {
        final UUID gameIdValue = UUID.randomUUID();
        final GameId gameId = new GameId(gameIdValue);
        given(pickTetrominoUseCase.pickTetromino(gameId, ShapeType.L)).willReturn(
            new TetrominoPickedEvent(
                gameId,
                new Tetromino(
                    new TetrominoId(UUID.randomUUID()), new Shape(ShapeType.L),
                    TetraminoStatus.IDLE,
                    null
                )
            )
        );

        executingGameStartedListener.onApplicationEvent(new NextRoundStartedEventDTO(
            this, gameIdValue, "L", 0
        ));

        final Shape shape = new Shape(ShapeType.L);
        then(fallTetrominoUseCase).should().fall(new BoardId(gameId), new Tetromino(new TetrominoId(UUID.randomUUID()), shape, TetraminoStatus.IDLE, null));
    }
}