package com.ippon.kata.tetris.gaming.domain;

import com.ippon.kata.tetris.shared.domain.GameId;
import com.ippon.kata.tetris.shared.domain.ShapeType;

public record NextRoundStartedEvent(GameId gameId, ShapeType shapeType, int roundIndex) {

}
