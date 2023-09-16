package com.ippon.kata.tetris.gaming;

import static com.ippon.kata.tetris.gaming.domain.RoundStatus.IDLE;

import com.ippon.kata.tetris.gaming.domain.Game;
import com.ippon.kata.tetris.gaming.domain.GameStartedEvent;
import com.ippon.kata.tetris.gaming.domain.Round;
import com.ippon.kata.tetris.gaming.usecase.TetrisGameStartUseCase;
import com.ippon.kata.tetris.shared.EventPublisher;
import com.ippon.kata.tetris.shared.GameId;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TetrisGameStart implements TetrisGameStartUseCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(TetrisGameStart.class);
    private final EventPublisher<GameStartedEvent> eventPublisher;
    private final Games games;

    public TetrisGameStart(EventPublisher<GameStartedEvent> eventPublisher, Games games) {
        this.eventPublisher = eventPublisher;
        this.games = games;
    }

    @Override
    public GameStartedEvent start() {
        final Game startedGame = games.save(
            new Game(
                new GameId(UUID.randomUUID()),
                false,
                false,
                new Round(IDLE, 0),
                false,
                null)
        );
        LOGGER.info("GAMING : Game started ({})", startedGame.id());
        return eventPublisher.publish(new GameStartedEvent(startedGame.id()));
    }
}
