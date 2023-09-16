package com.ippon.kata.tetris.executing.infrastructure.primary.spring;

import com.ippon.kata.tetris.executing.application.domain.BoardId;
import com.ippon.kata.tetris.executing.application.domain.TetraminoStatus;
import com.ippon.kata.tetris.executing.application.domain.Tetromino;
import com.ippon.kata.tetris.executing.application.domain.TetrominoMovedEvent;
import com.ippon.kata.tetris.executing.application.domain.TetrominoPickedEvent;
import com.ippon.kata.tetris.executing.application.usecase.FallTetrominoUseCase;
import com.ippon.kata.tetris.executing.application.usecase.InitializeBoardUseCase;
import com.ippon.kata.tetris.executing.application.usecase.PickTetrominoUseCase;
import com.ippon.kata.tetris.gaming.infrastructure.secondary.spring.GameStartedEventDTO;
import com.ippon.kata.tetris.gaming.infrastructure.secondary.spring.NextRoundStartedEventDTO;
import com.ippon.kata.tetris.shared.domain.GameId;
import com.ippon.kata.tetris.shared.domain.ShapeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ExecutingGameStartedListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExecutingGameStartedListener.class);
    private final InitializeBoardUseCase initializeBoardUseCase;
    private final PickTetrominoUseCase pickTetrominoUseCase;
    private final FallTetrominoUseCase fallTetrominoUseCase;

    public ExecutingGameStartedListener(
        InitializeBoardUseCase initializeBoardUseCase,
        PickTetrominoUseCase pickTetrominoUseCase,
        FallTetrominoUseCase fallTetrominoUseCase) {

        this.initializeBoardUseCase = initializeBoardUseCase;
        this.pickTetrominoUseCase = pickTetrominoUseCase;
        this.fallTetrominoUseCase = fallTetrominoUseCase;
    }

    @Async
    @EventListener
    public void onApplicationEvent(GameStartedEventDTO event) {
        LOGGER.info("EXECUTING : Receive game started {}", event.getGameId());
        initializeBoardUseCase.init(new GameId(event.getGameId()));
    }

    @Async
    @EventListener
    public void onApplicationEvent(NextRoundStartedEventDTO event) throws InterruptedException {
        LOGGER.info("EXECUTING : Receive next round started {}", event.gameId());
        final TetrominoPickedEvent tetrominoPickedEvent = pickTetrominoUseCase.pickTetromino(
            new GameId(event.gameId()),
            ShapeType.valueOf(event.shapeType())
        );
        LOGGER.info("Place tetromino {}", tetrominoPickedEvent.tetromino());
        TetrominoMovedEvent tetrominoMovedEvent = fallTetrominoUseCase.fall(new BoardId(new GameId(event.gameId())), tetrominoPickedEvent.tetromino());
        while (tetrominoMovedEvent.tetromino().status() != TetraminoStatus.FIXED) {
            final Tetromino tetromino = tetrominoMovedEvent.tetromino();
            LOGGER.info("Move down {}", tetromino);
            tetrominoMovedEvent = fallTetrominoUseCase.fall(new BoardId(new GameId(event.gameId())), tetromino);
            Thread.sleep(1000);
        }
    }
}
