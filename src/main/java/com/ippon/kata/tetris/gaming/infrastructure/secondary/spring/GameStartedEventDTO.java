package com.ippon.kata.tetris.gaming.infrastructure.secondary.spring;

import com.ippon.kata.tetris.gaming.application.domain.GameStartedEvent;
import java.util.UUID;
import org.springframework.context.ApplicationEvent;

public class GameStartedEventDTO extends ApplicationEvent {

    private final UUID gameId;

    public GameStartedEventDTO(Object source, UUID gameId) {
        super(source);
        this.gameId = gameId;
    }

    public static GameStartedEventDTO from(Object source, GameStartedEvent event) {
        return new GameStartedEventDTO(source, event.gameId().value());
    }

    public UUID getGameId() {
        return gameId;
    }
}
