package com.ippon.kata.tetris.gaming.domain;

import com.ippon.kata.tetris.shared.GameId;
import com.ippon.kata.tetris.shared.ShapeType;

public record NextRoundStartedEvent(GameId gameId, ShapeType shapeType, int roundIndex) {

}
