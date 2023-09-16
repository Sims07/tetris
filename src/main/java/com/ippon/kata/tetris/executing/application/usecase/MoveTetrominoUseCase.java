package com.ippon.kata.tetris.executing.application.usecase;

import com.ippon.kata.tetris.executing.application.domain.TetrominoMovedEvent;
import com.ippon.kata.tetris.shared.domain.Direction;
import com.ippon.kata.tetris.shared.domain.GameId;
import java.util.Optional;

public interface MoveTetrominoUseCase {

    Optional<TetrominoMovedEvent> move(GameId gameId, Direction direction);
}
