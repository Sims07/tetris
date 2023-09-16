package com.ippon.kata.tetris.scoring.application.usecase;

import com.ippon.kata.tetris.scoring.application.domain.ScoreInitializedEvent;
import com.ippon.kata.tetris.shared.domain.GameId;

public interface InitializeScoreUseCase {

    ScoreInitializedEvent init(GameId gameId);
}
