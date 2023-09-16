package com.ippon.kata.tetris.gaming.infrastructure.primary.spring;

import com.ippon.kata.tetris.gaming.application.domain.Game;
import com.ippon.kata.tetris.gaming.application.domain.Games;
import com.ippon.kata.tetris.gaming.application.usecase.StartNextRoundUseCase;
import com.ippon.kata.tetris.preparing.infrastructure.secondary.spring.TetrominoGeneratedEventDTO;
import com.ippon.kata.tetris.scoring.infrastructure.secondary.spring.ScoreUpdatedEventDTO;
import com.ippon.kata.tetris.shared.domain.GameId;
import com.ippon.kata.tetris.shared.domain.ShapeType;
import com.ippon.kata.tetris.shared.secondary.spring.model.BoardInitializedEventDTO;
import com.ippon.kata.tetris.shared.secondary.spring.model.TetrominoMovedEventDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class GameListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(GameListener.class);
  private final Games games;
  private final StartNextRoundUseCase nextRoundUseCase;

  public GameListener(Games games, StartNextRoundUseCase nextRoundUseCase) {
    this.games = games;
    this.nextRoundUseCase = nextRoundUseCase;
  }

  @Async
  @EventListener
  public void onApplicationEvent(BoardInitializedEventDTO event) {
    final Game game = games.get(new GameId(event.getGameId()));
    final Game gameSaved =
        games.add(
            new Game(
                game.id(),
                true,
                game.tetrominoGenerated(),
                game.currentRound(),
                game.scoreInitialized(),
                game.waitingTetromino(),
                game.settings(),
                false));
    LOGGER.info("GAMING : receive board initialized {}", gameSaved);
    startNextRound(gameSaved);
  }

  @Async
  @EventListener
  public void onApplicationEvent(TetrominoGeneratedEventDTO event) {
    final Game game = games.get(new GameId(event.getGameId()));
    final Game saved = games.add(game.tetrominoGenerated(ShapeType.valueOf(event.getShape())));
    LOGGER.info("GAMING : receive tetromino generated  {}", saved);
    startNextRound(saved);
  }

  @Async
  @EventListener
  public void onApplicationEvent(ScoreUpdatedEventDTO event) {
    final Game game = games.get(new GameId(event.getGameId()));
    final Game saved = games.add(game.initializeScore());
    LOGGER.info("GAMING : receive score initialized {}", saved);
    startNextRound(saved);
  }

  @Async
  @EventListener
  public void onApplicationEvent(TetrominoMovedEventDTO event) {
    LOGGER.info("GAMING : receive tetromino moved {}", event);
    if (event.outOfScope()) {
      LOGGER.info("You loose");
    } else {
      final Game game = games.get(new GameId(event.gameId()));
      if (game.ended()) {
        LOGGER.info("IGNORE MOVE ENDED BECAUSE game {} is ended", game.id());
      } else if (event.tetrominoFixed()) {
        final Game updatedGame = game.finishRound();
        startNextRound(games.add(updatedGame));
      }
    }
  }

  private void startNextRound(Game game) {
    if (game.nextRoundAvailable()) {
      nextRoundUseCase.start(game.id(), game.waitingTetromino().shape());
    } else {
      LOGGER.debug("GAMING :  could not start next round {}", game);
    }
  }
}
