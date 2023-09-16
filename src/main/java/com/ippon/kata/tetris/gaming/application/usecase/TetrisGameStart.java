package com.ippon.kata.tetris.gaming.application.usecase;

import static com.ippon.kata.tetris.gaming.application.domain.RoundStatus.IDLE;

import com.ippon.kata.tetris.gaming.application.domain.Game;
import com.ippon.kata.tetris.gaming.application.domain.GameStartedEvent;
import com.ippon.kata.tetris.gaming.application.domain.Games;
import com.ippon.kata.tetris.gaming.application.domain.Level;
import com.ippon.kata.tetris.gaming.application.domain.Round;
import com.ippon.kata.tetris.gaming.application.domain.Settings;
import com.ippon.kata.tetris.shared.domain.GameId;
import com.ippon.kata.tetris.shared.secondary.spring.EventPublisher;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TetrisGameStart implements TetrisGameStartUseCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(TetrisGameStart.class);
    public static final Level STARTED_LEVEL = new Level(1);
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
                null, new Settings(new Level(1)))
        );
        LOGGER.info("GAMING : Game started ({})", startedGame.id());
    return eventPublisher.publish(new GameStartedEvent(startedGame.id(), STARTED_LEVEL));
    }
}
