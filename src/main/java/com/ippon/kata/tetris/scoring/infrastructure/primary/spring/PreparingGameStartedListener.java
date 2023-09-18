package com.ippon.kata.tetris.scoring.infrastructure.primary.spring;

import com.ippon.kata.tetris.gaming.infrastructure.secondary.spring.GameStartedEventDTO;
import com.ippon.kata.tetris.scoring.application.domain.ScoreComputedEvent;
import com.ippon.kata.tetris.scoring.application.usecase.ComputeScoreUseCase;
import com.ippon.kata.tetris.scoring.application.usecase.InitializeScoreUseCase;
import com.ippon.kata.tetris.shared.domain.EventPublisher;
import com.ippon.kata.tetris.shared.domain.GameId;
import com.ippon.kata.tetris.shared.secondary.spring.model.LinesErasedEventDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class PreparingGameStartedListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(PreparingGameStartedListener.class);
  private final InitializeScoreUseCase initializeScoreUseCase;
  private final ComputeScoreUseCase computeScoreUseCase;
  private final EventPublisher<ScoreComputedEvent> eventPublisher;

  public PreparingGameStartedListener(
      InitializeScoreUseCase initializeScoreUseCase,
      ComputeScoreUseCase computeScoreUseCase,
      EventPublisher<ScoreComputedEvent> eventPublisher) {
    this.initializeScoreUseCase = initializeScoreUseCase;
    this.computeScoreUseCase = computeScoreUseCase;
    this.eventPublisher = eventPublisher;
  }

  @Async
  @EventListener
  public void onApplicationEvent(GameStartedEventDTO event) {
    LOGGER.info("SCORING : Receive game started to {}", event.getGameId());
    eventPublisher.publish(initializeScoreUseCase.init(new GameId(event.getGameId())));
  }

  @Async
  @EventListener
  public void onApplicationEvent(LinesErasedEventDTO event) {
    LOGGER.info("SCORING : Receive lines erased to {}", event.gameId());
    eventPublisher.publish(
        computeScoreUseCase.compute(
            new GameId(event.gameId()), event.erasedLines().size(), event.level()));
  }
}
