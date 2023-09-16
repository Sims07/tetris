package com.ippon.kata.tetris.gaming.usecase;

import com.ippon.kata.tetris.gaming.domain.NextRoundStartedEvent;
import com.ippon.kata.tetris.shared.domain.GameId;
import com.ippon.kata.tetris.shared.domain.ShapeType;

public interface StartNextRoundUseCase {


    NextRoundStartedEvent start(GameId gameId, ShapeType shapeType);
}
