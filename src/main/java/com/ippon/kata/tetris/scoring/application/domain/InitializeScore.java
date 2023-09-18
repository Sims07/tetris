package com.ippon.kata.tetris.scoring.application.domain;

import com.ippon.kata.tetris.scoring.application.usecase.InitializeScoreUseCase;
import com.ippon.kata.tetris.shared.domain.GameId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitializeScore implements InitializeScoreUseCase {

  private static final Logger LOGGER = LoggerFactory.getLogger(InitializeScore.class);
  private final Scores scores;

  public InitializeScore(Scores scores) {
    this.scores = scores;
  }

  @Override
  public ScoreComputedEvent init(GameId gameId) {
    LOGGER.info("SCORING : Command initialize score");
    return new ScoreComputedEvent(scores.save(new Score(gameId, 0)));
  }
}
