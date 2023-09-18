package com.ippon.kata.tetris.gaming.application.domain;

import com.ippon.kata.tetris.gaming.application.usecase.TetrisGameStartUseCase;
import com.ippon.kata.tetris.shared.domain.EventPublisher;
import com.ippon.kata.tetris.shared.domain.Level;
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
    endAllGames();
    final Game startedGame = games.add(Game.newGame());
    LOGGER.info("GAMING : Game started ({})", startedGame.id());
    return eventPublisher.publish(new GameStartedEvent(startedGame.id(), STARTED_LEVEL));
  }

  private void endAllGames() {
    games.list().stream()
        .map(game -> games.update(game.id(), Game::end))
        .forEach(game -> LOGGER.info("{} is ended", game.id()));
  }
}
