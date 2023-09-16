package com.ippon.kata.tetris.scoring.usecase;

import com.ippon.kata.tetris.scoring.domain.ScoreInitializedEvent;
import com.ippon.kata.tetris.shared.domain.GameId;

public interface InitializeScoreUseCase {

    ScoreInitializedEvent init(GameId gameId);
}
