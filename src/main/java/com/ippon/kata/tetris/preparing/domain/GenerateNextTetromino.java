package com.ippon.kata.tetris.preparing.domain;

import com.ippon.kata.tetris.preparing.secondary.spring.TetrominoGeneratedPublisher;
import com.ippon.kata.tetris.shared.domain.GameId;
import com.ippon.kata.tetris.shared.secondary.spring.EventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenerateNextTetromino implements GenerateNextTetrominoUseCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenerateNextTetromino.class);
    private final EventPublisher<TetrominoGeneratedEvent> eventPublisher;

    public GenerateNextTetromino(TetrominoGeneratedPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public TetrominoGeneratedEvent generateNextTetromino(GameId gameId) {
        LOGGER.info("PREPARING : Command Generate tetromino");
        final TetrominoGeneratedEvent tetrominoGeneratedEvent = new TetrominoGenerator().generate(gameId);
        eventPublisher.publish(tetrominoGeneratedEvent);
        return tetrominoGeneratedEvent;
    }
}
