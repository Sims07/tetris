package com.ippon.kata.tetris.scoring.application.usecase;

import com.ippon.kata.tetris.scoring.application.domain.ScoreComputedEvent;
import com.ippon.kata.tetris.shared.domain.GameId;

public interface InitializeScoreUseCase {

  ScoreComputedEvent init(GameId gameId);
}
