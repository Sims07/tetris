package com.ippon.kata.tetris.scoring.application.domain;

import com.ippon.kata.tetris.scoring.application.usecase.UpdateScoreUseCase;
import com.ippon.kata.tetris.shared.domain.GameId;

public class UpdateScore implements UpdateScoreUseCase {
  private final Scores scores;

  public UpdateScore(Scores scores) {
    this.scores = scores;
  }

  @Override
  public ScoreUpdatedEvent erasedLines(GameId gameId, int nbErasedLines, int level) {
    final Score score = scores.get(gameId);
    Score updatedScore = score.erasedLines(nbErasedLines, level);
    final Score newScore = scores.save(updatedScore);
    return new ScoreUpdatedEvent(newScore);
  }
}
