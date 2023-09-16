package com.ippon.kata.tetris.executing.application.usecase;

import com.ippon.kata.tetris.executing.application.domain.BoardInitializedEvent;
import com.ippon.kata.tetris.shared.domain.GameId;

public interface InitializeBoardUseCase {

    BoardInitializedEvent init(GameId gameId);
}
