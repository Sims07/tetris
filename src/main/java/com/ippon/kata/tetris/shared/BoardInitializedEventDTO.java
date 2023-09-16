package com.ippon.kata.tetris.shared;

import com.ippon.kata.tetris.executing.domain.BoardInitializedEvent;
import java.util.UUID;
import org.springframework.context.ApplicationEvent;

public class BoardInitializedEventDTO extends ApplicationEvent {

    private final UUID gameId;

    public BoardInitializedEventDTO(Object source, UUID gameId) {
        super(source);
        this.gameId = gameId;
    }

    public static BoardInitializedEventDTO from(Object source, BoardInitializedEvent event) {
        return new BoardInitializedEventDTO(source, event.gameId().value());
    }

    public UUID getGameId() {
        return gameId;
    }
}
