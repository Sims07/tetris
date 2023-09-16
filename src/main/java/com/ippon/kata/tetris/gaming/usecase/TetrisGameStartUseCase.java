package com.ippon.kata.tetris.gaming.usecase;

import com.ippon.kata.tetris.gaming.domain.GameStartedEvent;

public interface TetrisGameStartUseCase {

    GameStartedEvent start();
}
