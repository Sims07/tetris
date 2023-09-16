package com.ippon.kata.tetris.executing.application.domain;

import com.ippon.kata.tetris.shared.domain.GameId;
import java.util.UUID;

public class BoardFixture {
    public static Board givenNewBoard() {
        return new Board(new BoardId(new GameId(UUID.randomUUID())));
    }
}
