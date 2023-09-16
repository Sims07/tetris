package com.ippon.kata.tetris.executing.usecase;

import com.ippon.kata.tetris.executing.domain.TetrominoMovedEvent;
import com.ippon.kata.tetris.shared.Direction;
import com.ippon.kata.tetris.shared.GameId;
import java.util.Optional;

public interface MoveTetrominoUseCase {

    Optional<TetrominoMovedEvent> move(GameId gameId, Direction direction);
}
