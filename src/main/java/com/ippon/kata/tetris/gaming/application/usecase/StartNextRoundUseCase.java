package com.ippon.kata.tetris.gaming.application.usecase;

import com.ippon.kata.tetris.gaming.application.domain.NextRoundStartedEvent;
import com.ippon.kata.tetris.shared.domain.GameId;
import com.ippon.kata.tetris.shared.domain.ShapeType;

public interface StartNextRoundUseCase {


    NextRoundStartedEvent start(GameId gameId, ShapeType shapeType);
}
