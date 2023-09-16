package com.ippon.kata.tetris.preparing.infrastructure.secondary.spring;

import com.ippon.kata.tetris.preparing.application.domain.TetrominoGeneratedEvent;
import java.util.UUID;
import org.springframework.context.ApplicationEvent;

public class TetrominoGeneratedEventDTO extends ApplicationEvent {

    private final String shape;
    private final UUID gameId;

    public TetrominoGeneratedEventDTO(Object source, String shape, UUID gameId) {
        super(source);
        this.shape = shape;
        this.gameId = gameId;
    }

    public static TetrominoGeneratedEventDTO from(Object source, TetrominoGeneratedEvent event) {
        return new TetrominoGeneratedEventDTO(source,
            event.tetromino().shape().name(),
            event.gameId().value()
        );
    }

    public String getShape() {
        return shape;
    }

    public UUID getGameId() {
        return gameId;
    }
}
