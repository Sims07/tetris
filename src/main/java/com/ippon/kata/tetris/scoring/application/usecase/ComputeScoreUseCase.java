package com.ippon.kata.tetris.scoring.application.usecase;

import com.ippon.kata.tetris.scoring.application.domain.ScoreComputedEvent;
import com.ippon.kata.tetris.shared.domain.GameId;

public interface ComputeScoreUseCase {

  ScoreComputedEvent compute(GameId gameId, int nbErasedLines, int level);
}
