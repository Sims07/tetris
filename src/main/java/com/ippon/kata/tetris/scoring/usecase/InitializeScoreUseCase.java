package com.ippon.kata.tetris.scoring.usecase;

import com.ippon.kata.tetris.scoring.domain.ScoreInitializedEvent;
import com.ippon.kata.tetris.shared.GameId;

public interface InitializeScoreUseCase {

    ScoreInitializedEvent init(GameId gameId);
}
