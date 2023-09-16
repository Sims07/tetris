package com.ippon.kata.tetris.shared;

import com.ippon.kata.tetris.executing.domain.TetraminoStatus;
import com.ippon.kata.tetris.executing.domain.TetrominoMovedEvent;
import java.util.UUID;
import org.springframework.context.ApplicationEvent;

public class TetrominoMovedEventDTO extends ApplicationEvent {

    private final UUID gameId;
    private final ShapeType shapeType;
    private final Direction direction;
    private final boolean tetrominoFixed;
    private final boolean outOfScope;

    public TetrominoMovedEventDTO(Object source,
        UUID gameId,
        ShapeType shapeType,
        Direction direction,
        boolean tetrominoFixed,
        boolean outOfScope) {
        super(source);
        this.gameId = gameId;
        this.shapeType = shapeType;
        this.direction = direction;
        this.tetrominoFixed = tetrominoFixed;
        this.outOfScope = outOfScope;
    }

    public static TetrominoMovedEventDTO from(Object source, TetrominoMovedEvent domainEvent) {
        return new TetrominoMovedEventDTO(
            source,
            domainEvent.gameId().value(),
            domainEvent.tetromino().shape().shapeType(),
            domainEvent.direction(),
            domainEvent.tetromino().status() == TetraminoStatus.FIXED,
            domainEvent.outOfScope());
    }

    public Direction direction() {
        return direction;
    }

    public ShapeType shapeType() {
        return shapeType;
    }

    public boolean tetrominoFixed() {
        return tetrominoFixed;
    }

    public UUID gameId() {
        return gameId;
    }

    @Override
    public String toString() {
        return "TetrominoMovedEventDTO{" +
            "gameId=" + gameId +
            ", shapeType=" + shapeType +
            ", direction=" + direction +
            ", tetrominoFixed=" + tetrominoFixed +
            '}';
    }

    public boolean outOfScope() {
        return outOfScope;
    }
}
