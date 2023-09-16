package com.ippon.kata.tetris.executing.infrastructure.primary.spring;

import com.ippon.kata.tetris.executing.application.domain.BoardId;
import com.ippon.kata.tetris.executing.application.domain.RoundIndex;
import com.ippon.kata.tetris.executing.application.domain.RoundIndexes;
import com.ippon.kata.tetris.executing.application.domain.TetrominoPickedEvent;
import com.ippon.kata.tetris.executing.application.usecase.FallTetrominoUseCase;
import com.ippon.kata.tetris.executing.application.usecase.InitializeBoardUseCase;
import com.ippon.kata.tetris.executing.application.usecase.PickTetrominoUseCase;
import com.ippon.kata.tetris.gaming.infrastructure.secondary.spring.GameStartedEventDTO;
import com.ippon.kata.tetris.gaming.infrastructure.secondary.spring.NextRoundStartedEventDTO;
import com.ippon.kata.tetris.shared.domain.Direction;
import com.ippon.kata.tetris.shared.domain.GameId;
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
  private final InitializeBoardUseCase initializeBoardUseCase;
  private final PickTetrominoUseCase pickTetrominoUseCase;
  private final FallTetrominoUseCase fallTetrominoUseCase;
  private final ApplicationEventPublisher applicationEventPublisher;
  private final RoundIndexes roundIndexes;

  public ExecutingGameStartedListener(
      InitializeBoardUseCase initializeBoardUseCase,
      PickTetrominoUseCase pickTetrominoUseCase,
      FallTetrominoUseCase fallTetrominoUseCase,
      ApplicationEventPublisher applicationEventPublisher,
      RoundIndexes roundIndexes) {

    this.initializeBoardUseCase = initializeBoardUseCase;
    this.pickTetrominoUseCase = pickTetrominoUseCase;
    this.fallTetrominoUseCase = fallTetrominoUseCase;
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
    RoundIndex roundIndex = roundIndexes.add(new RoundIndex(event.roundIndex()));
    
    final TetrominoPickedEvent tetrominoPickedEvent =
        pickTetrominoUseCase.pickTetromino(gameId, ShapeType.valueOf(event.shapeType()));
    fallTetrominoUseCase.fall(new BoardId(gameId), tetrominoPickedEvent.tetromino());
    while (roundIndex.equals(currentRoundIndex())) {
      applicationEventPublisher.publishEvent(new MoveTetrominoCmd(this, gameId, Direction.DOWN));
      Thread.sleep(1000);
    }
  }

  private RoundIndex currentRoundIndex() {
    return roundIndexes.get();
  }
}
