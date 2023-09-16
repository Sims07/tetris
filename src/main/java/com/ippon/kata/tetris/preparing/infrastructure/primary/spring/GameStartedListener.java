package com.ippon.kata.tetris.preparing.infrastructure.primary.spring;

import com.ippon.kata.tetris.gaming.infrastructure.secondary.spring.GameStartedEventDTO;
import com.ippon.kata.tetris.preparing.application.usecase.GenerateNextTetrominoUseCase;
import com.ippon.kata.tetris.shared.domain.GameId;
import com.ippon.kata.tetris.shared.secondary.spring.model.TetrominoPickedEventDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class GameStartedListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameStartedListener.class);
    private final GenerateNextTetrominoUseCase generateNextTetrominoUseCase;

    public GameStartedListener(GenerateNextTetrominoUseCase generateNextTetrominoUseCase) {
        this.generateNextTetrominoUseCase = generateNextTetrominoUseCase;
    }

    @Async
    @EventListener
    public void onApplicationEvent(GameStartedEventDTO event) {
        LOGGER.info("PREPARING : Receive game started");
        generateNextTetrominoUseCase.generateNextTetromino(new GameId(event.getGameId()));
    }

    @Async
    @EventListener
    public void onApplicationEvent(TetrominoPickedEventDTO event) {
        LOGGER.info("PREPARING : Receive tetromino picked");
        generateNextTetrominoUseCase.generateNextTetromino(new GameId(event.getGameId()));
    }
}
