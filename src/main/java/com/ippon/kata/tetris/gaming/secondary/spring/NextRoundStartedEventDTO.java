package com.ippon.kata.tetris.gaming.secondary.spring;

import com.ippon.kata.tetris.gaming.domain.NextRoundStartedEvent;
import java.util.UUID;
import org.springframework.context.ApplicationEvent;

public class NextRoundStartedEventDTO extends ApplicationEvent {

    private final UUID gameId;
    private final String shapeType;
    private final int roundIndex;

    public NextRoundStartedEventDTO(Object source, UUID gameId, String shapeType, int roundIndex) {
        super(source);
        this.gameId = gameId;
        this.shapeType = shapeType;
        this.roundIndex = roundIndex;
    }

    public static NextRoundStartedEventDTO from(Object source, NextRoundStartedEvent event) {
        return new NextRoundStartedEventDTO(source,
            event.gameId().value(),
            event.shapeType().name(),
            event.roundIndex());
    }

    public UUID gameId() {
        return gameId;
    }

    public String shapeType() {
        return shapeType;
    }

    public int roundIndex() {
        return roundIndex;
    }
}
