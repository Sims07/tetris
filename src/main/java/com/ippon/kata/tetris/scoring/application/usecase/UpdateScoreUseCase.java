package com.ippon.kata.tetris.scoring.application.usecase;

import com.ippon.kata.tetris.scoring.application.domain.ScoreUpdatedEvent;
import com.ippon.kata.tetris.shared.domain.GameId;

public interface UpdateScoreUseCase {

    ScoreUpdatedEvent erasedLines(GameId gameId, int nbErasedLines);
}
