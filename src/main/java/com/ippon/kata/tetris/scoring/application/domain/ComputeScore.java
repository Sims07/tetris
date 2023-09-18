package com.ippon.kata.tetris.scoring.application.domain;

import com.ippon.kata.tetris.scoring.application.usecase.ComputeScoreUseCase;
import com.ippon.kata.tetris.shared.domain.GameId;

public class ComputeScore implements ComputeScoreUseCase {
  private final Scores scores;

  public ComputeScore(Scores scores) {
    this.scores = scores;
  }

  @Override
  public ScoreComputedEvent compute(GameId gameId, int nbErasedLines, int level) {
    final Score score = scores.get(gameId);
    Score updatedScore = score.erasedLines(nbErasedLines, level);
    final Score newScore = scores.save(updatedScore);
    return new ScoreComputedEvent(newScore);
  }
}
