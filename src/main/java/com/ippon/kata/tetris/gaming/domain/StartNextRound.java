package com.ippon.kata.tetris.gaming.domain;

import com.ippon.kata.tetris.gaming.Games;
import com.ippon.kata.tetris.gaming.usecase.StartNextRoundUseCase;
import com.ippon.kata.tetris.shared.EventPublisher;
import com.ippon.kata.tetris.shared.GameId;
import com.ippon.kata.tetris.shared.ShapeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StartNextRound implements StartNextRoundUseCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(StartNextRound.class);
    private final Games games;
    private final EventPublisher<NextRoundStartedEvent> eventPublisher;

    public StartNextRound(Games games, EventPublisher<NextRoundStartedEvent> eventPublisher) {
        this.games = games;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public NextRoundStartedEvent start(GameId gameId, ShapeType shapeType) {
        final Game game = games.get(gameId);
        final Game saved = games.save(new Game(
            game.id(),
            game.boardInitialized(),
            game.tetrominoGenerated(),
            new Round(RoundStatus.STARTED, game.currentRound().index() + 1),
            game.scoreInitialized(),
            null));
        LOGGER.info("GAMING : Start new round {}", saved);
        return eventPublisher.publish(new NextRoundStartedEvent(gameId, shapeType, saved.currentRound().index()));
    }
}
