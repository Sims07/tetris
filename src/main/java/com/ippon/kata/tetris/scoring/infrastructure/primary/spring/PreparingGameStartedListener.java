package com.ippon.kata.tetris.scoring.infrastructure.primary.spring;

import com.ippon.kata.tetris.gaming.infrastructure.secondary.spring.GameStartedEventDTO;
import com.ippon.kata.tetris.scoring.application.domain.ScoreUpdatedEvent;
import com.ippon.kata.tetris.scoring.application.usecase.InitializeScoreUseCase;
import com.ippon.kata.tetris.scoring.application.usecase.UpdateScoreUseCase;
import com.ippon.kata.tetris.shared.domain.GameId;
import com.ippon.kata.tetris.shared.domain.EventPublisher;
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
  private final UpdateScoreUseCase updateScoreUseCase;
  private final EventPublisher<ScoreUpdatedEvent> eventPublisher;

  public PreparingGameStartedListener(
      InitializeScoreUseCase initializeScoreUseCase,
      UpdateScoreUseCase updateScoreUseCase,
      EventPublisher<ScoreUpdatedEvent> eventPublisher) {
    this.initializeScoreUseCase = initializeScoreUseCase;
    this.updateScoreUseCase = updateScoreUseCase;
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
        updateScoreUseCase.erasedLines(
            new GameId(event.gameId()), event.erasedLines().size(), event.level()));
  }
}
