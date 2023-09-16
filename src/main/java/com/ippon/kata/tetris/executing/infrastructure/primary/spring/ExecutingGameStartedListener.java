package com.ippon.kata.tetris.executing.infrastructure.primary.spring;

import com.ippon.kata.tetris.executing.application.domain.BoardId;
import com.ippon.kata.tetris.executing.application.domain.LinesErasedEvent;
import com.ippon.kata.tetris.executing.application.domain.RoundIndex;
import com.ippon.kata.tetris.executing.application.domain.RoundIndexes;
import com.ippon.kata.tetris.executing.application.domain.Speed;
import com.ippon.kata.tetris.executing.application.domain.TetrominoPickedEvent;
import com.ippon.kata.tetris.executing.application.usecase.EraseLineUseCase;
import com.ippon.kata.tetris.executing.application.usecase.FallTetrominoUseCase;
import com.ippon.kata.tetris.executing.application.usecase.InitializeBoardUseCase;
import com.ippon.kata.tetris.executing.application.usecase.PickTetrominoUseCase;
import com.ippon.kata.tetris.executing.infrastructure.secondary.spring.MoveTetrominoCmd;
import com.ippon.kata.tetris.gaming.infrastructure.secondary.spring.GameStartedEventDTO;
import com.ippon.kata.tetris.gaming.infrastructure.secondary.spring.NextRoundStartedEventDTO;
import com.ippon.kata.tetris.shared.domain.Direction;
import com.ippon.kata.tetris.shared.domain.GameId;
import com.ippon.kata.tetris.shared.domain.Level;
import com.ippon.kata.tetris.shared.domain.ShapeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ExecutingGameStartedListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(ExecutingGameStartedListener.class);
  public static final int GAME_SPEED = 1000;
  public static final int MAX_FALLING_STEP = 30;
  private final InitializeBoardUseCase initializeBoardUseCase;
  private final PickTetrominoUseCase pickTetrominoUseCase;
  private final FallTetrominoUseCase fallTetrominoUseCase;
  private final EraseLineUseCase eraseLineUseCase;
  private final ApplicationEventPublisher applicationEventPublisher;
  private final RoundIndexes roundIndexes;

  public ExecutingGameStartedListener(
      InitializeBoardUseCase initializeBoardUseCase,
      PickTetrominoUseCase pickTetrominoUseCase,
      FallTetrominoUseCase fallTetrominoUseCase,
      EraseLineUseCase eraseLineUseCase,
      ApplicationEventPublisher applicationEventPublisher,
      RoundIndexes roundIndexes) {

    this.initializeBoardUseCase = initializeBoardUseCase;
    this.pickTetrominoUseCase = pickTetrominoUseCase;
    this.fallTetrominoUseCase = fallTetrominoUseCase;
    this.eraseLineUseCase = eraseLineUseCase;
    this.applicationEventPublisher = applicationEventPublisher;
    this.roundIndexes = roundIndexes;
  }

  @Async
  @EventListener
  public void onApplicationEvent(GameStartedEventDTO event) {
    LOGGER.info("EXECUTING : Receive game started {}", event.getGameId());
    initializeBoardUseCase.init(new GameId(event.getGameId()));
  }

  @Async
  @EventListener
  public void onApplicationEvent(NextRoundStartedEventDTO event) throws InterruptedException {
    LOGGER.info("EXECUTING : Receive next round started {}", event.gameId());
    final GameId gameId = new GameId(event.gameId());
    eraseCompletedLines(gameId, event.level());
    RoundIndex roundIndex =
        roundIndexes.add(new RoundIndex(event.roundIndex(), new GameId(event.gameId())));

    final TetrominoPickedEvent tetrominoPickedEvent =
        pickTetrominoUseCase.pickTetromino(gameId, ShapeType.valueOf(event.shapeType()));
    fallTetrominoUseCase.fall(new BoardId(gameId), tetrominoPickedEvent.tetromino());
    int fallingStep = 0;
    while (roundNotEnded(event, roundIndex) && fallingStep < MAX_FALLING_STEP) {
      applicationEventPublisher.publishEvent(new MoveTetrominoCmd(this, gameId, Direction.DOWN));
      fallingStep++;
      Thread.sleep(new Speed(new Level(event.level())).value());
    }
  }

  private boolean roundNotEnded(NextRoundStartedEventDTO event, RoundIndex roundIndex) {
    return roundIndex.equals(currentRoundIndex(new GameId(event.gameId())));
  }

  private void eraseCompletedLines(GameId gameId, int level) {
    LinesErasedEvent linesErasedEvent = eraseLineUseCase.eraseCompletedLines(new BoardId(gameId), new Level(level));
    while (!linesErasedEvent.erasedLines().isEmpty()) {
      linesErasedEvent = eraseLineUseCase.eraseCompletedLines(new BoardId(gameId), new Level(level));
    }
  }

  private RoundIndex currentRoundIndex(GameId gameId) {
    return roundIndexes.get(gameId);
  }
}
