package com.ippon.kata.tetris.scoring.application.usecase;

import com.ippon.kata.tetris.scoring.application.domain.ScoreUpdatedEvent;
import com.ippon.kata.tetris.shared.domain.GameId;

public interface InitializeScoreUseCase {

    ScoreUpdatedEvent init(GameId gameId);
}
