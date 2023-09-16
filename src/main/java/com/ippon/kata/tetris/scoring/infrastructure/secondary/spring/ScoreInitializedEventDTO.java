package com.ippon.kata.tetris.scoring.infrastructure.secondary.spring;

import com.ippon.kata.tetris.scoring.application.domain.ScoreInitializedEvent;
import java.util.UUID;
import org.springframework.context.ApplicationEvent;

public class ScoreInitializedEventDTO extends ApplicationEvent {

    private final UUID gameId;

    public ScoreInitializedEventDTO(Object source, UUID gameId) {
        super(source);
        this.gameId = gameId;
    }

    public static ScoreInitializedEventDTO from(Object source, ScoreInitializedEvent event) {
        return new ScoreInitializedEventDTO(source,
            event.score().gameId().value()
        );
    }


    public UUID getGameId() {
        return gameId;
    }
}
