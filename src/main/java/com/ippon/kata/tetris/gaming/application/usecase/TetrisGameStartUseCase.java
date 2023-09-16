package com.ippon.kata.tetris.gaming.application.usecase;

import com.ippon.kata.tetris.gaming.application.domain.GameStartedEvent;

public interface TetrisGameStartUseCase {

    GameStartedEvent start();
}
