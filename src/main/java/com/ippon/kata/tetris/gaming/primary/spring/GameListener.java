package com.ippon.kata.tetris.gaming.primary.spring;

import com.ippon.kata.tetris.gaming.Games;
import com.ippon.kata.tetris.gaming.domain.Game;
import com.ippon.kata.tetris.gaming.domain.GameStatus;
import com.ippon.kata.tetris.gaming.domain.RoundStatus;
import com.ippon.kata.tetris.gaming.domain.Tetromino;
import com.ippon.kata.tetris.gaming.usecase.StartNextRoundUseCase;
import com.ippon.kata.tetris.preparing.secondary.spring.TetrominoGeneratedEventDTO;
import com.ippon.kata.tetris.scoring.secondary.spring.ScoreInitializedEventDTO;
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
        final Game gameSaved = games.save(
            new Game(
                game.id(),
                true,
                game.tetrominoGenerated(),
                game.currentRound(),
                game.scoreInitialized(),
                game.waitingTetromino()
            )
        );
        LOGGER.info("GAMING : receive board initialized {}", gameSaved);
        startNextRound(gameSaved);
    }

    @Async
    @EventListener
    public void onApplicationEvent(TetrominoGeneratedEventDTO event) {
        final Game game = games.get(new GameId(event.getGameId()));
        final Game saved = games.save(
            new Game(
                game.id(),
                game.boardInitialized(),
                true,
                game.currentRound(),
                game.scoreInitialized(),
                new Tetromino(ShapeType.valueOf(event.getShape()))
            )
        );
        LOGGER.info("GAMING : receive tetromino generated  {}", saved);
        startNextRound(saved);
    }

    @Async
    @EventListener
    public void onApplicationEvent(ScoreInitializedEventDTO event) {
        final Game game = games.get(new GameId(event.getGameId()));
        final Game saved = games.save(
            new Game(
                game.id(),
                game.boardInitialized(),
                game.tetrominoGenerated(),
                game.currentRound(),
                true,
                game.waitingTetromino())
        );
        LOGGER.info("GAMING : receive score initialized {}", saved);
        startNextRound(saved);
    }

    @Async
    @EventListener
    public void onApplicationEvent(TetrominoMovedEventDTO event) {
        LOGGER.info("GAMING : receive tetromino moved {}", event);
        if (event.outOfScope()) {
            LOGGER.info("You loose");
        } else if (event.tetrominoFixed()) {
            final Game game = games.get(new GameId(event.gameId()));
            final Game updatedGame = game.finishRound();
            startNextRound(games.save(updatedGame));
        }
    }

    private void startNextRound(Game gameSaved) {
        if (gameSaved.status() == GameStatus.PLAYING
            && gameSaved.currentRound().status() != RoundStatus.STARTED
            && gameSaved.waitingTetromino() != null) {
            nextRoundUseCase.start(gameSaved.id(), gameSaved.waitingTetromino().shape());
        }
    }
}
