package com.ippon.kata.tetris.gaming.application.domain;

import com.ippon.kata.tetris.gaming.application.usecase.StartNextRoundUseCase;
import com.ippon.kata.tetris.shared.domain.GameId;
import com.ippon.kata.tetris.shared.domain.Level;
import com.ippon.kata.tetris.shared.domain.ShapeType;
import com.ippon.kata.tetris.shared.secondary.spring.EventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StartNextRound implements StartNextRoundUseCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(StartNextRound.class);
    public static final Level INITIAL_LEVEL = new Level(1);
    private final Games games;
    private final EventPublisher<NextRoundStartedEvent> eventPublisher;

    public StartNextRound(Games games, EventPublisher<NextRoundStartedEvent> eventPublisher) {
        this.games = games;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public NextRoundStartedEvent start(GameId gameId, ShapeType shapeType) {
        final Game game = games.get(gameId);
        final Game saved = games.add(game.newRound());
        LOGGER.info("GAMING : Start new round {}", saved);
        return eventPublisher.publish(new NextRoundStartedEvent(gameId, shapeType, saved.currentRound().index(), saved.settings().level()));
    }
}
