package com.ippon.kata.tetris.executing.usecase;

import com.ippon.kata.tetris.executing.domain.BoardInitializedEvent;
import com.ippon.kata.tetris.shared.domain.GameId;

public interface InitializeBoardUseCase {

    BoardInitializedEvent init(GameId gameId);
}
